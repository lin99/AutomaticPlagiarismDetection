import java.util.Scanner;


public class Main {

	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);	
		int caso=1;
		boolean casos=true;
		while(casos){
			int personas= in.nextInt();
			int encuestas=in.nextInt();
			int religiones=personas;
			
			if(personas==0 && encuestas==0){ break;}
			
			int[] reli=new int[personas];
			for(int a=1;a<=personas;a++){
				reli[a-1]=a;
				//System.out.println(reli[a]);
			}
			
			for(int a=0;a<encuestas;a++){
				int per1=in.nextInt();
				int per2=in.nextInt();
				if(reli[per1-1]<reli[per2-1]){
					reli[per2-1]=reli[per1-1];
				}else if(reli[per2-1]<reli[per1-1]){
					reli[per1-1]=reli[per2-1];
				}				
			}
			for(int a=0;a<personas;a++){
				for(int b=0;b<personas;b++){
					if(reli[a]==reli[b]&&a!=b){
						reli[b]=0;
					}
				}
			}
			for(int a=0;a<personas;a++){
				if(reli[a]==0){
					religiones=religiones-1;
				}
			}
			
			
			System.out.print("Case "+caso+": ");
			System.out.println(religiones);
			caso=caso+1;
						
		}
		
	}

}