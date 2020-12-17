package kraptis91.maritime.api.service;

import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.retriever.MaritimeDataRetriever;
import kraptis91.maritime.retriever.RetrieverFactory;
import kraptis91.maritime.retriever.exception.RetrieverException;

import java.util.List;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 7/12/2020. */
public class VesselService {

  public List<Vessel> getVesselsByShipType(String shipType, int skip, int limit) {
    // create a demo Maritime data retriever
    MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
    // use the demo Maritime data retriever to return demo data
    return dataRetriever.getVesselsByType(shipType, skip, limit);
  }

  public Vessel getVesselByMMSI(int mmsi) throws RetrieverException {
    // create a demo Maritime data retriever
    MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
    // use the Maritime data retriever to return demo data
    return dataRetriever.getVesselByMMSI(mmsi);
  }

  public Vessel getVesselByName(String vesselName) throws RetrieverException {
    // create a demo Maritime data retriever
    MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
    // use the Maritime data retriever to return demo data
    return dataRetriever.getVesselByName(vesselName);
  }
}
