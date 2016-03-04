package logic.clustering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import logic.datastructures.UnionFind;

/*
 * Implements the SingleLink Clustering algorithm.
 * Is designed to support many calls to the cluster
 * method efficiently.
 * 
 * @author: Andres Felipe Cruz
 */
public class SingleLinkClustering implements ClusteringAlgorithm {

	/*
	 * The number of clusters the algorithm is supposed to find.
	 */
	private int clusters;
	
	/*
	 * A list of edges, is stored in order to 
	 * avoid re-memory allocation if the algorithm is used
	 * many times.
	 */
	private ArrayList<Edge> edges;
	
	/*
	 * Aux map for index mapping.
	 */
	private HashMap<Integer,Integer> aux;
	
	/*
	 * Init the algorithm the number of clusters to find.
	 */
	public SingleLinkClustering(int clusters) {
		this.clusters = clusters;
	}
	
	
	/*
	 * Build the list in a lazy approach.
	 */
	private void initEdgesIfNeeded(int sz){
		if( edges == null )
			edges = new ArrayList<>( sz );
		else 
			edges.clear();
	}
	
	/*
	 *  Build the list of edges based on the matrix distance of the
	 *  data to be clustered.
	 */
	private void buildEdges( double distance[][] ){
		initEdgesIfNeeded( distance.length * distance.length );
		for(int i=0; i<distance.length; i++){
			for(int j=i; j<distance.length; j++){
				edges.add(new Edge(i, j, distance[i][j]));
			}
		}
	}
	
	/*
	 * Performs Single Link clustering in O( n^2 * log(n) ) time and O(n^2) space.
	 * Where n is the number of elements to be clustered.
	 * 
	 * DistaceMatrix is a double matrix where the (i,j) position stores the distance
	 * of the ith object regarding the jth object.
	 */
	@Override
	public int[] cluster(double[][] distanceMatrix) {
		
		int n = distanceMatrix.length;
		
		UnionFind dsu = new UnionFind( n );
		buildEdges(distanceMatrix);
		Collections.sort(edges, (a,b) -> Double.compare(a.weight, b.weight));
		
		int idx = 0;
		Edge currentEdge = null;
		while( dsu.components() > clusters ){
			currentEdge = edges.get(idx);
			if( !dsu.sameComponent(currentEdge.u, currentEdge.v) )
				dsu.union(currentEdge.u, currentEdge.v);
			
			idx++;
		}
		
		initAuxMapIfNeeded(); //Puts clusters in [0,clusters-1] range.
		int clusters[] = dsu.getParents(); 
		for(int i=0; i<clusters.length; i++){
			if( aux.containsKey(clusters[i]) == false )
				aux.put(clusters[i], aux.size());
		}
		
		for(int i=0; i<clusters.length; i++)
			clusters[i] = aux.get(clusters[i]);
		
		return clusters;
	}
	
	private void initAuxMapIfNeeded() {
		if(aux == null)
			aux = new HashMap<>();
		else
			aux.clear();
	}

	private class Edge{
		int u,v;
		double weight;
		public Edge(int u, int v, double weight) {
			this.u = u;
			this.v = v;
			this.weight = weight;
		}
	}

}
