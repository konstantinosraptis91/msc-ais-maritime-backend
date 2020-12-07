package kraptis91.maritime.api.controller;

import io.javalin.http.Handler;
import kraptis91.maritime.api.service.VesselService;
import kraptis91.maritime.retriever.MaritimeDemoDataRetriever;
import kraptis91.maritime.retriever.RetrieverFactory;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 7/12/2020. */
public class VesselController {

  public static Handler getVesselsByShipType =
      ctx -> {
        // extract param from url path
        String shipTypeParam = ctx.pathParam("shiptype");
        // create a new service
        VesselService service = new VesselService();
        // use service to get the demo data
        ctx.json(service.getVesselsByShipType(shipTypeParam));
      };
}
