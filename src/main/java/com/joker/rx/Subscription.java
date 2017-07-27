package com.joker.rx;

public interface Subscription {
	
	void unsubscribe();

    boolean isUnsubscribed();
}
