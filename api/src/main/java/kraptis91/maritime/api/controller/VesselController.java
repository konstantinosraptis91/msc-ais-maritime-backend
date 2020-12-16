package kraptis91.maritime.api.controller;

import io.javalin.http.Handler;
import kraptis91.maritime.api.service.VesselService;
import kraptis91.maritime.retriever.exception.RetrieverException;

import java.util.Objects;
import java.util.Optional;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 7/12/2020. */
public class VesselController {

  public static Handler getVesselsByShipType =
      ctx -> {
        // extract param from url path
        String shipTypeParam = ctx.pathParam("shiptype");

        Integer skipHeader = ctx.header("skip", Integer.class).getOrNull();
        Integer limitHeader = ctx.header("limit", Integer.class).getOrNull();

        // create a new service
        VesselService service = new VesselService();
        // use service to get the demo data
        ctx.json(
            service.getVesselsByShipType(
                shipTypeParam,
                Optional.ofNullable(skipHeader).orElse(0),
                Optional.ofNullable(limitHeader).orElse(5)));
      };

  public static Handler getVesselByMMSI =
      ctx -> {
        // extract param from url path
        Integer mmsiParam = ctx.pathParam("mmsi", Integer.class).getOrNull();

        if (!Objects.isNull(mmsiParam)) {
          // create a new service
          VesselService service = new VesselService();
          // use service to get the demo data
          ctx.json(service.getVesselByMMSI(mmsiParam));
        } else {
          ctx.status(400);
        }
      };

  public static Handler getVesselByName =
      ctx -> {
        // extract param from url path
        String vesselNameParam = ctx.pathParam("name");

        try {
          // create a new service
          VesselService service = new VesselService();
          // use service to get the demo data
          ctx.json(service.getVesselByName(vesselNameParam));
        } catch (RetrieverException e) {
          ctx.status(404);
        }
      };
}
