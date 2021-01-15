package kraptis91.maritime.db;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import kraptis91.maritime.db.enums.MongoDB;
import org.bson.Document;
import org.junit.Ignore;
import org.junit.Test;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 30/11/2020. */
public class MongoDBTest {

  @Ignore
  @Test
  public void testMaritimeInstance() throws Exception {
    // System.out.println(MongoDB.MARITIME.getDatabase().listCollectionNames().first());

    final BasicDBObject searchQuery = new BasicDBObject();
    searchQuery.put("altitude", 943);
    MongoCollection<Document> collection =
        MongoDB.MARITIME.getDatabase().getCollection("nari_dynamic_sar");

    try (MongoCursor<Document> cursor = collection.find(searchQuery).iterator()) {
      while (cursor.hasNext()) {
        System.out.println(cursor.next().toJson());
      }
    }
  }
}
