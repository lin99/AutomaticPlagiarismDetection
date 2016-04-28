package controller;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

import grammars.java.JavaLexer;
import grammars.java.JavaParser;
import logic.languageSettings.JavaLanguageSettings;
import logic.languageSettings.LanguageAnalyzer;
import logic.languageSettings.LanguageSettings;
import logic.languageSettings.PythonLanguageSettings;
import logic.languageSettings.SpecificLanguage;
import logic.stringMetrics.JaroWinkler;
import logic.stringMetrics.LevenshteinDistance;
import logic.stringMetrics.LongestCommonSubsequence;
import logic.stringMetrics.StringMetric;
import model.Model;
import view.MainFrame;

public class App {

	public static Model model;
	public static MainFrame view;
	public static String dir = "../sourceCodes";
	public static StringMetric metric = new LevenshteinDistance();
	public static StringMetric metrics[] = { new JaroWinkler(), new LevenshteinDistance(),
			new LongestCommonSubsequence() };

	public static final int NOPARAMETER = 0;
	public static final int NOPARAMETERKMEANS = 1;
	public static final int SINGLELINK = 2;
	public static final int KMEANS = 3;

	public static final String[] algorithmNames = { "No parameter clustering (Hierachical Based)",
			"No parameter clustering (KMeans Based)", "Single link clustering", "KMeans Clustering" };
	
	public static LanguageSettings availableLanguages[] = { 
			new JavaLanguageSettings(),
			new PythonLanguageSettings()
	};

	public static int CLUSTERING_ALGORITHM = NOPARAMETERKMEANS;

	static void initModel() throws IOException {
		model = new Model(dir);
	}

	public static void selectClusteringAlgorithmId(int id) {
		App.CLUSTERING_ALGORITHM = id;
		System.out.println("Setting " + CLUSTERING_ALGORITHM + " " + getCurrentAlgorithmName());
	}

	static void initView() {
		view = new MainFrame();
		view.setVisible(true);
		// view.setSize(600,600);
	}

	static void init() throws IOException {
		initModel();
		initView();
	}

	public static void main(String[] args) throws IOException {
		init();
	}

	public static void setSourceCode(int idx) {
		model.initIfNeeded();
		view.setTitle(App.dir + "  -  " + getSourceCodeName(idx));
		view.setSourceCode(model.getSourceCode(idx));

	}

	public static double getRadius() {
		return model.getRadius();
	}

	public static int[] getClusters() {
		model.initIfNeeded();
		return model.getClusters();
	}

	public static StringMetric getMetric() {
		return metric;
	}

	public static double[][] getPoints() {
		model.initIfNeeded();
		return model.getPoints();
	}

	public static void setClusters(int clusters) {
		try {
			model.setNumberOfClusters(clusters);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void modelChanged() {
		view.getData();
		view.repaint();
	}

	public static void computeModel() {
		try {
			model.computeModel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getSourceCode(int idx) {
		model.initIfNeeded();
		return model.getSourceCode(idx);
	}

	public static void repaintView() {
		// TODO Auto-generated method stub
		view.repaint();
	}

	public static void selectMetric(int selectedIndex) {
		metric = metrics[selectedIndex];
	}
	
	public static boolean checkDirectoryAndShowError(){
		if( validDirectory() == false ){
			App.showSelectedLanguageError();
			return false;
		}
		return true;
	}

	public static String[][] getReport(int p) {
		// TODO Auto-generated method stub
		model.initIfNeeded();
		return model.getReport(((double) p) / 100.0);
	}

	public static String[] getReportColumns() {
		return new String[] { "Code A", "Code B", "Percentage" };
	}

	public static String[][] getReportByCluster(Integer cluster) {
		model.initIfNeeded();
		return model.getReportByCluster(cluster);
	}

	public static String[] getClusterReportColumns() {
		// TODO Auto-generated method stub
		return new String[] { "Code A", "Code B", "Cluster", "Percentage" };
	}

	public static String[][] clusterReport() {
		model.initIfNeeded();
		return model.clusterReport();
	}

	public static void setSourceCodesDirectory(String selected) {
		dir = selected;
		model.setSourceFolder(selected);
		// view.setTitle(getSoureCodeDirectory());
		view.repaint();
	}

	public static String getSoureCodeDirectory() {
		return dir;
	}

	public static String getCurrentAlgorithmName() {
		return algorithmNames[CLUSTERING_ALGORITHM];
	}

	public static int getNumberOfClusters() {
		return model.getNumberOfClusters();
	}

	public static String getSourceCodeName(int idx) {
		return model.getSourceCodeName(idx);
	}

	public static Dimension getDrawingDimension() {
		return view.getDrawingDimension();
	}

	public static void saveModel(String dir) {
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(dir);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(model);
			oos.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error saving");
		}
	}

	public static void loadModel(String dir) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(dir);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Model result = (Model) ois.readObject();
			model = result;
			ois.close();
			view.getDataWithoutCompute();
			System.out.println(model.getClusters());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static int getCountOfDistances() {
		return model.getCountOfDistances();
	}

	public static void setProgress(int n) {
		view.setProgress(n);
	}
	
	

	private static String getFileExtension(File file) {
	    String name = file.getName();
	    try {
	        return name.substring(name.lastIndexOf(".") + 1);
	    } catch (Exception e) {
	        return "";
	    }
	}
	
	public static boolean validDirectory(){
		return detectFileExtension(dir) != null;
	}
	
	public static String getHTMLOutput(){
		return model.getHTMLOutput();
	}
	
	public static LanguageSettings detectFileExtension( String directory ){
		File folder = new File(directory);
		HashMap<String, Integer> hashMap = new HashMap<>();
		for(File f : folder.listFiles()){
			String ext = getFileExtension(f);
			if( hashMap.containsKey(ext) == false )
				hashMap.put(ext, 1);
			else
				hashMap.put( ext, hashMap.get(ext) + 1 );
		}
		
		LanguageSettings choosen = null;
		for( LanguageSettings language: availableLanguages ){
			Integer ctr = hashMap.get(language.getFileNameExtension());
			if( ctr != null && ( choosen == null || ctr > hashMap.get(choosen.getFileNameExtension()) ) )
				choosen = language;
		}
		
		return choosen;
	}

	public static void showSelectedLanguage(LanguageSettings selectedLanguage) {
		view.setTitle(selectedLanguage.getLanguageName() + " selected.");
	}

	public static void showSelectedLanguageError() {
		ArrayList<String> langs  = new ArrayList<>();
		for(LanguageSettings settings : availableLanguages)
			langs.add(settings.getLanguageName());
		
		JOptionPane.showMessageDialog(null, "The selected directory does not contain "
				+ "any file of the following languages: " + langs);

	}
}
