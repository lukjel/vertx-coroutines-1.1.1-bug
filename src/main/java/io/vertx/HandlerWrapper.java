package io.vertx;

import io.reactivex.Single;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.reactivex.core.eventbus.Message;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class HandlerWrapper implements Handler<Message<JsonObject>> {

	private Logger log = LoggerFactory.getLogger(getClass());

	private final BasicHandler handler;

	public HandlerWrapper(BasicHandler handler) {
		this.handler = handler;
	}

	@Override
	public void handle(Message<JsonObject> msg) {
		Single<JsonObject> observable;
		try {
			observable = this.handler.handle(msg.body());
		} catch (Throwable e) {
			log.warn("Error catched", e);
			observable = Single.error(e);
		}
		final AtomicBoolean replied = new AtomicBoolean(false);
		observable.subscribe(
			(JsonObject res) -> {
				msg.reply(res);
				replied.set(true);
			},
			err -> {
				log.warn("Error served during subscribe", err);
				msg.fail(0, err.toString());
			}
		);
	}
}
