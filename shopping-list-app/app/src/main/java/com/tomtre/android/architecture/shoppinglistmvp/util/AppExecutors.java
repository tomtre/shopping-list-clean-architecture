package com.tomtre.android.architecture.shoppinglistmvp.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

public class AppExecutors {

    private final Executor diskIOExecutor;
    private final Executor mainThreadExecutor;

    public Executor getDiskIOExecutor() {
        return diskIOExecutor;
    }

    public Executor getMainThreadExecutor() {
        return mainThreadExecutor;
    }

    public AppExecutors(Executor diskIOExecutor, Executor mainThreadExecutor) {
        this.diskIOExecutor = diskIOExecutor;
        this.mainThreadExecutor = mainThreadExecutor;
    }

    public static class MainThreadExecutor implements Executor {
        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }


}
