import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;


public class parcial {
	
	public static void main(String[] args) throws FileNotFoundException {
    
    	Scanner scan;
			scan = new Scanner( System.in );

		int t = Integer.parseInt( scan.nextLine() );
		MyLinkedList<Integer> res = new MyLinkedList<>( );
		for ( int test = 0; test < t; test++ ) {
			MyLinkedList<Integer> lst = new MyLinkedList<>( );
			int n = Integer.parseInt( scan.nextLine() );//numero de comandos
			int primero =0;
			int ultimo  =0;
			boolean stack = false;
			boolean queue = false;
			for( int i = 0 ; i < n ; i++ ){
				int arr[] = readInts(scan.nextLine());
				int command = arr[0]; // tipo de comando (1 o 2)
				int x = arr[1]; // dato que se va a agregar o sacar
				
				if (arr[0] == 1)
					lst.add(arr[1]);
				else if(arr[0] == 2){
					primero = lst.get(0);
					ultimo = lst.get(lst.size()-1);
					
					if (ultimo == primero){
						if (arr[1] == primero)
							stack = true;
						if (arr[1]== ultimo)
								queue = true;	
								
						
					}
					else{
						if (arr[1] == primero){
							stack = true;
							queue = false;
						}

						if (arr[1]== ultimo){
							queue = true;	
							stack = false;
						}
					}

					
				}
			}
			if (stack&&queue)
				res.add(4);
			else if(stack)
				res.add(1);
			else if (queue)
				res.add(2);
			else if (!stack&&!queue)
				res.add(3);
		}
		
		for ( int test = 0; test < t; test++ ) {
			System.out.println("Case #"+(test+1)+":");
			int a=res.remove(0);
			if(a==1)
				System.out.println("stack");
			else if(a==2)
				System.out.println("queue");
			else if(a==3)
				System.out.println("imposible");
			else if(a==4)
				System.out.println("no estoy seguro");
		}
	}

	public static int[] readInts(String cad){
		String line[] = cad.split( " " );
		int arr[] = new int[line.length];
		for( int i = 0 ; i < arr.length ; i++ ){
			arr[i] = Integer.parseInt( line[i] );
		}
		return arr;
	}

	
	/**
	 * LinkedList class implements a doubly-linked list.
	 */
	public static class MyLinkedList<AnyType> implements Iterable<AnyType>
	{
	    /**
	     * Construct an empty LinkedList.
	     */
	    public MyLinkedList( )
	    {
	        doClear( );
	    }
	    
	    private void clear( )
	    {
	        doClear( );
	    }
	    
	    /**
	     * Change the size of this collection to zero.
	     */
	    public void doClear( )
	    {
	        beginMarker = new Node<>( null, null, null );
	        endMarker = new Node<>( null, beginMarker, null );
	        beginMarker.next = endMarker;
	        
	        theSize = 0;
	        modCount++;
	    }
	    
	    /**
	     * Returns the number of items in this collection.
	     * @return the number of items in this collection.
	     */
	    public int size( )
	    {
	        return theSize;
	    }
	    
	    public boolean isEmpty( )
	    {
	        return size( ) == 0;
	    }
	    
	    /**
	     * Adds an item to this collection, at the end.
	     * @param x any object.
	     * @return true.
	     */
	    public boolean add( AnyType x )
	    {
	        
	        add( size( ), x );   
	        return true;         
	    }
	    
	    /**
	     * Adds an item to this collection, at specified position.
	     * Items at or after that position are slid one position higher.
	     * @param x any object.
	     * @param idx position to add at.
	     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
	     */
	    public void add( int idx, AnyType x )
	    {
	        addBefore( getNode( idx, 0, size( ) ), x );
	    }
	    
	    /**
	     * Adds an item to this collection, at specified position p.
	     * Items at or after that position are slid one position higher.
	     * @param p Node to add before.
	     * @param x any object.
	     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
	     */    
	    private void addBefore( Node<AnyType> p, AnyType x )
	    {
	        Node<AnyType> newNode = new Node<>( x, p.prev, p );
	        newNode.prev.next = newNode;
	        p.prev = newNode;         
	        theSize++;
	        modCount++;
	    }   
	    
	    
	    /**
	     * Returns the item at position idx.
	     * @param idx the index to search in.
	     * @throws IndexOutOfBoundsException if index is out of range.
	     */
	    public AnyType get( int idx )
	    {
	        return getNode( idx ).data;
	    }
	        
