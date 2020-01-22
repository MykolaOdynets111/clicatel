package requestLibary.MWM;

public class mvmLookUpPOJO {
    private String purchaseDate;

    private String raasTransactionReference;

    private String targetIdentifier;

    private String sourceIdentifier;

    private String productId;

    private String vendorId;

    private String ctxTransactionReference;

    private String purchaseAmount;

    public mvmLookUpPOJO(String purchaseDate, String raasTransactionReference, String targetIdentifier, String sourceIdentifier, String productId, String vendorId, String ctxTransactionReference, String purchaseAmount) {
        this.purchaseDate = purchaseDate;
        this.raasTransactionReference = raasTransactionReference;
        this.targetIdentifier = targetIdentifier;
        this.sourceIdentifier = sourceIdentifier;
        this.productId = productId;
        this.vendorId = vendorId;
        this.ctxTransactionReference = ctxTransactionReference;
        this.purchaseAmount = purchaseAmount;
    }

    public mvmLookUpPOJO() {
    }

    public String getPurchaseDate ()
    {
        return purchaseDate;
    }

    public void setPurchaseDate (String purchaseDate)
    {
        this.purchaseDate = purchaseDate;
    }

    public String getRaasTransactionReference ()
    {
        return raasTransactionReference;
    }

    public void setRaasTransactionReference (String raasTransactionReference)
    {
        this.raasTransactionReference = raasTransactionReference;
    }

    public String getTargetIdentifier ()
    {
        return targetIdentifier;
    }

    public void setTargetIdentifier (String targetIdentifier)
    {
        this.targetIdentifier = targetIdentifier;
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

    public String getVendorId ()
    {
        return vendorId;
    }

    public void setVendorId (String vendorId)
    {
        this.vendorId = vendorId;
    }

    public String getCtxTransactionReference ()
    {
        return ctxTransactionReference;
    }

    public void setCtxTransactionReference (String ctxTransactionReference)
    {
        this.ctxTransactionReference = ctxTransactionReference;
    }

    public String getPurchaseAmount ()
    {
        return purchaseAmount;
    }

    public void setPurchaseAmount (String purchaseAmount)
    {
        this.purchaseAmount = purchaseAmount;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [purchaseDate = "+purchaseDate+", raasTransactionReference = "+raasTransactionReference+", targetIdentifier = "+targetIdentifier+", sourceIdentifier = "+sourceIdentifier+", productId = "+productId+", vendorId = "+vendorId+", ctxTransactionReference = "+ctxTransactionReference+", purchaseAmount = "+purchaseAmount+"]";
    }
}
