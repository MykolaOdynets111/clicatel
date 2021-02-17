package api.domains.airtel_simulator.repo;

import api.domains.airtel_simulator.model.AirtelSimulatorRequest;

public class AirtelSimulatorRequestRepo {
    public static AirtelSimulatorRequest setUpAirtelSimData(String responseCode, String action){
        return AirtelSimulatorRequest.builder()
                .action(action)
                .delay(0)
                .fieldName("")
                .httpSatusCode(0)
                .httpStatusCode(0)
                .id("")
                .responseCode(responseCode)
                .build();
    }

}
