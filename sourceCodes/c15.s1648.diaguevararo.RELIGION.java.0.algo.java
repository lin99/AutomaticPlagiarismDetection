import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
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
	public static void main(String[] args) throws FileNotFoundException {
		int caso=1, i=0, j=0;
		Scanner scan = new Scanner(System.in);
		while(scan.hasNextLine()){
			int nper=0;
			nper = scan.nextInt();
			QuickFind arbol = new QuickFind(nper);
			int npar = scan.nextInt();
			if(nper==0&&npar==0)		break;
			for(int aux=1; aux<=npar ; aux++){
				i=scan.nextInt();
				j=scan.nextInt();
				arbol.union((i-1),(j-1));
			}
			System.out.println("Case "+(caso++)+": "+arbol.count());
		}
	}	
}