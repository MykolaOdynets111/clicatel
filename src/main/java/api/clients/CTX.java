package api.clients;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.XML;
import static util.readers.PropertiesReader.getProperty;

public class CTX extends BasedAPIClient{

    public static String ResponseCode_2520;
    public static String ResponseCode_2523;
    public static String ResponseCode_2521;
    public static String ResponseCode_2519;
    public static String ResponseCode_2515;
    public static String ResponseCode_2514;
    public static String ResponseCode_2518;
    public static String ResponseCode_2516;
    public static String ResponseCode_2517;
    public static String ResponseCode_2513;
    public static String ResponseCode_2512;
    public static String ResponseCode_2522;
    public static String ResponseCode_2524;
    public static String ResponseCode_2525;
    public static String ResponseCode_2605;
    public static String ResponseCode_2608;
    public static String ResponseCode_2201;
    public static String ResponseCode_2607;
    public static String ResponseCode_2606;
    public static String ResponseCode_2603;
    public static String ResponseCode_2604;
    public static String ResponseCode_2601;
    public static String ResponseCode_2602;
    public static String ResponseCode_2502;
    public static String ResponseCode_2239;
    public static String ResponseCode_2232;
    public static String ResponseCode_2225;
    public static String ResponseCode_2407;
    public static String ResponseCode_2501;
    public static String ResponseCode_2217;
    public static String ResponseCode_2253;
    public static String ResponseCode_2252;
    public static String ResponseCode_2222;
    public static String ResponseCode_2230;
    public static String ResponseCode_2504;
    public static String ResponseCode_2233;
    public static String ResponseCode_2229;
    public static String ResponseCode_2226;
    public static String ResponseCode_2406;
    public static String ResponseCode_2241;
    public static String ResponseCode_2212;
    public static String ResponseCode_2250;
    public static String ResponseCode_2242;
    public static String ResponseCode_2218;
    public static String ResponseCode_2216;
    public static String ResponseCode_2508;
    public static String ResponseCode_2211;
    public static String ResponseCode_2507;
    public static String ResponseCode_2231;
    public static String ResponseCode_2235;
    public static String ResponseCode_2251;
    public static String ResponseCode_2213;
    public static String ResponseCode_2210;
    public static String ResponseCode_2228;
    public static String ResponseCode_2403;
    public static String ResponseCode_2240;
    public static String ResponseCode_2223;
    public static String ResponseCode_2505;
    public static String ResponseCode_2227;
    public static String ResponseCode_2215;
    public static String ResponseCode_2221;
    public static String ResponseCode_2405;
    public static String ResponseCode_2214;
    public static String ResponseCode_2219;
    public static String ResponseCode_2237;
    public static String ResponseCode_2224;
    public static String ResponseCode_2503;
    public static String ResponseCode_2234;
    public static String ChannelID_07;
    public static String alternateClientId;
    public static String channelSessionId_3133827176;
    public static String channelSessionId_3133827170;
    public static String ResponseCode_2236;
    public static String ResponseCode_2203;
    public static String ResponseCode_2202;
    public static String ResponseCode_2204;
    public static String ResponseCode_2220;


