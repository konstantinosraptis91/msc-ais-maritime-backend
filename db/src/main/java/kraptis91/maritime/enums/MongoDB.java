package kraptis91.maritime.enums;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import kraptis91.maritime.model.OceanConditions;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 30/11/2020. */
public enum MongoDB {
  MARITIME("maritime");

  private final MongoDatabase database;

  MongoDB(String dbName) {
    // local mongo db client
    MongoClient mongoClient =
        new MongoClient(MongoDBConfig.INSTANCE.getHost(), MongoDBConfig.INSTANCE.getPort());

    // remote mongo db client
    //    com.mongodb.client.MongoClient mongoClient =
    //        com.mongodb.client.MongoClients.create(
    //            "mongodb+srv://"
    //                + MongoDBConfig.INSTANCE.getUser()
    //                + ":"
    //                + MongoDBConfig.INSTANCE.getPassword()
    //                + "@"
    //                + MongoDBConfig.INSTANCE.getHost());

    CodecProvider pojoCodecProvider =
        PojoCodecProvider.builder().register(OceanConditions.class).build();

    CodecRegistry pojoCodecRegistry =
        CodecRegistries.fromRegistries(
            MongoClient.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(pojoCodecProvider));

    this.database = mongoClient.getDatabase(dbName).withCodecRegistry(pojoCodecRegistry);
  }

  public MongoDatabase getDatabase() {
    return database;
  }
}
