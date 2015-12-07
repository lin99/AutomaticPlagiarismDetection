import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class C <AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the tree.
     */
    public C( )
    {
        root = null;
        contador=0;
    }
    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( AnyType x)
    {
        root = insert( x , root );
    }

    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> insert (AnyType x, BinaryNode<AnyType> t)
    {
  

    	if( t == null )
            return new BinaryNode<>( x, null, null );
        
        int compareResult = x.compareTo( t.element );
        contador++;
            
        if( compareResult < 0 )
            t.left = insert( x, t.left);
        else if( compareResult > 0 )
            t.right = insert( x, t.right );
        else{
             // Duplicate; do nothing
        }
    	
        return t;
    }

    /**
     * Internal method to compute height of a subtree.
     * @param t the node that roots the subtree.
     */
    private int height( BinaryNode<AnyType> t )
    {
        if( t == null )
            return -1;
        else
            return 1 + Math.max( height( t.left ), height( t.right ) );    
    }
    // Basic node stored in unbalanced binary search trees
    private static class BinaryNode<AnyType>
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

      /** The tree root. */
    private BinaryNode<AnyType> root;
    private int contador;
        // Test program
    public static void main( String [ ] args ) throws Exception
    {		
    		Scanner input;
    		File f = new File("C_2.txt");
    		if (f.exists()) {
    			input = new Scanner(f);
    		} else {
    			input = new Scanner(System.in);
    		}
    		int casos = Integer.parseInt(input.nextLine());
    		for (int caso = 0; caso < casos; caso++) {
    				C<Integer> arbol = new C<> ();
    				int cantidad = Integer.parseInt(input.nextLine());
    				int[] n = new int[cantidad];
    				String lines[] = input.nextLine().split(" ");
    				for (int i = 0; i < cantidad; i++) {
    					n[i] = Integer.parseInt(lines[i]);
    				}	
    				int comparaciones=2;
    				int h=0;
    				for(int i=0; i<n.length;i++){
    					arbol.insert(n[i]);
    				}        	
    				while(n.length>comparaciones-1){
    					comparaciones*=2;
    					h++;
    				}
    				comparaciones=2;
    				int comph;
    				int compmin=0;
    				int d=0;
    				for(int i=1;i<=h;i++){
    					comph=comparaciones*i;
    					compmin+=comph;
    					comparaciones*=2;
    					d=i;
    				}
    				comparaciones--;
    				comph=n.length-comparaciones;	
    				comph*=d;
    				compmin+=comph;
    				System.out.println("Case #"+(caso+1)+":");
    				System.out.println(arbol.contador+" "+compmin);
    		}
    	}	
	}