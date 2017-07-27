package com.joker.rx;

import java.util.concurrent.TimeUnit;

import com.joker.rx.functions.Action0;

public class TestScheduler extends Scheduler {

	@Override
	public Worker createWorker() {
		return new TestWorker();
	}
	
	private class TestWorker extends Worker {

		@Override
		public Subscription schedule(Action0 action) {
			action.call();
			return null;
		}

		@Override
		public Subscription schedule(Action0 action, long delayTime, TimeUnit unit) {
			return null;
		}
		
	}

}
