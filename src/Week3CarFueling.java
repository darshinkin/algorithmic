import java.util.*;
import java.io.*;

public class Week3CarFueling {
    static int computeMinRefills(int dist, int tank, int[] stopsSrc) {
        int stopsLenght = stopsSrc.length + 1;
        int[] stops = new int[stopsLenght];
        for (int i = 0; i < stopsSrc.length; ++i) {
            stops[i] = stopsSrc[i];
        }
        stops[stopsLenght-1] = dist;
        if (tank >= dist) {
            return 0;
        }

        int count = 0;
        int previous = 0;
        int currentStop = 0;
        for (int i = 0; i < stops.length; i++) {
//            System.out.print("Step "+i+":");
            if ((stops[i]-currentStop) >= tank) {
//                System.out.println(String.format("Stop=%d; current=%d, previous=%d", stops[i],
//                        currentStop, previous));
                if ((previous - currentStop) > tank || (stops[i] - previous) > tank) {
//                    System.out.println(String.format("(previous - currentStop) > tank: %d",
//                            stops[i] - currentStop));
                    return -1;
                }
                ++count;
//                System.out.println("count:" + count);
                currentStop = (stops[i]-currentStop) == tank ? stops[i] : previous;
                continue;
            }
//            System.out.println(String.format("Skipped: %d, (stops[i]-currentStop)=%d", stops[i], (stops[i]-currentStop)));
            previous = stops[i];
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dist = scanner.nextInt();
        int tank = scanner.nextInt();
        int n = scanner.nextInt();
        int stops[] = new int[n];
        for (int i = 0; i < n; i++) {
            stops[i] = scanner.nextInt();
        }

        System.out.println(computeMinRefills(dist, tank, stops));
    }
}
