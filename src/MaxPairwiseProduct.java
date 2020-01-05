import java.util.*;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static java.lang.Math.max;

public class MaxPairwiseProduct {
    static long getMaxPairwiseProduct(int[] numbers) {
        long max_product = 0;
        int n = numbers.length;

        for (int first = 0; first < n; ++first) {
            for (int second = first + 1; second < n; ++second) {
                max_product = max(max_product,
                        (long)(numbers[first]) * (long)(numbers[second]));
//                System.out.println(String.format("%d x %d = %d",
//                        numbers[first], numbers[second], max_product));
            }
        }

        return max_product;
    }

    static long getMaxPairwiseProductFast(int[] numbers) {
        long max1 = 0;
        int max1Index = 0;
        long max2 = 0;
        for (int i = 0; i < numbers.length; i++) {
            if(numbers[i] > max1) {
                max1 = numbers[i];
                max1Index = i;
            }
        }
        if (max1 == 0) {
            return 0;
        }

        for (int i = 0; i < numbers.length; i++) {
            if (i != max1Index && numbers[i] > max2) {
                max2 = numbers[i];
            }
        }
        if (max2 == 0) {
            return 0;
        }

        long res = max1*max2;
//        System.out.println(String.format("max1=%d; max2=%d; res=%d; max1*max2=%d",
//                max1, max2, res, max1*max2));

        return max1*max2;
    }

    public static void main(String[] args) {
        /*while (true) {
            int n = ThreadLocalRandom.current().nextInt(1, 10);
            System.out.println("n = " + n);
            int[] numbers = new int[n];
            for (int i = 0; i < n; i++) {
                int d = ThreadLocalRandom.current().nextInt(1, 100000);
                numbers[i] = d;
            }
            System.out.println(Arrays.toString(numbers));

            long res1 = getMaxPairwiseProduct(numbers);
            long res2 = getMaxPairwiseProductFast(numbers);

            if (res1 != res2) {
                System.out.println(String.format("Error occurred %d != %d", res1, res2));
                break;
            }
            System.out.println(String.format("OK: %d=%d", res1, res2));

        }*/

        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
        }
        System.out.println(getMaxPairwiseProductFast(numbers));
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new
                    InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

}