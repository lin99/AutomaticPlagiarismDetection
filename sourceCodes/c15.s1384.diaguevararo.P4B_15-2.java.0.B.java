import java.util.Scanner;
public class Main {
    public static void main(String[] args)throws Exception {
	Scanner scan = new Scanner(System.in);
	int p = Integer.parseInt(scan.nextLine());
	for (int caso = 1; caso <= p; caso++) {
            int aux=0;
            System.out.println("Case #"+(caso)+":");
            int i2;
            int Na=scan.nextInt();
            int Ma=scan.nextInt();
            int Qa=scan.nextInt();
            i2=(int)(Math.pow(2, Na+1)-1);
            int arb[]=new int[i2];
            int a[]=new int[Na];
            int bar[]=new int[Ma];
            arb[0]=0;
            for(int i=1; i<i2;i++){
                arb[i]=aux;
                    if(i%2==0)  aux++;
            }
            for(int i=0; i<Ma;i++)       bar[i]=scan.nextInt();
            for(int i=0; i<Qa;i++){
		aux=scan.nextInt();
		int Pa=scan.nextInt();
		for(int j=0; j<Na;j++){
                    a[0]=bar[j];							
                    for(int k=1; k<Na;k++)       a[k]=arb[a[k-1]-1]+1;
                    if(aux==a[j]){
                        Pa--;
			aux=arb[aux-1]+1;
                    }
                    else        aux=arb[aux-1]+1;
                    if(Pa==0){
                        System.out.println("game over");
			j=Na;
                    }
                    if(aux==1&&Pa!=0)        System.out.println("win");						
		}
            }
	}
    }
}