	    /**
	     * Changes the item at position idx.
	     * @param idx the index to change.
	     * @param newVal the new value.
	     * @return the old value.
	     * @throws IndexOutOfBoundsException if index is out of range.
	     */
	    public AnyType set( int idx, AnyType newVal )
	    {
	        Node<AnyType> p = getNode( idx );
	        AnyType oldVal = p.data;
	        
	        p.data = newVal;   
	        return oldVal;
	    }
	    
	    /**
	     * Gets the Node at position idx, which must range from 0 to size( ) - 1.
	     * @param idx index to search at.
	     * @return internal node corresponding to idx.
	     * @throws IndexOutOfBoundsException if idx is not between 0 and size( ) - 1, inclusive.
	     */
	    private Node<AnyType> getNode( int idx )
	    {
	        return getNode( idx, 0, size( ) - 1 );
	    }

	    /**
	     * Gets the Node at position idx, which must range from lower to upper.
	     * @param idx index to search at.
	     * @param lower lowest valid index.
	     * @param upper highest valid index.
	     * @return internal node corresponding to idx.
	     * @throws IndexOutOfBoundsException if idx is not between lower and upper, inclusive.
	     */    
	    private Node<AnyType> getNode( int idx, int lower, int upper )
	    {
	        Node<AnyType> p;  
	        if( idx < lower || idx > upper )
	            throw new IndexOutOfBoundsException( "getNode index: " + idx + "; size: " + size( ) );
	        if( idx < size( ) / 2 ){
	            p = beginMarker.next;
	            for( int i = 0; i < idx; i++ )
	                p = p.next;            
	        }
	        else{
	            p = endMarker;
	            for( int i = size( ); i > idx; i-- )
	                p = p.prev;
	        }         
	        return p;
	    }
	    
	    /**
	     * Removes an item from this collection.
	     * @param idx the index of the object.
	     * @return the item was removed from the collection.
	     */
	    public AnyType remove( int idx )
	    {
	        return remove( getNode( idx ) );
	    }
	    
	    /**
	     * Removes the object contained in Node p.
	     * @param p the Node containing the object.
	     * @return the item was removed from the collection.
	     */
	    private AnyType remove( Node<AnyType> p )
	    {
	        p.next.prev = p.prev;
	        p.prev.next = p.next;
	        theSize--;
	        modCount++;
	        
	        return p.data;
	    }
	    
	    /**
	     * Returns a String representation of this collection.
	     */
	    public String toString( )
	    {
	        StringBuilder sb = new StringBuilder( "[ " );

	        for( AnyType x : this )
	            sb.append( x + " " );
	        sb.append( "]" );

	        return new String( sb );
	    }

	    /**
	     * Obtains an Iterator object used to traverse the collection.
	     * @return an iterator positioned prior to the first element.
	     */
	    public java.util.Iterator<AnyType> iterator( )
	    {
	        return new LinkedListIterator( );
	    }

	    /**
	     * This is the implementation of the LinkedListIterator.
	     * It maintains a notion of a current position and of
	     * course the implicit reference to the MyLinkedList.
	     */
	    private class LinkedListIterator implements java.util.Iterator<AnyType>
	    {
	        private Node<AnyType> current = beginMarker.next;
	        private int expectedModCount = modCount;
	        private boolean okToRemove = false;
	        
	        public boolean hasNext( )
	        {
	            return current != endMarker;
	        }
	        
	        public AnyType next( )
	        {
	            if( modCount != expectedModCount )
	                throw new java.util.ConcurrentModificationException( );
	            if( !hasNext( ) )
	                throw new java.util.NoSuchElementException( ); 
	                   
	            AnyType nextItem = current.data;
	            current = current.next;
	            okToRemove = true;
	            return nextItem;
	        }
	        
	        public void remove( )
	        {
	            if( modCount != expectedModCount )
	                throw new java.util.ConcurrentModificationException( );
	            if( !okToRemove )
	                throw new IllegalStateException( );
	                
	            MyLinkedList.this.remove( current.prev );
	            expectedModCount++;
	            okToRemove = false;       
	        }
	    }
	    
	    /**
	     * This is the doubly-linked list node.
	     */
	    private static class Node<AnyType>
	    {
	        public Node( AnyType d, Node<AnyType> p, Node<AnyType> n )
	        {
	            data = d; prev = p; next = n;
	        }
	        
	        public AnyType data;
	        public Node<AnyType>   prev;
	        public Node<AnyType>   next;
	    }
	    
	    private int theSize;
	    private int modCount = 0;
	    private Node<AnyType> beginMarker;
	    private Node<AnyType> endMarker;
	}
}
