package model;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import controller.App;
import logic.DistanceMatrixBuilder;
import logic.clustering.ClusteringAlgorithm;
import logic.clustering.KMeans;
import logic.clustering.NoParametersClusteringHierachical;
import logic.clustering.NoParametersClusteringKMeans;
import logic.clustering.SingleLinkClustering;
import logic.languageSettings.LanguageSettings;
import mdsj.MDSJ;

public class Model implements Serializable{
	private ReadSourceFiles reader;
	private List<String> strings;
	private double radius;
	private int[] clusters;
	private static int numberOfClusters;
	private double output[][];
	String dirName;
	ClusteringAlgorithm algorithm;
	double distanceMatrix[][];
	private String htmlOutput;
	
	public Model(String dir) throws IOException {
		dirName = dir;
		numberOfClusters = 5;
		//algorithm = new KMeans(numberOfClusters, output);
		//algorithm = new NoParametersClustering(0.0003);
	//	computeModel();
	}
	
	public String getHTMLOutput(){
		return htmlOutput;
	}

	
	public void computeModel() throws IOException{
		long time = System.currentTimeMillis();
		LanguageSettings selectedLanguage = App.detectFileExtension( dirName );
		if( selectedLanguage == null )
			App.showSelectedLanguageError();
		else
			App.showSelectedLanguage(selectedLanguage);
		
		reader = new ReadSourceFiles(dirName,selectedLanguage);
		strings = reader.getStrings();
		time = System.currentTimeMillis()-time;
		System.out.println("Time elapsed reading codes: " + time + " ms.");
		radius = 8.0;
		//ClusteringAlgorithm algorithm = new SingleLinkClustering( numberOfClusters ); //Clustering Algorithm object
		
		//Builds the matrix distance according to a specific metric
				//In this case the object stored in metrics[0]
				DistanceMatrixBuilder builder = new DistanceMatrixBuilder(
						App.getMetric(), 
						strings
					);
		
		//Actually builds the matrix
		distanceMatrix = builder.buildMatrix();
		
		printMatrixToFile(distanceMatrix);
		
		//HERE CALLS PYTHON CODE... and sets up the html directory
		
//		htmlOutput = "E:/UN/IX/Lenguajes de programacion/AutomaticPlagiarismDetection/AutomaticPlagiarismDetection/temp-plot.html";
		htmlOutput = System.getProperty("user.dir").replace("\\", "/") + "/temp-plot.html";
		
		
//		Dimension drawingDimension = App.getDrawingDimension();
//		System.out.println(drawingDimension);
//		output = scaleDataTo2D( distanceMatrix, 
//				10.0, drawingDimension.getWidth()*0.9  ); // Creates 2 dimensional representation of the documents
//		 // and set the coordinates in the range [0,500]
//		printMatrix(output);
////		ClusteringAlgorithm algorithm = new KMeans(numberOfClusters, output);
//		//algorithm = new KMeans(numberOfClusters, output);
//		//algorithm = new SingleLinkClustering(numberOfClusters);
//		//algorithm = new NoParametersClustering(0.0003);
//		switch (App.CLUSTERING_ALGORITHM) {
//		case App.SINGLELINK:
//			System.out.println("Running Single Link");
//			algorithm = new SingleLinkClustering(numberOfClusters);
//			break;
//		case App.KMEANS:
//			System.out.println("Running KMeans");
//			algorithm = new KMeans(numberOfClusters, output);
//			break;
//		
//		case App.NOPARAMETER:
//			System.out.println("Running No parameter hierachical");
//			algorithm = new NoParametersClusteringHierachical(0.036);
//			break;
//			
//		case App.NOPARAMETERKMEANS:
//			System.out.println("Running no parameter k means");
//			algorithm = new NoParametersClusteringKMeans(0.036);
//			break;
//		
//
//		default:
//			break;
//		}
//		
//		clusters = algorithm.cluster(distanceMatrix);
//		HashMap<Integer, Integer> xd = new HashMap<>();
//		for(int i=0; i<clusters.length; i++)
//			xd.put(clusters[i], xd.containsKey(clusters[i]) ? 
//								xd.get(clusters[i]) + 1 : 0 );
//		
//		System.out.println("REal clusters: " + xd.size());

	}
	
	public void initIfNeeded(){
		if(!isInit()){
			App.modelChanged();
			App.repaintView();
		}
	}
	
	public boolean isInit(){
		return distanceMatrix != null;
	}

	public double getRadius() {
		return radius;
	}

	public int[] getClusters() {
		return clusters;
	}
	
	/*
	 * Scales data in a matrix in the range [minValue,maxValue]
	 */
	static void reScaleData(double minValue, double maxValue, double data[][]){
		double min = Double.MAX_VALUE;
		double max = Double.MIN_NORMAL;
		
		for(int i=0; i<data.length; i++)
			for(int j=0; j<data[i].length; j++){
				min = Math.min(min, data[i][j]);
				max = Math.max(max, data[i][j]);
			}
		//x := (c - a) * (z - y) / (b - a) + y
		for(int i=0; i<data.length; i++)
			for(int j=0; j<data[i].length; j++)
				data[i][j] = (data[i][j] - min) * (maxValue - minValue) / (max - min) + minValue;
	}
	
