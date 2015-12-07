
import static java.lang.Math.floor;
import java.util.Scanner;

public class Main {
    
    public static class BinarySearchTree<AnyType extends Comparable<? super AnyType>>
    {
        public int comparator;
        public BinarySearchTree( )
        {
            root = null;
            comparator=0;
        }
        public void insert( AnyType x )
        {
            root = insert( x, root );
        }
        public void remove( AnyType x )
        {
            root = remove( x, root );
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
        private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t )
        {
            if( t == null )
                return new BinaryNode<>( x, null, null );

            int compareResult = x.compareTo( t.element );

            if( compareResult < 0 ){
                t.left = insert( x, t.left );
                comparator++;
            }else if( compareResult > 0 ){
                t.right = insert( x, t.right );
                comparator++;
            }else
                ;  // Duplicate; do nothing
            return t;
        }
        private BinaryNode<AnyType> remove( AnyType x, BinaryNode<AnyType> t )
        {
            if( t == null )
                return t;   // Item not found; do nothing

            int compareResult = x.compareTo( t.element );

            if( compareResult < 0 )
                t.left = remove( x, t.left );
            else if( compareResult > 0 )
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
        private boolean contains( AnyType x, BinaryNode<AnyType> t )
        {
            if( t == null )
                return false;

            int compareResult = x.compareTo( t.element );

            if( compareResult < 0 )
                return contains( x, t.left );
            else if( compareResult > 0 )
                return contains( x, t.right );
            else
                return true;    // Match
        }
        private int height( BinaryNode<AnyType> t )
        {
            if( t == null )
                return -1;
            else
                return 1 + Math.max( height( t.left ), height( t.right ) );    
        }
        private class BinaryNode<AnyType>
        {
                // Constructors
            BinaryNode( AnyType theElement )
            {
                this( theElement, null, null );
            }

            BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt )
            {
                element  = theElement;
                left     = lt;
                right    = rt;
            }

            AnyType element;            // The data in the node
            BinaryNode<AnyType> left;   // Left child
            BinaryNode<AnyType> right;  // Right child
        }
        private BinaryNode<AnyType> root;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t=sc.nextInt();
        
        //t casos
        for(int j=0;j<t;j++){
        BinarySearchTree Arbolito = new BinarySearchTree();
        int Q = sc.nextInt();
        int put []=new int[Q];
        for(int i=0;i<Q;i++){
            put[i]=sc.nextInt();
            Arbolito.insert(put[i]);
        }
        //A061168 [Recorrido optimo del arbol ordenado]
        
        int array [] = new int [Q];
        for(int i=1;i<=Q;i++){
            double v = floor((Math.log(i) / Math.log(2)));
            int value = (int) v;
            array[(i-1)]=value;
        }
        int total=0;
        for(int k=0;k<Q;k++){
            total+=array[k];            
        }
        System.out.println("Case #"+(j+1)+":");
        System.out.println(Arbolito.comparator+" "+total);
        }
    }
    
}

