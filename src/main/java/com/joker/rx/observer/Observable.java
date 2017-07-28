package com.joker.rx.observer;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import com.joker.rx.Scheduler;
import com.joker.rx.Subscription;
import com.joker.rx.internal.OperatorSubscribeOn;



public class Observable<T> {
	final OnSubscribe onSubscriber;
	
	protected Observable (OnSubscribe onSubscribe) {
		this.onSubscriber = onSubscribe;
	}
	
	public static <T>Observable<T> create(OnSubscribe onSubscribe) {
		return new Observable<T>(onSubscribe);
	}
	
	public Subscriber subscribe(Subscriber subscriber) {
		subscriber.onStart();
		onSubscriber.call(subscriber);
		return subscriber;
	}
	
	
	public final Observable<T> subscribeOn(Scheduler scheduler) {
		return create(new OperatorSubscribeOn<T>(scheduler, this));
	}
	
	public Future<T> toFuture() {
		final CountDownLatch finished = new CountDownLatch(1);
		final AtomicReference<T> value = new AtomicReference<T>();
		final AtomicReference<Throwable> error = new AtomicReference<Throwable>();
		@SuppressWarnings("unchecked")
		final Subscriber s = ((Observable<T>)this).subscribe(new Subscriber<T>() {

			public void onNext(T param) {
				value.set(param);
			}

			public void onCompleted() {
				finished.countDown();

			}

			public void onError(Throwable e) {
				error.compareAndSet(null, e);
				finished.countDown();
			}

			@Override
			public void onStart() {
			}

			@Override
			public void unsubscribe() {
			}

		});

		return new Future<T>() {

			private volatile boolean cancelled;

			private T getValue() throws ExecutionException {
				final Throwable throwable = error.get();
				if (throwable != null) {
					throw new ExecutionException("Observable onError", throwable);
				} else if (cancelled) {
					// Contract of Future.get() requires us to throw this:
					throw new CancellationException("Subscription unsubscribed");
				} else {
					return value.get();
				}
			}

			public boolean cancel(boolean mayInterruptIfRunning) {
				if (finished.getCount() > 0) {
					cancelled = true;
					s.unsubscribe();
					
					finished.countDown();
					return true;
					
				} else {
					return false;
				}
			}

			public boolean isCancelled() {
				return cancelled;
			}

			public boolean isDone() {
				return finished.getCount() == 0;
			}

			public T get() throws InterruptedException, ExecutionException {
				finished.await();
                return getValue();
			}

			public T get(long timeout, TimeUnit unit)
					throws InterruptedException, ExecutionException, TimeoutException {
				if (finished.await(timeout, unit)) {
                    return getValue();
                } else {
                    throw new TimeoutException("Timed out after " + unit.toMillis(timeout) + "ms waiting for underlying Observable.");
                }
			}

		};
		
	}
	
	
	
	public static interface OnSubscribe<T> {
		public void call(Subscriber<? super T> subscriber);
		
//		public void call(Observer<? super T> observer);
	}
	
}
