package db.repository.mysql;

import db.entities.mysql.Account;
import lombok.Synchronized;

/**
 * Class DB account table entity initializer, based on builder pattern
 * <p>
 *
 * @author ksichenko
 */

public class AccountRepo {

    @Synchronized
    public static Account getAccount() {
        return Account.builder()
                .id("1234567890098765432")
                .name("automation_test_account")
                .createdDate("2016-09-28 14:35:36")
                .modifiedDate("2016-09-28 14:35:36")
                .state("ACTIVE")
                .companyName("Clickatell")
                .clickatell(0)
                .tractBillingAccountId(null)
                .currency("EUR")
                .currencyLocked(1)
                .discountEnabled(0)
                .tractBillingAccountNum(null)
                .displayCurrency("EUR")
                .vip(0)
                .tractRegisterId(null)
                .salesForceLeadId(null)
                .registrationCountryId(null)
                .billingType("PREPAID")
                .prepaidTractBillingAccountNum(null)
                .lastLoginDate("2016-08-16 10:35:52")
                .paypalEnabled(1)
                .whatsAppEnabled(0)
                .build();
    }
}
