package kraptis91.maritime.dao;

import com.mongodb.client.MongoCollection;
import kraptis91.maritime.MongoDB;
import kraptis91.maritime.dao.mongodb.MongoDBOceanConditionsDao;
import kraptis91.maritime.model.OceanConditions;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 30/11/2020. */
public class MongoDBOceanConditionsDaoTest {

  /** @author Stavros Lamprinos [stalab at linuxmail.org] on 11/12/2020. */
  @Test
  public void testOceanConditionsMany() throws Exception {
    //  Only for testing. Inserting should be removed from here
    //  Τζάμπα τα sets? μήπως θέλει από το διάβασμα του αρχείου σε Document και insert?
    MongoCollection<Document> collection =
        MongoDB.MARITIME.getDatabase().getCollection("oceanConditions");

    Set<OceanConditions> ocCsvData = MongoDBOceanConditionsDao.createOceanConditionsSetDemo();

    //  testing
    Assert.assertNotNull(ocCsvData);
    List<Document> ocDocuments = new ArrayList<>();

    for (OceanConditions oc : ocCsvData) {
      Document doc = new Document();
      HashMap<String, Object> documentValues = new HashMap<>();
      documentValues.put("longitude", oc.getLongitude());
      documentValues.put("latitude", oc.getLatitude());
      documentValues.put("bottomDepth", oc.getBottomDepth());
      documentValues.put("tidalEffect", oc.getTidalEffect());
      documentValues.put("seaHeight", oc.getSeaHeight());
      documentValues.put("meanWaveLength", oc.getMeanWaveLength());
      //  Πρόβλημα με τον Big Int κατά το insert. Δες το
      //documentValues.put("timestamp", o.getTimestamp());
      doc.putAll(documentValues);

      ocDocuments.add(doc);
    }

    collection.insertMany(ocDocuments);

  }
}
