package com.example.orderprocessing.monitoring.interfaces;

import org.apache.commons.lang.NotImplementedException;

import java.util.concurrent.TimeUnit;

public interface ICreationTimer {

    default void recordTime(Runnable runnable) {
        throw new NotImplementedException("ICreationTimer.recordTime(Runnable runnable) muss be implemented");
    }

    default void recordTime(long duration, TimeUnit unit) {
        throw new NotImplementedException("ICreationTimer.recordTime(long duration, TimeUnit unit)  muss be implemented");
    }

}
