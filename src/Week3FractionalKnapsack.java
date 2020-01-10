import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class Week3FractionalKnapsack {

    static class Item {
        private int value;
        private int weight;

        public Item (int value, int weight) {
            this.value = value;
            this.weight = weight;
        }


    }

    private static double getOptimalValue(int capacity, int[] values, int[] weights) {
        Double value = 0d;
        Map<Double, Item> items = createItems(values, weights);
        for (Map.Entry<Double, Item> entry: items.entrySet()) {
            if (capacity > entry.getValue().weight) {
                value += entry.getValue().value;
                capacity -= entry.getValue().weight;
            } else {
                value += (capacity * entry.getKey());
                break;
            }
            if (capacity == 0) {
                break;
            }
        }
        return value;
    }

    private static Map<Double, Item> createItems(int[] values, int[] weights) {
        int length = values.length;
        Map<Double, Item> range = new TreeMap<>(Collections.reverseOrder());
        for (int i = 0; i < length; ++i) {
            range.put((double)values[i]/(double)weights[i], new Item(values[i], weights[i]));
        }

        return range;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int capacity = scanner.nextInt();
        int[] values = new int[n];
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();
            weights[i] = scanner.nextInt();
        }
        double optimalValue = getOptimalValue(capacity, values, weights);
        DecimalFormat df = new DecimalFormat("#.####");
        df.setMaximumFractionDigits(4);
        df.setMinimumFractionDigits(4);
        System.out.println(df.format(optimalValue));
    }
} 
