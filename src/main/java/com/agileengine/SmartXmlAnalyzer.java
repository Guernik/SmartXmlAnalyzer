package com.agileengine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agileengine.args.ParsedArgs;
import com.agileengine.exceptions.ArgumentsException;
import com.agileengine.exceptions.XmlAnalyzerException;
import com.agileengine.xmlanalyzer.XmlAnalyzer;

public class SmartXmlAnalyzer {
	
	 private static Logger LOGGER = LoggerFactory.getLogger(SmartXmlAnalyzer.class);

	public static void main(String[] args) {		
		/*
		String[] test_args1 = {"make-everything-ok-button", "./samples/sample-0-origin.html", "./samples/sample-1-evil-gemini.html"};
		String[] test_args2 = {"make-everything-ok-button", "./samples/sample-0-origin.html", "./samples/sample-2-container-and-clone.html"};
		String[] test_args3 = {"make-everything-ok-button", "./samples/sample-0-origin.html", "./samples/sample-3-the-escape.html"};
		String[] test_args4 = {"make-everything-ok-button", "./samples/sample-0-origin.html", "./samples/sample-4-the-mash.html"};		
		 */
		
		new SmartXmlAnalyzer().runApp(args);
		
		
		
		
		
		
	}
	public static void printUsage() {
		System.out.println("java -jar xmlanalyzer.jar original_id original_file diff_file");
		System.out.println("Example: xmlanalyzer.jar make-everything-ok-button ./samples/sample-0-origin.html ./samples/sample-1-evil-gemini.html" );
	}
	
	

	
	private void runApp(String[] args) {
		LOGGER.info("SamrtXmlAnalyzer starting up");				 
		 try {
			ParsedArgs parsed = ParsedArgs.parseArgs(args);
			
			XmlAnalyzer xml_analyzer = new XmlAnalyzer(parsed);
			
			String target_xpath = xml_analyzer.obtainTargetPath();
			
			LOGGER.info("Target path obtained: {}", target_xpath);
			
		
		 
		} catch (ArgumentsException e) {
			LOGGER.error("Invalid Arguments");
			printUsage();
			e.printStackTrace();
		} catch (XmlAnalyzerException e) {

			e.printStackTrace();
		}
		
		
	}	

}