	/*
	 * Performs MultiDimensional Scaling and rescales the data in a given range
	 */
	private static double[][] scaleDataTo2D( double distanceMatrix[][], double lowerBound, double upperBound ){
		double[][] output = MDSJ.classicalScaling(distanceMatrix); // apply MDS
		reScaleData(lowerBound, upperBound, output);
		return output;
	}

	public double[][] getPoints() {
		return output;
	}

	public void setNumberOfClusters(int clusters2) throws IOException {
		numberOfClusters = clusters2;
		//computeModel();
//		App.modelChanged();
	}

	public String getSourceCode(int idx) {
		try {
			return reader.getSourceCode(idx);
		} catch (Exception e) {
			throw new RuntimeException("Error getting source code "  + e);
		}
	}
	
	private static void printMatrix(double[][] distanceMatrix) {
		for(int i=0; i<distanceMatrix.length; i++){
			for(int j=0; j<distanceMatrix[i].length; j++){
				System.out.print( distanceMatrix[i][j] + "\t" );
			}
			System.out.println();
		}
		System.out.println();
	}

	public String[][] getReport(double d) {
		
		ArrayList<String[]> aux = new ArrayList<>();
		
		for(int i=0; i<distanceMatrix.length; i++)
			for(int j=i+1; j<distanceMatrix.length; j++)
				if( 1.0 - distanceMatrix[i][j] >= d && distanceMatrix[i][j] < 1.0 )
					aux.add(new String[]{ ""+i, ""+j, String.valueOf((1.0 - distanceMatrix[i][j])*100) });
		

		Collections.sort(aux, new Comparator<String[]>() {
			@Override
			public int compare(String[] o1, String[] o2) {
				return Double.compare( Double.parseDouble(o1[2]),Double.parseDouble(o2[2]) );
			}
		});
		
		String mat[][] = new String[aux.size()][3];
		for(int i=0; i<aux.size(); i++){
			String xd[] = aux.get(i);
			mat[i][0] = xd[0];
			mat[i][1] = xd[1];
			mat[i][2] = xd[2];
		}
		
		return mat;
	}

	public String[][] getReportByCluster(Integer cl) {
		ArrayList<String[]> aux = new ArrayList<>();
		
		for(int i=0; i<distanceMatrix.length; i++)
			for(int j=i+1; j<distanceMatrix.length; j++)
				if( clusters[i] == clusters[j] && clusters[i] == cl )
					aux.add(new String[]{ ""+i, ""+j, ""+((1.0 - distanceMatrix[i][j])*100) });
		
		String mat[][] = new String[aux.size()][3];
		for(int i=0; i<aux.size(); i++){
			String xd[] = aux.get(i);
			mat[i][0] = xd[0];
			mat[i][1] = xd[1];
			mat[i][2] = xd[2];
		}
		
		return mat;
	}

	public String[][] clusterReport() {
		System.out.println(Arrays.toString(clusters));
		ArrayList<String[]> aux = new ArrayList<>();
		
		for(int i=0; i<distanceMatrix.length; i++)
			for(int j=i; j<distanceMatrix.length; j++)
					if(clusters[i] == clusters[j] && i != j)
					aux.add(new String[]{ ""+i, ""+j , clusters[i]+"",  ""+(1.0 - distanceMatrix[i][j]) });
		
		Collections.sort(aux, new Comparator<String[]>() {
			@Override
			public int compare(String[] o1, String[] o2) {
				return Integer.parseInt(o1[2]) - Integer.parseInt(o2[2]);
			}
		});
		
		String mat[][] = new String[aux.size()][4];
		for(int i=0; i<aux.size(); i++){
			String xd[] = aux.get(i);
			mat[i][0] = xd[0];
			mat[i][1] = xd[1];
			mat[i][2] = xd[2];
			mat[i][3] = xd[3];
		}
		
		return mat;
	}

	public void setSourceFolder(String selected) {
		dirName = selected;
	}

	public int getNumberOfClusters() {
		return numberOfClusters;
	}

	public String getSourceCodeName(int idx) {
		return reader.getFileName(idx);
	}

	public int getCountOfDistances() {
		File folder = new File(dirName);
		int n = folder.list().length;
		return n*(n+1)/2;
	}
	
	private static void printMatrixToFile(double[][] distanceMatrix) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter("distances.txt", "UTF-8");
		for(int k = 0; k < distanceMatrix.length; k++){
			for(int j = 0; j < distanceMatrix.length; j++){
				writer.print(distanceMatrix[k][j]);
				writer.print(' ');
			}
			writer.print('\n');
		}
		writer.close();	
	}
	
}
