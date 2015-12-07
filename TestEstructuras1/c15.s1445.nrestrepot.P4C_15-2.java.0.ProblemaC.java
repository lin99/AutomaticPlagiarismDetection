/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoiv;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author PERSONAL
 */
public class ProblemaC {
 
    
    public static void main(String[] args) throws FileNotFoundException {
                BinarySearchTree<Integer> mainTree = new BinarySearchTree<>();
               BinarySearchTree<Integer> secondTree = new BinarySearchTree<>();
		Scanner scan;
                int firstR = 0, secondR = 0;
                
		File f = new File("C_2.txt");
		if (f.exists()) {
			scan = new Scanner(f);
		} else {
			scan = new Scanner(System.in);
		}
                int t = Integer.parseInt(scan.nextLine());
                int nCases = 1;
                for (int i = 0; i < t; i++) {
                    mainTree.makeEmpty();
                    secondTree.makeEmpty();
                    int numbers = Integer.parseInt(scan.nextLine());
                    String theNumbers = scan.nextLine();
                   
                    String[] line = theNumbers.split(" ");
                    for (int j = 0; j < line.length; j++) {
                       mainTree.insert(Integer.parseInt(line[j]));
                    }
                    
                    int[] sorting = new int[line.length];
                    for (int j = 0; j < line.length; j++) {
                        sorting[j] = Integer.parseInt(line[j]);
                    }
                    Arrays.sort(sorting);
                    sort(sorting, secondTree);
                    firstR = mainTree.getComparisions();
                    secondR = secondTree.getComparisions();
                    System.out.println("Case #" + nCases + ":");
                    System.out.println(firstR + " " + secondR);
                    nCases++;
                   
                }
                
    }
    

    
    public static void sort(int[] f, BinarySearchTree<Integer> tree) {
       
        if(f.length == 1)tree.insert(f[0]);
        else {
            sortL(f,  tree);
            sortR(f,  tree);
        }
    }

    public static void sortL(int[] s, BinarySearchTree<Integer> tree) {
        String building = "";
        String[] i = {""};
        if (s.length==0 || s.length == 1);
        else {
            tree.insert(s[s.length / 2]);
            for (int j = 0; j < s.length / 2; j++) {
                if (building != "")building = building + " " + s[j];
                else building = building + s[j];
            }
            i = building.split(" ");
            int[]  tmp = new int[i.length];
            for (int j = 0; j < i.length; j++) {
                tmp[j] = Integer.parseInt(i[j]);
                
            }
            sort(tmp, tree);
        }
    }

    public static void sortR(int[] s, BinarySearchTree<Integer> tree) {
        String building = "";
        String[] d = {""};
        if (s.length==0 || s.length == 1);
        else {
            tree.insert(s[(s.length / 2)]);
            for (int k = (s.length / 2); k < s.length; k++) {
                if (building != "")building = building + " " + s[k];
                else building = building + s[k];
            }
            d = building.split(" ");
             int[]  tmp = new int[d.length];
            for (int j = 0; j < d.length; j++) {
                tmp[j] = Integer.parseInt(d[j]);
                
            }
            sort(tmp, tree);
        }
    }
    
    
public static class BinarySearchTree<AnyType extends Comparable<? super AnyType>>
{  
   
    public BinarySearchTree( )
    {
        root = null;
        comparisions = 0;
    }

    public void insert( AnyType x )
    {
        if(!contains(x))root = insert( x, root );
    }


    public boolean contains( AnyType x )
    {
        return contains( x, root );
    }

   
    public void makeEmpty( )
    {
        root = null;
        comparisions = 0;
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
            comparisions++;  
            t.left = insert( x, t.left );
        }
        else if( compareResult > 0 ){
            comparisions++;  
            t.right = insert( x, t.right );
        }
        else
            ;  // Duplicate; do nothing
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

        /**
         * @return the comparisions
         */
        public int getComparisions() {
            return comparisions;
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
    private int comparisions;

  
}
    

}
