package kraptis91.maritime.retriever;

import kraptis91.maritime.model.Vessel;

import java.util.List;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 7/12/2020. */
public interface MaritimeDemoDataRetriever {

  List<Vessel> getVesselsByType(String shipType);
}
