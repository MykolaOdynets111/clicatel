package db.entities.mysql;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class DB account table mapper
 * <p>
 *
 * @author ksichenko
 */

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "mc2_testing.account")
public class Account {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date")
    private String createdDate;

    @Column(name = "modified_date")
    private String modifiedDate;

    @Column(name = "state")
    private String state;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "clickatell")
    private Integer clickatell;

    @Column(name = "tract_billing_account_id")
    private String tractBillingAccountId;

    @Column(name = "currency")
    private String currency;

    @Column(name = "currency_locked")
    private Integer currencyLocked;

    @Column(name = "discount_enabled")
    private Integer discountEnabled;

    @Column(name = "tract_billing_account_num")
    private String tractBillingAccountNum;

    @Column(name = "display_currency")
    private String displayCurrency;

    @Column(name = "vip")
    private Integer vip;

    @Column(name = "tract_register_id")
    private String tractRegisterId;

    @Column(name = "sales_force_lead_id")
    private String salesForceLeadId;

    @Column(name = "registration_country_id")
    private String registrationCountryId;

    @Column(name = "billing_type")
    private String billingType;

    @Column(name = "prepaid_tract_billing_account_num")
    private String prepaidTractBillingAccountNum;

    @Column(name = "last_login_date")
    private String lastLoginDate;

    @Column(name = "paypal_enabled")
    private Integer paypalEnabled;

    @Column(name = "whats_app_enabled")
    private Integer whatsAppEnabled;
}
