package api.funding_source_config;

import api.clients.BasedAPIClient;
import api.clients.ProductLookupClient;
import api.clients.ReserveAndTransactClient;
import api.clients.UsersClient;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.springframework.context.annotation.Description;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static api.clients.FundingSourceConfigClient.*;

import static api.clients.ProductLookupClient.*;
import static api.clients.ReserveAndTransactClient.TestClient3;
import static api.domains.product_lookup.repo.ProductLookupRequestRepo.setUpPostCreateFundingSource;
import static db.clients.HibernateBaseClient.executeCustomQuery;
import static db.clients.HibernateBaseClient.executeCustomQueryAndReturnValues;
import static db.custom_queries.FundingSourceQueries.*;
import static db.enums.Sessions.POSTGRES_SQL;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class FundingSourceConfigTest extends BasedAPIClient {

    @Test
    @Description("GET /fundingSources :: happy path")
    @TmsLink("MKP-337")
    public void testGetFundingSources(){
        //Delete funding source country and funding source // Pre conditions
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_COUNTRY_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));

        //Create new funding source
        List<String> CountryCodes = new ArrayList<String>();
        CountryCodes.add(UsersClient.country);
        CountryCodes.add(ProductLookupClient.CountryCode_710);
        val body = setUpPostCreateFundingSource(true, "",ProductLookupClient.confirmationWindowSizeSeconds, CountryCodes,
                fundingSourceName, ProductLookupClient.fundingSourceId_1206, ReserveAndTransactClient.FeeAmount0, TestClient3,"",false, ProductLookupClient.reservationTimeout,
                ProductLookupClient.reserveFundsEndpoint, ProductLookupClient.retryDelay,ProductLookupClient.serviceWindowSizeSeconds,false,
                ProductLookupClient.transactionEndpoint, "", null, false);

        executePostCreateFundingSource(body)
                .then().assertThat().statusCode(SC_OK)
                .body("fundingSourceName", Matchers.containsString(ProductLookupClient.fundingSourceName))
                .body("id",Matchers.is(Integer.parseInt(ProductLookupClient.fundingSourceId_1206)));

        GetFundingSources()
                .then().assertThat().statusCode(SC_OK)
                .body("fundingSourceName",Matchers.hasItem(fundingSourceName));

        val selectFundingSourceNameByActive = executeCustomQueryAndReturnValues(POSTGRES_SQL, format(SELECT_FUNDING_SOURCE_NAME_BY_ACTIVE,true));
        System.out.println(selectFundingSourceNameByActive);
        assertThat(selectFundingSourceNameByActive.contains(fundingSourceName));

        //Post conditions: Delete the funding source
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_COUNTRY_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));
        executeCustomQuery(POSTGRES_SQL, format(DELETE_FUNDING_SOURCE_BY_FUNDING_SOURCE_ID, ProductLookupClient.fundingSourceId_1206));

    }
}
