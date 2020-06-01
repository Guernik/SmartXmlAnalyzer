package com.agileengine.args;

import java.io.File;

import com.agileengine.exceptions.ArgumentCantException;
import com.agileengine.exceptions.ArgumentsException;
import com.agileengine.exceptions.EmptyIdInvalidArgumentException;
import com.agileengine.exceptions.InvalidFileException;

public class ParsedArgs {
	
	private final String originalId;
	private final File originalFilePath;
	private final File diffFilePath;




	public static ParsedArgs parseArgs(String[] args) throws ArgumentsException {
		String original_id = args[0];
		
		// Validations
		if (args.length  != 3) {
			throw new ArgumentCantException();
		}
		File original_file = new File(args[1]);
		File diff_file = new File(args[2]);
		

		if (original_id.isEmpty()) {
			throw new EmptyIdInvalidArgumentException();
		}		
		
		if (! original_file.isFile() ) {
			throw new InvalidFileException();
		}
		if (! diff_file.isFile()) {
			throw new InvalidFileException();
		}
		
		return new ParsedArgs(original_id, original_file, diff_file);
	}
	
	private ParsedArgs(String originalId, File originalFilePath, File diffFilePath) {		
		this.originalId = originalId;
		this.originalFilePath = originalFilePath;
		this.diffFilePath = diffFilePath;
	}	
	
	public String getOriginalId() {
		return originalId;
	}




	public File getOriginalFilePath() {
		return originalFilePath;
	}




	public File getDiffFilePath() {
		return diffFilePath;
	}








}
