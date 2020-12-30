package kraptis91.maritime.model;

import kraptis91.maritime.parser.dto.csv.NariDynamicDto;
import kraptis91.maritime.parser.dto.csv.NariStaticDto;
import kraptis91.maritime.parser.dto.csv.SeaStateForecastDto;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 11/12/2020.
 */
public class ModelExtractor {

    public static OceanConditions extractOceanConditions(SeaStateForecastDto dto) {

        return new OceanConditions.Builder(dto.getLon(), dto.getLat(), dto.getTs())
                .withBottomDepth(dto.getDpt())
                .withTidalEffect(dto.getWlv())
                .withSeaHeight(dto.getHs())
                .withMeanWaveLength(dto.getLm())
                .build();
    }

    public static Vessel extractVessel(NariStaticDto dto, String shipType, String country) {

        Vessel vessel =
                Vessel.fluentBuilder(dto.getMmsi())
                        .withIMO(dto.getImo())
                        .withVesselName(dto.getShipName())
                        .withCallSign(dto.getCallSign())
                        .withDraught(dto.getDraught())
                        .withShipType(shipType)
                        .withCountry(country)
                        .build();

        vessel.addVoyageAndApplyMeasurement(
                extractVoyage(dto),
                ReceiverMeasurement.builder()
                        .withETA(dto.getEta())
                        .withToPort(dto.getToPort())
                        .withDate(new Date(dto.getT()))
                        .build());

        return vessel;
    }

    public static Voyage extractVoyage(NariStaticDto dto) {
        return Voyage.createInstance(dto.getDestination());
    }

    public static VesselTrajectoryPoint extractVesselTrajectoryPoint(NariDynamicDto dto,
                                                                     String vesselId) {
        return VesselTrajectoryPoint.builder()
                .withCoordinates(GeoPoint.of(dto.getLon(), dto.getLat()))
                .withSpeed(dto.getSpeed())
                .withTimestamp(dto.getT())
                .withVesselId(vesselId)
                .build();
    }

    public static @NotNull VesselTrajectoryChunk extractVesselTrajectoryChunk(Document document) {

//        final int mmsi = document.getInteger("mmsi");
//        final String vesselName = document.getString("vesselName");
//        final String shipType = document.getString("shipType");
//        final Date startDate = document.getDate("startDate");
//        final Date endDate = document.getDate("endDate");
//        final GeoPoint avgGeoPoint = extractGeoPoint(document.get("avgGeoPoint", Document.class));
//        final double avgSpeed = document.getDouble("avgSpeed");
//        final int nPoints = document.getInteger("nPoints");
//
//        return new VesselTrajectoryChunkBuilder(mmsi)
//                .withVesselName(vesselName)
//                .withShipType(shipType)
//                .withStartDate(startDate)
//                .withEndDate(endDate)
//                .withAvgGeoPoint(avgGeoPoint)
//                .withAvgSpeed(avgSpeed)
//                .withNPoints(nPoints)
//                .buildChunk();
        return extractVesselTrajectoryPointListChunk(document);
    }

    public static @NotNull VesselTrajectoryPointListChunk extractVesselTrajectoryPointListChunk(Document document) {

        final int mmsi = document.getInteger("mmsi");
        final String vesselName = document.getString("vesselName");
        final String shipType = document.getString("shipType");
        final Date startDate = document.getDate("startDate");
        final Date endDate = document.getDate("endDate");
        final GeoPoint avgGeoPoint = extractGeoPoint(document.get("avgGeoPoint", Document.class));
        final double avgSpeed = document.getDouble("avgSpeed");
        final int nPoints = document.getInteger("nPoints");

        return new VesselTrajectoryChunkBuilder(mmsi)
            .withVesselName(vesselName)
            .withShipType(shipType)
            .withStartDate(startDate)
            .withEndDate(endDate)
            .withAvgGeoPoint(avgGeoPoint)
            .withAvgSpeed(avgSpeed)
            .withNPoints(nPoints)
            .buildPointListChunk();
    }

    public static VesselTrajectoryPoint extractVesselTrajectoryPoint(Document pointDoc) {
        return VesselTrajectoryPoint.builder()
            .withCoordinates(extractGeoPoint(pointDoc.get("geoPoint", Document.class)))
            .withSpeed(pointDoc.getDouble("speed"))
            .withTimestamp(pointDoc.getLong("timestamp"))
            .withVesselId(pointDoc.getObjectId("vesselId").toHexString())
            .build();
    }

    public static GeoPoint extractGeoPoint(Document geoPointDoc) {
        List<Double> coordinates = geoPointDoc.getList("coordinates", Double.class);
        return GeoPoint.of(coordinates.get(0), coordinates.get(1));
    }

}
