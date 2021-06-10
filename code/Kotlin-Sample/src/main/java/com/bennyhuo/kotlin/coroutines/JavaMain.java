package com.bennyhuo.kotlin.coroutines;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;

public class JavaMain {
    public static void main(String[] args) {
        CompletableFuture<String> future = new CompletableFuture<>();
        Object result = SuspendToBlockingKt.suspendableApi(new Continuation<String>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NotNull Object o) {
                if(o instanceof String) {
                    future.complete((String) o);
                } else {
                    future.completeExceptionally((Throwable) o);
                }
            }
        });

        if (result != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            future.complete((String) result);
        }

        System.out.println(future.join());
    }
}
