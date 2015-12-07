import static java.lang.Math.floor;
import java.util.Scanner;

public class Main {

    public static class BinarySearchTree
{
    /**
     * Construct the tree.
     */
    public BinarySearchTree( )
    {
        root = null;
        Nonodos=1;
        comparaciones=0;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( int x )
    {
        root = insert( x, root );
    }

  
    public void remove( int x )
    {
        root = remove( x, root );
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public int findMin( ) throws Exception
    {
        if( isEmpty( ) )
            throw new Exception( );
        return findMin( root ).element;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public int findMax( ) throws Exception
    {
        if( isEmpty( ) )
            throw new Exception( );
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains( int x )
    {
        return contains( x, root );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( )
    {
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }

    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode insert( int x, BinaryNode t )
    {
        if( t == null )
            return new BinaryNode( x, null, null,Nonodos );
            //Nonodos++;
        //int compareResult = x.compareTo( t.element );
        
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

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the largest item.
     */
    private BinaryNode findMax( BinaryNode t )
    {
        if( t != null )
            while( t.right != null )
                t = t.right;

        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains( int x, BinaryNode t )
    {
        if( t == null )
            return false;
            
        //int compareResult = x.compareTo( t.element );
            
        if( x < t.element ){
            return contains( x, t.left );
        } else if( x > t.element ){
            return contains( x, t.right );
        }else{
            return true;    // Match 
        }
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the subtree.
     */
    private void printTree( BinaryNode t )
    {
        if( t != null )
        {
            printTree( t.left );
            System.out.println( t.element );
            printTree( t.right );
        }
    }

    /**
     * Internal method to compute height of a subtree.
     * @param t the node that roots the subtree.
     */
    private int height( BinaryNode t )
    {
        if( t == null )
            return -1;
        else
            return 1 + Math.max( height( t.left ), height( t.right ) );    
    }
    
    // Basic node stored in unbalanced binary search trees
    private static class BinaryNode
    {
            // Constructors
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


      /** The tree root. */
    private BinaryNode root;
    public int Nonodos;
    public int comparaciones;
}

    
    public static void main(String[] args) {
       Scanner tecl= new Scanner(System.in);
       int t=tecl.nextInt();
       int conCas=1;
       while(conCas<=t){
           int n=tecl.nextInt();
           int [] arreglo= new int [n];
           //AvlTree avl=new AvlTree ();
           //AvlTree avl2=new AvlTree ();
           BinarySearchTree bin=new BinarySearchTree ();
           for (int i=0;i<n;i++){
           arreglo [i]=tecl.nextInt();  
           }
           for (int i=0;i<n;i++){
           //avl.insert(arreglo [i]);
           bin.insert(arreglo [i]);
           }
           
            int array [] = new int [n];
            for(int i=1;i<=n;i++){
            double v = floor((Math.log(i) / Math.log(2)));
            int value = (int) v;
            array[(i-1)]=value;
            }
            int total=0;
            for(int j=0;j<n;j++){
            total+=array[j];            
            }
           //amplitud(avl,avl2);
           System.out.println("Case #"+conCas+":");
           System.out.println(bin.comparaciones+" "+total);
           conCas++;
       }
    
    }
    
}