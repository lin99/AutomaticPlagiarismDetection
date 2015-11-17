import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		int n = 0;
		int[][] matriz;
		int casillas = 0;
		int caso = 1;
		Scanner in = new Scanner(System.in);
		
		while(in.hasNext()) {
			int sum_row = 0;
			int sum_col = 0;
			n = in.nextInt();
			matriz = new int[n][n];
//			Llena matriz
			for (int i=0; i<n; i++) {
				for (int j=0; j<n; j++) {
					matriz[i][j] = in.nextInt();
				}
			}
			//Calcula numero de casillas ganadoras
			for (int i=0; i<n; i++) {
				for (int j=0; j<n; j++) {
					for (int k=0; k<n; k++) {
						sum_row += matriz[i][k]; 
						sum_col += matriz[k][j];
					}
					if (sum_row < sum_col) {
						casillas++;
					}
					sum_row = 0;
					sum_col = 0;
				}
			}
			System.out.println("Case #"+(caso)+":\n"+casillas);
			casillas = 0;
			caso++;
		}

	}

}
