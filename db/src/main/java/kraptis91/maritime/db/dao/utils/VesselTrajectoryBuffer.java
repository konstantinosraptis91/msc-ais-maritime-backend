package kraptis91.maritime.db.dao.utils;

import com.google.common.collect.ImmutableList;
import kraptis91.maritime.db.utils.ModelExtractor;
import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.VesselTrajectoryChunk;
import kraptis91.maritime.parser.dto.csv.NariDynamicDto;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 27/12/2020.
 */
public class VesselTrajectoryBuffer {

    public static final Logger LOGGER =
            Logger.getLogger(VesselTrajectoryBuffer.class.getName());

    private final Map<Integer, VesselTrajectoryChunk> incompletedChunkMap;
    private final List<VesselTrajectoryChunk> completedChunkList = new ArrayList<>();

    // total vessel trajectory points in completedChunkList chunks
    private int pointsInChunkListCounter = 0;
    private final int capacity;

    private int dtoCounter = 0;

    public VesselTrajectoryBuffer(int capacity) {
        this.incompletedChunkMap = new LinkedHashMap<>();
        this.capacity = capacity;
    }

    public static VesselTrajectoryBuffer createInstance(int capacity) {
        return new VesselTrajectoryBuffer(capacity);
    }

    public void addPoint(NariDynamicDto dto, Vessel vessel) {

        VesselTrajectoryChunk chunk;

        dtoCounter++;
        // System.out.println("DtoCounter: " + dtoCounter + " " + dto.toString());

        if (incompletedChunkMap.containsKey(dto.getMMSI())) {

            chunk = incompletedChunkMap.get(dto.getMMSI());

            if (chunk.getNumberOfPoints() < chunk.getChunkFixedSize()) {

                // System.out.println("DtoCounter: " + dtoCounter + " extractVesselTrajectoryPoint 1");
                chunk.getPointList().add(
                        ModelExtractor.extractVesselTrajectoryPoint(dto, vessel.getId()));
            } else {

                // chunk is full remove it and add it as completed in completed list
                incompletedChunkMap.remove(dto.getMMSI());
                finalizeChunk(chunk);
                // System.out.println("DtoCounter: " + dtoCounter + " Adding to completedChunkList");
                completedChunkList.add(chunk);
                pointsInChunkListCounter += chunk.getNumberOfPoints();

                // create a new chunk for that mmsi and add it to map
                chunk = VesselTrajectoryChunk.builder(dto.getMMSI())
                        .withVesselName(vessel.getVesselName())
                        .withShipType(vessel.getShipType())
                        .build();

                chunk.getPointList().add(
                        ModelExtractor.extractVesselTrajectoryPoint(dto, vessel.getId()));

                incompletedChunkMap.put(dto.getMMSI(), chunk);
            }

        } else {

            chunk = VesselTrajectoryChunk.builder(dto.getMMSI())
                    .withVesselName(vessel.getVesselName())
                    .withShipType(vessel.getShipType())
                    .build();

            // System.out.println("DtoCounter: " + dtoCounter + " extractVesselTrajectoryPoint 2");
            chunk.getPointList().add(
                    ModelExtractor.extractVesselTrajectoryPoint(dto, vessel.getId()));

            incompletedChunkMap.put(dto.getMMSI(), chunk);
        }

    }

    public boolean isCompletedListFull() {
        return pointsInChunkListCounter >= capacity;
    }

    public void clearCompletedChunkList() {
        completedChunkList.clear();
        pointsInChunkListCounter = 0;
    }

    public List<VesselTrajectoryChunk> getCompletedChunkList() {
        return ImmutableList.copyOf(completedChunkList);
    }

    public List<VesselTrajectoryChunk> getIncompletedChunkList() {
        incompletedChunkMap.values().forEach(this::finalizeChunk);
        return new ArrayList<>(incompletedChunkMap.values());
    }

    public void clearIncompletedChunkMap() {
        incompletedChunkMap.clear();
    }

    private void finalizeChunk(VesselTrajectoryChunk chunk) {
        chunk.setStartDate(new Date(chunk.calcStartDateTimestamp()));
        chunk.setEndDate(new Date(chunk.calcEndDateTimestamp()));
        chunk.setAvgGeoPoint(chunk.calcAvgGeoPoint());
        chunk.setAvgSpeed(chunk.calcAvgSpeed());
    }

}
