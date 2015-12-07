import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		//System.setIn(new FileInputStream("c_2.txt"));
		Scanner sn = new Scanner(System.in);
		int c = sn.nextInt();
		
		for (int i = 1; i <= c; i++) {
			BinarySearchTree<Integer> bst = new BinarySearchTree<>();
			AvlTree<Integer> avlt= new AvlTree();
			ArrayList<Integer>al2 = new ArrayList<>();
			System.out.println("Case #"+i+":");			
			int n = sn.nextInt();
			for (int j = 0; j < n; j++) {
				int m = sn.nextInt();
				bst.insert(m);
				al2.add(m);			
			}
			Collections.sort(al2);	
			for (int j = 0; j < al2.size(); j++) {
				avlt.insert(al2.get(j));
			}
			
			//bst.printTree();
			//avlt.printLevel();
			ArrayList<AvlNode>al= new ArrayList<>();
			al.addAll(avlt.printLevel());
			BinarySearchTree<Integer> bst2 = new BinarySearchTree<>();
			//System.out.println(al.get(0).element+" al");
			
			for (int j = 0; j < al.size(); j++) {
				String cad = ""+al.get(j).element;
				int e = Integer.parseInt(cad);
				bst2.insert(e);
			}
			System.out.println(bst.getCompa()+" "+bst2.getCompa());
			/*
			bst.makeEmpty();
			bst2.makeEmpty();
			avlt.makeEmpty();
			al.clear();
			*/
			
		}
		
		
		
		//avlt.printLevel();
		

	}

}
//BinarySearchTree class
//
//CONSTRUCTION: with no initializer
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//void remove( x )       --> Remove x
//boolean contains( x )  --> Return true if x is present
//Comparable findMin( )  --> Return smallest item
//Comparable findMax( )  --> Return largest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//void printTree( )      --> Print tree in sorted order
//******************ERRORS********************************
//Throws UnderflowException as appropriate

/**
* Implements an unbalanced binary search tree.
* Note that all "matching" is based on the compareTo method.
* @author Mark Allen Weiss
*/
class BinarySearchTree<AnyType extends Comparable<? super AnyType>>
{
	/**
	 * Construct the tree.
	 */
	public BinarySearchTree( )
	{
		root = null;
		compa = 0;
	}

	/**
	 * Insert into the tree; duplicates are ignored.
	 * @param x the item to insert.
	 */
	public void insert( AnyType x )
	{
		root = insert( x, root );
	}

	/**
	 * Remove from the tree. Nothing is done if x is not found.
	 * @param x the item to remove.
	 */
	public void remove( AnyType x )
	{
		root = remove( x, root );
	}

	/**
	 * Find the smallest item in the tree.
	 * @return smallest item or null if empty.
	 */
	public AnyType findMin( ) throws Exception
	{
		if( isEmpty( ) )
			throw new Exception( );
		return findMin( root ).element;
	}

	/**
	 * Find the largest item in the tree.
	 * @return the largest item of null if empty.
	 */
	public AnyType findMax( ) throws Exception
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
	public boolean contains( AnyType x )
	{
		return contains( x, root );
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty( )
	{
		root = null;
		compa = 0;
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
	
	
	public BinaryNode<AnyType> getRoot() {
		return root;
	}

	

	/**
	 * Internal method to insert into a subtree.
	 * @param x the item to insert.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t )
	{
		if( t == null )
			return new BinaryNode<>( x, null, null );

		int compareResult = x.compareTo( t.element );
		

		 if( compareResult < 0 ){
	            t.left = insert( x, t.left );
	            compa++;
		 }
	        else if( compareResult > 0 ){
	            t.right = insert( x, t.right );
	            compa++;
	        }
			
			return t;
	}

	/**
	 * Internal method to remove from a subtree.
	 * @param x the item to remove.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
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

	/**
	 * Internal method to find the smallest item in a subtree.
	 * @param t the node that roots the subtree.
	 * @return node containing the smallest item.
	 */
	private BinaryNode<AnyType> findMin( BinaryNode<AnyType> t )
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
	private BinaryNode<AnyType> findMax( BinaryNode<AnyType> t )
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

	/**
	 * Internal method to print a subtree in sorted order.
	 * @param t the node that roots the subtree.
	 */
	private void printTree( BinaryNode<AnyType> t )
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
		// Key key;
	}


	/** The tree root. */
	private BinaryNode<AnyType> root;
	private int compa;
	
	public int getCompa() {
		return compa;
	}

	public void setCompa(int compa) {
		this.compa = compa;
	}

	

	
	// Test program
	
}




//AvlTree class
//
//CONSTRUCTION: with no initializer
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//void remove( x )       --> Remove x (unimplemented)
//boolean contains( x )  --> Return true if x is present
//boolean remove( x )    --> Return true if x was present
//Comparable findMin( )  --> Return smallest item
//Comparable findMax( )  --> Return largest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//void printTree( )      --> Print tree in sorted order
//******************ERRORS********************************
//Throws UnderflowException as appropriate

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
class AvlTree<AnyType extends Comparable<? super AnyType>>
{
	/**
	 * Construct the tree.
	 */
	public AvlTree( )
	{
		root = null;
	}

