/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;


/**
 *
 * @author Estudiante
 */
public class Main {

    	private static Scanner scan;

	public static void main(String args[]) throws FileNotFoundException {

		scan = new Scanner(System.in);

		String[] parametros = scan.nextLine().split(" ");
		Integer n = Integer.parseInt(parametros[0]);
		Integer m = Integer.parseInt(parametros[1]);

		HashMap<String, Nodo> ciudadesPosicion = new HashMap<>();

		String ciudadOrigen = "";
		String ciudadDestino = "";
		double peso = 0;

		Nodo nodo = null;
		Nodo nodoDestino = null;
		List<Arista> adyacencias = null;

		for (int i = 0; i < m; i++) {
			parametros = scan.nextLine().split(" ");
			ciudadOrigen = parametros[0];
			ciudadDestino = parametros[1];
			peso = Double.parseDouble(parametros[2]);

			nodo = ciudadesPosicion.get(ciudadOrigen);
			if (nodo == null) {
				nodo = new Nodo(ciudadOrigen);
			}
			adyacencias = nodo.adyacencias;
			if (adyacencias == null) {
				adyacencias = new ArrayList<Arista>();
			}
			nodoDestino = ciudadesPosicion.get(ciudadDestino);
			if (nodoDestino == null) {
				nodoDestino = new Nodo(ciudadDestino, new ArrayList<Arista>());
			}

			adyacencias.add(new Arista(nodoDestino, peso));

			nodo.adyacencias = (ArrayList<Arista>) adyacencias;
			ciudadesPosicion.put(ciudadDestino, nodoDestino);
			ciudadesPosicion.put(ciudadOrigen, nodo);

		}

		MyDijkstra dijkstra = new MyDijkstra();

		Integer t = Integer.parseInt(scan.nextLine());
		for (int i = 0; i < t; i++) {
			parametros = scan.nextLine().split(" ");
			ciudadOrigen = parametros[0];
			ciudadDestino = parametros[1];

			dijkstra.computePaths(ciudadesPosicion, ciudadesPosicion.get(ciudadOrigen));
			dijkstra.getShortestPathTo(ciudadesPosicion.get(ciudadDestino));

		}

	}

}

class Nodo implements Comparable<Nodo> {
	public String ciudad;
	public ArrayList<Arista> adyacencias;
	public Nodo previous;
	public double distanciaMinima = Double.POSITIVE_INFINITY;
	public double max = 0.0;

	public Nodo(String ciudad) {

		this.ciudad = ciudad;
	}

	public Nodo(String ciudad, ArrayList<Arista> adyacencias) {

		this.ciudad = ciudad;
		this.adyacencias = adyacencias;
	}

	@Override
	public int compareTo(Nodo o) {

		return Double.compare(distanciaMinima, o.distanciaMinima);
	}

	public String toString() {

		return "(" + this.ciudad + " :: " + this.distanciaMinima + ")";
	}

}

class Arista implements Comparable<Arista> {
	public final Nodo destino;
	public final double peso;

	public Arista(Nodo ciudadDestino, double peso) {

		this.destino = ciudadDestino;
		this.peso = peso;
	}

	public int compareTo(Arista other) {

		return this.destino.compareTo(other.destino);
	}

	public String toString() {

		return "(" + this.destino + ", " + this.peso + ")";
	}
}

class MyDijkstra {

	public void computePaths(HashMap<String, Nodo> ciudades, Nodo source) {

		PriorityQueue<Nodo> cola = new PriorityQueue<Nodo>();
		cola.add(source);

		for (Nodo nodo : ciudades.values()) {
			nodo.distanciaMinima = Double.POSITIVE_INFINITY;
			nodo.max = 0.0;
		}
		source.distanciaMinima = 0.0;
		// MyHeap<Nodo> NodoQueue = new MyHeap();
		// NodoQueue.insert(source);

		while (!cola.isEmpty()) {
			Nodo u = cola.poll();

			for (Arista e : u.adyacencias) {
				Nodo v = e.destino;
				double weight = e.peso;

				double distanceThroughU = u.distanciaMinima + weight;
				if (distanceThroughU < v.distanciaMinima) {
					cola.remove(v);
					v.distanciaMinima = distanceThroughU;
					if (weight > v.max || v.max > v.distanciaMinima) {
						v.max = weight;
					}
					v.previous = u;
					cola.add(v);
				}
			}
		}
	}

	public void getShortestPathTo(Nodo target) {

		Double max = 0.0;
		for (Nodo nodo = target; nodo != null; nodo = nodo.previous) {
			if (nodo.max > max && nodo.max < Double.POSITIVE_INFINITY) {
				max = nodo.max;
			}
		}

		if (max == 0.0) {
			System.out.println("IMPOSIBLE");
		}
		else {
			System.out.println(max);
		}
	}

    
}
