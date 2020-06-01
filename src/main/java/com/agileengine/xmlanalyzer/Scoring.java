package com.agileengine.xmlanalyzer;

import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scoring {

	private static Logger LOGGER = LoggerFactory.getLogger(Scoring.class);

	
	private static final Double HIERARCHY_MAX_SCORE = 0.35d;
	private static final Double CLASS_LIST_MAX_SCORE = 0.15d;
	private static final Double TAG_TYPE_MAX_SCORE = 0.35d;
	private static final Double INNER_TEXT_MAX_SCORE = 0.15d;


	private final Element originalElement;
	private  Element candidateElement;
	
	
	public Scoring(Element originalElement) {
		this.originalElement = originalElement;
	}
	
	
	public Double getElementScore(Element element) {
		this.candidateElement = element;
		
		Double score = getTagTypeScore() +
						getClassListScore() +
						getHierarchyLevelScore() +
						getInnerTextScore();
		
		return score;
						
	}
	
	
	
	
	
	
	/**
	 * If the tags of the elements are of same type get full score. Get 0 otherwise.
	 * @return
	 */
	private Double getTagTypeScore() {
		Boolean sameTag = originalElement.tagName().equalsIgnoreCase(candidateElement.tagName());
		
		Double score = 0d;
		
		if (sameTag) {
			score = TAG_TYPE_MAX_SCORE;
		}
		
		return score;
	}	
	private Double getClassListScore() {
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
	private Double getHierarchyLevelScore() {
		
		Integer origParentSize = originalElement.parents().size();
		Integer candidateParentSize = candidateElement.parents().size();
		LOGGER.info("Original partens: {} -- Candidate parents: {}", origParentSize, candidateParentSize);
		//original_element.parents().stream().map(Element::tagName).forEach(LOGGER::info);
		Integer difference = Math.abs(origParentSize - candidateParentSize);
		Double score = HIERARCHY_MAX_SCORE * Math.exp(-difference);
		
		LOGGER.debug("Candidate element: {} Hierarchy level score: {}", candidateElement.tagName(),score);		
		
		return score;
	}
	
	
	public Integer getInnerTextScore() {
		return null;
	}

}