	/**
	 * Insert into the tree; duplicates are ignored.
	 * @param x the item to insert.
	 */
	public void insert( AnyType x )
	{
		root = insert( x, root );
	}

	/**
	 * Remove from the tree. Nothing is done if x is not found.
	 * @param x the item to remove.
	 */
	public void remove( AnyType x )
	{
		root = remove( x, root );
	}


	/**
	 * Internal method to remove from a subtree.
	 * @param x the item to remove.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private AvlNode<AnyType> remove( AnyType x, AvlNode<AnyType> t )
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
		return balance( t );
	}

	/**
	 * Find the smallest item in the tree.
	 * @return smallest item or null if empty.
	 */
	public AnyType findMin( ) throws Exception
	{
		if( isEmpty( ) )
			throw new Exception( );
		return findMin( root ).element;
	}

	/**
	 * Find the largest item in the tree.
	 * @return the largest item of null if empty.
	 */
	public AnyType findMax( ) throws Exception
	{
		if( isEmpty( ) )
			throw new Exception( );
		return findMax( root ).element;
	}

	/**
	 * Find an item in the tree.
	 * @param x the item to search for.
	 * @return true if x is found.
	 */
	public boolean contains( AnyType x )
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

	public ArrayList<AvlNode> printLevel( )
	{
		if( isEmpty( ) )
			System.out.println( "Empty tree" );
		else
			return printLevel( root );
		
		return null;
		
		//return printLevel();
	}


	private static final int ALLOWED_IMBALANCE = 1;

	// Assume t is either balanced or within one of being balanced
	private AvlNode<AnyType> balance( AvlNode<AnyType> t )
	{
		if( t == null )
			return t;

		if( height( t.left ) - height( t.right ) > ALLOWED_IMBALANCE )
			if( height( t.left.left ) >= height( t.left.right ) )
				t = rotateWithLeftChild( t );
			else
				t = doubleWithLeftChild( t );
		else
			if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE )
				if( height( t.right.right ) >= height( t.right.left ) )
					t = rotateWithRightChild( t );
				else
					t = doubleWithRightChild( t );

		t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
		return t;
	}

	public void checkBalance( )
	{
		checkBalance( root );
	}

	private int checkBalance( AvlNode<AnyType> t )
	{
		if( t == null )
			return -1;

		if( t != null )
		{
			int hl = checkBalance( t.left );
			int hr = checkBalance( t.right );
			if( Math.abs( height( t.left ) - height( t.right ) ) > 1 ||
					height( t.left ) != hl || height( t.right ) != hr )
				System.out.println( "OOPS!!" );
		}

		return height( t );
	}


	/**
	 * Internal method to insert into a subtree.
	 * @param x the item to insert.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private AvlNode<AnyType> insert( AnyType x, AvlNode<AnyType> t )
	{
		if( t == null )
			return new AvlNode<>( x, null, null );

		int compareResult = x.compareTo( t.element );

		if( compareResult < 0 )
			t.left = insert( x, t.left );
		else if( compareResult > 0 )
			t.right = insert( x, t.right );
		else
			;  // Duplicate; do nothing
		return balance( t );
	}

	/**
	 * Internal method to find the smallest item in a subtree.
	 * @param t the node that roots the tree.
	 * @return node containing the smallest item.
	 */
	private AvlNode<AnyType> findMin( AvlNode<AnyType> t )
	{
		if( t == null )
			return t;

		while( t.left != null )
			t = t.left;
		return t;
	}

