package logic.clustering;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

import mdsj.MDSJ;

public class NoParametersClusteringKMeans implements ClusteringAlgorithm {
	
	private double ratio;
	
	public NoParametersClusteringKMeans(double ratio) {
		this.ratio = ratio;
	}

	@Override
	public int[] cluster(double[][] distanceMatrix) {
		double[][] output = MDSJ.classicalScaling(distanceMatrix); // apply MDS
		int clusters[];
		double quality;
		
		ClusteringAlgorithm algo = new KMeans(2, output);
		clusters = algo.cluster(distanceMatrix);
		quality = qualityMeasure(clusters, output);
		
		for(int k=3; k<=distanceMatrix.length; k++){
			clusters = new KMeans(k, output).cluster(distanceMatrix);
			double aux = qualityMeasure(clusters, output);
			System.out.println(quality +  " " +  Math.abs(aux-quality));
			if( Math.abs(aux-quality) <= ratio ){
				
				HashMap<Integer, Integer> xd = new HashMap<>();
				for(int i=0; i<clusters.length; i++)
					xd.put(clusters[i], xd.containsKey(clusters[i]) ? 
										xd.get(clusters[i]) + 1 : 0 );
				
				System.out.println("Choosen: " + k + " " + xd.size());
				return clusters;
			}
			quality = aux;
		}
		
		System.out.println("Choosen: " + distanceMatrix.length);
		return clusters;
	}
	
	double qualityMeasure(int clusters[], double coords[][]){
		HashMap<Integer, double[]> aux = new HashMap<>();
		HashMap<Integer, Integer> aux2 = new HashMap<>();
		
		for(int i=0; i<clusters.length; i++){
		
			if( aux.containsKey(clusters[i]) == false ){
				aux.put(clusters[i], new double[]{0,0});
				aux2.put(clusters[i], 1);
			}
			
			aux2.put(clusters[i], aux2.get(clusters[i])+1);
			
			
			double point[] = aux.get(clusters[i]);
			point[0] += coords[0][i];
			point[1] += coords[1][i];
			
			aux.put(clusters[i],point);
			
		}
		
		for(Entry<Integer, double[]> entry : aux.entrySet()){
			entry.getValue()[0] /= aux2.get(entry.getKey());
			entry.getValue()[1] /= aux2.get(entry.getKey());
		}
		
		double dist = Double.MAX_VALUE;
		Integer keys[] = aux.keySet().toArray(new Integer[aux.size()]);
		for(int i=0; i<keys.length; i++){
			for(int j=i+1; j<keys.length; j++){
				double a[] = aux.get(keys[i]);
				double b[] = aux.get(keys[j]);
				dist = Math.min(dist,  (a[0] - b[0])*(a[0] - b[0]) + (a[1] - b[1])*(a[1] - b[1]) );
			}
		}
		
		return dist;
	}
	
	public static void main(String[] args) {
		int n = 30;
		double p[][] = new double[2][n];
		double cl[][] = { {2.9,4.5}, {13.0,1.0}, {5.0,10.0}, {0.0,2.0} };
		for(int i=0; i<n; i++){
			int idx = ThreadLocalRandom.current().nextInt(cl.length);
			double x = cl[idx][0];
			double y = cl[idx][1];
			
			p[0][i] = ThreadLocalRandom.current().nextDouble() + x;
			p[1][i] = ThreadLocalRandom.current().nextDouble() + y;
//			System.out.println(p[0][i] + "\t" + p[1][i]);
		}
		
		double dist[][] = new double[n][n];
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				dist[i][j] = (p[0][i]-p[0][j])*(p[0][i]-p[0][j]) + (p[1][i]-p[1][j])*(p[1][i]-p[1][j]); 
			}
		}
		
		NoParametersClusteringHierachical algo = new NoParametersClusteringHierachical(0.1);
		algo.cluster(dist);
	}

}

