package kraptis91.maritime.retriever;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020.
 */
public interface MaritimeDataRetriever {

    String getVesselPosition();

    String getVesselTrajectory(String vesselName);

}
