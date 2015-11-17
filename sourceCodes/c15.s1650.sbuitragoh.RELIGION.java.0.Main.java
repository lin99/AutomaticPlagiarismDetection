
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        boolean test=true;
        input1:
        if(test){
        int input [] = new int[2];
        input[0]=sc.nextInt();
        input[1]=sc.nextInt();
        int Count [] = new int [input[0]];
        for(int i=0;i<Count.length;i++){
            Count[i]=i+1;
        }
        int Matrix [][]=new int [input[1]][2];
        for(int i=0;i<input[1];i++){
            for(int j=0;j<2;j++){
                Matrix[i][j]=sc.nextInt();  
                if(Matrix[i][0]==0&&Matrix[i][1]==0){
                    break input1;
                }
            }
        }
        for(int j=0; j<Count.length; j++){
            for(int i=0;i<input[1];i++){
                if(Count[j]==Matrix[i][1]){
                    Count [j]=Matrix[i][0];
                }                
            }
        }
        for(int i=0;i<(Count.length-1);i++){
            for(int j=i+1;j<Count.length;j++){
                if(Count[i]>Count[j]){
                    int aux=Count[i];
                    Count[i]=Count[j];
                    Count[j]=aux; 
                }
            }
        }
        int cnt=1;
        for(int i=0;i<(Count.length-1);i++){
            if(Count[i]!=Count[i+1]){
                cnt++;
            }
        }       
        System.out.println("Case 1: "+cnt);          
        
    }
    }
}
