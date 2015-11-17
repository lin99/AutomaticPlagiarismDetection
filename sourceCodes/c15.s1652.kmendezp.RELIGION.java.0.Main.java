import java.util.Scanner;

public class Main {

    
    public static void main(String[] args) {
            Scanner tecl=new Scanner(System.in);
            int concas=1;
            while (tecl.hasNext()){
            int N=tecl.nextInt();
            int M=tecl.nextInt();
            if (N==0 && M==0){
            break;
            }
            int Cont=1;
            int [] arreglo=new int [N];
            for (int q=1;q<=N;q++){
                arreglo [q-1]=q;
            }
            
            for (int q=0;q<M;q++){
                int i=tecl.nextInt();
                int j=tecl.nextInt();
                arreglo [j-1]=arreglo [i-1];
            }
            for (int x = 0; x < N; x++) {
                int aux;
                for (int y = x+1; y < N; y++) { 
                    if (arreglo [y] < arreglo [x]){ 
                    aux = arreglo [x]; 
                    arreglo [x] = arreglo [y]; 
                    arreglo [y] = aux; 
                    } 
                } 
            } 
            for (int q=0;q<N-1;q++){
               if(arreglo [q]!=arreglo [q+1])
                Cont++;            
            }
           System.out.println("Case "+concas+": "+Cont);
           concas++;
    } 
    }
    
}
