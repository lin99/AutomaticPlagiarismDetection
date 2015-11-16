package clustering;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class ClusteringTest {
	
	static double[][] randomEuclideanPoints(int n, double upperBound){
		double points[][] = new double[n][2];
		for(int i=0; i<n; i++){
			points[i][0] = ThreadLocalRandom.current().nextDouble(0.0, upperBound);
			points[i][1] = ThreadLocalRandom.current().nextDouble(0.0, upperBound);
		}
		
		return points;
	}
	
	static double[][] buildDistance( double points[][] ){
		double dist[][] = new double[points.length][points.length];
		
		for(int i=0; i<points.length; i++){
			for(int j=0; j<points.length; j++){
				double x1 = points[i][0],
					   x2 = points[j][0],
					   y1 = points[i][1],
					   y2 = points[j][1];
				
				dist[i][j] = Math.sqrt( (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) );
			}
		}
		
		return dist;
	}
	
	public static void main(String[] args) {
		double points[][] = randomEuclideanPoints(100, 600);
		double distanceMatrix[][] = buildDistance(points);
		int k = 7; //Number of clusters to be found
		ClusteringAlgorithm algorithm = new SingleLinkClustering(k); //Builds the clustering algorithm object
		int clusters[] = algorithm.cluster(distanceMatrix); //Executes the algorithm and return a mapping of each object to each cluster
		BasicClusteringWindow gui = new BasicClusteringWindow(points, clusters,k); //Shows a testing gui
	}
}
