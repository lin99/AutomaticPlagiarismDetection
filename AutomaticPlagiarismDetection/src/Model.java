import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

import clustering.ClusteringAlgorithm;
import clustering.SingleLinkClustering;
import mdsj.MDSJ;

public class Model {
	private ReadSourceFiles reader;
	private List<String> strings;
	private double radius;
	private int[] clusters;
	private int numberOfClusters;
	private double output[][];
	String dirName;
	public Model(String dir) throws IOException {
		dirName = dir;
		numberOfClusters = 3;
	//	computeModel();
	}
	
	void computeModel() throws IOException{
		long time = System.currentTimeMillis();
		reader = new ReadSourceFiles(dirName);
		strings = reader.getStrings();
		time = System.currentTimeMillis()-time;
		System.out.println("Time elapsed reading codes: " + time + " ms.");
		radius = 10.0;
		ClusteringAlgorithm algorithm = new SingleLinkClustering( numberOfClusters ); //Clustering Algorithm object
		
		//Builds the matrix distance according to a specific metric
				//In this case the object stored in metrics[0]
				DistanceMatrixBuilder builder = new DistanceMatrixBuilder(
						App.getMetric(), 
						strings
					);
		
		//Actually builds the matrix
		double distanceMatrix[][] = builder.buildMatrix();
		
		clusters = algorithm.cluster(distanceMatrix);
		
		output = scaleDataTo2D( distanceMatrix, 0.0, 500.0 ); // Creates 2 dimensional representation of the documents
		 // and set the coordinates in the range [0,500]
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
		computeModel();
		App.modelChanged();
	}

	public String getSourceCode(int idx) {
		try {
			return new Scanner(reader.getFile(idx)).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Error getting source code");
		}
	}
	
	
	
}
