package api.requestLibary.PWM;

public class pwmReserveFundPOJO {
    private String authCode;

    private String sourceIdentifier;

    private String productId;

    private String clientShareAmount;

    private String reserveAmount;

    private String raasTxnRef;

    private String purchaseAmount;

    private String feeAmount;

    private String targetIdentifier;

    private String clientTxnRef;

    private String channelSessionId;

    private String channelName;

    private String settlementAmount;

    private String channelId;

    private String productType;

    private String timestamp;

    public pwmReserveFundPOJO() {
    }

    //TODO add data builder workersS
    public pwmReserveFundPOJO(String authCode, String sourceIdentifier, String productId, String clientShareAmount, String reserveAmount, String raasTxnRef, String purchaseAmount, String feeAmount, String targetIdentifier, String clientTxnRef, String channelSessionId, String channelName, String settlementAmount, String channelId, String productType, String timestamp) {
        this.authCode = authCode;
        this.sourceIdentifier = sourceIdentifier;
        this.productId = productId;
        this.clientShareAmount = clientShareAmount;
        this.reserveAmount = reserveAmount;
        this.raasTxnRef = raasTxnRef;
        this.purchaseAmount = purchaseAmount;
        this.feeAmount = feeAmount;
        this.targetIdentifier = targetIdentifier;
        this.clientTxnRef = clientTxnRef;
        this.channelSessionId = channelSessionId;
        this.channelName = channelName;
        this.settlementAmount = settlementAmount;
        this.channelId = channelId;
        this.productType = productType;
        this.timestamp = timestamp;
    }

    public String getAuthCode ()
    {
        return authCode;
    }

    public void setAuthCode (String authCode)
    {
        this.authCode = authCode;
    }

    public String getSourceIdentifier ()
    {
        return sourceIdentifier;
    }

    public void setSourceIdentifier (String sourceIdentifier)
    {
        this.sourceIdentifier = sourceIdentifier;
    }

    public String getProductId ()
    {
        return productId;
    }

    public void setProductId (String productId)
    {
        this.productId = productId;
    }

    public String getClientShareAmount ()
    {
        return clientShareAmount;
    }

    public void setClientShareAmount (String clientShareAmount)
    {
        this.clientShareAmount = clientShareAmount;
    }

    public String getReserveAmount ()
    {
        return reserveAmount;
    }

    public void setReserveAmount (String reserveAmount)
    {
        this.reserveAmount = reserveAmount;
    }

    public String getRaasTxnRef ()
    {
        return raasTxnRef;
    }

    public void setRaasTxnRef (String raasTxnRef)
    {
        this.raasTxnRef = raasTxnRef;
    }

    public String getPurchaseAmount ()
    {
        return purchaseAmount;
    }

    public void setPurchaseAmount (String purchaseAmount)
    {
        this.purchaseAmount = purchaseAmount;
    }

    public String getFeeAmount ()
    {
        return feeAmount;
    }

    public void setFeeAmount (String feeAmount)
    {
        this.feeAmount = feeAmount;
    }

    public String getTargetIdentifier ()
    {
        return targetIdentifier;
    }

    public void setTargetIdentifier (String targetIdentifier)
    {
        this.targetIdentifier = targetIdentifier;
    }

    public String getClientTxnRef ()
    {
        return clientTxnRef;
    }

    public void setClientTxnRef (String clientTxnRef)
    {
        this.clientTxnRef = clientTxnRef;
    }

    public String getChannelSessionId ()
    {
        return channelSessionId;
    }

    public void setChannelSessionId (String channelSessionId)
    {
        this.channelSessionId = channelSessionId;
    }

    public String getChannelName ()
    {
        return channelName;
    }

    public void setChannelName (String channelName)
    {
        this.channelName = channelName;
    }

    public String getSettlementAmount ()
    {
        return settlementAmount;
    }

    public void setSettlementAmount (String settlementAmount)
    {
        this.settlementAmount = settlementAmount;
    }

    public String getChannelId ()
    {
        return channelId;
    }

    public void setChannelId (String channelId)
    {
        this.channelId = channelId;
    }

    public String getProductType ()
    {
        return productType;
    }

    public void setProductType (String productType)
    {
        this.productType = productType;
    }

    public String getTimestamp ()
    {
        return timestamp;
    }

    public void setTimestamp (String timestamp)
    {
        this.timestamp = timestamp;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [authCode = "+authCode+", sourceIdentifier = "+sourceIdentifier+", productId = "+productId+", clientShareAmount = "+clientShareAmount+", reserveAmount = "+reserveAmount+", raasTxnRef = "+raasTxnRef+", purchaseAmount = "+purchaseAmount+", feeAmount = "+feeAmount+", targetIdentifier = "+targetIdentifier+", clientTxnRef = "+clientTxnRef+", channelSessionId = "+channelSessionId+", channelName = "+channelName+", settlementAmount = "+settlementAmount+", channelId = "+channelId+", productType = "+productType+", timestamp = "+timestamp+"]";
    }
}
