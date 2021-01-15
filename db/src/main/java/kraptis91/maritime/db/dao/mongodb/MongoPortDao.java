package kraptis91.maritime.db.dao.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import kraptis91.maritime.codelists.CodelistsOfBiMap;
import kraptis91.maritime.db.dao.PortDao;
import kraptis91.maritime.db.dao.mongodb.query.utils.NearQueryOptions;
import kraptis91.maritime.db.enums.MongoDB;
import kraptis91.maritime.db.enums.MongoDBCollection;
import kraptis91.maritime.db.enums.TransactionStatus;
import kraptis91.maritime.model.Port;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 30/12/2020.
 */
public class MongoPortDao implements PortDao, DocumentBuilder {

    public static final Logger LOGGER = Logger.getLogger(MongoPortDao.class.getName());

    public static MongoCollection<Port> createWorldPortCollection() {
        return MongoDB.MARITIME
            .getDatabase()
            .getCollection(
                MongoDBCollection.WORLD_PORTS.getCollectionName(), Port.class);
    }

    @Override
    public void insertMany(List<Port> portList) {

        LOGGER.info("Inserting " + portList.size() + " ports to db START.");
        long startTime = System.currentTimeMillis();
        TransactionStatus status = TransactionStatus.FAILED;

        if (!portList.isEmpty()) {
            MongoCollection<Port> collection = createWorldPortCollection();
            collection.insertMany(portList);
            status = TransactionStatus.SUCCESS;
        } else {
            LOGGER.log(Level.WARNING, "Trying to insert an empty port list to db...");
        }

        long endTime = System.currentTimeMillis();
        LOGGER.info("Inserting " + portList.size()
            + " ports to db FINISH at [" + (endTime - startTime) + "]"
            + " with status: " + status.name());
    }

    @Override
    public List<Port> findPorts(int skip, int limit) {
        final List<Port> portList = new ArrayList<>();
        createWorldPortCollection()
            .find()
            .skip(skip)
            .limit(limit)
            .forEach((Consumer<Port>) portList::add);

        LOGGER.info("Found " + portList.size() + " ports");

        return portList;
    }

    @Override
    public List<Port> findPortsByCountryCode(String countryCode) {

        final List<Port> portList = new ArrayList<>();
        String codeToUpperCase = countryCode.toUpperCase();

        try {
            createWorldPortCollection()
                .find(Filters.eq("country",
                    CodelistsOfBiMap.COUNTRY_CODE_MAP.getOptionalValueForId(codeToUpperCase)
                        .orElseThrow(() -> new NoSuchElementException(
                            "ERROR... Country code bimap does not contain country code " + codeToUpperCase))))
                .forEach((Consumer<Port>) portList::add);
        } catch (NoSuchElementException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        LOGGER.info("Found " + portList.size()
            + " ports for countryCode: " + countryCode);

        return portList;
    }

    @Override
    public List<Port> findNearPorts(double longitude,
                                    double latitude,
                                    double maxDistance,
                                    double minDistance, int skip, int limit) {

        final Point refPoint = new Point(new Position(longitude, latitude));
        final List<Port> nearPorts = new ArrayList<>();
        createWorldPortCollection()
            .find(Filters.near("geoPoint", refPoint, maxDistance, minDistance))
            .skip(skip)
            .limit(limit)
            .forEach((Consumer<Port>) nearPorts::add);

        LOGGER.info("Found " + nearPorts.size() + " ports near ["
            + longitude + ", " + latitude + "] at max distance: "
            + maxDistance + " meters, min distance: "
            + minDistance + " meters");

        return nearPorts;
    }

    @Override
    public List<Port> findNearPorts(NearQueryOptions options) {
        return findNearPorts(options.getLongitude(),
            options.getLatitude(),
            options.getMaxDistance(),
            options.getMinDistance(),
            options.getSkip(),
            options.getLimit());
    }
}
