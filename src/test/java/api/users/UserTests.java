package api.users;

import api.clients.UsersClient;
import api.domains.users.repo.UsersRepo;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;


import static api.clients.UsersClient.executeUsers;
import static org.apache.http.HttpStatus.SC_OK;

public class UserTests {

    @Test
    @Description("30065 :: profile-management :: POST /user")
    @TmsLink("TECH-50971")
    public void testUsersPost() throws InterruptedException {
        String msisdn = UsersClient.getSaltString();
        val jsonBody = UsersRepo.setUpUsersPostData(UsersClient.firstName, UsersClient.middleName, UsersClient.lastName, UsersClient.language, UsersClient.country, msisdn);

        val raasTxnRef = executeUsers(jsonBody)
                .then().assertThat().statusCode(SC_OK)
                .body("firstName", Matchers.containsString(UsersClient.firstName))
                .body("middleName", Matchers.containsString(UsersClient.middleName))
                .body("lastName", Matchers.containsString(UsersClient.lastName))
                .body("msisdn", Matchers.equalTo(msisdn));
    }

}
