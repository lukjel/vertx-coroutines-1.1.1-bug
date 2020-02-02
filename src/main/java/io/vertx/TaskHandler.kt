package io.vertx

import io.vertx.core.json.JsonObject
import io.vertx.reactivex.core.Vertx

class TaskHandler(vertx: Vertx): KotlinHandlerAbstract(vertx)  {
	override suspend fun apply(input: JsonObject): JsonObject {
		// Some job but throws RuntimeException...
		return JsonObject().put("some", Integer.valueOf("error"))
	}
}