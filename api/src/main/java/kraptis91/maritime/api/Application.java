package kraptis91.maritime.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import io.javalin.plugin.json.JavalinJackson;
import kraptis91.maritime.api.controller.VesselController;
import kraptis91.maritime.api.enums.ServerConfig;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 7/12/2020. */
public class Application {

  public static void main(String[] args) {
    Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins)
            .start(ServerConfig.INSTANCE.getPort());

    app.get("/", ctx -> ctx.result("Server Is Up and Running..."));
    app.get("/vessels/shiptype/:shiptype", VesselController.getVesselsByShipType);
    app.get("/vessels/mmsi/:mmsi", VesselController.getVesselByMMSI);
    app.get("/vessels/name/:name", VesselController.getVesselByName);
    app.get("/vessels/destination/name/:name", VesselController.getVesselDestinationByName);
    app.get("/vessels/destination/mmsi/:mmsi", VesselController.getVesselDestinationByMMSI);
    app.get("/vessels/destination/:destination", VesselController.getVesselByDestination);
    app.get("/vessels/trajectory/name/:name", VesselController.getVesselTrajectoryByName);
    app.get("/vessels/trajectory/mmsi/:mmsi", VesselController.getVesselTrajectoryByMMSI);

    JavalinJackson.configure(
        new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL));
  }
}
