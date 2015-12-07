package cuartotaller;

import java.util.ArrayList;
import java.util.Scanner;

class BST<Integer extends Comparable<? super Integer>> {
    Integer value;
    BST left, right;
    
    public BST() {
        value = null;
        left = null;
        right = null;
    }
    public BST(Integer v) {
        value = v;
        left = null;
        right = null;
    }
    
    public int add(Integer x) {
        if(value == null) {
            value = x;
            return 0;
        }
        int ans = 0;
        BST cur = this; // Siempre es la raiz
        while(true) {
            ans++;
            if(cur.value.compareTo(x) < 0 ) { // valor es menor a x
                if(cur.left == null) {
                    cur.left = new BST(x);
                    break;
                }
                else {
                    cur = cur.left;
                }
            }
            else {
                if(cur.right == null) {
                    cur.right = new BST(x);
                    break;
                }
                else {
                    cur = cur.right;
                }
            }
        }
        return ans;
    }
}

public class problemaC {
    
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        
        int casos = leer.nextInt();
  
        for(int k = 1; k <= casos; k++) {
            int n = leer.nextInt();
            ArrayList<Integer> numeros = new ArrayList<>();
            BST<Integer> arbol = new BST<>();
            int ans1 = 0;
            for(int i = 1; i <= n; i++) {
                int num = leer.nextInt();
                numeros.add(num);
                ans1 += arbol.add(num);
            }
            
            int ans2 = 0, numerosProcesados = 0;
            for(int i = 0; i < numeros.size(); i++) { // recorrer altura
                int pot = i;
                for(int j = 1; j <= Math.pow(2, pot); j++) { // recorrer nodos altura i
                    ans2 += pot;
                    numerosProcesados++;
                    if(numerosProcesados == numeros.size())
                        break;
                }
                if(numerosProcesados == numeros.size())
                        break;
            }
            
            System.out.println("Case #" + k + ":");
            System.out.println(ans1 + " " + ans2);
            
            
        }
    }
}
