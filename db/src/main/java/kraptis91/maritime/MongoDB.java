package kraptis91.maritime;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 30/11/2020. */
public enum MongoDB {

  MARITIME("maritime");

  private MongoDatabase database;

  MongoDB(String dbName) {
    MongoClient mongoClient = new MongoClient("localhost", 27017);
    this.database = mongoClient.getDatabase(dbName);
  }

  public MongoDatabase getDatabase() {
    return database;
  }
}
