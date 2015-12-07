import java.util.*;

 class main {
	
	
	public static BinarySearchTrees<Integer> bts2;
	
	 public static void arreglar(ArrayList<Integer> arr){
		int x;
		int z = arr.size();
		ArrayList<Integer> a = new ArrayList<>();
		x = z/2;
		
		bts2.insert(arr.get(x));
		
		if(z!=1){
		if( z== 3){
			bts2.insert(arr.get(0));
			bts2.insert(arr.get(2));
		}else if(z == 2){
			bts2.insert(arr.get(0));
		}
		else{
		for(int i = 0 ; i < x; i++ ){
			
			a.add(i, arr.get(i));
			
		}
		arreglar(a);
		for(int i = (x+1), j = 0; i < z; i++,j++){
			a.add(j, arr.get(i));
			
		}
		arreglar(a);
		}
		}
		
	}
	
	
	
	public static void main(String args[]){
	
		int t;
                BufferReader br = new BufferedReader( new InputStreamReader(System.in) );
                StringTokenizer st;
//		Scanner in = new Scanner(System.in);

		t = Integer.parseInt( br.readLine() );//in.nextInt();
		
		for(int i = 0 ; i < t ; i++){
			int c,d;
			System.out.println("Case #"+(i+1)+":");
			
			int N;
			N = Integer.parseInt( br.readLine() );//in.nextInt();
			int[] num = new int[N];
			BinarySearchTrees<Integer> bts = new BinarySearchTrees<>();
			BinarySearchTrees<Integer> bts3 = new BinarySearchTrees<>();
			bts2 = new BinarySearchTrees<>();
			


                        String line = br.readLine() ;
                        st = new StringTokenizer( line );
                        while(st.hasMoreTokens() ){
                    		num[j] = Integer.parseInt( st.nextToken() );
				bts.insert(num[j]);
                        }
			//for(int j = 0 ; j < N ; j++){
			//	num[j] = in.nextInt();
			//	bts.insert(num[j]);
			//}
			c = bts.getComp();
			System.out.print(c);
			ArrayList<Integer> arr = new ArrayList<>();
			arr = bts.printTree();
			Collections.sort(arr);
			
			//System.out.println();
			main.arreglar(arr);
			
			arr = bts2.printTree();
			
						
			
			for(int j = 0; j < N; j++){
				bts3.insert(arr.get(j));
				
			}
			
			
			
			d = bts3.getComp();
			
			System.out.println(" "+d);
		
			
			bts.reset();
			bts2.reset();
			bts2.makeEmpty();
			bts.makeEmpty();
			bts3.reset();
			bts3.makeEmpty();
			
			
		}
	}
	
	}

class BinarySearchTrees<AnyType extends Comparable<? super AnyType>>
	{
		 
		 private int  comp = 0 ;
		 ArrayList<AnyType> i = new ArrayList<>();
	   
		private BinaryNode<AnyType> root;
		
		public int getComp(){
			return this.comp;
		}
		public void reset(){
			this.comp = 0;
		}
		 
	    public BinarySearchTrees( )
	    {
	        root = null;
	    }

	    

	    public void insert( AnyType x )
	    {
	        root = insert( x, root );
	    }
	    
	    public void makeEmpty( )
	    {
	        root = null;
	    }
	   

	    private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t )
	    {	
	    	
	        if( t == null ){
	            return new BinaryNode<>( x, null, null );}
	        
	        int compareResult = x.compareTo( t.element );
	        
	        if( compareResult < 0 ){
	        	comp ++;
	            t.left = insert( x, t.left );
	            }
	        else if( compareResult > 0 ){
	        	comp ++;
	            t.right = insert( x, t.right );
	            }
	         
	        return t;
	    }

	    

	    

	   

	   

	    public boolean isEmpty( )
	    {
	        return root == null;
	    }

	    

	    public ArrayList<AnyType> printTree( )
	    {	
	    	
	        if( isEmpty( ) ){}
	            else
	         printTree( root );
	        
	        return i;
	    }

	   

	    private ArrayList<AnyType> printTree( BinaryNode<AnyType> t )
	    {		    	
	        if( t != null )
	        	
	        {	
	        	i.add(t.element);
	            printTree( t.left );
	            printTree( t.right );
	        }
	        return i;
	    }

	    
	   
	    private class BinaryNode<AnyType>
	    {
	            
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

	        AnyType element;            
	        BinaryNode<AnyType> left;   
	        BinaryNode<AnyType> right;  
	    }  
	}
