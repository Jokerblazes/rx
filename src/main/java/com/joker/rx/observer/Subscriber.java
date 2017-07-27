package com.joker.rx.observer;

public abstract class Subscriber<T> implements Observer<T> {
	private Observer observer;
	public Subscriber() {
	}
	public Subscriber(Subscriber<? super T> subscriber) {
	}
	public Observer getObserver() {
		return observer;
	}
	public void setObserver(Observer observer) {
		this.observer = observer;
	}
	//事件还未发送之前被调用，可以用于做一些准备工作
	public abstract void onStart();
	//取消订阅
	public abstract void unsubscribe();
}
