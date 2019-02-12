import java.util.concurrent.Future;

public class VertxRestService extends AbstractVerticle {

    @Override
    public void start(Future future) {

        vertx.createHttpServer()
                .requestHandler(createRouter()::accept)
                .listen(8080, httpServerAsyncResult -> {
                    if (httpServerAsyncResult.succeeded()) {
                        future.complete();
                    } else {
                        future.fail(httpServerAsyncResult.cause());
                    }

                });

    }

    ....

}