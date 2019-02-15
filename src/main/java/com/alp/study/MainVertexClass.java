package com.alp.study;


import com.alp.study.model.BasicTransferInformation;
import com.alp.study.model.BasicTransferInformationEntity;
import com.alp.study.model.DestinationInformation;
import com.alp.study.model.DestinationInformationEntity;
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

        data = new RequeryConfiguration().provideDataStore();
        gson = new GsonBuilder().setPrettyPrinting().create();

        Router router = Router.router(vertx);
        router.get("/").handler(rc -> {
            rc.response().putHeader("content-type", "text/html")
                    .end("Welcome to API Service");
        });

        router.route("/api*").handler(BodyHandler.create());
        router.get("/api/getAll").handler(this::getAll);
        router.get("/api/getByCity/:city").handler(this::getByCity);
        router.get("/api/getByToCity/:toCity").handler(this::getByCity);
        router.get("/api/getByFromCity/:fromCity").handler(this::getByCity);
        router.post("/api/addOne").handler(this::addOne);


        //!!!Bug Sometimes after shutdown the port remains busy. Therefore,
        // it becomes impossible to raise the service on the same port, the reason is not clear.!!!

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

    private void getByCity(RoutingContext routingContext) {

        final String city = routingContext.request().getParam("city");
        final String fromCity = routingContext.request().getParam("fromCity");
        final String toCity = routingContext.request().getParam("toCity");

        Result<DestinationInformationEntity> destinationData = null;

        if (city != null) {
            destinationData = data.select(DestinationInformationEntity.class)
                    .where(DestinationInformationEntity.DESTINATION_CITY.equal(city).or(DestinationInformationEntity.ORIGIN_CITY.equal(city)) ).get();
        } else if(fromCity != null) {
            destinationData = data.select(DestinationInformationEntity.class)
                    .where(DestinationInformationEntity.ORIGIN_CITY.equal(fromCity) ).get();
        } else  if(toCity != null) {
            destinationData = data.select(DestinationInformationEntity.class)
                    .where(DestinationInformationEntity.DESTINATION_CITY.equal(toCity) ).get();
        } else {
            routingContext.response().setStatusCode(400).end();
        }

        Map<Integer, DestinationDataClass> destinationDataRows = new LinkedHashMap<>();

        for(Iterator<DestinationInformationEntity> i = destinationData.iterator(); i.hasNext();) {
            DestinationInformationEntity cached =  i.next();
            DestinationDataClass insert = new DestinationDataClass(cached.getId(), cached.getOriginCountry(),
                    cached.getOriginStatePost(), cached.getOriginCity(), cached.getOriginLatitude(),
                    cached.getOriginLongitude(), cached.getDestinationCountry(), cached.getDestinationStatePost(),
                    cached.getDestinationCity(), cached.getDestinationLatitude(), cached.getDestinationLongitude(),
                    cached.getTransferId());
            destinationDataRows.put(cached.getTransferId(), insert);
        }

        Result<BasicTransferInformationEntity> basicData = data.select(BasicTransferInformationEntity.class)
                .where(BasicTransferInformationEntity.ID.in(destinationDataRows.keySet()) ).get();

        Map<Integer, InputDataClass> basicDataRows = new LinkedHashMap<>();

        for(Iterator<BasicTransferInformationEntity> i = basicData.iterator(); i.hasNext();) {
            BasicTransferInformation cached =  i.next();
            InputDataClass insert = new InputDataClass(cached.getId(), cached.getName(), cached.getPost(),
                    cached.getDateStart().toString(), cached.getDateEnd().toString());
            basicDataRows.put(cached.getId(), insert);
        }

        Map<Integer, OutputDataClass> outputDataRows = new LinkedHashMap<>();
        destinationDataRows.keySet();
        for(Integer key : basicDataRows.keySet()) {
            if(destinationDataRows.containsKey(key)) {
                outputDataRows.put(key, new OutputDataClass(basicDataRows.get(key), destinationDataRows.get(key)));
            } else {
                outputDataRows.put(key, new OutputDataClass(basicDataRows.get(key)));
            }
        }

        String json = gson.toJson(outputDataRows.values());
        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(json);

    }

    private void addOne(RoutingContext routingContext) {
        // Read the request's content and push to db.
        //Request example:curl -X POST -H "Content-Type: application/json"
        // -d '{"name":"Oxanna", "post":"Mobile Phone", "dateStart":"2019-01-20", "dateEnd":"2019-02-20",
        // "addressStart":"Everest", "addressEnd":"China"}' http://localhost:8086/api/addOne

        final InputDataClass DataRow = gson.fromJson(routingContext.getBodyAsString(), InputDataClass.class);
        System.out.println(DataRow.toString());


        BasicTransferInformationEntity basicDataRows = new BasicTransferInformationEntity();
        basicDataRows.setName(DataRow.getName());
        basicDataRows.setPost(DataRow.getPost());
        basicDataRows.setDateStart(Str2Date(DataRow.getDateStart()));
        basicDataRows.setDateEnd(Str2Date(DataRow.getDateEnd()));
        BasicTransferInformation current = data.insert(basicDataRows).blockingGet();



        GeoData from  = new GeoData(DataRow.getadressStart());
        GeoData to = new GeoData(DataRow.getaddressEnd());

        DestinationInformationEntity destinationDataRows = new DestinationInformationEntity();

        destinationDataRows.setTransferId(current.getId());

        destinationDataRows.setOriginCity(from.getCity());
        destinationDataRows.setOriginStatePost(from.getAdministrativeArea());
        destinationDataRows.setOriginCountry(from.getCountry());
        destinationDataRows.setOriginLatitude(from.getLatitude());
        destinationDataRows.setOriginLongitude(from.getLongitude());

        destinationDataRows.setDestinationCity(to.getCity());
        destinationDataRows.setDestinationStatePost(to.getAdministrativeArea());
        destinationDataRows.setDestinationCountry(to.getCountry());
        destinationDataRows.setDestinationLatitude(to.getLatitude());
        destinationDataRows.setDestinationLongitude(to.getLongitude());

        //If "DestinationInformation" information will no be inserted, we will be have record in "BasicTransferInformation" only.

        DestinationInformation currentDestination = data.insert(destinationDataRows).blockingGet();

        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end("BasicInfoId: " + current.getId() + "\nDestinationInfoId: " + currentDestination.getId());
    }

    private void getAll(RoutingContext routingContext) {
        // Пишем запрос базу данных.
        // Получаем объект Result, по сути, специальный массив объектов автоматически сгенерированного класса BasicTransferInformationEntity
        Result<BasicTransferInformationEntity> basicData = data.select(BasicTransferInformationEntity.class)
                .where(BasicTransferInformationEntity.ID.greaterThan(-1)).get();
        Result<DestinationInformationEntity> destinationData = data.select(DestinationInformationEntity.class)
                .where(BasicTransferInformationEntity.ID.greaterThan(-1)).get();
        //Возвращаемое значение имеет вид: {"$id_state": "LOADED", "$name_state": "LOADED", "$post_state": "LOADED",
        //  "$dateStart_state": "LOADED", "$dateEnd_state": "LOADED", "id": 1, "name": "Линус Торвальдсus",
        //  "post": "some Unixus", "dateStart": "Feb 11, 2019", "dateEnd": "Feb 14, 2019" }
        //Приводим к InputDataClass, пакуем всё в Map и возвращаем прекрасно выглядящий массив json'ов




        Map<Integer, InputDataClass> basicDataRows = new LinkedHashMap<>();

        for(Iterator<BasicTransferInformationEntity> i = basicData.iterator(); i.hasNext();) {
            BasicTransferInformation cached =  i.next();
            InputDataClass insert = new InputDataClass(cached.getId(), cached.getName(), cached.getPost(),
                    cached.getDateStart().toString(), cached.getDateEnd().toString());
            basicDataRows.put(cached.getId(), insert);
        }

        Map<Integer, DestinationDataClass> destinationDataRows = new LinkedHashMap<>();

        for(Iterator<DestinationInformationEntity> i = destinationData.iterator(); i.hasNext();) {
            DestinationInformationEntity cached =  i.next();
            DestinationDataClass insert = new DestinationDataClass(cached.getId(), cached.getOriginCountry(),
                    cached.getOriginStatePost(), cached.getOriginCity(), cached.getOriginLatitude(),
                    cached.getOriginLongitude(), cached.getDestinationCountry(), cached.getDestinationStatePost(),
                    cached.getDestinationCity(), cached.getDestinationLatitude(), cached.getDestinationLongitude(),
                    cached.getTransferId());
            destinationDataRows.put(cached.getTransferId(), insert);
        }

        Map<Integer, OutputDataClass> outputDataRows = new LinkedHashMap<>();

        for(Integer key : basicDataRows.keySet()) {
            if(destinationDataRows.containsKey(key)) {
                outputDataRows.put(key, new OutputDataClass(basicDataRows.get(key), destinationDataRows.get(key)));
            } else {
                outputDataRows.put(key, new OutputDataClass(basicDataRows.get(key)));
            }
        }


        String json = gson.toJson(outputDataRows.values());
        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(json);
    }

    private static java.sql.Date Str2Date (String startDate) {
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
