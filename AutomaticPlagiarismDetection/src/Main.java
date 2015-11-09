
import java.io.IOException;




public class Main {

	public static void main(String[] args) throws IOException {
		long time = System.currentTimeMillis();		
		SourceCodeToStringBuilder builder = new SourceCodeToStringBuilder("../Code.java"); //Reads data
		String st = builder.readSourceCode(); //Builds String
		System.out.println(st.length()); 		//Prints results	
		time = System.currentTimeMillis()-time;
		System.out.println("Time: " + time + " ms.");		
	}

}
