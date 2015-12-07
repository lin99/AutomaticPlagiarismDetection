
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

  public static HashMap<String, Integer> ciudades;
  public static int contadorCiudades = 0;

  static final int MAXIMO = 10005;
  static final int INFINITO = 1 << 30;

  // lista de adyacencia
  static List<List<Nodo>> listaAdyacencia = new ArrayList<List<Nodo>>();

  static double distancia[] = new double[MAXIMO];
  static boolean visitado[] = new boolean[MAXIMO];

  // Cola de prioridad
  static Heap<Nodo> heap = new Heap<Nodo>();

  // numero de ciudades
  static int n;

  static void init() {
    for (int i = 0; i <= n; ++i) {
      distancia[i] = INFINITO;
      visitado[i] = false;
    }
  }

  /**
   * Paso de relajacion
   * 
   * @param actual
   * @param adyacente
   * @param peso
   */
  static void relajacion(int actual, int adyacente, double peso) {
    if (distancia[actual] + peso < distancia[adyacente]) {
      distancia[adyacente] = distancia[actual] > peso ? distancia[actual] : peso;
      heap.insert(new Nodo(adyacente, distancia[adyacente])); // agregamos adyacente a la cola de
    }
  }


  static void dijkstra(int inicial, int destino) {
    init();
    heap.insert(new Nodo(inicial, 0)); // Insertamos el vértice inicial en la Cola de Prioridad
    distancia[inicial] = 0; // Este paso es importante, inicializamos la distancia del inicial como
                            // 0
    int actual, adyacente;
    double peso;
    while (!heap.isEmpty()) { // Mientras cola no este vacia
      actual = heap.removeMin().primero;
      if (visitado[actual]) {
        continue;
      }
      visitado[actual] = true; // Marco como visitado el vértice actual

      for (int i = 0; i < listaAdyacencia.get(actual).size(); ++i) {
        adyacente = listaAdyacencia.get(actual).get(i).primero;
        peso = listaAdyacencia.get(actual).get(i).segundo;
        if (!visitado[adyacente]) {
          relajacion(actual, adyacente, peso);
        }
      }
    }

    if (distancia[destino] == INFINITO) {
      System.out.println("IMPOSIBLE");
    } else {
      System.out.println(distancia[destino]);
    }
  }

  public static void anadirCiudad(String ciudad) {
    Integer valor = (Integer) ciudades.get(ciudad);
    if (valor == null) {
      contadorCiudades++;
      ciudades.put(ciudad, new Integer(contadorCiudades));
    }
  }

  public static int consultarCiudad(String ciudad) {
    Integer valor = (Integer) ciudades.get(ciudad);
    if (valor != null) {
      return valor.intValue();
    } else {
      return -1;
    }
  }

  public static void main(String[] args) {
    ciudades = new HashMap<String, Integer>();

    Scanner scanner = new Scanner(System.in);

    // System.out.println("Ingrese la cantidad de ciudades y de carreteras: ");
    String linea = scanner.nextLine();
    String[] cantidades = linea.split(" ");
    n = Integer.parseInt(cantidades[0]);
    int m = Integer.parseInt(cantidades[1]);

    for (int i = 0; i <= n; ++i)
      listaAdyacencia.add(new ArrayList<Nodo>()); // inicializamos lista de adyacencia
    for (int i = 0; i < m; ++i) {

      // System.out.println("Ingrese la carretera " + i + ": ");
      linea = scanner.nextLine();

      String[] carretera = linea.split(" ");
      anadirCiudad(carretera[0]);
      anadirCiudad(carretera[1]);

      int origen = consultarCiudad(carretera[0]);
      int destino = consultarCiudad(carretera[1]);
      Double peso = Double.parseDouble(carretera[2]);
//      int peso = valor.intValue();
      listaAdyacencia.get(origen).add(new Nodo(destino, peso));

    }

    // System.out.println("Ingrese el numero de consultas: ");
    linea = scanner.nextLine();
    int q = Integer.parseInt(linea);

    for (int i = 0; i < q; i++) {
      // System.out.println("Ingrese la consulta " + i + ": ");
      linea = scanner.nextLine();
      String[] consulta = linea.split(" ");

      dijkstra(consultarCiudad(consulta[0]), consultarCiudad(consulta[1]));

    }
  }
  
  public static class Heap<E extends Comparable<E>> {

    private Object vector[];
    private int ultimo;
    private int capacidad;

    public Heap() {
      vector = new Object[30000];
      ultimo = 0;
      capacidad = 30000;
    }

    public Heap(int cap) {
      vector = new Object[cap + 1];
      ultimo = 0;
      capacidad = cap;
    }

    public int size() {
      return ultimo;
    }

    /**
     * Retorna si el heap es vacio o no
     * 
     * @return booleano indicando si el heap es vacio o no
     */
    public boolean isEmpty() {
      return size() == 0;
    }

    /**
     * Retorna el valor del menor, sin eliminar
     * 
     * @return el valor del menor
     */
    public E min() {
      if (isEmpty())
        return null;
      else
        return (E) vector[1];
    }

    private int compare(Object x, Object y) {
      return ((E) x).compareTo((E) y);
    }

    /**
     * Inserta un nuevo nodo en el heap
     * @param nodo nodo a insertar
     */
    public void insert(E nodo) {
      if (size() == capacidad)
        System.out.println("Se lleno el heap");
      else {
        ultimo++;
        vector[ultimo] = nodo;
        burbujaHaciaArriba();
      }
    }

    /**
     * Remueve y retorna el menor valor
     * @return el menor valor
     */
    public E removeMin() {
      if (isEmpty()) {
        System.out.println("El heap es vacio");
        return null;
      }

      else {
        E min = min();
        vector[1] = vector[ultimo];
        ultimo--;
        burbujaHaciaAbajo();
        return min;
      }
    }

    /**
     * 
     * Metodo que reordena los elementos en orden para preservar las propiedades del Heap
     */
    private void burbujaHaciaAbajo() {
      int index = 1;
      while (true) {
        int child = index * 2;
        if (child > size())
          break;
        if (child + 1 <= size()) {
          child = encontrarMenor(child, child + 1);
        }
        if (compare(vector[index], vector[child]) <= 0)
          break;
        intercambiar(index, child);
        index = child;
      }
    }

    /**
     * Metodo para reordenar los elementos en orden para preservar las propiedades del heap
     */
    private void burbujaHaciaArriba() {
      int index = size();
      while (index > 1) {
        int parent = index / 2;
        if (compare(vector[index], vector[parent]) >= 0)
          break;
        intercambiar(index, parent);
        index = parent;
      }
    }

    /**
     * itercambia dos enteros
     * 
     * @param i primer entero a intercambiar
     * @param j segundo entero a intercambiar
     */
    private void intercambiar(int i, int j) {
      Object temp = vector[i];
      vector[i] = vector[j];
      vector[j] = temp;
    }

    /**
     * Metodo usado para comprar, retorna el menor de los valores
     * 
     * @param hijoIzquierdo
     * @param hijoDerecho
     */
    private int encontrarMenor(int hijoIzquierdo, int hijoDerecho) {
      if (compare(vector[hijoIzquierdo], vector[hijoDerecho]) <= 0)
        return hijoIzquierdo;
      else
        return hijoDerecho;
    }

  };

  /**
   * Clase nodo que hace las veces de grafo
   * @author Marcela Talero
   *
   */
  public static class Nodo implements Comparable<Nodo> {
    int primero;
    double segundo;

    Nodo(int d, double p) {
      this.primero = d;
      this.segundo = p;
    }

    public int compareTo(Nodo other) {
      if (segundo > other.segundo)
        return 1;
      if (segundo == other.segundo)
        return 0;
      return -1;
    }
  };
  
}
