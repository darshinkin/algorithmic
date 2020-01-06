import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GCD {
  private static int gcd_naive(int a, int b) {
    int current_gcd = 1;
    for(int d = 2; d <= a && d <= b; ++d) {
      if (a % d == 0 && b % d == 0) {
        if (d > current_gcd) {
          current_gcd = d;
        }
      }
    }
//    System.out.println("gcd_naive = " + current_gcd);
    return current_gcd;
  }

  private static int gcdEuclidean(int a, int b) {
//    System.out.println("Euclidean calculation:");
    if (a == b) {
      return a;
    } else {
      if (b > a) {
        int temp = b;
        b = a;
        a = temp;
      }
      int res = 1;
      while (true) {
        if (a<b) {
          break;
        } else {
          int temp = a % b;
//          System.out.println("temp = " + temp);
          if (temp == 0) {
            res = b;
            break;
          } else {
            a = b;
            b = temp;
          }
        }
      }
      return res;
    }
  }

  public static void main(String args[]) {
    Scanner scanner = new Scanner(System.in);
    int a = scanner.nextInt();
    int b = scanner.nextInt();

    System.out.println(gcdEuclidean(a, b));

//    debug();
  }

  private static void debug() {
    while (true) {
      int a = ThreadLocalRandom.current().nextInt(1, 40);
      int b = ThreadLocalRandom.current().nextInt(1, 40);
      System.out.println(String.format("a=%d; b=%d", a, b));
      long res1 = gcd_naive(a, b);
      long res2 = gcdEuclidean(a, b);
      if (res1 != res2) {
        System.out.println(String.format("Error occurred %d != %d", res1, res2));
        break;
      }
      System.out.println(String.format("OK: %d=%d", res1, res2));
    }
  }
}
