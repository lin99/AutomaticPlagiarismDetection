package grafos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeSet;

public class Main {
	public static class Graph{
		public static final int INFINITY = 1000000000;
		ArrayList<Edge>[] adjList;

		public static class Edge implements Comparable<Edge>{
			int node;
			Double weight;
			public Edge(int node, double dist) {
				super();
				this.node = node;
				this.weight = dist;
			}

			@Override
			public int compareTo(Edge e) {
				return this.weight.compareTo(e.weight);
			}

			@Override
			public String toString() {
				return "Edge [node=" + node + ", weight=" + weight + "]";
			}
		}

		public Graph(int n) {
			adjList = new ArrayList [n];
			for (int i = 0; i < n; i++) {
				adjList[i]=new ArrayList<>();
			}
		}

		public void connect (int a, int b, Double w){
			adjList[a].add(new Edge(b,w));
		}

		public double biggestFromPath (int [] path, double[] dist, int out)
		{
			int v=out;
			int u=path[out];
			if(u==-1)
				return INFINITY;
			double k=dist[v]-dist[u];
			while(true)
			{
				v=u;
				u=path[u];
				if(u==-1) break;
				double k1=dist[v]-dist[u];
				if(k1>k)
					k=k1;
			}
			return k;
		}

		public void dijkstra (int in, int out)
		{
			TreeSet<Double> [] costos = new TreeSet [adjList.length];
			boolean [] visited = new boolean [adjList.length];
			for (int i = 0; i < costos.length; i++)
				costos[i]= new TreeSet<>();
			costos[in].add(0.0);
			Queue<Integer> q = new LinkedList<Integer>();
			q.offer(in);
			while(!q.isEmpty())
			{
				int a = q.poll();
				if(!visited[a])
				{
					//System.out.println("a"+a);
					for (Edge edge : adjList[a])
					{
						int b=edge.node;
						//System.out.println("b"+b);
						double weight=edge.weight;
						if(weight>costos[a].first())
							costos[b].add(weight);
						else
							costos[b].add(costos[a].first());
						q.offer(b);
					}
					visited[a]=true;
				}
			}
			//System.out.println(Arrays.toString(adjList));
			if(costos[out].isEmpty()) System.out.println("IMPOSIBLE");
			else System.out.println(costos[out].first());
		}
	}
	public static void main(String[] args) 
	{
		Scanner sn = new Scanner (System.in);
		String [] line = sn.nextLine().split(" ");
		int n = Integer.parseInt(line[0]);
		int m = Integer.parseInt(line[1]);
		Graph g = new Graph (n);
		HashMap<String,Integer> ciudades = new HashMap<>();
		int c = 0;
		for (int i = 0; i < m; i++)
		{
			line = sn.nextLine().split(" ");
			Integer a=ciudades.get(line[0]);
			if (a==null)
			{
				a=c++;
				ciudades.put(line[0], a);
			}
			Integer b=ciudades.get(line[1]);
			if (b==null)
			{
				b=c++;
				ciudades.put(line[1], b);
			}
			
			Double w=Double.parseDouble(line[2]);
			g.connect(a, b, w);
		}
		int q = Integer.parseInt(sn.nextLine());
		for (int i = 0; i < q; i++)
		{
			line=sn.nextLine().split(" ");
			int in=ciudades.get(line[0]);
			int out=ciudades.get(line[1]);
			g.dijkstra(in, out);
		}
	}
}
