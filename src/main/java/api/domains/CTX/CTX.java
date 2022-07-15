package api.domains.CTX;

import api.domains.customer_account_validation.model.CustomerAccountValidationRequest;

import static api.clients.UsersClient.getRandomNumbers;

public class CTX {

    public static String CtxCreateBody(String ClientID, String PurchaseAmount, String OriginID, String ProductID, String ChannelIndicator, String AlternateClientID,
                                       String SourceID, String ChannelSessionID) {

        {
            String RandomNumbers = getRandomNumbers("123456789152345", 15);

            String requestBody = "<?xml version=" + "'1.0'" + "?>\r\n" +
                    "<S:Envelope xmlns:S=" + "'http://schemas.xmlsoap.org/soap/envelope/'" + ">\r\n" +
                    "<S:Body>\r\n" +
                    "<purchase xmlns=" + "'http://clickatell.com/types'" + ">\r\n" +
                    "<clientId>"+ClientID+"</clientId>\r\n" +
                    "<apiToken>tysJdLJ5anXynmfVx8DnuOaZXuWfwJMLUvxt3pId4Okzl9s0WcHQxwMt3FnzEyEf</apiToken>\r\n" +
                    "<purchaseAmount>"+PurchaseAmount+"</purchaseAmount>\r\n" +
                    "<timeLocalTransaction>155039</timeLocalTransaction>\r\n" +
                    "<dateLocalTransaction>0710</dateLocalTransaction>\r\n" +
                    "<originId>"+OriginID+"</originId>\r\n" +
                    "<clientTransactionId>" + RandomNumbers + "-0000</clientTransactionId>\r\n" +
                    "<productId>"+ProductID+"</productId>\r\n" +
                    "<channelIndicator>"+ChannelIndicator+"</channelIndicator>\r\n" +
                    "<alternateClientId>"+AlternateClientID+"</alternateClientId>\r\n" +
                    "<sourceId>"+SourceID+"</sourceId>\r\n" +
                    "<channelSessionId>"+ChannelSessionID+"</channelSessionId>\r\n" +
                    "</purchase>\r\n" +
                    "</S:Body>\r\n" +
                    "</S:Envelope>";
            return requestBody;
        }
    }

    public static String CreateCTXQueryTransactionBody(String ClientID,String AlternateClientID,String ClientTransactionId){
        {
            String requestBody= "<?xml version=" + "'1.0'" + "?>\r\n" +
                    "<S:Envelope xmlns:S=" + "'http://schemas.xmlsoap.org/soap/envelope/'" + ">\r\n" +
                    "<S:Body>\r\n" +
                    "<queryTransaction xmlns=" + "'http://clickatell.com/types'" + ">\r\n" +
                    "<clientId>"+ClientID+"</clientId>\r\n" +
                    "<apiToken>tysJdLJ5anXynmfVx8DnuOaZXuWfwJMLUvxt3pId4Okzl9s0WcHQxwMt3FnzEyEf</apiToken>\r\n" +
                    "<clientTransactionId>"+ClientTransactionId+"</clientTransactionId>\r\n" +
                    "<alternateClientId>"+AlternateClientID+"</alternateClientId>\r\n" +
                    "</queryTransaction>\r\n" +
                    "</S:Body>\r\n" +
                    "</S:Envelope>";
            return requestBody;

        }
    }
}
