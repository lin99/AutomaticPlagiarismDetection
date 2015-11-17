import java.util.Scanner;

public class Main{
	public static class QuickFind {
		private int[] id;
		private int count;
		
		public QuickFind(int N) {
			count = N;
			id = new int[N];
			for (int i = 0; i < N; i++)
				id[i] = i;
		}

		public int count(){
			return count;
		}

		public int find(int p){
			return id[p]; 
		}

		public boolean find(int p, int q){
			return id[p] == id[q]; 
		}

		public void union(int p, int q) {
			if ( find(p, q) ) return;
			int pid = id[p];
			for (int i = 0; i < id.length; i++)
				if (id[i] == pid) id[i] = id[q]; 
			count--;
		}
	}

	@SuppressWarnings("resource")
	public static void main(String[] args){
		int N, M, i,j, cas=0;
		Scanner scan;
		scan = new Scanner(System.in);
		while(scan.hasNextLine()){
			N = scan.nextInt();
			M = scan.nextInt();
                        if (N==0 && M==0) break;		
			QuickFind arbol = new QuickFind(N+1);
			for(int k=0 ;k<M ; k++){
				i = scan.nextInt();
				j = scan.nextInt();
				arbol.union(i, j);
			}
			System.out.println("Case "+(cas+1)+": "+(arbol.count()-1));
			cas++;
		}
	}	
}