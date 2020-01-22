package api.requestLibary.MWM;
/**
 *Vend Behaviour payload object
 * This object object is used to set system behaviour on the request
 * Author: Adam Bethlehem
 */
public class mvmVendBehaviourControlPOJO {
    private String delayMs;

    private String targetIdentifier;

    private String ctxTransactionResponseCode;

    private String sourceIdentifier;

    private String vendorResponseCode;

    private String vendorResponseMessage;

    private String vendorTransactionReference;

    private String ctxSystemResponseCode;

    public mvmVendBehaviourControlPOJO(String delayMs, String targetIdentifier, String ctxTransactionResponseCode, String sourceIdentifier, String vendorResponseCode, String vendorResponseMessage, String vendorTransactionReference, String ctxSystemResponseCode) {
        this.delayMs = delayMs;
        this.targetIdentifier = targetIdentifier;
        this.ctxTransactionResponseCode = ctxTransactionResponseCode;
        this.sourceIdentifier = sourceIdentifier;
        this.vendorResponseCode = vendorResponseCode;
        this.vendorResponseMessage = vendorResponseMessage;
        this.vendorTransactionReference = vendorTransactionReference;
        this.ctxSystemResponseCode = ctxSystemResponseCode;
    }

    public mvmVendBehaviourControlPOJO() {
    }

    public String getDelayMs ()
    {
        return delayMs;
    }

    public void setDelayMs (String delayMs)
    {
        this.delayMs = delayMs;
    }

    public String getTargetIdentifier ()
    {
        return targetIdentifier;
    }

    public void setTargetIdentifier (String targetIdentifier)
    {
        this.targetIdentifier = targetIdentifier;
    }

    public String getCtxTransactionResponseCode ()
    {
        return ctxTransactionResponseCode;
    }

    public void setCtxTransactionResponseCode (String ctxTransactionResponseCode)
    {
        this.ctxTransactionResponseCode = ctxTransactionResponseCode;
    }

    public String getSourceIdentifier ()
    {
        return sourceIdentifier;
    }

    public void setSourceIdentifier (String sourceIdentifier)
    {
        this.sourceIdentifier = sourceIdentifier;
    }

    public String getVendorResponseCode ()
    {
        return vendorResponseCode;
    }

    public void setVendorResponseCode (String vendorResponseCode)
    {
        this.vendorResponseCode = vendorResponseCode;
    }

    public String getVendorResponseMessage ()
    {
        return vendorResponseMessage;
    }

    public void setVendorResponseMessage (String vendorResponseMessage)
    {
        this.vendorResponseMessage = vendorResponseMessage;
    }

    public String getVendorTransactionReference ()
    {
        return vendorTransactionReference;
    }

    public void setVendorTransactionReference (String vendorTransactionReference)
    {
        this.vendorTransactionReference = vendorTransactionReference;
    }

    public String getCtxSystemResponseCode ()
    {
        return ctxSystemResponseCode;
    }

    public void setCtxSystemResponseCode (String ctxSystemResponseCode)
    {
        this.ctxSystemResponseCode = ctxSystemResponseCode;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [delayMs = "+delayMs+", targetIdentifier = "+targetIdentifier+", ctxTransactionResponseCode = "+ctxTransactionResponseCode+", sourceIdentifier = "+sourceIdentifier+", vendorResponseCode = "+vendorResponseCode+", vendorResponseMessage = "+vendorResponseMessage+", vendorTransactionReference = "+vendorTransactionReference+", ctxSystemResponseCode = "+ctxSystemResponseCode+"]";
    }
}
