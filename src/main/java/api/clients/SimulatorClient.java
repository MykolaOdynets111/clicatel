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

    public static Response addAirtelTestCases(List<SimulatorRequest> body, Port port) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/airtelSimulator/addtestcases",airtelUrl,port.getPort()))
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response removeAllAirtelTestCases(Port port) {
        return basedAPIClient.get()
                .delete(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/airtelSimulator/removealltestcases",airtelUrl,port.getPort()))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response addMtnTestCases(List<SimulatorRequest> body, Port port) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/addtestcases",mtnUrl,port.getPort()))
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response removeAllMtnTestCases(Port port) {
        return basedAPIClient.get()
                .delete(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s:%d/removealltestcases",mtnUrl,port.getPort()))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

}