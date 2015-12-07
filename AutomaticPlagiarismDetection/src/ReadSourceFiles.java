

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadSourceFiles {
	public String directory;
	private File[] listOfFiles;
	
	public ReadSourceFiles(String directory) {
		this.directory = directory;
	}
	
	public List<String> getStrings() throws IOException{
		ArrayList<String> sourceCodes = new ArrayList<>();
		File folder = new File(directory);
		listOfFiles = folder.listFiles();
		int idx = 0;
		for(File f : listOfFiles){
			SourceCodeToStringBuilder builder = new SourceCodeToStringBuilder(f.getAbsolutePath());
			sourceCodes.add( builder.readSourceCode() );
			System.out.println((idx++) + " " + f.getName() + " " + sourceCodes.get(sourceCodes.size()-1).length() );
		}
		return sourceCodes;
	}
	
	public File getFile(int idx){
		return listOfFiles[idx];
	}
}
