import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String salida = "";
        String data[] = in.readLine().split(" ");
        int n = Integer.parseInt(data[0]);
        int m = Integer.parseInt(data[1]);
        Graph grafo = new Graph(n);
        
        for (int i = 0; i < m; i++) {
            String data1[] = in.readLine().split(" ");
            grafo.insertEdge(data1[0], data1[1], Double.parseDouble(data1[2]));
        }
        
        int q = Integer.parseInt(in.readLine());
        for (int ii = 0; ii < q; ii++) {
            String data2[] = in.readLine().split(" ");
//          grafo.dijkstra(grafo.graph.get(data2[0]));
            grafo.dijkstra(data2[0]);
            double max = grafo.max(grafo.graph.get(data2[0]), grafo.graph.get(data2[1]));
            if(max > 0) salida = salida + max + "\n";
            else salida = salida + "IMPOSIBLE" + "\n";
            //grafo.printAllPaths();

        }
        System.out.println(salida);

    }

    public static class Graph {

        private final Map<String, Vertex> graph;
        public PriorityQueue <Vertex> Q = new PriorityQueue<Vertex>();
        public static class Edge {

            public final String v1, v2;
            public final int dist;

            public Edge(String v1, String v2, int dist) {
                this.v1 = v1;
                this.v2 = v2;
                this.dist = dist;
            }
        }

        public static class Vertex implements Comparable<Vertex> {

            public final String name;
            public double dist = Double.MAX_VALUE; 
            public Vertex previous = null;
            public final Map<Vertex, Double> neighbours = new HashMap<>();

            public Vertex(String name) {
                this.name = name;
            }
            
            public int compareTo(Vertex other) {
                return Double.compare(dist, other.dist);
            }

//            private void printPath() {
//               if (this == this.previous) {
//                  System.out.printf("%s", this.name);
//               } else if (this.previous == null) {
//                  System.out.printf("%s(unreached)", this.name);
//               } else {
//                  this.previous.printPath();
//                  System.out.printf(" -> %s(%f)", this.name, this.dist);
//               }
//            }            
        }

        public Graph(int tam) {
            graph = new HashMap<>(tam);
        }

        public void insertEdge(String v1, String v2, double dist) {
            if (!graph.containsKey(v1)) {
                graph.put(v1, new Vertex(v1));
            }

            if (!graph.containsKey(v2)) {
                graph.put(v2, new Vertex(v2));
            }

            graph.get(v1).neighbours.put(graph.get(v2), dist);
        }

        
        
        public void dijkstra(String startName) {
           if (!graph.containsKey(startName)) {
              System.err.printf("El vertice no esta contenido");
              return;
           }
           final Graph.Vertex source = graph.get(startName);
           NavigableSet<Graph.Vertex> q = new TreeSet<>();
           
           for (Graph.Vertex v : graph.values()) {
              v.previous = v == source ? source : null;
              v.dist = v == source ? 0 : Double.MAX_VALUE;
              q.add(v);
           }
           dijkstra(q);
           
        }

        private void dijkstra(final NavigableSet<Vertex> q) {
            Vertex u, v;
            while (!q.isEmpty()) {

                u = q.pollFirst(); 
                if (u.dist == Double.MAX_VALUE) {
                    break; 
                }
                
                for (Map.Entry<Vertex, Double> a : u.neighbours.entrySet()) {
                    v = a.getKey(); 
                    final double alternateDist = u.dist + a.getValue();

                    if (alternateDist < v.dist) { 
                        q.remove(v);
                        v.dist = alternateDist;
                        v.previous = u;
                        q.add(v);
                    }
                }
            }
        }
        
//        public void printAllPaths() {
//           for (Graph.Vertex v : graph.values()) {
//              v.printPath();
//              System.out.println();
//           }
//        }

        public double max(Vertex v, Vertex u ){
            Vertex aux = u;
            double maxx[] = new double[100];
            double max = aux.dist;
            int i = 0;
            while(aux != null) {
                if(aux.previous != null){
                    max = max - aux.previous.dist;
                    maxx[i] = max;
                    if(aux.previous.dist == 0){
                        i++;
                        maxx[i] = aux.dist;
                    }
                    max = aux.previous.dist;
                    if(aux.previous.equals(v)) {
                        break;
                    }
                } else {
                    return -1;
                }
                aux = aux.previous;
                i++;
            }
            
            double resultado = maxx[0]; 
            for(int ii = 0; ii < i; ii++){ 
                if(maxx[ii] > resultado) resultado = maxx[ii];
            } 
            max = resultado;
            return max;
        }

    }
}
