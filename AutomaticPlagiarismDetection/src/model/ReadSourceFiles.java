package model;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.antlr.v4.runtime.ANTLRFileStream;

import logic.languageSettings.LanguageAnalyzer;
import logic.languageSettings.LanguageSettings;

public class ReadSourceFiles implements Serializable {
	public String directory;
	private List<String> filesCache;
	private List<String> namesCahe;
	private LanguageSettings settings;
	
	public ReadSourceFiles(String directory, LanguageSettings settings) {
		this.directory = directory;
		this.filesCache = new ArrayList<>();
		namesCahe = new ArrayList<>();
		this.settings = settings;
	}
	
	public List<String> getStrings() throws IOException{
		ArrayList<String> sourceCodes = new ArrayList<>();
		File folder = new File(directory);
		int idx = 0;
		
		for(File f : folder.listFiles()){
			LanguageAnalyzer analyzer = settings.buildLanguageAnalyzer(new ANTLRFileStream(f.getAbsolutePath()));
			if( f.getName().endsWith( "."+settings.getFileNameExtension() ) == false ) continue;
			Scanner sc = new Scanner(f);
			filesCache.add(sc.useDelimiter("\\Z").next());
			SourceCodeToStringBuilder builder = new SourceCodeToStringBuilder();
			String source = builder.readSourceCode( analyzer );
			sourceCodes.add( source );
			namesCahe.add( f.getName() );
			System.out.println((idx++) + " " + f.getName() + " " + sourceCodes.get(sourceCodes.size()-1).length() );
			
		}
		return sourceCodes;
	}
	
	
	public String getFileName(int idx){
		return namesCahe.get(idx);
	}
	
	public String getSourceCode(int idx){
		return filesCache.get(idx);
	}

}
