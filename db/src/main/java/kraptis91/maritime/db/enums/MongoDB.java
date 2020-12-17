package kraptis91.maritime.db.enums;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import kraptis91.maritime.db.dao.mongodb.codec.VesselCodec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 30/11/2020.
 */
public enum MongoDB {
    MARITIME("maritime");

    private final MongoDatabase database;

    MongoDB(String dbName) {

        // create a mongo db client
        com.mongodb.client.MongoClient mongoClient =
                com.mongodb.client.MongoClients.create(createConnectionString());

        // codec registry for all model class by package reference
        CodecProvider pojoCodecProvider =
                PojoCodecProvider.builder().register("kraptis91.maritime.model").build();

        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                CodecRegistries.fromCodecs(new VesselCodec()),
                MongoClient.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(pojoCodecProvider));

        this.database = mongoClient.getDatabase(dbName)
                .withCodecRegistry(pojoCodecRegistry);
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    private String createConnectionString() {

        return MongoDBConfig.INSTANCE.useRemote()
                ? "mongodb+srv://"
                + MongoDBConfig.INSTANCE.getUser()
                + ":"
                + String.valueOf(MongoDBConfig.INSTANCE.getPassword())
                + "@"
                + MongoDBConfig.INSTANCE.getHost()
                : "mongodb://" + MongoDBConfig.INSTANCE.getHost() + ":" + MongoDBConfig.INSTANCE.getPort();
    }
}
