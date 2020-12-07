package kraptis91.maritime.retriever;

import kraptis91.maritime.retriever.impl.MaritimeDataRetrieverImpl;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 6/12/2020. */
public class RetrieverFactory {

  public static MaritimeDataRetriever createMaritimeDataRetriever() {
    return new MaritimeDataRetrieverImpl();
  }

  public static MaritimeDemoDataRetriever createMaritimeDemoDataRetriever() {
    return new MaritimeDataRetrieverImpl();
  }
}
