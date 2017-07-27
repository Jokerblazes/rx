package com.joker.rx;

import java.util.concurrent.TimeUnit;

import com.joker.rx.functions.Action0;




public abstract class Scheduler {
	static final long CLOCK_DRIFT_TOLERANCE_NANOS;
    static {
        CLOCK_DRIFT_TOLERANCE_NANOS = TimeUnit.MINUTES.toNanos(
                Long.getLong("rx.scheduler.drift-tolerance", 15));
    }

    public abstract Worker createWorker();
    
    public long now() {
        return System.currentTimeMillis();
    }

    
    
    
    public abstract static class Worker {
    	public abstract Subscription schedule(Action0 action);

        /**
         * Schedules an Action for execution at some point in the future.
         * <p>
         * Note to implementors: non-positive {@code delayTime} should be regarded as non-delayed schedule, i.e.,
         * as if the {@link #schedule(rx.functions.Action0)} was called.
         *
         * @param action
         *            the Action to schedule
         * @param delayTime
         *            time to wait before executing the action; non-positive values indicate an non-delayed
         *            schedule
         * @param unit
         *            the time unit of {@code delayTime}
         * @return a subscription to be able to prevent or cancel the execution of the action
         */
        public abstract Subscription schedule(final Action0 action, final long delayTime, final TimeUnit unit);
    }
}
