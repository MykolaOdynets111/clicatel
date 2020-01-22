package API.mwmTests.PING;

import api.testUtilities.testConfig;
import io.restassured.http.ContentType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import api.requestLibary.MWM.mvmLookUpPOJO;
import api.requestLibary.MWM.mwmVendPOJO;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class lookUpTest extends testConfig {



    @DataProvider(name = "mwmVendLookUp", parallel = true)
    public Object[] createUSSDTestData() {

        return new String[][]{

                {"2019-10-10T12:01:14.362+02:00", "zltvmktsnr8zcsn7iy29kmfs", "2342222221234", "2341111114321", "100", "21", "1111111111111", "001"},
                {"2019-10-10T12:01:15.362+02:00", "zltvmktsnr8zcsn7iy39kmfs", "2342222222234", "2341111115321", "100", "21", "1111111111111", "002"},
                {"2019-10-10T12:01:16.362+02:00", "zltvmktsnr8zcsn7iy49kmfs", "2342222223234", "2341111116321", "100", "21", "1111111111111", "003"},
                {"2019-10-10T12:01:17.362+02:00", "zltvmktsnr8zcsn7iy59kmfs", "2342222224234", "2341111117321", "100", "21", "1111111111111", "004"},
                {"2019-10-10T12:01:18.362+02:00", "zltvmktsnr8zcsn7iy69kmfs", "2342222225234", "2341111118321", "100", "21", "1111111111111", "005"},



        };
    }

    @Test(dataProvider ="mwmVendLookUp" )
    public void simpleVendTestLookUp(String purchaseDate, String raasTnxRef, String targetIdentifier, String srcIdentifier, String productID, String vendorID, String ctxTnxRef, String purchaseAMount) {

        mwmVendPOJO payLoad = new mwmVendPOJO(purchaseDate, raasTnxRef, targetIdentifier, srcIdentifier, productID, vendorID, ctxTnxRef, purchaseAMount);

        given()

                .spec(MWM_Vend_RequestSpec)
                .contentType(ContentType.JSON)
                .body(payLoad)
                .post()
                .then()
                .log().ifValidationFails()
                .assertThat()
                .statusCode(200);
        mvmLookUpPOJO lookUpPayLoad = new mvmLookUpPOJO(payLoad.getPurchaseDate(),payLoad.getRaasTransactionReference(),payLoad.getTargetIdentifier(),payLoad.getSourceIdentifier(),payLoad.getProductId(),
                payLoad.getVendorId(),payLoad.getCtxTransactionReference(),payLoad.getPurchaseAmount());
        given()
                .spec(MWM_VendLookUp_RequestSpec)
                .contentType(ContentType.JSON)
                .body(lookUpPayLoad)
                .post()
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("raasTransactionReference",equalTo(payLoad.getRaasTransactionReference()))
                .body("vendorResponseMessage",equalTo("Success"))
                .statusCode(200);





    }




}
