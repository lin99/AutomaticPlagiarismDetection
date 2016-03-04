package datastructures;


/*
 * Tarjan's UnionFind data structure implementation.
 * overall complexity in terms of union and find
 * operatios: O(n * alpha(n) ) where alpha is the
 * inverse of the Ackerman function. A common bound
 * is O(n * logn) as well.
 * 
 * @author: Andres Felipe Cruz
 */
public class UnionFind {
	@SuppressWarnings("unused")
	private int parent[], rank[],  n, components;

	public UnionFind(int n) {
		super();
		this.n = n;
		this.components = n;
		this.parent = new int[n];
		this.rank = new int[n];
		for(int i=0; i<n; i++){
			parent[i] = i;
			rank[i] = 0;
		}
	}
	
	private int find(int x){
		if( x == parent[x] )
			return x;
		else
			return parent[x] = find( parent[x] );
	}	
	
	public void union( int x , int y ){
	    int xRoot = find( x );
	    int yRoot = find( y );
	    
	    if( xRoot != yRoot )
	    	components--;
	    
	    if( rank[ xRoot ] > rank[ yRoot ] )
	        parent[ yRoot ] = xRoot;
	    else{
	        parent[ xRoot ] = yRoot;
	        if( rank[ xRoot ] == rank[ yRoot ] )
	        	rank[ yRoot ]++;
	    }
	}
	
	public boolean sameComponent( int x, int y ){
		return find( x ) == find( y );
	}

	public int components(){
		return components;
	}
	
	public int[] getParents(){
		int ret[] = new int[n];
		for(int i=0; i<n; i++)
			ret[i] = find(i);
		
		return ret;
	}
}
