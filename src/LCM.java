import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class LCM {
  private static long lcm_naive(int a, int b) {
    for (long l = 1; l <= (long) a * b; ++l)
      if (l % a == 0 && l % b == 0)
        return l;

    return (long) a * b;
  }

  private static long lcmFast(int d1, int d2) {
    long a = (long)d1;
    long b = (long)d2;
    if (a == 0 || b == 0) {
      return 0;
    }
    if (b > a) {
      long temp = b;
      b = a;
      a = temp;
    }
    long maxRes = a * b;
    for (int i = 1; (i*b) < maxRes; ++i) {
      long resB = i*b;
      long resA = i*a;
//      System.out.println(String.format("Intermediate result: i=%d, resA=%d; resB=%d", i, resA, resB));
      if (resB % a == 0 && resB % b == 0) {
        return resB;
      } else if (resA % a == 0 && resA % b == 0) {
        return resA;
      }
    }
    return maxRes;
  }

  public static void main(String args[]) {
    prod();
//    debug();
  }

  private static void prod() {
    Scanner scanner = new Scanner(System.in);
    int a = scanner.nextInt();
    int b = scanner.nextInt();

    System.out.println(lcmFast(a, b));
  }

  private static void debug() {
    while (true) {
      int a = ThreadLocalRandom.current().nextInt(1, 40);
      int b = ThreadLocalRandom.current().nextInt(1, 40);
      System.out.println(String.format("a=%d; b=%d", a, b));
      long res1 = lcm_naive(a, b);
      long res2 = lcmFast(a, b);
      if (res1 != res2) {
        System.out.println(String.format("Error occurred %d != %d", res1, res2));
        break;
      }
      System.out.println(String.format("OK: %d=%d", res1, res2));
    }
  }
}
