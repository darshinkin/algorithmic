package files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ReaderAsyncFile {
    public static void main(String[] args) throws InterruptedException {


        String fileName = "test.txt";
        CompletableFuture<Integer> fileSize = readingFile(fileName);
        while(true) {
            if (!fileSize.isDone()) {
                System.out.println("File is loading.");
                TimeUnit.SECONDS.sleep(1);
            } else {
                break;
            }
        }
    }

    private static CompletableFuture<Integer> readingFile(String fileName) {
        return CompletableFuture.supplyAsync(() ->
        {
            try {
                return Files.readAllBytes(Paths.get(fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        })
                .thenApply(bytes -> {
                    System.out.println(String.format("Read %d bytes", bytes.length));
                    return bytes.length;
                })
                .exceptionally(throwable -> {
                    System.out.println(String.format("Error [%s] occurred during file read", throwable.getMessage()));
                    return null;
                });
    }
}
