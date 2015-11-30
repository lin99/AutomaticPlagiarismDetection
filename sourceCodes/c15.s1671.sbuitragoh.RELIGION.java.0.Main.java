
import java.util.Scanner;
 
public class Main {
    
    static class QuickFind {
    private int[] id;
    private int count;

    public QuickFind(int N) {
        count =N;
        id = new int[N];
        for(int i=0;i<N;i++)
            id[i]=i+1;
    }
    public int count() 
    { return count; }

    public int find(int p) 
    { return id[p-1]; }

    public boolean find(int p, int q) 
    { return id[p-1] == id[q-1]; }

    public void union(int p, int q) {
        if(find(p,q)) return;
        int pid=id[p-1];
        for(int i=0; i<id.length;i++)
            if(id[i]==pid) id[i]=id[q-1];
            count--;
        
    }

}
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        int cnt=1;
        cicle1:
        while(sc.hasNext()){
            int Fst=sc.nextInt();
            int Snd=sc.nextInt();
            QuickFind QF = new QuickFind(Fst);
            if(Fst==0&&Snd==0){
                    break;
            }
            for(int i=0;i<Snd;i++){
                int Uno=sc.nextInt();
                int Dos=sc.nextInt();
                QF.union(Uno,Dos);
            }
                
            System.out.println("Case "+cnt+": "+QF.count);         
        }
    }
}
