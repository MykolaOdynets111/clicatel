/*
Some Request don't require a request body but instead require us to build up urls with parameters
We can easily build this URI string using the String builder methods
 */package api.parameterizedUrlBuilder;

public class getFinancialTermsCalculate {

    public String getRequest(String clientID, String productID, String purchaseAmount ){

        String requestURL = "http://docker-minion04.dev.za01.payd.co:30309/financial-terms/calculate?clientId="
                + clientID + "&productId="
                + productID
                + "&purchaseAmount="
                + purchaseAmount;
        return requestURL;
    }

}

