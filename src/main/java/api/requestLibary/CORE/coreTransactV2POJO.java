package api.requestLibary.CORE;

public class coreTransactV2POJO {

    private String reserveFundsTxnRef;

    private String amount;

    private String targetIdentifier;

    private String clientId;

    private String clientTxnRef;

    private String sourceIdentifier;

    private String productId;

    private String channelSessionId;

    private String accountIdentifier;

    private String channelName;

    private String channelId;

    private String timestamp;

    public coreTransactV2POJO() {
    }

    public coreTransactV2POJO(String accountIdentifier, String amount, String channelId, String channelName, String channelSessionId, String clientId, String clientTxnRef, String productId, String sourceIdentifier, String targetIdentifier, String timestamp, String reserveFundsTxnRef) {

        this.accountIdentifier = accountIdentifier;
        this.amount = amount;
        this.channelId = channelId;
        this.channelName = channelName;
        this.channelSessionId = channelSessionId;
        this.clientId = clientId;
        this.clientTxnRef = clientTxnRef;
        this.productId = productId;
        this.sourceIdentifier = sourceIdentifier;
        this.targetIdentifier = targetIdentifier;
        this.timestamp = timestamp;
        this.reserveFundsTxnRef = reserveFundsTxnRef;

    }


    public String getReserveFundsTxnRef ()
    {
        return reserveFundsTxnRef;
    }

    public void setReserveFundsTxnRef (String reserveFundsTxnRef)
    {
        this.reserveFundsTxnRef = reserveFundsTxnRef;
    }

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getTargetIdentifier ()
    {
        return targetIdentifier;
    }

    public void setTargetIdentifier (String targetIdentifier)
    {
        this.targetIdentifier = targetIdentifier;
    }

    public String getClientId ()
    {
        return clientId;
    }

    public void setClientId (String clientId)
    {
        this.clientId = clientId;
    }

    public String getClientTxnRef ()
    {
        return clientTxnRef;
    }

    public void setClientTxnRef (String clientTxnRef)
    {
        this.clientTxnRef = clientTxnRef;
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

    public String getChannelSessionId ()
    {
        return channelSessionId;
    }

    public void setChannelSessionId (String channelSessionId)
    {
        this.channelSessionId = channelSessionId;
    }

    public String getAccountIdentifier ()
    {
        return accountIdentifier;
    }

    public void setAccountIdentifier (String accountIdentifier)
    {
        this.accountIdentifier = accountIdentifier;
    }

    public String getChannelName ()
    {
        return channelName;
    }

    public void setChannelName (String channelName)
    {
        this.channelName = channelName;
    }

    public String getChannelId ()
    {
        return channelId;
    }

    public void setChannelId (String channelId)
    {
        this.channelId = channelId;
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
        return "ClassPojo [reserveFundsTxnRef = "+reserveFundsTxnRef+", amount = "+amount+", targetIdentifier = "+targetIdentifier+", clientId = "+clientId+", clientTxnRef = "+clientTxnRef+", sourceIdentifier = "+sourceIdentifier+", productId = "+productId+", channelSessionId = "+channelSessionId+", accountIdentifier = "+accountIdentifier+", channelName = "+channelName+", channelId = "+channelId+", timestamp = "+timestamp+"]";
    }

}
