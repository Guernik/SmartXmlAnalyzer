package com.agileengine.xmlanalyzer;

import org.jsoup.nodes.Element;

public final class ScoredElement {
	
	
	private final Element element;
	
	private final Double score;

	public ScoredElement(Element element, Double score) {
		super();
		this.element = element;
		this.score = score;		
	}

	public Element getElement() {
		return element;
	}

	public Double getScore() {
		return score;
	}
	
	
	

}
