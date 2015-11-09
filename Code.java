import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Suptee
 */
public class Code {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer st = new StringTokenizer(br.readLine());

            int numberOfInputs = Integer.parseInt(st.nextToken());
            ArrayList<Integer> amountArray = new ArrayList<>();
            ArrayList<Integer> priceArray = new ArrayList<>();
            ArrayList<Integer> sortedPriceArray = new ArrayList<>();
            String line;
            int i = 0;
            while((line = br.readLine()) != null && line.length() > 0) {
                StringTokenizer st1 = new StringTokenizer(line);
                amountArray.add(Integer.parseInt(st1.nextToken()));
                int price = Integer.parseInt(st1.nextToken());
                priceArray.add(price);
                sortedPriceArray.add(price);
                i++;
                if(i == numberOfInputs)
                    break;
            }

            sortedPriceArray.sort(null);
            int index = priceArray.indexOf(sortedPriceArray.get(0));
            int sumOfPrice = 0;

            for (int j = 0; j < priceArray.size(); j++) {
                if(j < index) {
                    sumOfPrice += amountArray.get(j)*priceArray.get(j);
                }
                else {
                    sumOfPrice += amountArray.get(j)*priceArray.get(index);
                }
            }

            System.out.println(sumOfPrice);

        } catch (IOException ex) {
            Logger.getLogger(DuffMeat_588A.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
