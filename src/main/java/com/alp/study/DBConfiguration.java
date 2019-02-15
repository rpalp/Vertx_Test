package com.alp.study;



import com.fasterxml.jackson.databind.ObjectMapper;
import io.requery.Persistable;
import io.requery.cache.EntityCacheBuilder;
import io.requery.cache.WeakEntityCache;
import io.requery.jackson.EntityMapper;
import io.requery.meta.EntityModel;
import io.requery.reactivex.ReactiveEntityStore;
import io.requery.reactivex.ReactiveSupport;
import io.requery.sql.*;
import com.alp.study.model.Models;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Executors;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

public class DBConfiguration {

    private String dbUrl="jdbc:mysql://172.17.0.2:3306/db_delivery";
    private String user="admin";
    private String pass="s1mpletooR";



    public ObjectMapper objectMapper(EntityDataStore entityDataStore) {
        return new EntityMapper(Models.DEFAULT, entityDataStore);
    }



    public ReactiveEntityStore<Persistable> provideDataStore() {

        ConnectionProvider connectionProvider = new ConnectionProvider() {
            @Override
            public Connection getConnection() {
                try {
                    return DriverManager.getConnection(dbUrl, user, pass);
                }  catch (SQLException ex) {
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
                    return null;
                }

            }
        };

        EntityModel model = com.alp.study.model.Models.DEFAULT;
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();

        Configuration configuration = new ConfigurationBuilder(connectionProvider, model)
                .useDefaultLogging()
                .setWriteExecutor(Executors.newSingleThreadExecutor())
                .setEntityCache(new EntityCacheBuilder(model)
                        .useReferenceCache(true)
                        .useSerializableCache(true)
                        .useCacheManager(cacheManager)
                        .build())
                .build();

//        SchemaModifier tables = new SchemaModifier(configuration);
//        tables.createTables(TableCreationMode.DROP_CREATE);

        return  ReactiveSupport.toReactiveStore(new EntityDataStore<Persistable>(configuration));
    }
}
