package api.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import static api.clients.BasedAPIClient.*;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class SimulatorsClient {

    public static Response Ping3Line() {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/threeline/ping",LineSimulator))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
}

    public static Response PingRaasV3Simulator() {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/raasBankSimulatorsV3/health",RaasBankSimulator))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response PingAirTelProxySimulator() {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/airtel/ping",AirTelSimulator))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

}
