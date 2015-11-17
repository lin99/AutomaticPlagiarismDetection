import java.util.*;

class PuntoB{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int casos = sc.nextInt();
		for(int caso=1; caso<= casos; caso++){
			System.out.println("Case #" + caso + ":");
			int altura = sc.nextInt(), barriles = sc.nextInt(), intentos = sc.nextInt();
			int[] arrBarr = new int[barriles];
			for(int i=0; i < barriles; i++){
				arrBarr[i] = sc.nextInt() / (int)(Math.pow(2,i));
			}
			for(int i=0; i < intentos; i++){
				int base = sc.nextInt(), vidas = sc.nextInt();
				boolean muerto = false;
				for(int j = 0; j < barriles && muerto == false; j++){
					if(base == arrBarr[j]) vidas--;
					if(vidas == 0) muerto = true;
					base /= 2;
				}
				if(muerto == true) System.out.println("game over");
				else System.out.println("win");
			}
		}
	}
}