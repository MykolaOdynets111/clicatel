package api.requestLibary.CORE;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CtxPOJO
{

    private String sourceId;

    private String clientId;

    private String channelIndicator;

    private String productId;

    private String clientTransactionId;

    private String purchaseAmount;

    private String alternateClientId;

    private String timeLocalTransaction;

    private String xmlns;

    private String apiToken;

    private String originId;

    private String channelSessionId;

    private String dateLocalTransaction;

    public CtxPOJO() {

    }

    public CtxPOJO(String sourceId, String clientId, String channelIndicator, String productId, String clientTransactionId, String purchaseAmount, String alternateClientId, String timeLocalTransaction, String xmlns, String apiToken, String originId, String channelSessionId, String dateLocalTransaction) {

        this.sourceId = sourceId;
        this.clientId = clientId;
        this.channelIndicator = channelIndicator;
        this.productId = productId;
        this.clientTransactionId = clientTransactionId;
        this.purchaseAmount = purchaseAmount;
        this.alternateClientId = alternateClientId;
        this.timeLocalTransaction = timeLocalTransaction;
        this.xmlns = xmlns;
        this.apiToken = apiToken;
        this.originId = originId;
        this.channelSessionId = channelSessionId;
        this.dateLocalTransaction = dateLocalTransaction;
    }

    public String getSourceId ()
    {
        return sourceId;
    }

    public void setSourceId (String sourceId)
    {
        this.sourceId = sourceId;
    }

    public String getClientId ()
    {
        return clientId;
    }

    public void setClientId (String clientId)
    {
        this.clientId = clientId;
    }

    public String getChannelIndicator ()
    {
        return channelIndicator;
    }

    public void setChannelIndicator (String channelIndicator)
    {
        this.channelIndicator = channelIndicator;
    }

    public String getProductId ()
    {
        return productId;
    }

    public void setProductId (String productId)
    {
        this.productId = productId;
    }

    public String getClientTransactionId ()
    {
        return clientTransactionId;
    }

    public void setClientTransactionId (String clientTransactionId)
    {
        this.clientTransactionId = clientTransactionId;
    }

    public String getPurchaseAmount ()
    {
        return purchaseAmount;
    }

    public void setPurchaseAmount (String purchaseAmount)
    {
        this.purchaseAmount = purchaseAmount;
    }

    public String getAlternateClientId ()
    {
        return alternateClientId;
    }

    public void setAlternateClientId (String alternateClientId)
    {
        this.alternateClientId = alternateClientId;
    }

    public String getTimeLocalTransaction ()
    {
        return timeLocalTransaction;
    }

    public void setTimeLocalTransaction (String timeLocalTransaction)
    {
        this.timeLocalTransaction = timeLocalTransaction;
    }

    public String getXmlns ()
    {
        return xmlns;
    }

    public void setXmlns (String xmlns)
    {
        this.xmlns = xmlns;
    }

    public String getApiToken ()
    {
        return apiToken;
    }

    public void setApiToken (String apiToken)
    {
        this.apiToken = apiToken;
    }

    public String getOriginId ()
    {
        return originId;
    }

    public void setOriginId (String originId)
    {
        this.originId = originId;
    }

    public String getChannelSessionId ()
    {
        return channelSessionId;
    }

    public void setChannelSessionId (String channelSessionId)
    {
        this.channelSessionId = channelSessionId;
    }

    public String getDateLocalTransaction ()
    {
        return dateLocalTransaction;
    }

    public void setDateLocalTransaction (String dateLocalTransaction)
    {
        this.dateLocalTransaction = dateLocalTransaction;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [sourceId = "+sourceId+", clientId = "+clientId+", channelIndicator = "+channelIndicator+", productId = "+productId+", clientTransactionId = "+clientTransactionId+", purchaseAmount = "+purchaseAmount+", alternateClientId = "+alternateClientId+", timeLocalTransaction = "+timeLocalTransaction+", xmlns = "+xmlns+", apiToken = "+apiToken+", originId = "+originId+", channelSessionId = "+channelSessionId+", dateLocalTransaction = "+dateLocalTransaction+"]";
    }

}
