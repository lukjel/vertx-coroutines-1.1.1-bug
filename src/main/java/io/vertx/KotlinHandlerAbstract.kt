package io.vertx

import io.reactivex.Single
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.dispatcher
import io.vertx.reactivex.core.Vertx
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.rx2.rxSingle

abstract class KotlinHandlerAbstract(vertx: Vertx) : BasicHandler {

	val vertx = vertx.delegate!!

	override fun handle(input: JsonObject): Single<JsonObject> {
		return GlobalScope.rxSingle(vertx.dispatcher()) {
			apply(input)
		}
	}

	internal abstract suspend fun apply(input: JsonObject): JsonObject

}
