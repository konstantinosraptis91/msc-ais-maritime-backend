package kraptis91.maritime.api.service;

import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.retriever.MaritimeDemoDataRetriever;
import kraptis91.maritime.retriever.RetrieverFactory;

import java.util.List;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 7/12/2020. */
public class VesselService {

  public List<Vessel> getVesselsByShipType(String shipType) {
    // create a demo Maritime data retriever
    MaritimeDemoDataRetriever demoDataRetriever =
        RetrieverFactory.createMaritimeDemoDataRetriever();
    // use the demo Maritime data retriever to return demo data
    return demoDataRetriever.getVesselsByType(shipType);
  }
}
