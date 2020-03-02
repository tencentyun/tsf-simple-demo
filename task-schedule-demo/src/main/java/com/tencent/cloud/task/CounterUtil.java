package com.tencent.cloud.task;

import java.util.concurrent.atomic.AtomicLong;

public class CounterUtil {

    private static  final AtomicLong counter = new AtomicLong(0);

    public static Long getNextCount() {
        return counter.incrementAndGet();
    }
}
