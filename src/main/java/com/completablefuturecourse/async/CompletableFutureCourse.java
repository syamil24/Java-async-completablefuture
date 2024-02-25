package com.completablefuturecourse.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureCourse {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        completableFuture.get(); // this will block the thread to wait until the object processing completed
        completableFuture.complete("return some dummy data"); // using complete method will not block the current thread although the processing not completed yet


    }

    private static void delay(int min){
        try {
            TimeUnit.MINUTES.sleep(min);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
