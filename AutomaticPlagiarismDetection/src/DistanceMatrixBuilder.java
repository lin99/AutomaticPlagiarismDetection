import java.util.List;

public class DistanceMatrixBuilder {
	private StringMetric metric;
	private List<String> sourceCodeRepresentations;
	
	public DistanceMatrixBuilder(StringMetric metric, List<String> sourceCodeRepresentations) {
		this.metric = metric;
		this.sourceCodeRepresentations = sourceCodeRepresentations;
	}
	
	public double[][] buildMatrix(){
		int n = sourceCodeRepresentations.size();
		double matrix[][] = new double[n][n];
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				System.out.println("Computing distance " + i + " " + j);
				matrix[i][j] = metric.distance(
						sourceCodeRepresentations.get(i), 
						sourceCodeRepresentations.get(j)
					);
			}
		}
		
		return matrix;
	}
}
