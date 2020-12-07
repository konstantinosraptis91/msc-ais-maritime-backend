package kraptis91.maritime.api;

import io.javalin.Javalin;
import kraptis91.maritime.api.controller.VesselController;
import kraptis91.maritime.api.enums.ServerConfig;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 7/12/2020. */
public class Server {

  private static Javalin app;

  public Server() {
    start();
  }

  public void start() {
    app = Javalin.create().start(ServerConfig.INSTANCE.getPort());
    bindControllers();
  }

  private void bindControllers() {
    app.get("/", ctx -> ctx.result("Server Is Up and Running..."));
    app.get("/vessels/:shiptype", VesselController.getVesselsByShipType);
  }

  //  public void stop() {
  //    Runtime.getRuntime()
  //        .addShutdownHook(
  //            new Thread(
  //                () -> {
  //                  app.stop();
  //                }));
  //    app.events(
  //        event -> {
  //          event.serverStopping(() -> {});
  //          event.serverStopped(() -> {});
  //        });
  //  }

  public static Javalin getApp() {
    return app;
  }

  public static void main(String[] args) {
    new Server();
  }
}
