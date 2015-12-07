
package proyecto4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problema4_C {

    static BinarySearchTree<Integer> arbol = new BinarySearchTree<>();
   
    public static class BinarySearchTree<AnyType extends Comparable<? super AnyType>>{

    private BinaryNode<AnyType> root;
    public static int count=0;
    
    public int count(){
        return count;
    }
    
    private static class BinaryNode<AnyType>{
        
        
        BinaryNode( AnyType theElement ){
            this( theElement, null, null );
        }

        BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt ){
            element  = theElement;
            left     = lt;
            right    = rt;
        }
        AnyType element;           
        BinaryNode<AnyType> left; 
        BinaryNode<AnyType> right; 
    }    
    
    public BinarySearchTree( ){
        root = null;
        count=0;
    }

    public void insert( AnyType x ){
        if(!contains(x))root = insert( x, root );
    }
    
    private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t ){
        if( t == null )
            return new BinaryNode<>( x, null, null );
        
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 ){
            t.left = insert( x, t.left );
            count++;
        }else if( compareResult > 0 ){
            t.right = insert( x, t.right );
            count++;
        }
        else
            ;  // Duplicate; do nothing
        return t;
    }
    
    public void remove( AnyType x ){
        root = remove( x, root );
    }

    private BinaryNode<AnyType> remove( AnyType x, BinaryNode<AnyType> t ){
        if( t == null )
            return t;   // Item not found; do nothing
            
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            t.left = remove( x, t.left );
        else if( compareResult > 0 )
            t.right = remove( x, t.right );
        else if( t.left != null && t.right != null ){
            t.element = findMin( t.right ).element;
            t.right = remove( t.element, t.right );
        }
        else
            t = ( t.left != null ) ? t.left : t.right;
        return t;
    }
    
    public AnyType findMin( ) throws Exception{
        if( isEmpty( ) )
            throw new Exception( );
        return findMin( root ).element;
    }

    private BinaryNode<AnyType> findMin( BinaryNode<AnyType> t ){
        if( t == null )
            return null;
        else if( t.left == null )
            return t;
        return findMin( t.left );
    }
    
    public AnyType findMax( ) throws Exception{
        if( isEmpty( ) )
            throw new Exception( );
        return findMax( root ).element;
    }

    private BinaryNode<AnyType> findMax( BinaryNode<AnyType> t ){
        if( t != null )
            while( t.right != null )
                t = t.right;
        return t;
    }
    
    public boolean contains( AnyType x ){
        return contains( x, root );
    }
    
    private boolean contains( AnyType x, BinaryNode<AnyType> t ){
        if( t == null )
            return false;    
        int compareResult = x.compareTo( t.element );
        if( compareResult < 0 )
            return contains( x, t.left );
        else if( compareResult > 0 )
            return contains( x, t.right );
        else
            return true; 
    }    
    
    public void makeEmpty( ){
        root = null;
        count=0;
    }

    public boolean isEmpty( ){
        return root == null;
    }

    public void printTree( ){
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }
    
    private void printTree( BinaryNode<AnyType> t ){
        if( t != null ){
            printTree( t.left );
            System.out.println( t.element );
            printTree( t.right );
        }
    }

    public int height(){
        return height(root); 
    }
    
    private int height( BinaryNode<AnyType> t ){
        if( t == null )
            return -1;
        else
            return 1 + Math.max( height( t.left ), height( t.right ) );    
    }
}
    

    public static void Ordenar (String[] f){
        for (int j = 0; j < f.length; j++) {
            for (int k=j+1; k < f.length; k++) {
                if (Integer.parseInt(f[j])>Integer.parseInt(f[k])) {
                    String tmp=f[j];
                    f[j]=f[k];
                    f[k]=tmp;
                }
            }
        }Ordenar2(f);
    }
            
    
    public static void Ordenar2(String[] f) {
        if (f.length == 0);
        if (f.length==1) arbol.insert(Integer.parseInt(f[0]));
        else {
            ordenarI(f);
            ordenarD(f);
        }
    }

    public static void ordenarI(String[] s) {
        String iz = "";
        String[] i={""};
        if (s.length == 0 || s.length == 1);
        else {
            arbol.insert(Integer.parseInt(s[s.length / 2]));
            for (int j = 0; j < s.length / 2; j++) {
                if (iz != "") {
                    iz = iz + " " + s[j];
                } else {
                    iz = iz + s[j];
                }
            }
            i = iz.split(" ");
            Ordenar2(i);
        }
    }

    public static void ordenarD(String[] s) {
        String de = "";
        String[] d ={""};
        if (s.length == 0 || s.length == 1);
         else {
            arbol.insert(Integer.parseInt(s[(s.length / 2)]));
            for (int k = (s.length / 2); k < s.length; k++) {
                if (de != "") {
                    de = de + " " + s[k];
                } else {
                    de = de + s[k];
                }
            }
            d = de.split(" ");
            Ordenar2(d);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan;
        File f = new File ("C_2.txt");
        if (f.exists()) scan = new Scanner(f);
        else scan = new Scanner(System.in);
        String[] h;
        int p = Integer.parseInt(scan.nextLine());
        int q = 1;
        while (p>0){
            scan.nextLine();
            h = scan.nextLine().split(" ");
            for (int i = 0; i < h.length; i++) {
                arbol.insert(Integer.parseInt(h[i]));
            }
            int n = arbol.count();
            arbol.makeEmpty();
            Ordenar(h);
            int o = arbol.count();
            System.out.println("Case #"+q+":");
            System.out.println(n+" "+o);
            arbol.makeEmpty();
            p--;
            q++;
        }
    }
}
   
   
