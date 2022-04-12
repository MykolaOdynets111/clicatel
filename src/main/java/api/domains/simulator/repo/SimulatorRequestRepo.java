package api.domains.simulator.repo;

import api.domains.simulator.model.SimulatorRequest;

public class SimulatorRequestRepo {
    public static SimulatorRequest setUpAirtelSimData(String responseCode, String action){
        return SimulatorRequest.builder()
                .action(action)
                .delay(3000)
                .fieldName("")
                .httpStatusCode(0)
                .httpSatusCode(0)
                .id("")
                .responseCode(responseCode)
                .build();
    }

    public static SimulatorRequest setUpMtnSimData(String responseCode, String id, String action, int httpStatusCode){
        return SimulatorRequest.builder()
                .responseCode(responseCode)
                .id(id)
                .action(action)
                .delay(0)
                .httpStatusCode(httpStatusCode)
                .fieldName("")
                .build();
    }

}
