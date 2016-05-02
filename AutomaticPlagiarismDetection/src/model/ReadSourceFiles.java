package model;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
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
	private List<String> users;
	private List<String> submissions;
	private String contest;
	
	public ReadSourceFiles(String directory, LanguageSettings settings) {
		this.directory = directory;
		this.filesCache = new ArrayList<>();
		namesCahe = new ArrayList<>();
		this.settings = settings;
		this.users = new ArrayList<>();
		this.submissions = new ArrayList<>();
		
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
			//System.out.println((idx++) + " " + f.getName() + " " + sourceCodes.get(sourceCodes.size()-1).length() );
			users.add(getUser(f.getName()));
			//System.out.println("here");
			submissions.add(getSubmission(f.getName()));
			contest = getContest(f.getName());
			printUsersToFile(users);
			printUsersAndSubsToFile(users, submissions);
			printContestToFile(contest);
			
			
		}
		return sourceCodes;
	}
	
	
	public String getFileName(int idx){
		return namesCahe.get(idx);
	}
	
	public String getSourceCode(int idx){
		return filesCache.get(idx);
	}
	
	public String getUser(String fileName){
		String[] name;
		System.out.println(fileName);
		name = fileName.split("\\.");
		return name[2];
	}
	
	public String getSubmission(String fileName){
		String [] submission;
		submission = fileName.split("\\.");                                                               
		//System.out.print(submission[1]);
		return submission[1];
	}
	
	public String getContest(String fileName){
		String[] contest;
		contest = fileName.split("\\.");
		return contest[0];
	}
	
	private static void printUsersToFile( List<String> users) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter("users.txt", "UTF-8");
		int id = 0;
		for(String u: users){
				writer.print(id);
				writer.print(' ');
				writer.print(u);
			id++;
			writer.print('\n');
		}
		
		writer.close();	
	}
	
	private static void printUsersAndSubsToFile( List<String> users, List<String> submissions) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter("usersAndSubs.txt", "UTF-8");
		int id = 0;
		for(int k = 0; k < users.size(); k++){ 
			
			writer.print(id);
			writer.print(' ');
			writer.print(users.get(k));
			writer.print(' ');
			writer.print(submissions.get(k));
			writer.print('\n');
			id++;
		}
		
		writer.close();	
	}
	
	private static void printContestToFile(String contest) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter("contest.txt", "UTF-8");
		writer.print(contest);
		writer.close();
	}


}
