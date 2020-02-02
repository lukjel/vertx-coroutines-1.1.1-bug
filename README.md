# BUG reproducer

Handling `RuntimeErrors` during processing a stream.

`TaskHandler` produce RuntimeException (`Integer.valueOf("one")`). It should be "catched" in `HandlerWrapper`:
a) in try - catch (line 30)
or
b) during `subscribe` (line 38).

With dependency:
```xml
        <dependency>
            <groupId>org.jetbrains.kotlinx</groupId>
            <artifactId>kotlinx-coroutines-rx2</artifactId>
            <version>1.1.1</version>
        </dependency>
```

Works ok. After upgrading to any newer, for example `1.2.0` there is no error...
Please notice - when changing to `1.3.x`  you need to edit `KotlinHandlerAbstract`.

Line 16
from:
```kotlin
		return GlobalScope.rxSingle(vertx.dispatcher()) {
```
to
```kotlin
		return rxSingle(vertx.dispatcher()) {
```


## Run

`mvn clean package`

`java -jar target/kotlin-coroutines-bug-fat.jar`

on the second console:

`curl localhost:8080/`

with libarary `1.1.1` the output is:
`Some fail: (RECIPIENT_FAILURE,0) java.lang.NumberFormatException: For input string: "error"`

with newer version there is no output - connection hangs.
