package com.agileengine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agileengine.args.ParsedArgs;
import com.agileengine.exceptions.ArgumentsException;
import com.agileengine.xmlanalyzer.XmlAnalyzer;

public class SmartXmlAnalyzer {
	
	 private static Logger LOGGER = LoggerFactory.getLogger(SmartXmlAnalyzer.class);

	public static void main(String[] args) {		
		String[] test_args = {"make-everything-ok-button", "./samples/sample-0-origin.html", "./samples/sample-1-evil-gemini.html"};
		
//		new SmartXmlAnalyzer().runApp(args);
		
		new SmartXmlAnalyzer().runApp(test_args);
		
		
		
		
		
		
	}
	
	

	
	private void runApp(String[] args) {
		LOGGER.info("SamrtXmlAnalyzer starting up");				 
		 try {
			ParsedArgs parsed = ParsedArgs.parseArgs(args);
			
			
			XmlAnalyzer xml_analyzer = new XmlAnalyzer(parsed);
			
			String target_xpath = xml_analyzer.obtainTargetXPath();
			
			LOGGER.info("Target xpath obtained: {}", target_xpath);
			
		} catch (ArgumentsException e) {
			LOGGER.error("Invalid Arguments");			
			e.printStackTrace();
		}
		
		
	}	

}
