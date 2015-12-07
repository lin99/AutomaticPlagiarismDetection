import static java.lang.Math.floor;
import java.util.Scanner;

public class Main {

    public static class BinarySearchTree
{
    public BinarySearchTree( )
    {
        root = null;
        Nonodos=1;
        comparaciones=0;
    }

    public void insert( int x )
    {
        root = insert( x, root );
    }

  
    public void remove( int x )
    {
        root = remove( x, root );
    }


    public int findMin( ) throws Exception
    {
        if( isEmpty( ) )
            throw new Exception( );
        return findMin( root ).element;
    }


    public int findMax( ) throws Exception
    {
        if( isEmpty( ) )
            throw new Exception( );
        return findMax( root ).element;
    }

 
    public boolean contains( int x )
    {
        return contains( x, root );
    }


    public void makeEmpty( )
    {
        root = null;
    }


    public boolean isEmpty( )
    {
        return root == null;
    }


    public void printTree( )
    {
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }

    private BinaryNode insert( int x, BinaryNode t )
    {
        if( t == null )
            return new BinaryNode( x, null, null,Nonodos );
        
        if( x < t.element ){
            t.left = insert( x, t.left );
            comparaciones++;
        }else if( x > t.element ){
            t.right = insert( x, t.right );
            comparaciones++;
        }else
            ;  // Duplicate; do nothing
        return t;
    }


    private BinaryNode remove( int x, BinaryNode t )
    {
        if( t == null )
            return t;   // Item not found; do nothing
            
        //int compareResult = x.compareTo( t.element );
            
        if( x < t.element )
            t.left = remove( x, t.left );
        else if( x > t.element )
            t.right = remove( x, t.right );
        else if( t.left != null && t.right != null ) // Two children
        {
            t.element = findMin( t.right ).element;
            t.right = remove( t.element, t.right );
        }
        else
            t = ( t.left != null ) ? t.left : t.right;
        return t;
    }


    private BinaryNode findMin( BinaryNode t )
    {
        if( t == null )
            return null;
        else if( t.left == null )
            return t;
        return findMin( t.left );
    }


    private BinaryNode findMax( BinaryNode t )
    {
        if( t != null )
            while( t.right != null )
                t = t.right;

        return t;
    }

    private boolean contains( int x, BinaryNode t )
    {
        if( t == null )
            return false;
            
        if( x < t.element ){
            return contains( x, t.left );
        } else if( x > t.element ){
            return contains( x, t.right );
        }else{
            return true;    // Match 
        }
    }

    private void printTree( BinaryNode t )
    {
        if( t != null )
        {
            printTree( t.left );
            System.out.println( t.element );
            printTree( t.right );
        }
    }


    private int height( BinaryNode t )
    {
        if( t == null )
            return -1;
        else
            return 1 + Math.max( height( t.left ), height( t.right ) );    
    }
    
    private static class BinaryNode
    {

        BinaryNode( int theElement )
        {
            this( theElement, null, null,1 );
        }

        BinaryNode( int theElement, BinaryNode lt, BinaryNode rt, int k )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
            key     =k;
        }

        int element;            // The data in the node
        BinaryNode left;   // Left child
        BinaryNode right;  // Right child
        int key;
    }


     private BinaryNode root;
    public int Nonodos;
    public int comparaciones;
}

    
    public static void main(String[] args) {
       Scanner tecl= new Scanner(System.in);
       int q=tecl.nextInt();
       int conCas=1;
       while(conCas<=q){
           int a=tecl.nextInt();
           int [] arreglo= new int [a];
           BinarySearchTree bin=new BinarySearchTree ();
           for (int i=0;i<a;i++){
           arreglo [i]=tecl.nextInt();  
           }
           for (int i=0;i<a;i++){
           //avl.insert(arreglo [i]);
           bin.insert(arreglo [i]);
           }
           
            int array [] = new int [a];
            for(int i=1;i<=a;i++){
            double v = floor((Math.log(i) / Math.log(2)));
            int value = (int) v;
            array[(i-1)]=value;
            }
            int total=0;
            for(int j=0;j<a;j++){
            total+=array[j];            
            }
           System.out.println("Case #"+conCas+":");
           System.out.println(bin.comparaciones+" "+total);
           conCas++;
       }
    
    }
    
}