package kraptis91.maritime.api.controller;

import io.javalin.http.Handler;
import kraptis91.maritime.api.service.ServiceFactory;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 30/12/2020.
 */
public class PortController {

    public static Handler getPorts =
        ctx ->
            ctx.json(
                ServiceFactory.createPortService()
                    .getPorts(
                        ctx.header("skip", Integer.class).get(),
                        ctx.header("limit", Integer.class).get()));

    public static Handler getPortsByCountryCode =
        ctx ->
            ctx.json(
                ServiceFactory.createPortService()
                    .getPortsByCountryCode(
                        ctx.pathParam("code")));

    public static Handler getNearPortsByReferencePoint =
        ctx ->
            ctx.json(
                ServiceFactory.createPortService()
                    .getNearPortsByReferencePoint(
                        ctx.pathParam("lon", Double.class).get(),
                        ctx.pathParam("lat", Double.class).get(),
                        ctx.pathParam("dist", Double.class).get(),
                        ctx.header("skip", Integer.class).get(),
                        ctx.header("limit", Integer.class).get()));

    public static Handler getNearPortsByMMSI =
        ctx ->
            ctx.json(
                ServiceFactory.createPortService()
                    .getNearPortsByMMSI(
                        ctx.pathParam("mmsi", Integer.class).get(),
                        ctx.pathParam("dist", Double.class).get(),
                        ctx.header("skip", Integer.class).get(),
                        ctx.header("limit", Integer.class).get()));
}
