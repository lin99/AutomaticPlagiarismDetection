import java.util.*;

public class ProblemA{
	
	public static boolean comparar(ArrayList<String> comandos, String cadena){
		for(String m: comandos)
			if(m.equals(cadena))
				return true;
				return false;
			}
	
	public static void main(String[] args){
		Scanner scaner = new Scanner(System.in);
		int casos = Integer.parseInt(scaner.nextLine());
			for(int c = 1; c <= casos; c++){
				System.out.println("Case #" + c + ":");
				ArrayList<String> comandos = new ArrayList<String> ();
					int operaciones = Integer.parseInt(scaner.nextLine());
			for(int i = 0; i < operaciones; i++){
				String[] oper = scaner.nextLine().split(" ");
					if(oper[0].equals("agregar"))
						if(!comparar(comandos, oper[1]))
							comandos.add(oper[1]);
					if(oper[0].equals("buscar")){
						boolean bool = false;
					for(String cadena: comandos){                                                               
						if(cadena.length() >= oper[1].length() && oper[1].equals(cadena.substring(0, oper[1].length()))){
							bool = true;
							System.out.println("yes");
						break;
						}
					}
					if(bool == false) 
						System.out.println("no");
				}
				if(oper[0].equals("sugerir")){
					int g = 0;
				for(String cadena: comandos){
					if(cadena.length() >= oper[1].length() && oper[1].equals(cadena.substring(0, oper[1].length()))){
						g++;
						}
					}
				System.out.println(g);
				}
			}
		
	}
}
}