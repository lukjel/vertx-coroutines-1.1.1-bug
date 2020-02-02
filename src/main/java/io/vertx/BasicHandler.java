package io.vertx;

import io.reactivex.Single;
import io.vertx.core.json.JsonObject;

public interface BasicHandler {
	Single<JsonObject> handle(JsonObject input);
}
