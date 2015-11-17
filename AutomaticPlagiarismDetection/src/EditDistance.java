/*
 * Edit distance returns the minimum amount of operations required to
 * change one string into another, using operations like insertions,
 * deletions or substitutions.
 * */
public class EditDistance implements StringMetric{
	
	double[][] distance;
	
	void initDistance(int m, int n){
		
		if( distance == null )
			distance = new double[m][n];
		
		if( distance.length < m || distance[0].length < n )
			distance = new double[m][n];
		
		for(int i=0; i<m; i++)
			for(int j=0; j<n; j++)
				distance[i][j] = 0.0;
	}
	
	@Override
	public double distance(String str1, String str2) {
		int i, j, m, n;
		m = str1.length();
		n = str2.length();
		
		initDistance(m+1,n+1);
		
		for (i = 0; i <= m; i++){
			for (j = 0; j <= n; j++ ){
				if (i == 0)
					distance[i][j] = j;
				else if (j == 0)
					distance[i][j] = i;
				else if (str1.charAt(i-1) == str2.charAt(j-1))
					distance[i][j] = distance[i-1][j-1];
				else
					distance[i][j] = 1 + Math.min(distance[i][j-1], Math.min(distance[i-1][j], distance[i-1][j-1]));
			}
		}
		return distance[m][n];
	}

}
