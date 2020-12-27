package kraptis91.maritime.db.dao.mongodb;

import com.mongodb.client.MongoCollection;
import kraptis91.maritime.db.dao.DaoFactory;
import kraptis91.maritime.db.dao.VesselDao;
import kraptis91.maritime.db.dao.VesselTrajectoryPointDao;
import kraptis91.maritime.db.enums.MongoDB;
import kraptis91.maritime.db.enums.MongoDBCollection;
import kraptis91.maritime.db.utils.ModelExtractor;
import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.VesselTrajectory;
import kraptis91.maritime.model.VesselTrajectoryPoint;
import kraptis91.maritime.parser.CSVParser;
import kraptis91.maritime.parser.dto.csv.NariDynamicDto;
import kraptis91.maritime.parser.exception.CSVParserException;
import kraptis91.maritime.parser.utils.InputStreamUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 13/12/2020. */
public class MongoVesselTrajectoryDao implements VesselTrajectoryPointDao {

  public static final Logger LOGGER = Logger.getLogger(MongoVesselTrajectoryDao.class.getName());
  public static final int NUMBER_OF_CHUNKS = 50;

  private MongoCollection<VesselTrajectory> vesselTrajectoryMongoCollection;

  public static MongoCollection<VesselTrajectory> createVesselTrajectoryCollection() {
    return MongoDB.MARITIME
        .getDatabase()
        .getCollection(
            MongoDBCollection.VESSEL_TRAJECTORY.getCollectionName(), VesselTrajectory.class);
  }

  //  public MongoCollection<VesselTrajectory> getVesselTrajectoryCollection() {
  //    if (Objects.isNull(vesselTrajectoryMongoCollection)) {
  //      vesselTrajectoryMongoCollection = createVesselTrajectoryCollection();
  //    }
  //
  //    return vesselTrajectoryMongoCollection;
  //  }

  @Override
  public void insertMany(InputStream csvStream, int chunkSize) throws Exception {

    // LOGGER.info("Inserting " + is.available() + " bytes to db.");

    final BufferedReader bufferedReader =
        new BufferedReader(
            new InputStreamReader(InputStreamUtils.getBufferedInputStream(csvStream)));
    final CSVParser parser = new CSVParser();
    // key = trajectory uuid, value = Trajectory
    final Map<String, VesselTrajectory> trajectoryMap = new LinkedHashMap<>();
    // key = mmsi, value = vessel
    final Map<Integer, Vessel> vesselMap = new LinkedHashMap<>();
    // key = mmsi, value = list of trajectory uuids
    final Map<Integer, List<String>> trajectoryUUIDMap = new LinkedHashMap<>();
    // Vessel dao to find ObjectId of vessels
    final VesselDao vesselDao = DaoFactory.createMongoVesselDao();
    // Buffer to write trajectories
    final List<VesselTrajectory> buffer = new ArrayList<>(chunkSize);

    String line;
    NariDynamicDto dto;
    boolean isFirstLine = true;

    while ((line = bufferedReader.readLine()) != null) {

      // omit first line
      if (isFirstLine) {
        isFirstLine = false;
        continue;
      }

      try {

        // parse current line to the dto
        dto = parser.extractNariDynamicDto(line);
        // before add find vessel ObjectId
        if (trajectoryUUIDMap.containsKey(dto.getMmsi())) { // trajectory found, get it from map

          final long t = dto.getT();
          final NariDynamicDto tempDto = dto;

          VesselTrajectory trajectory =
              trajectoryUUIDMap.get(dto.getMmsi()).stream()
                  .map(trajectoryMap::get)
                  .filter(
                      trajectory1 ->
                          t >= trajectory1.getStartDate().getTime()
                              && t <= trajectory1.getEndDate().getTime())
                  .findAny()
                  .orElseThrow(
                      () ->
                          new NoSuchElementException(
                              "Error... cannot find a trajectory to store current dto: "
                                  + tempDto.toString()));

          if (trajectory.getNumberOfPoints() < 250) { // 250 points per trajectory

            if (vesselMap.containsKey(dto.getMmsi())) { // check if vessel if in vessel map

              trajectory
                  .getPointList()
                  .add(
                      ModelExtractor.extractVesselTrajectoryPoint(
                          dto, vesselMap.get(dto.getMmsi()).getId()));

            } else { // or get it from db and store it in map

              Optional<Vessel> oVessel = vesselDao.findVesselByMMSI(dto.getMmsi());

              if (oVessel.isPresent()) { // found

                trajectory
                    .getPointList()
                    .add(ModelExtractor.extractVesselTrajectoryPoint(dto, oVessel.get().getId()));

                // add it to map
                vesselMap.put(dto.getMmsi(), oVessel.get());

              } else { // not found in db

                // LOGGER.log(Level.SEVERE, "Error... cannot find vessel in db with mmsi " + dto.getMmsi());
              }
            }

          } else { // points are 250, time to store trajectory in buffer

            if (buffer.size() == chunkSize) { // dump buffer to db and clear
              insertMany(buffer);
              buffer.clear();
            }

            buffer.add(trajectory);

            // remove trajectory from maps
            trajectoryUUIDMap.get(trajectory.getMmsi()).remove(trajectory.getUUID());
            trajectoryMap.remove(trajectory.getUUID());
          }

        } else { // trajectory not yet created, create a new one and store it to map

          // create a trajectory for every voyage of the vessel with mmsi dto.getMMSI
          final int mmsi = dto.getMmsi();
          final long t = dto.getT();
          final NariDynamicDto tempDto = dto;
          final Vessel tempVessel =
              vesselMap.containsKey(mmsi) // check if vessel if in vessel map
                  ? vesselMap.get(mmsi)
                  : vesselDao
                      .findVesselByMMSI(mmsi)
                      .orElseThrow(
                          () ->
                              new NoSuchElementException(
                                  "Error... cannot find vessel in db with mmsi " + mmsi));

          // in case vessel retrieved from db, store it in map
          if (vesselMap.containsKey(mmsi)) {
            vesselMap.put(mmsi, tempVessel);
          }

          List<VesselTrajectory> trajectories =
              tempVessel.getVoyageMap().values().stream()
                  .map(
                      voyage ->
                          ModelExtractor.extractVesselTrajectory(
                              tempVessel.getMMSI(),
                              tempVessel.getVesselName(),
                              tempVessel.getShipType(),
                              voyage))
                  .collect(Collectors.toList());

          trajectories.forEach(
              trajectory -> {
                String uuid = UUID.randomUUID().toString();
                trajectory.setUUID(uuid);
                trajectoryMap.put(uuid, trajectory);

                tempVessel
                    .getVoyageMap()
                    .values()
                    .forEach(
                        voyage -> {
                          if (t >= voyage.getFirstMeasurement().getDate().getTime()
                              && t <= voyage.getLastMeasurement().getDate().getTime()) {

                            trajectory
                                .getPointList()
                                .add(
                                    ModelExtractor.extractVesselTrajectoryPoint(
                                        tempDto, tempVessel.getId()));
                          }
                        });

                if (trajectoryUUIDMap.containsKey(tempVessel.getMMSI())) {

                  trajectoryUUIDMap.get(tempVessel.getMMSI()).add(uuid);
                } else {

                  List<String> uuidList = new ArrayList<>();
                  uuidList.add(uuid);
                  trajectoryUUIDMap.put(tempVessel.getMMSI(), uuidList);
                }
              });
        }

      } catch (CSVParserException e) {
        // LOGGER.log(Level.WARNING, "Discarding corrupted line" + e.getMessage());
      } catch (NoSuchElementException e) {
        // LOGGER.log(Level.SEVERE, e.getMessage());
      }
    }

    // add any trajectories left in map before clear map
    trajectoryMap.values().forEach(this::insertOne);
    trajectoryMap.clear();
    trajectoryUUIDMap.clear();
    vesselMap.clear();
    insertMany(buffer);
    buffer.clear();

    LOGGER.info("All lines inserted to db successfully.");
    // LOGGER.info("Total vessel trajectories added to db: " + totalTrajectories);
  }

