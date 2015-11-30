import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

import clustering.ClusteringAlgorithm;
import clustering.KMeans;
import clustering.SingleLinkClustering;
import mdsj.MDSJ;

public class Model {
	private ReadSourceFiles reader;
	private List<String> strings;
	private double radius;
	private int[] clusters;
	private static int numberOfClusters;
	private double output[][];
	String dirName;
	ClusteringAlgorithm algorithm;
	double distanceMatrix[][];
	
	public Model(String dir) throws IOException {
		dirName = dir;
		numberOfClusters = 4;
		algorithm = new KMeans(numberOfClusters, output);
		
	//	computeModel();
	}
	
	void computeModel() throws IOException{
		long time = System.currentTimeMillis();
		reader = new ReadSourceFiles(dirName);
		strings = reader.getStrings();
		time = System.currentTimeMillis()-time;
		System.out.println("Time elapsed reading codes: " + time + " ms.");
		radius = 10.0;
		//ClusteringAlgorithm algorithm = new SingleLinkClustering( numberOfClusters ); //Clustering Algorithm object
		
		//Builds the matrix distance according to a specific metric
				//In this case the object stored in metrics[0]
				DistanceMatrixBuilder builder = new DistanceMatrixBuilder(
						App.getMetric(), 
						strings
					);
		
		//Actually builds the matrix
		distanceMatrix = builder.buildMatrix();
		
		output = scaleDataTo2D( distanceMatrix, 0.0, 400.0 ); // Creates 2 dimensional representation of the documents
		 // and set the coordinates in the range [0,500]
		printMatrix(output);
//		ClusteringAlgorithm algorithm = new KMeans(numberOfClusters, output);
		//algorithm = new KMeans(numberOfClusters, output);
		algorithm = new SingleLinkClustering(numberOfClusters);
		clusters = algorithm.cluster(distanceMatrix);
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
		App.modelChanged();
	}

	public String getSourceCode(int idx) {
		try {
			return new Scanner(reader.getFile(idx)).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Error getting source code");
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
					aux.add(new String[]{ ""+i, ""+j, ""+(1.0 - distanceMatrix[i][j]) });
		

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
					aux.add(new String[]{ ""+i, ""+j, ""+(1.0 - distanceMatrix[i][j]) });
		
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
					if(clusters[i] == clusters[j])
					aux.add(new String[]{ clusters[i]+"",""+i, ""+j, ""+(1.0 - distanceMatrix[i][j]) });
		
		Collections.sort(aux, new Comparator<String[]>() {
			@Override
			public int compare(String[] o1, String[] o2) {
				return Integer.parseInt(o1[0]) - Integer.parseInt(o2[1]);
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
	
}
