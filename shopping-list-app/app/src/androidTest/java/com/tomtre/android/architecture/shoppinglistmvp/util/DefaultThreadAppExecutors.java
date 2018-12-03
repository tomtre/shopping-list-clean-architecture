package com.tomtre.android.architecture.shoppinglistmvp.util;

import java.util.concurrent.Executor;

public class DefaultThreadAppExecutors extends AppExecutors{

    private static Executor EXECUTOR = Runnable::run;

    public DefaultThreadAppExecutors() {
        super(EXECUTOR, EXECUTOR);
    }
}