  public void insertOne(VesselTrajectory trajectory) {
    MongoCollection<VesselTrajectory> collection = createVesselTrajectoryCollection();
    collection.insertOne(trajectory);
  }

  @Override
  public void insertMany(List<VesselTrajectory> trajectories) {
    // LOGGER.info("Inserting data to db START.");
    MongoCollection<VesselTrajectory> collection = createVesselTrajectoryCollection();
    collection.insertMany(trajectories);
    // LOGGER.info("Inserting data to db END.");
  }

  @Override
  public List<VesselTrajectoryPoint> findVesselTrajectory(String vesselName, int skip, int limit) {
    final List<VesselTrajectoryPoint> trajectoryPointList = new ArrayList<>();
    //    createVesselTrajectoryCollection()
    //        .aggregate(
    //            Arrays.asList(
    //                Aggregates.lookup("vessels", "vesselId", "_id", "vesselTrajectory"),
    //                Aggregates.match(Filters.eq("vesselTrajectory.vesselName", vesselName)),
    //                Aggregates.skip(skip),
    //                Aggregates.limit(limit)))
    //        .forEach((Consumer<VesselTrajectoryPoint>) trajectoryPointList::add);
    return trajectoryPointList;
  }

  @Override
  public List<VesselTrajectoryPoint> findVesselTrajectory(int mmsi, int skip, int limit) {
    final List<VesselTrajectoryPoint> trajectoryPointList = new ArrayList<>();
    //    createVesselTrajectoryCollection()
    //        .aggregate(
    //            Arrays.asList(
    //                Aggregates.lookup("vessels", "vesselId", "_id", "vesselTrajectory"),
    //                Aggregates.match(Filters.eq("vesselTrajectory.mmsi", mmsi)),
    //                Aggregates.skip(skip),
    //                Aggregates.limit(limit)))
    //        .forEach((Consumer<VesselTrajectoryPoint>) trajectoryPointList::add);
    return trajectoryPointList;
  }

  public static int calcNumberOfPointsPerTrajectory(int mmsiCounter) {
    return mmsiCounter / NUMBER_OF_CHUNKS;
  }
}
