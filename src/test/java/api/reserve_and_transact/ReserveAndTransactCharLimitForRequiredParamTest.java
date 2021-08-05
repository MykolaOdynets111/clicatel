package api.reserve_and_transact;

import api.clients.ReserveAndTransactClient;
import api.clients.TransactClient;
import api.domains.transact.model.TransactResponse;
import api.enums.ChannelId;
import api.enums.Port;
import api.enums.Version;
import io.qameta.allure.Description;
import io.qameta.allure.TmsLink;
import lombok.val;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static api.clients.TransactClient.executeTransact;
import static api.domains.transact.repo.TransactRequestRepo.*;
import static api.enums.ChannelName.USSD;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_OK;

public class ReserveAndTransactCharLimitForRequiredParamTest {

}
