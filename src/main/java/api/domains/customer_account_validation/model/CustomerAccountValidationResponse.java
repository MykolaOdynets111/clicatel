package api.domains.customer_account_validation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Collection;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CustomerAccountValidationResponse {

    @JsonProperty("customerName")
    private String customerName;

    @JsonProperty("customerInfo.name")
    private String customerInfo_name;

    @JsonProperty("customerInfo.status")
    private int customerInfo_status;

    @JsonProperty("customerInfo.address")
    private String customerInfo_address;

    @JsonProperty("customerInfo.phone")
    private String customerInfo_phone;

    @JsonProperty("customerInfo.accountNumber")
    private int customerInfo_accountNumber;

    @JsonProperty("customerInfo.meterNumber")
    private String customerInfo_meterNumber;

    @JsonProperty("customerInfo.tariff")
    private String customerInfo_tariff;

    @JsonProperty("customerInfo.district")
    private String customerInfo_district;

    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("responseMessage")
    private String responseMessage;

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private Collection message;

    @JsonProperty("accountInfo.amount")
    private int accountInfo_amount;

    @JsonProperty("accountInfo.targetIdentifier")
    private String accountInfo_targetIdentifier;

    @JsonProperty("accountInfo.arrears")
    private int accountInfo_arrears;

    @JsonProperty("accountInfo.minimumAmount")
    private int accountInfo_minimumAmount;

    @JsonProperty("accountInfo.tariff")
    private String accountInfo_tariff;

    @JsonProperty("accountInfo.accountNumber")
    private String accountInfo_accountNumber;

    @JsonProperty("accountInfo.providerName")
    private String accountInfo_providerName;
}
