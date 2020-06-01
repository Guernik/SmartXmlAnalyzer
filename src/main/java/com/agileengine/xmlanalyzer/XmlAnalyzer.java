package com.agileengine.xmlanalyzer;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


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

	public String obtainTargetXPath() throws XmlAnalyzerException {
		
		// parse original file and get attrs from id
		Optional<Element> originalElementOpt = findElementById(parsed.getOriginalFilePath(), parsed.getOriginalId());		
		Element originalElement = originalElementOpt.orElseThrow(() -> new OriginalElementNotFoundException());
		
		Optional<Elements> targetFileElementsOpt = getAllElements(parsed.getDiffFilePath());
		Elements targetFileElements = targetFileElementsOpt.orElseThrow(() -> new HtmlFileParsingException());
		
		
		Scoring scoring = new Scoring(originalElement);
		
		targetFileElements.stream().map( scoring::getElementScore);		
		
		
		

		// parse diff file and get all elements
		
		//calculate score for each element
		
		return null;
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
