import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Fibonacci {
  private static long calc_fib(int n) {
    if (n <= 1)
      return n;

    return calc_fib(n - 1) + calc_fib(n - 2);
  }

  private static long calcFibFast(int n) {
    if (n <= 1) {
      return n;
    }

    long lastFirst = 0;
    long lastSecond = 1;
    for (int i = 2; i <= n; ++i) {
      long tempLastSecond = lastSecond;
      lastSecond = lastFirst + lastSecond;
      lastFirst = tempLastSecond;
    }

    return lastSecond;
  }


  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();


    System.out.println(calcFibFast(n));
//    debug();
  }

  private static void debug() {
    while (true) {
      int n = ThreadLocalRandom.current().nextInt(1, 40);
      System.out.println("n = " + n);
      long res1 = calc_fib(n);
      long res2 = calcFibFast(n);
      if (res1 != res2) {
        System.out.println(String.format("Error occurred %d != %d", res1, res2));
        break;
      }
      System.out.println(String.format("OK: %d=%d", res1, res2));
    }
  }
}
