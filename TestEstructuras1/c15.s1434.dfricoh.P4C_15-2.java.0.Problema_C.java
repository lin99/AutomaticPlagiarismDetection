package ed.oct.pkg19;

import java.io.*;
import java.util.*;

public class Problema_C {

    static MyBST<Integer> arbol = new MyBST<>();
    
    public static class MyBST<AnyType extends Comparable<? super AnyType>> {

        private BinaryNode<AnyType> raiz;
        private static int contador;
        
        private class BinaryNode<AnyType> {

            BinaryNode(AnyType theElement) {
                this(null, theElement, null);
            }

            BinaryNode(BinaryNode<AnyType> i, AnyType theElement, BinaryNode<AnyType> d) {
                elemento = theElement;
                HijoI = i;
                HijoD = d;
            }
            AnyType elemento;
            BinaryNode<AnyType> HijoI;
            BinaryNode<AnyType> HijoD;
        }

        public MyBST() {
            raiz = null;
            contador = 0;
        }

        public boolean esVacio() {
            return raiz == null;
        }

        public void Vaciar() {
            raiz = null;
            contador = 0;
        }
        
        public int Contador(){
            return contador;
        }

        public void Insertar(AnyType x) {
            if(!Contiene(x))raiz = insert(x, raiz);
        }

        private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t) {
            if (t == null) {
                return new BinaryNode<>(null, x, null);
            }
            int compareResult = x.compareTo(t.elemento);
            if (compareResult < 0) {
                t.HijoI = insert(x, t.HijoI);
                contador ++;
            } else if (compareResult > 0) {
                t.HijoD = insert(x, t.HijoD);
                contador ++;
            }
            return t;
        }

        public void Eliminar(AnyType x) {
            raiz = remove(x, raiz);
        }

        private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
            if (t == null) {
                return t;
            }
            int compareResult = x.compareTo(t.elemento);
            if (compareResult < 0) {
                t.HijoI = remove(x, t.HijoI);
            } else if (compareResult > 0) {
                t.HijoD = remove(x, t.HijoD);
            } else if (t.HijoI != null && t.HijoD != null) {
                t.elemento = findMin(t.HijoD).elemento;
                t.HijoD = remove(t.elemento, t.HijoD);
            } else {
                t = (t.HijoI != null) ? t.HijoI : t.HijoD;
            }
            return t;
        }

        public AnyType buscarMin() throws Exception {
            if (esVacio()) {
                throw new Exception("El arbol esta vacio");
            }
            return findMin(raiz).elemento;
        }

        private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
            if (t == null) {
                return null;
            } else if (t.HijoI == null) {
                return t;
            }
            return findMin(t.HijoI);
        }

        public AnyType buscarMax() throws Exception {
            if (esVacio()) {
                throw new Exception();
            }
            return findMax(raiz).elemento;
        }

        private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
            if (t != null) {
                while (t.HijoD != null) {
                    t = t.HijoD;
                }
            }
            return t;
        }

        public boolean Contiene(AnyType x) {
            return contains(x, raiz);
        }

        private boolean contains(AnyType x, BinaryNode<AnyType> t) {
            if (t == null) {
                return false;
            }
            int compareResult = x.compareTo(t.elemento);
            if (compareResult < 0) {
                return contains(x, t.HijoI);
            } else if (compareResult > 0) {
                return contains(x, t.HijoD);
            } else {
                return true;
            }
        }

        public void Imprimir() {
            if (esVacio()) {
                System.out.println("El arbol esta vacio");
            } else {
                printTree(raiz);
            }
        }

        //Imprime de abajo hacia arriba

        private void printTree(BinaryNode<AnyType> t) {
            if (t != null) {
                printTree(t.HijoI);
                System.out.println(t.elemento);
                printTree(t.HijoD);
            }
        }

        /*//Imprime de arriba hacia abajo
         private void printTree(BinaryNode<AnyType> t) {
         if (t != null) {
         System.out.println(t.elemento);
         printTree(t.HijoI);
         printTree(t.HijoD);
         }
         }*/
        public int Altura() {
            return height(raiz);
        }

        private int height(BinaryNode<AnyType> t) {
            if (t == null) {
                return -1;
            } else {
                return 1 + Math.max(height(t.HijoI), height(t.HijoD));
            }
        }
    }

    public static void Ordenar(String[] f){
        for (int i = 0; i < f.length; i++) {
            for (int j = i+1; j < f.length; j++) {
                if(Integer.parseInt(f[i]) > Integer.parseInt(f[j])){
                    String tmp=f[i];
                    f[i]=f[j];
                    f[j]=tmp;
                }
            }
        }Ordenar2(f);
    }
    
    public static void Ordenar2(String[] f) {
        if (f.length == 0);
        if(f.length == 1)arbol.Insertar(Integer.parseInt(f[0]));
        else {
            ordenarI(f);
            ordenarD(f);
        }
    }

    public static void ordenarI(String[] s) {
        String iz = "";
        String[] i = {""};
        if (s.length==0 || s.length == 1);
        else {
            arbol.Insertar(Integer.parseInt(s[s.length / 2]));
            for (int j = 0; j < s.length / 2; j++) {
                if (iz != "")iz = iz + " " + s[j];
                else iz = iz + s[j];
            }
            i = iz.split(" ");
            Ordenar2(i);
        }
    }

    public static void ordenarD(String[] s) {
        String de = "";
        String[] d = {""};
        if (s.length==0 || s.length == 1);
        else {
            arbol.Insertar(Integer.parseInt(s[(s.length / 2)]));
            for (int k = (s.length / 2); k < s.length; k++) {
                if (de != "")de = de + " " + s[k];
                else de = de + s[k];
            }
            d = de.split(" ");
            Ordenar2(d);
        }
    }

    public static void main(String[] args) throws Exception {
        //final long startTime = System.currentTimeMillis();
        Scanner scan;
        File f = new File("C_2.txt");
        if(f.exists()) scan = new Scanner(f);
        else scan = new Scanner(System.in);
        String[] h;
        int p = Integer.parseInt(scan.nextLine());
        int q = 1;
        while(p>0){
            scan.nextLine();
            h = scan.nextLine().split(" ");
            for (int i = 0; i < h.length; i++) {
                arbol.Insertar(Integer.parseInt(h[i]));
            }
            int n = arbol.Contador();
            arbol.Vaciar();
            Ordenar(h);
            int o = arbol.Contador();
            System.out.println("Case #"+q+":");
            System.out.println(n+" "+o);
            arbol.Vaciar();
            p--;
            q++;
        }/*final long duration = System.currentTimeMillis() - startTime;
        System.out.println("Complexity " + duration);*/
    }
}
