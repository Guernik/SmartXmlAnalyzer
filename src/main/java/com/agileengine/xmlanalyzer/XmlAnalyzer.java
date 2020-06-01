package com.agileengine.xmlanalyzer;

import org.jsoup.nodes.Element;

import com.agileengine.args.ParsedArgs;

public class XmlAnalyzer {

	ParsedArgs parsed;
	
	public XmlAnalyzer(ParsedArgs parsed) {
		this.parsed = parsed;
	}

	public String obtainTargetXPath() {
		
		
		// parse original file and get attrs
		

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
	public Integer getHierarchyLevelScore(Element element) {
		return null;
	}
	public Integer getInnerTextScore(Element element) {
		return null;
	}
	
	
	
	
	
	

}
