

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadSourceFiles {
	public String directory;

	public ReadSourceFiles(String directory) {
		this.directory = directory;
	}
	
	public List<String> getStrings() throws IOException{
		ArrayList<String> sourceCodes = new ArrayList<>();
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
		for(File f : listOfFiles){
			SourceCodeToStringBuilder builder = new SourceCodeToStringBuilder(f.getAbsolutePath());
			sourceCodes.add( builder.readSourceCode() );
		}
		return sourceCodes;
	}
	
//	public static void main(String[] args) throws IOException {
//		ReadSourceFiles reader = new ReadSourceFiles("../sourceCodes");
//		List<String> list = reader.getStrings();
//		System.out.println(list.size());
//		for(String s : list){
//			System.out.println(s.length());
//		}
//	}
}
