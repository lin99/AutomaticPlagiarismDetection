import java.util.*;

 class main {



	public static void main(String args[]){


		Scanner in = new Scanner(System.in);


		int estudiantes,parejas;
		int contador = 1;
		boolean estado = true;

		while(estado){


			estudiantes= in.nextInt();
			parejas = in.nextInt();


			if(estudiantes==0 && parejas == 0)break;

			Union n = new Union(estudiantes);

			for(int k = 0; k < parejas ;k ++){
				int i,j;
				i = in.nextInt();
				j = in.nextInt();

				n.union(i-1, j-1);
			}

			System.out.println("Case "+contador+": "+n.count());

			contador++;

		}

	}




}

 class Union {

	private int[] id;
	private int count;

	public Union(int N) {
	count = N;
	id = new int[N];
	for (int i = 0; i < N; i++)
	id[i] = i;
	}

	public int count()
	{ return count; }

	public int find(int p)
	{ return id[p]; }

	public boolean find(int p, int q)
	{return id[p] == id[q]; }

	public void union(int p, int q) {
	if ( find(p, q) ) return;
	int pid = id[p];
	for (int i = 0; i < id.length; i++)
	if (id[i] == pid) id[i] = id[q];
	count--;
	}

	}
