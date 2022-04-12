package api.simulators;

import api.enums.ChannelId;
import api.enums.ChannelName;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static api.clients.ChannelsClient.getChannels;
import static api.clients.SimulatorsClient.*;
import static org.apache.http.HttpStatus.SC_OK;

public class SimulatorHealthChecks {

    @Test
    @Description("30113 :: 3line-simulator :: GET /ping")
    @TmsLink("TECH-53921")
    public void testPing3LineSimulator() {
        Ping3Line()
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @Description("32755 :: raas-bank-simulators-v3 :: GET /health")
    @TmsLink("TECH-79752")
    public void testPingRaasBankV3Simulator() {
        PingRaasV3Simulator()
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @Description("30117 :: airtel-proxy :: GET /ping")
    @TmsLink("TECH-60268")
    public void testPingAirTelProxySimulator() {
        PingAirTelProxySimulator()
                .then().assertThat().statusCode(SC_OK);
    }

    @Test
    @Description("GET /testresponse")
    @TmsLink("TECH-60264")
    public void testmwmTestResponse() {
        PingMWMSimulator(Identifier_18)
                .then().assertThat().statusCode(SC_OK);
    }
    @Test
    @Description("30091-magtipon3lineng-rest :: GET \u200B/magtipon3lineng\u200B/ping :: happy path")
    @TmsLink("TECH-149979")
    public void testMagtipon3LinengResponse() {
        Pingmagtipon3linengSimulator()
                .then().assertThat().statusCode(SC_OK);
    }
}

