package completablefuture;

import java.util.concurrent.*;

public class Example {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        runAsync();
//        supplyAsync();
//        thenApply();
//        thenApplyAsync();
        thenCompose();
    }

    static class User {}
    static class UserService {
        public static User getUserDetails(String userId) {
            return new User();
        }
    }
    static class CreditRatingService {
        public static Double getCreditRating(User user) {
            return 324.2353;
        }
    }

    private static void thenCompose() {
        String userId = "id";

        CompletableFuture<CompletableFuture<Double>> result1 =
                getUserDetail(userId).thenApply(user -> getCreditRating(user));

        CompletableFuture<Double> result2 =
                getUserDetail(userId). thenCompose(user -> getCreditRating(user));
    }

    private static CompletableFuture<User> getUserDetail(String userId) {
        return CompletableFuture.supplyAsync(() -> UserService.getUserDetails(userId));
    }

    private static CompletableFuture<Double> getCreditRating(User user) {
        return CompletableFuture.supplyAsync(() -> CreditRatingService.getCreditRating(user));
    }

    private static void thenApplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> whatYourNameFuture = CompletableFuture.supplyAsync(() -> "Dima")
                .thenApplyAsync(name -> "Hello " + name)
                .thenApplyAsync(greeting -> greeting + ". Welcome to completableFuture lesson.")
                .thenAcceptAsync(System.out::println);
        whatYourNameFuture.get();
    }

    private static void thenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> whatYourNameFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Dima";
        }).thenApply(name -> "Hello " + name)
                .thenApply(greeting -> greeting + ". Welcome to completableFuture lesson.")
                .thenAccept(System.out::println)
                .thenRun(() -> System.out.println("Just run"));

        System.out.println("Message in main thread. #1");
        whatYourNameFuture.get();
        System.out.println("Message in main thread. #2");
    }

    private static void supplyAsync() throws ExecutionException, InterruptedException {
        Executor executor = Executors.newFixedThreadPool(10);
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Asynch task result";
        }, executor);
        String result = future.get();
        System.out.println(result);
    }

    private static void runAsync() throws InterruptedException, ExecutionException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("Process in separate thread.");
        });

        future.get();
    }
}
