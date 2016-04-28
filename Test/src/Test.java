
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFrame;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

public class Test {
	
	
	
	

	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		JFXPanel jfxPanel = new JFXPanel(); // Scrollable JCompenent
		String content = new String(Files.readAllBytes(Paths.get("E:\\UN\\IX\\Lenguajes de programacion\\AutomaticPlagiarismDetection\\Test\\temp-plot.html")), "UTF-8");

		Platform.runLater( () -> { // FX components need to be managed by JavaFX
		   WebView webView = new WebView();
		   
		   webView.getEngine().loadContent( content );
		   jfxPanel.setScene( new Scene( webView ) );
		});
		
		JFrame frame = new JFrame();
		frame.add(jfxPanel);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
			
	}

}
