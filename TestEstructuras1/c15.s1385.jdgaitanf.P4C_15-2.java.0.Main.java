import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static class Nodo {
		private Nodo padre;
		private Nodo hijoIzq;
		private Nodo hijoDer;
		private int arbolIzq;
		private int arbolDer;
		private int valor;

		public Nodo(Nodo padre, Nodo hijoIzq, Nodo hijoDer, int valor) {
			this.padre = padre;
			this.hijoDer = hijoDer;
			this.hijoIzq = hijoIzq;
			this.valor = valor;
			this.arbolIzq = 0;
			this.arbolDer = 0;

		}

		public void addNormal(int x) {
			Nodo aux;
			if (x < this.valor) {
				if (this.hijoIzq == null) {
					aux = new Nodo(this, null, null, x);
					this.hijoIzq = aux;
					this.arbolIzq++;
				} else {
					this.arbolIzq++;
					this.hijoIzq.addNormal(x);
				}

			} else {
				if (this.hijoDer == null) {
					aux = new Nodo(this, null, null, x);
					this.hijoDer = aux;
					this.arbolDer++;
				} else {
					this.arbolDer++;
					this.hijoDer.addNormal(x);
				}
			}

		}


		public int Contar() {
			int counter = 0;
			Nodo aux = this;
			List<Nodo> BFS = new ArrayList<>();
			BFS.add(aux);
			while (!BFS.isEmpty()) {

				aux = BFS.get(0);
				counter = counter + aux.arbolDer + aux.arbolIzq;
				BFS.remove(0);
				if (aux.hijoIzq != null) {
					BFS.add(aux.hijoIzq);

				}
				if (aux.hijoDer != null) {
					BFS.add(aux.hijoDer);
				}

			}
			return counter;

		}
	}
	
	  public static int calcularPotencia(int N, int p){
	       int result=1;
	       int i=p;
	       while (i>0) {
	       result*=N;
	       i--;
	       }
	       return result;
	  }

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int cases, cont = 1;
		
		cases = in.nextInt();
		while (cases != 0) {
			System.out.println("Case #" + cont + ":");
			int Cantidad, PrimerValor, x, tC, a=0, b=0;
			
			Cantidad = in.nextInt();
			tC=Cantidad;
			
			PrimerValor = in.nextInt();
			Nodo raiz = new Nodo(null, null, null, PrimerValor);
			while (Cantidad > 1) {
				
				x = in.nextInt();
				raiz.addNormal(x);
				Cantidad--;
			}
			int c = 0;
			
			while (a<tC){					
				a = (calcularPotencia(2, b))-1;
				c = c+(calcularPotencia(2, b)*b);
				b++;				
			}
			c = c-(calcularPotencia(2, b-1)*(b-1));
			b=b-2;
			System.out.println(raiz.Contar() + " " + ((c)-((a-tC)*b)));
			cont++;
			cases--;
		}

	}

}
