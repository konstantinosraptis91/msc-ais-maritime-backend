package kraptis91.maritime.db.enums;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoDatabase;
import kraptis91.maritime.db.dao.mongodb.codec.OceanConditionsCodec;
import kraptis91.maritime.db.dao.mongodb.codec.PortCodec;
import kraptis91.maritime.db.dao.mongodb.codec.VesselCodec;
import kraptis91.maritime.db.dao.mongodb.codec.VesselTrajectoryPointListChunkCodec;
import kraptis91.maritime.db.dao.mongodb.conf.MongoDBConfig;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.concurrent.TimeUnit;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 30/11/2020.
 */
public enum MongoDB {
    MARITIME("maritime");

    private final MongoDatabase database;

    MongoDB(String dbName) {

        MongoClientSettings settings = MongoClientSettings.builder()
            .applyToSocketSettings(
                builder -> builder.connectTimeout(5, TimeUnit.MINUTES))
            .applyToSocketSettings(
                builder -> builder.readTimeout(5, TimeUnit.MINUTES))
            .applyConnectionString(new ConnectionString(createConnectionString()))
            .build();

        // create a mongo db client
        com.mongodb.client.MongoClient mongoClient =
            com.mongodb.client.MongoClients.create(settings);

        // codec registry for all model class by package reference
        CodecProvider pojoCodecProvider =
            PojoCodecProvider.builder().register("kraptis91.maritime.model").build();

        CodecRegistry pojoCodecRegistry =
            CodecRegistries.fromRegistries(
                CodecRegistries.fromCodecs(new VesselCodec()),
                CodecRegistries.fromCodecs(new VesselTrajectoryPointListChunkCodec()),
                CodecRegistries.fromCodecs(new PortCodec()),
                CodecRegistries.fromCodecs(new OceanConditionsCodec()),
                MongoClient.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(pojoCodecProvider));

        this.database = mongoClient.getDatabase(dbName).withCodecRegistry(pojoCodecRegistry);
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    private String createConnectionString() {

        return MongoDBConfig.useRemote()
            // ? "mongodb+srv://"
            ? "mongodb://"
            + MongoDBConfig.getUser()
            + ":"
            + String.valueOf(MongoDBConfig.getPassword())
            + "@"
            + MongoDBConfig.getHost() + ":" + MongoDBConfig.getPort()
            : "mongodb://" + MongoDBConfig.getHost() + ":" + MongoDBConfig.getPort();
    }

//    private String createConnectionString() {
//
//        return MongoDBConfig.useRemote()
//            ? "mongodb://"
//            //+ MongoDBConfig.INSTANCE.getUser()
//            //+ ":"
//            //+ String.valueOf(MongoDBConfig.INSTANCE.getPassword())
//            //+ "@"
//            + MongoDBConfig.getHost()
//            + ":"
//            + MongoDBConfig.getPort()
//            : "mongodb://" + MongoDBConfig.getHost() + ":" + MongoDBConfig.getPort();
//    }
}