    static
    {
        ResponseCode_2520 = getProperty("ResponseCode_2520");
        ResponseCode_2523 = getProperty("ResponseCode_2523");
        ResponseCode_2521 = getProperty("ResponseCode_2521");
        ResponseCode_2519 = getProperty("ResponseCode_2519");
        ResponseCode_2515 = getProperty("ResponseCode_2515");
        ResponseCode_2514 = getProperty("ResponseCode_2514");
        ResponseCode_2518 = getProperty("ResponseCode_2518");
        ResponseCode_2516 = getProperty("ResponseCode_2516");
        ResponseCode_2517 = getProperty("ResponseCode_2517");
        ResponseCode_2513 = getProperty("ResponseCode_2513");
        ResponseCode_2512 = getProperty("ResponseCode_2512");
        ResponseCode_2522 = getProperty("ResponseCode_2522");
        ResponseCode_2524 = getProperty("ResponseCode_2524");
        ResponseCode_2525 = getProperty("ResponseCode_2525");
        ResponseCode_2605 = getProperty("ResponseCode_2605");
        ResponseCode_2608 = getProperty("ResponseCode_2608");
        ResponseCode_2201 = getProperty("ResponseCode_2201");
        ResponseCode_2607 = getProperty("ResponseCode_2607");
        ResponseCode_2606 = getProperty("ResponseCode_2606");
        ResponseCode_2603 = getProperty("ResponseCode_2603");
        ResponseCode_2604 = getProperty("ResponseCode_2604");
        ResponseCode_2601 = getProperty("ResponseCode_2601");
        ResponseCode_2602 = getProperty("ResponseCode_2602");
        ResponseCode_2502 = getProperty("ResponseCode_2502");
        ResponseCode_2239 = getProperty("ResponseCode_2239");
        ResponseCode_2232 = getProperty("ResponseCode_2232");
        ResponseCode_2225 = getProperty("ResponseCode_2225");
        ResponseCode_2407 = getProperty("ResponseCode_2407");
        ResponseCode_2501 = getProperty("ResponseCode_2501");
        ResponseCode_2217 = getProperty("ResponseCode_2217");
        ResponseCode_2253 = getProperty("ResponseCode_2253");
        ResponseCode_2252 = getProperty("ResponseCode_2252");
        ResponseCode_2222 = getProperty("ResponseCode_2222");
        ResponseCode_2230 = getProperty("ResponseCode_2230");
        ResponseCode_2504 = getProperty("ResponseCode_2504");
        ResponseCode_2233 = getProperty("ResponseCode_2233");
        ResponseCode_2229 = getProperty("ResponseCode_2229");
        ResponseCode_2226 = getProperty("ResponseCode_2226");
        ResponseCode_2406 = getProperty("ResponseCode_2406");
        ResponseCode_2241 = getProperty("ResponseCode_2241");
        ResponseCode_2212 = getProperty("ResponseCode_2212");
        ResponseCode_2250 = getProperty("ResponseCode_2250");
        ResponseCode_2242 = getProperty("ResponseCode_2242");
        ResponseCode_2218 = getProperty("ResponseCode_2218");
        ResponseCode_2216 = getProperty("ResponseCode_2216");
        ResponseCode_2508 = getProperty("ResponseCode_2508");
        ResponseCode_2211 = getProperty("ResponseCode_2211");
        ResponseCode_2507 = getProperty("ResponseCode_2507");
        ResponseCode_2231 = getProperty("ResponseCode_2231");
        ResponseCode_2235 = getProperty("ResponseCode_2235");
        ResponseCode_2251 = getProperty("ResponseCode_2251");
        ResponseCode_2213 = getProperty("ResponseCode_2213");
        ResponseCode_2210 = getProperty("ResponseCode_2210");
        ResponseCode_2228 = getProperty("ResponseCode_2228");
        ResponseCode_2403 = getProperty("ResponseCode_2403");
        ResponseCode_2240 = getProperty("ResponseCode_2240");
        ResponseCode_2223 = getProperty("ResponseCode_2223");
        ResponseCode_2505 = getProperty("ResponseCode_2505");
        ResponseCode_2227 = getProperty("ResponseCode_2227");
        ResponseCode_2215 = getProperty("ResponseCode_2215");
        ResponseCode_2221 = getProperty("ResponseCode_2221");
        ResponseCode_2405 = getProperty("ResponseCode_2405");
        ResponseCode_2214 = getProperty("ResponseCode_2214");
        ResponseCode_2219 = getProperty("ResponseCode_2219");
        ResponseCode_2237 = getProperty("ResponseCode_2237");
        ResponseCode_2224 = getProperty("ResponseCode_2224");
        ResponseCode_2503 = getProperty("ResponseCode_2503");
        ResponseCode_2234 = getProperty("ResponseCode_2234");
        ChannelID_07 = getProperty("ChannelID_07");
        alternateClientId = getProperty("alternateClientId");
        channelSessionId_3133827176 = getProperty("channelSessionId_3133827176");
        channelSessionId_3133827170=getProperty("channelSessionId_3133827170");
        ResponseCode_2236 = getProperty("ResponseCode_2236");
        ResponseCode_2203 = getProperty("ResponseCode_2203");
        ResponseCode_2202 = getProperty("ResponseCode_2202");
        ResponseCode_2204=getProperty("ResponseCode_2204");
        ResponseCode_2220=getProperty("ResponseCode_2220");
    }

    public static Response validateCustomerAccount(String body) {
        return basedAPIClient.get()
                .post(new RequestSpecBuilder()
                        .setUrlEncodingEnabled(false)
                        .setBaseUri(String.format("%s:%s/WebClient/service", CtxUrl,CtxPort))
                        .setBody(body)
                        .setContentType(XML)
                        .addHeader("Soapaction","purchase")
                        .addHeader("Accept","ContentType.XML")
                        .log(ALL)
                        .build());

    }

   public static Response queryTransactionRequest(String body){
       return basedAPIClient.get()
               .post(new RequestSpecBuilder()
                       .setUrlEncodingEnabled(false)
                       .setBaseUri(String.format("%s:%s/WebClient/service", CtxUrl,CtxPort))
                       .setBody(body)
                       .setContentType(XML)
                       .addHeader("Soapaction","queryTransaction")
                       .addHeader("Accept","ContentType.XML")
                       .log(ALL)
                       .build());
   }
}
