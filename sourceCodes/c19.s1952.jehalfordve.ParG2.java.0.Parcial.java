/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parcial;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.EmptyStackException;
import java.util.Scanner;
/**
 *
 * @author Estudiante
 */
public class Parcial {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
		Scanner scan;
                int con=0;
                int con2=0;
                int c=0;
                int x=0;
                MyPila<Integer> p1= new MyPila<Integer>();
                MyPila<Integer> p2= new MyPila<Integer>();
		File f = new File( "AG2_1.txt" );
		if ( f.exists() ) {
			scan = new Scanner( f );
			//System.setOut( new PrintStream( "AG2_Out1.txt" ) );
		} else {
			scan = new Scanner( System.in );
		}

		int t = Integer.parseInt( scan.nextLine() );
		for ( int test = 0; test < t; test++ ) {
                    con=0;
                    c++;
                    con2=0;
		 int n = Integer.parseInt( scan.nextLine() );//numero de comandos
                        for( int i = 0 ; i < n ; i++ ){
				int arr[] = readInts(scan.nextLine());
				int command = arr[0]; // tipo de comando (1 o 2)
				
                                if(command==1){
                                x=arr[1];
                                p1.push(x);
				
                                }
                               
                                if(command==2){
                                x=arr[1];
				p2.push(x);
                                }
                        
                        
                            
                              for (int j = 0; j < arr.length; j++) {
                                
                                
                                  if(p1.theSize==p2.theSize){
                                  
                                 if(p1.peek()==p2.peek()){
                                    con++;
                                    p1.pop();
                                    p2.pop();
                                }else{
                                  con2++;
                                   
                                  
                                    p1.pop();
                                    p2.pop();  
                                    
                                }
                                
                            }
                              }
                              if(p1.theSize!=p2.theSize){
                                       System.out.println("Case #" + c +":"); 
                                  System.out.println("imposible");
                              
                                      
                                      }else{
                        
                        if(con==arr.length){
                          System.out.println("Case #" + c +":"); 
                                  System.out.println("queue");  
                        }
                        
                         if(con2==arr.length){
                          System.out.println("Case #" + c +":"); 
                                  System.out.println("stack ");  
                        }else{
                           System.out.println("Case #" + c +":"); 
                                  System.out.println("noestoy seguro");   
                         }
                        
                              }
                        
                        }
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







public  static class MyPila<AnyType> 
{
    /**
     * Construct an empty ArrayList.
     */
    public MyPila( )
    {
        doClear( );
    }
    
    /**
     * Returns the number of items in this collection.
     * @return the number of items in this collection.
     */
    public int size( )
    {
        return theSize;
    }
    
    /**
     * Returns true if this collection is empty.
     * @return true if this collection is empty.
     */ 
    public boolean isEmpty( )
    {
        return size( ) == 0;
    }
    
    /**
     * Returns the item at position idx.
     * @param idx the index to search in.
     * @throws ArrayIndexOutOfBoundsException if index is out of range.
     */
  /*  public AnyType get( int idx )
    {
        if( idx < 0 || idx >= size( ) )
            throw new ArrayIndexOutOfBoundsException( "Index " + idx + "; size " + size( ) );
        return theItems[ idx ];    
    }
        
    /**
     * Changes the item at position idx.
     * @param idx the index to change.
     * @param newVal the new value.
     * @return the old value.
     * @throws ArrayIndexOutOfBoundsException if index is out of range.
     */
   /* public AnyType set( int idx, AnyType newVal )
    {
        if( idx < 0 || idx >= size( ) )
            throw new ArrayIndexOutOfBoundsException( "Index " + idx + "; size " + size( ) );
        AnyType old = theItems[ idx ];    
        theItems[ idx ] = newVal;
        
        return old;    
    }*/

    @SuppressWarnings("unchecked")
    public void ensureCapacity( int newCapacity )
    {
        if( newCapacity < theSize )
            return;

        AnyType [ ] old = theItems;
        theItems = (AnyType []) new Object[ newCapacity ];
        for( int i = 0; i < size( ); i++ )
            theItems[ i ] = old[ i ];
    }
    
  /**
	     * Change the size of this collection to zero.
	     */
	    public void clear( )
	    {
	        doClear( );
	    }
	    
	    private void doClear( )
	    {
	        theSize = 0;
	        ensureCapacity( DEFAULT_CAPACITY );
	    }
	    
	    
	    
	    
	    public boolean push(AnyType x){
	        if( theItems.length == size( ) )
	            ensureCapacity( size( ) * 2 + 1 );

	       theItems[theSize++]=x;
	    	
	       return true;
	    }
	    
	    public AnyType pop(){
	    	if(isEmpty())
	            throw new EmptyStackException( );

	        return theItems[--theSize];
	     }
	    
	   public AnyType peek(){
		   if(isEmpty())
	           throw new EmptyStackException( );

	        return theItems[theSize-1];
	     }
   
    
    private static final int DEFAULT_CAPACITY = 10;
    
    private AnyType [ ] theItems;
    private int theSize;

    
}




















}
