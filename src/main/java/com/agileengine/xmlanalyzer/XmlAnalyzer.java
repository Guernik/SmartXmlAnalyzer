package com.agileengine.xmlanalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agileengine.args.ParsedArgs;
import com.agileengine.exceptions.HtmlFileParsingException;
import com.agileengine.exceptions.OriginalElementNotFoundException;
import com.agileengine.exceptions.XmlAnalyzerException;

public class XmlAnalyzer {
	
	

	private static Logger LOGGER = LoggerFactory.getLogger(XmlAnalyzer.class);

	ParsedArgs parsed;
	
	public XmlAnalyzer(ParsedArgs parsed) {
		this.parsed = parsed;
	}

	public String obtainTargetPath() throws XmlAnalyzerException {
		
		// parse original file and get attrs from id
		Optional<Element> originalElementOpt = findElementById(parsed.getOriginalFilePath(), parsed.getOriginalId());		
		Element originalElement = originalElementOpt.orElseThrow(() -> new OriginalElementNotFoundException());
		
		// get candidate elements or throw
		Optional<Elements> targetFileElementsOpt = getAllElements(parsed.getDiffFilePath());
		Elements targetFileElements = targetFileElementsOpt.orElseThrow(() -> new HtmlFileParsingException());
		
		
		Scoring scoring = new Scoring(originalElement);
		
		List<ScoredElement> list = targetFileElements.stream()
							.map( e -> new ScoredElement(e, scoring.getElementScore(e)))
							.collect(Collectors.toList());
		
		
		Collections.sort(list, (a,b) -> b.getScore().compareTo(a.getScore()));
		
		ScoredElement winner = list.get(0);
		
		scoring.getElementScore(list.get(1).getElement());
		scoring.getElementScore(winner.getElement());
		
		
		
		LOGGER.info("------ Element scoring table: ------");
		list.forEach(e -> {
			LOGGER.info("Element tag: {} text:{} classes: {} -- Score: {}", e.getElement().tagName(), e.getElement().text(), e.getElement().className(), e.getScore());
		});
		
		
		LOGGER.info("Winner tag: {} text:{} classes: {} -- Score: {}",
				winner.getElement().tagName(),
				winner.getElement().text(),
				winner.getElement().className(),
				winner.getScore());
		
		
		
		String path = getHtmlPath(winner.getElement());
		LOGGER.info("Path: {}", path);
		
		return path;
	}
	
	
	
	
	
	
	private String getHtmlPath(Element element ) {
		List<String> nodes_list = new ArrayList<>();
		traversePath(element,nodes_list);		
		
		Collections.reverse(nodes_list);
		return nodes_list.stream().collect(Collectors.joining(">"));		
		
	}

	private void traversePath(Element element, List<String> nodes_list) {	
		Element parent = element.parent();
		
		if (parent != null && !parent.tagName().equals("#root")) {			
			String tag_name = element.tagName();
			Elements sibs = element.siblingElements();
			// remove all siblings of different tag name
			sibs.stream().filter(s -> ! s.tagName().equalsIgnoreCase(tag_name)).forEach(e -> e.remove());
			sibs = element.siblingElements();
			Integer sib_index = element.elementSiblingIndex();
			
			if (sib_index == 0 && sibs.size() == 0) {
				nodes_list.add(tag_name);
			} else {
				sib_index++;
				nodes_list.add(tag_name + "[" + sib_index + "]");
			}
			
			traversePath(parent,nodes_list);
		} else {
			nodes_list.add(element.tagName());
		}
		
	}

	private Optional<Elements> getAllElements(File html_file) {
		
		try {
			Elements elements = Jsoup.parse(
					html_file,
			        "utf8",
			        html_file.getAbsolutePath()).getAllElements();
			return Optional.of(elements);
		} catch (IOException e) {
			LOGGER.error("Error reading [{}] file", html_file.getAbsolutePath(), e);
			return Optional.empty();
			
		}
		
		
		
	}
	
	// Copied from provided JsoupFindByIdSnippet.java
	private Optional<Element> findElementById(File htmlFile, String targetElementId) {
        try {
           Document doc = Jsoup.parse(
                    htmlFile,
                    "utf8",
                    htmlFile.getAbsolutePath());            

            return Optional.of(doc.getElementById(targetElementId));
            

        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            return Optional.empty();
        }
    }
	
	
	
	
	
	

}
