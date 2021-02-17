package api.clients;

import api.domains.airtel_simulator.model.AirtelSimulatorRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.Getter;

import java.util.ArrayList;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

@Getter
public class AirtelSimulatorClient extends BasedAPIClient {

    public static Response addTestCases(ArrayList<AirtelSimulatorRequest> body) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        //.setBasePath("http://ha-airtel-sim.uat.ng01.payd.co/airtelSimulator")
                        .setBaseUri("http://ha-airtel-sim.uat.ng01.payd.co/airtelSimulator/addtestcases")
                        .setBody(body)
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

    public static Response removeAllTestCases() {
        return basedAPIClient.get()
                .delete(new RequestSpecBuilder()
                        .setBasePath("http://ha-airtel-sim.uat.ng01.payd.co/airtelSimulator")
                        .setBaseUri("/removealltestcases")
                        .setContentType(JSON)
                        .log(ALL)
                        .build());
    }

}