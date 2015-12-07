import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        Ciudades pais = new Ciudades();
        pais.ejecutar();        
    }
    
    public static class Arista<T> {
        private final Vertice<T> vertice;
        protected double peso;

        public Arista(Vertice<T> vertice, double peso) {
            this.peso = peso;
            this.vertice = vertice;
        }
    }

    public static class Ciudades {
        protected Grafo<String> grafo;

        public Ciudades() {
            grafo = new Grafo<>();
        }

        public void ejecutar() throws IOException{
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String data[] = in.readLine().split(" ");
            Integer mCarreteras = Integer.parseInt(data[1]);
            for (int i = 0; i < mCarreteras; i++) {
                String data3[] = in.readLine().split(" ");
                grafo.addArista(data3[0], data3[1], Double.parseDouble(data3[2]));
            }
            int qConsultas = Integer.parseInt(in.readLine());
            for (int i = 0; i < qConsultas; i++) {
                String data4[] = in.readLine().split(" ");
                double res = grafo.dijkstra(data4[0], data4[1]);
                if (res == 9000000000000000000.0){
                    System.out.println("IMPOSIBLE");
                } else {
                    System.out.println(res);
                }
            }
        }
    }
    
    public static class ColaPrioridad<T> {
        private int size;
        private int sizeArray;
        private NodoPrioridad<T> arreglo[];

        public ColaPrioridad() {
            this.size = 0;
            arreglo = new NodoPrioridad[10];
            this.sizeArray = 10;
        }

        public boolean isEmpty(){
            return size == 0;
        }

        public void isNull (){
            NodoPrioridad<T> extra[] = new NodoPrioridad[10];
            size = 0;
            arreglo = extra;
            sizeArray = 10;
        }

        public void add(T dato, double prioridad){
            NodoPrioridad nodo = new NodoPrioridad(dato, prioridad);
            if ((size*2)+1 == sizeArray - 1){
                NodoPrioridad<T> extra[] = new NodoPrioridad[sizeArray*2];
                System.arraycopy(arreglo, 0, extra, 0, sizeArray);
                arreglo = extra;
                sizeArray = sizeArray*2;
            }
            int index = size + 1;
            arreglo[index] = nodo;
            int a = (int) index/2;
            while (getPrioridad(arreglo[index]) < getPrioridad(arreglo[a]) && a != 0){
                NodoPrioridad<T> plus = arreglo[index];
                arreglo[index] = arreglo[a];
                arreglo[a] = plus;
                index = a;
                a = (int) index/2;
            }
            size = size + 1;
        }

        private double getPrioridad (NodoPrioridad<T> nodo){
            if (nodo == null){
                return 9000000000000000000.0;
            } else {
                return nodo.prioridad;
            }
        }

        public T removeMin (){
            NodoPrioridad<T> min = arreglo[1];
            arreglo[1] = arreglo[size];
            arreglo[size] = null;
            int index = 1;
            while (getPrioridad(arreglo[index]) > getPrioridad(arreglo[2*index]) || getPrioridad(arreglo[index]) > getPrioridad(arreglo[(2*index)+1])){
                if (getPrioridad(arreglo[index*2]) <= getPrioridad(arreglo[(2*index)+1])){
                    NodoPrioridad<T> plus = arreglo[index];
                    arreglo[index] = arreglo[2*index];
                    arreglo[2*index] = plus;
                    index = 2*index;
                } else if (getPrioridad(arreglo[index*2]) > getPrioridad(arreglo[(2*index)+1])){
                    NodoPrioridad<T> plus = arreglo[index];
                    arreglo[index] = arreglo[(2*index)+1];
                    arreglo[(2*index)+1] = plus;
                    index = (2*index)+1;
                }
            }
            size = size - 1;
            return min.data;
        }
    }
    
    public static class Grafo<T> {
        private final ArrayList<Vertice<T>> vertices;
        private final HashMap<T, Integer> datos;
        private int nVertices;

        public Grafo() {
            vertices = new ArrayList<>();
            datos = new HashMap<>();
            nVertices = 0;
        }

        private void addVertice(T newDato){
            Vertice<T> newVertice = new Vertice(newDato);
            datos.put(newDato,nVertices);
            vertices.add(nVertices,newVertice);
            nVertices = nVertices + 1;
        }

        public void addArista(T origen, T fin, double peso){
            if (!datos.containsKey(origen)){
                addVertice(origen);
            }
            if (!datos.containsKey(fin)){
                addVertice(fin);
            }
            int indexOrigen = datos.get(origen);
            int indexFin = datos.get(fin);
            Vertice<T> extraOrigen = vertices.get(indexOrigen);
            Vertice<T> extraFin = vertices.get(indexFin);
            extraOrigen.addArista(extraFin, peso);
        }

        public double getPeso(T origen, T fin){
            int indexOrigen = datos.get(origen);
            int indexFin = datos.get(fin);
            Vertice<T> extraOrigen = vertices.get(indexOrigen);
            Vertice<T> extraFin = vertices.get(indexFin);
            return extraOrigen.getPeso(extraFin);
        }

        public double dijkstra (T origen, T fin){
            ArrayList<Double> menorCamino = new ArrayList<>();
            ColaPrioridad<Vertice<T>> cola = new ColaPrioridad<>();
            int indexOrigen = datos.get(origen);
            int indexFinal = datos.get(fin);
            Vertice<T> actual = vertices.get(indexOrigen);
            for (int i = 0; i < nVertices; i++) {
                menorCamino.add(9000000000000000000.0);
                vertices.get(i).visit = false;
            }
            menorCamino.set(indexOrigen, 0.0);
            cola.add(actual, 0.0);
            while (!cola.isEmpty()){
                actual = cola.removeMin();
                actual.visit = true;
                for (int i = 0; i < actual.nFines; i++) {
                    int indexActual = datos.get(actual.dato);
                    int indexFinI = datos.get(actual.finesVertices.get(i).dato);
                    double max = max(menorCamino.get(indexActual),getPeso(vertices.get(indexActual).dato, vertices.get(indexFinI).dato));
                    if (vertices.get(indexFinI).visit == false && menorCamino.get(indexFinI) > max){
                        menorCamino.set(indexFinI, max);
                        cola.add(vertices.get(indexFinI), menorCamino.get(indexFinI));
                    }
                }
            }
            return menorCamino.get(indexFinal);
        }

        private double max (double a, double b){
            if (a >= b){
                return a;
            } else {
                return b;
            }
        }
    }
    
    public static class NodoPrioridad<T> {
        protected T data;
        protected double prioridad;

        public NodoPrioridad(T data, double prioridad) {
            this.data = data;
            this.prioridad = prioridad;
        }
    }
    
    public static class Vertice<T> {
        protected T dato;
        private final ArrayList<Arista> aristas;
        protected HashMap<Vertice<T>, Integer> fines;
        protected ArrayList<Vertice<T>> finesVertices;
        protected int nFines;
        protected boolean visit;

        public Vertice(T newDato) {
            this.dato = newDato;
            this.aristas = new ArrayList<>();
            this.finesVertices = new ArrayList<>();
            this.fines = new HashMap<>();
            this.nFines = 0;
            this.visit = false;
        }

        public void addArista(Vertice<T> fin, double peso){
            Arista newArista = new Arista(fin, peso);
            aristas.add(nFines,newArista);
            finesVertices.add(nFines,fin);
            fines.put(fin,nFines);
            nFines = nFines + 1;
        }

        public double getPeso(Vertice<T> fin){
            int n = fines.get(fin);
            return aristas.get(n).peso;
        }

        public void setDato(T dato) {
            this.dato = dato;
        }
    }
}