	/**
	 * Internal method to find the largest item in a subtree.
	 * @param t the node that roots the tree.
	 * @return node containing the largest item.
	 */
	private AvlNode<AnyType> findMax( AvlNode<AnyType> t )
	{
		if( t == null )
			return t;

		while( t.right != null )
			t = t.right;
		return t;
	}

	/**
	 * Internal method to find an item in a subtree.
	 * @param x is item to search for.
	 * @param t the node that roots the tree.
	 * @return true if x is found in subtree.
	 */
	private boolean contains( AnyType x, AvlNode<AnyType> t )
	{
		while( t != null )
		{
			int compareResult = x.compareTo( t.element );

			if( compareResult < 0 )
				t = t.left;
			else if( compareResult > 0 )
				t = t.right;
			else
				return true;    // Match
		}

		return false;   // No match
	}

	/**
	 * Internal method to print a subtree in sorted order.
	 * @param t the node that roots the tree.
	 */
	private void printTree( AvlNode<AnyType> t )
	{
		if( t != null )
		{
			printTree( t.left );
			System.out.println( t.element );
			printTree( t.right );
		}
	}

	public ArrayList<AvlNode> printLevel( AvlNode<AnyType> t){

		ArrayList<AvlNode>li=new ArrayList <>();
		ArrayDeque<AvlNode> c = new ArrayDeque<>(); // cola de BinaryNode
		AvlNode aux;
		c.add(t);
		
		while(!c.isEmpty()){
			
			aux = c.remove();
			li.add(aux);
			//System.out.println(aux.element);
			if(aux.left != null)
				c.add(aux.left);
			if(aux.right != null)
				c.add(aux.right);

		}
		return li;

	}
	/**
	 * Return the height of node t, or -1, if null.
	 */
	private int height( AvlNode<AnyType> t )
	{
		return t == null ? -1 : t.height;
	}

	/**
	 * Rotate binary tree node with left child.
	 * For AVL trees, this is a single rotation for case 1.
	 * Update heights, then return new root.
	 */
	private AvlNode<AnyType> rotateWithLeftChild( AvlNode<AnyType> k2 )
	{
		AvlNode<AnyType> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
		k1.height = Math.max( height( k1.left ), k2.height ) + 1;
		return k1;
	}

	/**
	 * Rotate binary tree node with right child.
	 * For AVL trees, this is a single rotation for case 4.
	 * Update heights, then return new root.
	 */
	private AvlNode<AnyType> rotateWithRightChild( AvlNode<AnyType> k1 )
	{
		AvlNode<AnyType> k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = Math.max( height( k1.left ), height( k1.right ) ) + 1;
		k2.height = Math.max( height( k2.right ), k1.height ) + 1;
		return k2;
	}

	/**
	 * Double rotate binary tree node: first left child
	 * with its right child; then node k3 with new left child.
	 * For AVL trees, this is a double rotation for case 2.
	 * Update heights, then return new root.
	 */
	private AvlNode<AnyType> doubleWithLeftChild( AvlNode<AnyType> k3 )
	{
		k3.left = rotateWithRightChild( k3.left );
		return rotateWithLeftChild( k3 );
	}

	/**
	 * Double rotate binary tree node: first right child
	 * with its left child; then node k1 with new right child.
	 * For AVL trees, this is a double rotation for case 3.
	 * Update heights, then return new root.
	 */
	private AvlNode<AnyType> doubleWithRightChild( AvlNode<AnyType> k1 )
	{
		k1.right = rotateWithLeftChild( k1.right );
		return rotateWithRightChild( k1 );
	}



	/** The tree root. */
	private AvlNode<AnyType> root;


	
}
class AvlNode<AnyType>
{
	// Constructors
	AvlNode( AnyType theElement )
	{
		this( theElement, null, null );
	}

	AvlNode( AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt )
	{
		element  = theElement;
		left     = lt;
		right    = rt;
		height   = 0;
	}

	AnyType           element;      // The data in the node
	AvlNode<AnyType>  left;         // Left child
	AvlNode<AnyType>  right;        // Right child
	int               height;       // Height
}