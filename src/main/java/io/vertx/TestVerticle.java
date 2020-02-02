package io.vertx;

import io.vertx.core.Future;
import io.vertx.reactivex.core.AbstractVerticle;

public class TestVerticle extends AbstractVerticle {

	@Override
	public void start(Future<Void> startDone) throws Exception {
		this.vertx.eventBus().consumer("some-task", new HandlerWrapper(new TaskHandler(this.vertx)));
		this.vertx.deployVerticle(new ServerVerticle());
		startDone.complete();
	}
}
