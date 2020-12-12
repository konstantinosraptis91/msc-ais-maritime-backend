package kraptis91.maritime.enums;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import kraptis91.maritime.model.OceanConditions;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Arrays;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 30/11/2020. */
public enum MongoDB {
  MARITIME("maritime");

  private final MongoDatabase database;

  MongoDB(String dbName) {
    MongoClient mongoClient = new MongoClient("localhost", 27017);

    //    MongoCredential credential =
    //        MongoCredential.createCredential(
    //            MongoDBConfig.INSTANCE.getUser(),
    //            MongoDBConfig.INSTANCE.getSource(),
    //            MongoDBConfig.INSTANCE.getPassword().toCharArray());

    //    MongoClient mongoClient =
    //            MongoClients.create(MongoClientSettings.builder()
    //                    .applyToClusterSettings(builder ->
    //                            builder.hosts(Arrays.asList(new ServerAddress("", 27017))))
    //                    .credential(credential)
    //                    .build());

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
