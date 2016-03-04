

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadSourceFiles implements Serializable {
	public String directory;
	private List<String> filesCache;
	private List<String> namesCahe;
	
	public ReadSourceFiles(String directory) {
		this.directory = directory;
		this.filesCache = new ArrayList<>();
		namesCahe = new ArrayList<>();
	}
	
	public List<String> getStrings() throws IOException{
		ArrayList<String> sourceCodes = new ArrayList<>();
		File folder = new File(directory);
		int idx = 0;
		
		for(File f : folder.listFiles()){
			if( f.getName().endsWith(".java") == false ) continue;
			Scanner sc = new Scanner(f);
			filesCache.add(sc.useDelimiter("\\Z").next());
			SourceCodeToStringBuilder builder = new SourceCodeToStringBuilder(f.getAbsolutePath());
			sourceCodes.add( builder.readSourceCode() );
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
