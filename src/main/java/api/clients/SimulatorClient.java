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
                        .setBaseUri(String.format("%s/airtelSimulator/addtestcases",airtelUrl))
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response removeAllAirtelTestCases(Port port) {
        return basedAPIClient.get()
                .delete(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/airtelSimulator/removealltestcases",airtelUrl))
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }
    public static Response GetAirTelSimulatorResponseCodes() {
        return basedAPIClient.get()
                .get(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/airtelSimulator/responsecodes",airtelUrl))
//                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response addMtnTestCases(List<SimulatorRequest> body, Port port) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setBaseUri(String.format("%s/addtestcases",mtnUrl))
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .setRelaxedHTTPSValidation()
                        .build());
    }

    public static Response removeAllMtnTestCases(Port port) {
        return basedAPIClient.get()
                .delete(new RequestSpecBuilder()
                        //.setBaseUri(String.format("%s:%d/removealltestcases",mtnUrl,port.getPort()))
                        .setBaseUri(String.format("%s/removealltestcases",mtnUrl))
                        .setContentType(JSON)
                        .log(ALL)
                        .setRelaxedHTTPSValidation()
                        .build());
    }

}