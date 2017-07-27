package com.joker.rx.observer;

public interface Observer<T> {
	//发出时，需要触发 onCompleted() 方法作为标志
	public void onNext(T param);
	//事件队列完结
	public void onCompleted();
	//事件队列异常(事件处理异常时调用)
	public void onError();
}
