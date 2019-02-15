package com.alp.study;


import com.alp.study.model.BasicTransferInformation;
import com.alp.study.model.BasicTransferInformationEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.requery.Persistable;
import io.requery.query.Result;
import io.requery.reactivex.ReactiveEntityStore;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class MainVertexClass extends AbstractVerticle {


    private ReactiveEntityStore<Persistable> data;
    private Gson gson;


    @Override
    public void start(Future<Void> fut) {

        data = new DBConfiguration().provideDataStore();

        gson = new GsonBuilder().setPrettyPrinting().create();


        Router router = Router.router(vertx);
        router.get("/").handler(rc -> {
            rc.response().putHeader("content-type", "text/html")
                    .end("Welcome to API Service");
        });

        router.get("/api/getAll").handler(this::getAll);
        router.route("/api*").handler(BodyHandler.create());
        router.post("/api/addOne").handler(this::addOne);

        vertx.createHttpServer() // creates a HttpServer
                .requestHandler(router) // router will handle the requests
                .listen(8086, result -> {
                    if (result.succeeded()) {
                        fut.complete();
                    } else {
                        fut.fail(result.cause());
                    }
                });
    }

    private void addOne(RoutingContext routingContext) {
        // Read the request's content and create an instance.
        //Пример Запроса:curl -X POST -H "Content-Type: application/json"
        // -d '{"name":"Oxanna", "post":"mobile Phone", "dateStart":"2019-01-20", "dateEnd":"2019-02-20",
        // "addressStart":"Everest", "addressEnd":"China"}' http://localhost:8086/api/addOne

        final DataStructure DataRow = gson.fromJson(routingContext.getBodyAsString(), DataStructure.class);
        System.out.println(DataRow.toString());

        BasicTransferInformationEntity res = new BasicTransferInformationEntity();
        res.setName(DataRow.getName());
        res.setPost(DataRow.getPost());
        res.setDateStart(Str2Date(DataRow.getDateStart()));
        res.setDateEnd(Str2Date(DataRow.getDateEnd()));
        data.insert(res).blockingGet();

        String json = gson.toJson(DataRow);
        System.out.println("Gson: " + json);

        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(json);
    }

    private void getAll(RoutingContext routingContext) {
        // Пишем запрос базу данных.
        // Получаем объект Result, по сути, специальный массив объектов автоматически сгенерированного класса BasicTransferInformationEntity
        Result<BasicTransferInformationEntity> res = data.select(BasicTransferInformationEntity.class)
                .where(BasicTransferInformationEntity.ID.greaterThan(-1)).get();
        //Возвращаемое значение имеет вид: {"$id_state": "LOADED", "$name_state": "LOADED", "$post_state": "LOADED",
        //  "$dateStart_state": "LOADED", "$dateEnd_state": "LOADED", "id": 1, "name": "Линус Торвальдсus",
        //  "post": "some Unixus", "dateStart": "Feb 11, 2019", "dateEnd": "Feb 14, 2019" }
        //Приводим к DataStructure, пакуем всё в Map и возвращаем прекрасно выглядящий массив json'ов

        //!!!Bug При нескольких вызовах подряд скачат id, вероятно они вообще неверно отображаются!!!

        Map<Integer, DataStructure> DataRows = new LinkedHashMap<>();

        for(Iterator<BasicTransferInformationEntity> i = res.iterator(); i.hasNext();) {
            BasicTransferInformation cached =  i.next();
            DataStructure insert = new DataStructure(cached.getId(), cached.getName(), cached.getPost(),
                    cached.getDateStart().toString(), cached.getDateEnd().toString());
            DataRows.put(cached.getId(), insert);
        }

        String json = gson.toJson(DataRows.values());
        System.out.println("Gson: " + json);

        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(json);
    }

    public static java.sql.Date Str2Date (String startDate) {
        java.util.Date date;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf1.parse(startDate);
        } catch (ParseException pex) {
            System.out.println(pex.getErrorOffset());
            date = null;
        }
        return new java.sql.Date(date.getTime());
    }


}
