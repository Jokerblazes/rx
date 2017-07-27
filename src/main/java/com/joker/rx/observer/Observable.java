package com.joker.rx.observer;

import com.joker.rx.Scheduler;
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
	
	
	public static interface OnSubscribe<T> {
		public void call(Subscriber<? super T> subscriber);
		
//		public void call(Observer<? super T> observer);
	}
	
}
