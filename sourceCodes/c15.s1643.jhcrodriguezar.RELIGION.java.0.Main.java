import java.util.*;
public class Main{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner (System.in);
		int p=1;
		while(sc.hasNext()){
			int N=sc.nextInt();
			int M=sc.nextInt();
                        if((M==0)&&(N==0))
                        break;
			QuickFind arbol=new QuickFind(N);

			for(int s=0;s<M;s++){
				int i=sc.nextInt()-1;
				int j=sc.nextInt()-1;
				arbol.union(i, j);
				
			}
			System.out.println("Case #"+p+":");
			p++;
			System.out.println(arbol.count());
		}
	}

}

class QuickFind {

	private int[] id;

	private int count;

	public QuickFind(int N) {

		count = N;

		id = new int[N];

		for (int i = 0; i < N; i++)
			id[i] = i;

	}

	public int count() 

	{ return count; }

	public int find(int p) 

	{ return id[p]; }

	public boolean find(int p, int q) {
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