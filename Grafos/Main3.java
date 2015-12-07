import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;



public class Main {
	
	public static class Vertex implements Comparable<Vertex>{
		
	    //public final String name;
	    public final int name;
	    public ArrayList<Edge> neighbors = new ArrayList();
	    public double minDistance = Double.POSITIVE_INFINITY;
	    public Vertex previous;
	   // public Vertex(String argName) { name = argName; neighbors.add(new Edge(this,0)); }
	    public Vertex(int argName) { name = argName; neighbors.add(new Edge(this,0)); }
	    //public  String toString(){ return name;}
	    public int compareTo(Vertex other)
	    {
	        return Double.compare(minDistance, other.minDistance);
	    }
		@Override
		public String toString() {
			return "Vertex: " + name;
		}
		
	    
	   
	}
	
	public static class Edge{
	    public final Vertex target;
	    public final double weight;
	    public Edge(Vertex argTarget, double argWeight)
	    { target = argTarget; weight = argWeight; }
		@Override
		public String toString() {
			return "Edge: " + target;
		}
	    
		
	}
	
	

	public static class Dijkstra
	{
             public  double maximo (double uno, double dos){
                double resta = uno-dos;
                if (resta >0){
                    return uno;
                }else{
                    return dos;
                }
            }
             
             
	    public void computePaths(Vertex source)
	    {
	        source.minDistance = 0.;
	        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
	        vertexQueue.add(source);
                
                
	    while (!vertexQueue.isEmpty()) {
	    	Vertex u = vertexQueue.poll();
	    	
	            // Visit each edge exiting u
	            for (Edge e : u.neighbors)
	            {
	                Vertex v = e.target;
	                double weight = e.weight;
	                double distanceThroughU =  maximo(u.minDistance , weight);
	        if (distanceThroughU < v.minDistance ) {
	            vertexQueue.remove(v);
	            v.minDistance = distanceThroughU ;
	            v.previous = u;
	            vertexQueue.add(v);
	        }
	            }
	        }
            
           
	    }
	
	    public  List<Vertex> getShortestPathTo(Vertex target)
	    {
	        List<Vertex> path = new ArrayList<Vertex>();
	        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
	            path.add(vertex);
	
	        Collections.reverse(path);
	        return path;
	    }
	}
	
	
	 private static HashMap<String,Integer> vertices = new HashMap();
    // private static  Scanner entrada = new Scanner (System.in);
     private static  ArrayList<Vertex> grafo = new ArrayList<Vertex>();
     //private static Dijkstra algoritmo = new Dijkstra();
    private static  BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
     public static void main(String[] args) throws IOException  {
     	
     	//String ent = entrada.nextLine();
     	String ent = entrada.readLine();
     	String[] lectura = ent.split(" ");
     	String opcion = lectura[1];
     	int rutas = Integer.parseInt(opcion);
     	int id =0;
     	
     	
         for (int i=0;i<rutas;i++){	
     	  String ent1 = entrada.readLine();
     	  String[] lectura1 = ent1.split(" ");
     	  String v1 = lectura1[0];
     	  String v2 = lectura1[1];
     	  String cos = lectura1[2];
     	  double cost = Double.parseDouble(cos);
     	  int ind_1;
     	  int ind_2;
     	  if (!vertices.containsKey(v1)){
     		  vertices.put(v1,id);
     		  id++;
     		  Vertex ver1= new Vertex(vertices.get(v1));
     		  grafo.add(ver1);
     	  }
     	  if (!vertices.containsKey(v2)){
     		  vertices.put(v2,id);
     		  id++;
     		  grafo.add(new Vertex(vertices.get(v2)));
     	  }
     	  
     	grafo.get(found(vertices.get(v1))).neighbors.add(new Edge(grafo.get(found(vertices.get(v2))),cost));
       
       }
       String ind= entrada.readLine();
       int consultas = Integer.parseInt(ind);
       //int consultas = entrada.nextInt();
       for (int j=0; j<consultas;j++){
    	  String ent2 = entrada.readLine();
          String[] lectura2 = ent2.split(" ");
          //System.out.println(lectura2.length);
          String v1 = lectura2[0];
          String v2 = lectura2[1];
          Vertex source = grafo.get(found(vertices.get(v1)));
          Vertex target = grafo.get(found(vertices.get(v2))); 
        	 Dijkstra algoritmo = new Dijkstra();
        	 algoritmo.computePaths(source);
        	  if (target.minDistance==0.0 || target.minDistance== Double.POSITIVE_INFINITY){
        		  System.out.println("IMPOSIBLE");
        	  }else{
        		  System.out.println(target.minDistance);
        	  }
        	  source.minDistance=Double.POSITIVE_INFINITY;
        	  target.minDistance=Double.POSITIVE_INFINITY;

        	  
       }
       
         
         
         
         
         
         
         
     }
     
     
     public static int found (int name){
    	 int find = 0;
    	 for (Vertex v:grafo){
    		 if (v.name==name){
    			 find=grafo.indexOf(v); 
    			  break;
    		 }
    		 else find=-1;
    	 }
    	 return find;
     }
}
