package com.joker.rx.internal;

import com.joker.rx.Scheduler;
import com.joker.rx.Scheduler.Worker;
import com.joker.rx.functions.Action0;
import com.joker.rx.observer.Observable;
import com.joker.rx.observer.Observable.OnSubscribe;
import com.joker.rx.observer.Subscriber;

public final class OperatorSubscribeOn<T> implements OnSubscribe<T> {
	final Scheduler scheduler;
	final Observable<T> source;//原始观察者
	public OperatorSubscribeOn(Scheduler scheduler,Observable<T> source) {
		this.scheduler = scheduler;
		this.source = source;
	}
	
	
	public void call(final Subscriber<? super T> subscriber) {
		final Worker inner = scheduler.createWorker();
		
		inner.schedule(new Action0() {
			
			public void call() {
				final Thread t = Thread.currentThread();
				
				Subscriber<T> s = new Subscriber<T>(subscriber) {

					public void onNext(T param) {
						System.out.println("this is next");
						subscriber.onNext(param);
					}

					public void onCompleted() {
						subscriber.onCompleted();
					}

					@Override
					public void onStart() {
						subscriber.onStart();
					}

					@Override
					public void unsubscribe() {
						subscriber.unsubscribe();
					}

					public void onError(Throwable e) {
						
					}
				};
				source.subscribe(s);
			}
		});
	}

}
