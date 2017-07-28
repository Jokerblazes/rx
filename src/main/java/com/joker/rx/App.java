package com.joker.rx;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.joker.rx.observer.Observable;
import com.joker.rx.observer.Subscriber;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		//this is test
//		Observable.create(new Observable.OnSubscribe() {
//			public void call(Subscriber subscriber) {
//				subscriber.onNext("hello");
//				subscriber.onNext("joker");
//				subscriber.onCompleted();
//			}
//		}).subscribe(new Subscriber<String>() {
//
//			public void onNext(String param) {
//				System.out.println(param);
//			}
//
//			public void onCompleted() {
//				System.out.println("执行完毕！");
//			}
//
//			public void onError() {
//				System.out.println("出现异常！");
//			}
//
//			@Override
//			public void onStart() {
//				
//			}
//
//			@Override
//			public void unsubscribe() {
//				
//			}
//		});
		
		
		Future<Object> future = Observable.create(new Observable.OnSubscribe() {
			public void call(Subscriber subscriber) {
				subscriber.onNext("hello");
				subscriber.onCompleted();
			}
		}).subscribeOn(new TestScheduler()).toFuture();
		
		try {
			Object a = future.get();
			System.out.println(a);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
