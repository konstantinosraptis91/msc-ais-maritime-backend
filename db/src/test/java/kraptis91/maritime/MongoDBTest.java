package kraptis91.maritime;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.io.BsonOutput;
import org.junit.Test;

import java.util.function.Consumer;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 30/11/2020. */
public class MongoDBTest {

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
