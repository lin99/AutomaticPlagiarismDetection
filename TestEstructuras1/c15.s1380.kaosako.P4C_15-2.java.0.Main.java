import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
public class Main {
    static class TreeNode{
        private int value;
        private TreeNode childR;
        private TreeNode childL;
        private TreeNode(){
            value=0;
            childR=null;
            childL=null;
        }
        public boolean isSheet(){
            return (childR==null&&childL==null);
        }
        public int addCompare(int val, int compare, TreeNode node) {
            if(node.isSheet()&&node.value==0){
                node.value= val;
                return compare;
            }else if(val<node.value){
                if(node.childL==null) node.childL= new TreeNode();
                return 1+addCompare(val, compare, node.childL);
            }else if(val>node.value){
                if(node.childR==null) node.childR= new TreeNode();
                return 1+addCompare(val, compare, node.childR);
            }else{
                return 0;
            }
        }
    }
    public static void main(String[] args){
        String line = "";
        int cases=0;
        
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            line = br.readLine();
            cases = Integer.parseInt(line);
            for(int i=1;i<=cases;i++){
                System.out.println("Case #"+i+":");
                ArrayList numbers = new ArrayList<>(Integer.parseInt(br.readLine()));
                   numbers = addElements(br.readLine());
                   int firstCompared=0;
                   int secoundCompared=0;
                   firstCompared = compare(numbers);
                   numbers=order(numbers);
                   ArrayList newNumbers = new ArrayList<Integer>();
                   bestOrder(numbers, newNumbers,0,numbers.size()-1);
                   secoundCompared = compare(newNumbers);
                   System.out.println(firstCompared+" "+secoundCompared);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private static int compare(ArrayList numbers) {
        TreeNode node=new TreeNode();
        int quantit=0;
        for(int i=0; i<numbers.size();i++){
            quantit = quantit + node.addCompare((int) numbers.get(i), 0, node);
        }
        return quantit;
    }
    private static void bestOrder(ArrayList<Integer> numbers, ArrayList<Integer> n, int min, int max ) {
        if(max==min){
            n.add(numbers.get(min));
        }else if((max-min)==1){
            n.add(numbers.get(min));
            n.add(numbers.get(max));
        }else{
            int mitad = (int)Math.ceil( (double)(max-min)/2 ) + min;
            n.add(numbers.get(mitad));
            bestOrder(numbers, n, min, mitad-1);
            bestOrder(numbers, n, mitad+1, max);
        }
    }
    private static ArrayList order(ArrayList<Integer> numbers) {
        for(int i=0;i<(numbers.size()-1);i++){
            for(int j=i+1;j<numbers.size();j++){
                if(numbers.get(i)>numbers.get(j)){
                    int temp=numbers.get(i);
                    numbers.set(i,numbers.get(j));
                    numbers.set(j,temp);
                }
            }
        }
        return numbers;
    }
    private static ArrayList addElements(String numbers) {
        String[] elements = numbers.split(" ");
        ArrayList finalList = new ArrayList(elements.length);
        for(int i=0;i<elements.length;i++){
            finalList.add(Integer.parseInt(elements[i]));
        }
        return finalList;
    }
} 