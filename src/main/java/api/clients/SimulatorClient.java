package api.clients;

import api.domains.simulator.model.SimulatorRequest;
import api.enums.Port;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Getter;

import java.util.*;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

@Getter
public class SimulatorClient extends BasedAPIClient {

    public static Response addAirtelTestCases(List<SimulatorRequest> body) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setBaseUri("http://ha-airtel-sim.uat.ng01.payd.co/airtelSimulator")
                        .setBasePath("/addtestcases")
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response removeAllAirtelTestCases() {
        return basedAPIClient.get()
                .delete(new RequestSpecBuilder()
                        .setBaseUri("http://ha-airtel-sim.uat.ng01.payd.co/airtelSimulator")
                        .setBasePath("/removealltestcases")
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response addMtnTestCases(List<SimulatorRequest> body, Port port) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/addtestcases",baseUrl,port.getPort()))
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response removeAllMtnTestCases(Port port) {
        return basedAPIClient.get()
                .delete(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/removealltestcases",baseUrl,port.getPort()))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

}