package com.agileengine.xmlanalyzer;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agileengine.args.ParsedArgs;
import com.agileengine.exceptions.OriginalElementNotFoundException;

public class XmlAnalyzer {
	private static final Double HIERARCHY_MAX_SCORE = 0.35d;
	private static final Double CLASS_LIST_MAX_SCORE = 0.15d;
	private static final Double TAG_TYPE_MAX_SCORE = 0.35d;
	private static final Double INNER_TEXT_MAX_SCORE = 0.15d;
	

	private static Logger LOGGER = LoggerFactory.getLogger(XmlAnalyzer.class);

	ParsedArgs parsed;
	
	public XmlAnalyzer(ParsedArgs parsed) {
		this.parsed = parsed;
	}

	public String obtainTargetXPath() throws OriginalElementNotFoundException {
		
		// parse original file and get attrs from id
		Optional<Element> originalElementOpt = findElementById(parsed.getOriginalFilePath(), parsed.getOriginalId());
		
		Element originalElement = originalElementOpt.orElseThrow(() -> new OriginalElementNotFoundException());
		
		getHierarchyLevelScore(originalElement, originalElement);
		

		// parse diff file and get all elements
		
		//calculate score for each element
		
		return null;
	}
	
	
	public Integer getIdScore(Element element) {
		return null;
	}
	public Integer getTagTypeScore(Element element) {
		return null;
	}	
	public Integer getClassListScore(Element element) {
		return null;
	}
	
	/**
	 * Get a score with the following equation
	 *  score = 0.35 * e^(-x)
	 *  where x is the difference in levels between the candidate element and the 
	 *  original element. (Difference in parent nodes)
	 *  
	 *  Default Max score for this function = 0.35
	 * @param element
	 * @return
	 */
	public Float getHierarchyLevelScore(Element original_element, Element candidate_element) {
		
		Integer origParentSize = original_element.parents().size();
		Integer candidateParentSize = candidate_element.parents().size();
		LOGGER.info("Original partens: {} -- Candidate parents: {}", origParentSize, candidateParentSize);
		//original_element.parents().stream().map(Element::tagName).forEach(LOGGER::info);
		Integer difference = Math.abs(origParentSize - candidateParentSize);
		Double score = HIERARCHY_MAX_SCORE * Math.exp(-difference);
		
		
		
		
		
		return null;
	}
	public Integer getInnerTextScore(Element element) {
		return null;
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
