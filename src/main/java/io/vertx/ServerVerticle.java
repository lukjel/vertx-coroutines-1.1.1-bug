package io.vertx;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;

public class ServerVerticle extends AbstractVerticle {

	private HttpServer server;

	@Override
	public void start(Future<Void> startDone) throws Exception {
		server = vertx.createHttpServer().requestHandler(req -> {
			vertx.eventBus().send("some-task", new JsonObject(), res -> {
				if (res.succeeded()) {
					req.response()
						.putHeader("content-type", "text/plain")
						.end("Hello from Vert.x!");
				} else {
					req.response()
						.putHeader("content-type", "text/plain")
						.end("Some fail: " + res.cause());
				}
			});
		});
		server.listen(8080, res -> {
				if (res.succeeded()) {
					startDone.complete();
				} else {
					startDone.fail(res.cause());
				}
			}
		);
	}
}
