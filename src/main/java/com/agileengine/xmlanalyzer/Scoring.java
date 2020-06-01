package com.agileengine.xmlanalyzer;

import java.util.Collections;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Scoring algorithm
 * 
 * This could be further optimized defining a score threshold and starting the search at the same level of the original element.
 * @author Emilio Nahuel
 *
 */
public class Scoring {

	private static Logger LOGGER = LoggerFactory.getLogger(Scoring.class);

	
	private static final Double HIERARCHY_MAX_SCORE = 0.15d;
	private static final Double CLASS_LIST_MAX_SCORE = 0.35d;
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
		LOGGER.debug("Candidate element: {} tag type level score: {}", candidateElement.tagName(),score);
		return score;
	}	
	
	
	/**
	 * Return 60% of max score if class list of candidate element contains at least one of the classes
	 * of the original element.
	 * Returns 100% of max score if candidate element contains all of the original element classes.
	 * Retunrs 0 otherwise
	 * @return
	 */
	private Double getClassListScore() {
		Double score = 0d;
		Set<String> originalElementSet = originalElement.classNames();
		Set<String> candidateElementSet = candidateElement.classNames();
		
		if (Collections.disjoint(originalElementSet, candidateElementSet)) {
			score =  0.0d;
		} else if (candidateElementSet.containsAll(originalElementSet)) {
			score =  CLASS_LIST_MAX_SCORE;
		} else if (originalElementSet.stream().anyMatch(candidateElementSet::contains)) {
			score = 0.6d * CLASS_LIST_MAX_SCORE;
		}
		LOGGER.debug("Candidate element: {} Classlist level score: {}", candidateElement.tagName(),score);
		return score;
		
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
		LOGGER.debug("Original partens: {} -- Candidate parents: {}", origParentSize, candidateParentSize);
		//original_element.parents().stream().map(Element::tagName).forEach(LOGGER::info);
		Integer difference = Math.abs(origParentSize - candidateParentSize);
		Double score = HIERARCHY_MAX_SCORE * Math.exp(-difference);
		
		LOGGER.debug("Candidate element: {} Hierarchy level score: {}", candidateElement.tagName(),score);		
		
		return score;
	}
	
	
	/**
	 * TODO: implement some sort of text diference scoring algorithm
	 * If elements have same inner text, returns full score. Returns 0 otherwise
	 * @return
	 */
	private Double getInnerTextScore() {
		Boolean sameText = originalElement.ownText().equalsIgnoreCase(candidateElement.ownText());
		
		Double score = 0d;
		
		if (sameText) {
			score = INNER_TEXT_MAX_SCORE;
		}
		
		LOGGER.debug("Candidate element: {} Inner text level score: {}", candidateElement.tagName(),score);
		
		return score;
	}

}
