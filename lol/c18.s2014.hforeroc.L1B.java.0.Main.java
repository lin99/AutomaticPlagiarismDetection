    import java.util.Scanner;
     
     
    public class Main {
    	public static void main(String[] args) {
    		Scanner numero = new Scanner(System.in);
    		int v = 1;
    		int [][] matriz;
            int aT;
            int bF;
            int cC;
            int dC;
            while(numero.hasNext()){
                aT = numero.nextInt();
                matriz = new int[aT][aT];
                dC = 0;
     
                for(int f=0;f<aT;f++){//i
                    for(int c=0;c<aT;c++){//j
                        matriz[f][c]=numero.nextInt();
                    }
                }
     
                for(int c1=0;c1<aT;c1++){
                    for(int c2=0;c2<aT;c2++){
                        bF=0;
                        cC=0;
                        for(int f=0;f<aT;f++){
                            bF = bF + matriz[c1][f];
                        }
                        for(int c=0;c<aT;c++){
                            cC = cC + matriz[c][c2];
                        }
                        if(bF < cC){
                            dC++;
                        }
                    }
                }
                System.out.println("Case #"+v+":");
                System.out.println(dC);
                v++;
            }
     
    	}
     
    }