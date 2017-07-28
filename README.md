# rx

rx是一个在java虚拟机上使用可观测的序列来组成的异步、基于事件的程序库。

## 链接

[rx源码](https://github.com/Jokerblazes/rx.git)



## 如何使用

```java
Future<Object> future = Observable.create(new Observable.OnSubscribe() {
	public void call(Subscriber subscriber) {
		subscriber.onNext("hello");
		subscriber.onCompleted();
	}
}).subscribeOn(new TestScheduler()).toFuture();

Object a = future.get();
```



