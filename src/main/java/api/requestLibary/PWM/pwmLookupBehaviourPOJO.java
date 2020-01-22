package api.requestLibary.PWM;

public class pwmLookupBehaviourPOJO {
    private String delayMs;

    private String reserveTransactionReference;

    private String targetIdentifier;

    private String sourceIdentifier;

    private String reserveResponseCode;

    public pwmLookupBehaviourPOJO(String delayMs, String reserveTransactionReference, String targetIdentifier, String sourceIdentifier, String reserveResponseCode) {
        this.delayMs = delayMs;
        this.reserveTransactionReference = reserveTransactionReference;
        this.targetIdentifier = targetIdentifier;
        this.sourceIdentifier = sourceIdentifier;
        this.reserveResponseCode = reserveResponseCode;
    }

    public pwmLookupBehaviourPOJO() {
    }

    public String getDelayMs() {
        return delayMs;
    }

    public void setDelayMs(String delayMs) {
        this.delayMs = delayMs;
    }

    public String getReserveTransactionReference() {
        return reserveTransactionReference;
    }

    public void setReserveTransactionReference(String reserveTransactionReference) {
        this.reserveTransactionReference = reserveTransactionReference;
    }

    public String getTargetIdentifier() {
        return targetIdentifier;
    }

    public void setTargetIdentifier(String targetIdentifier) {
        this.targetIdentifier = targetIdentifier;
    }

    public String getSourceIdentifier() {
        return sourceIdentifier;
    }

    public void setSourceIdentifier(String sourceIdentifier) {
        this.sourceIdentifier = sourceIdentifier;
    }

    public String getReserveResponseCode() {
        return reserveResponseCode;
    }

    public void setReserveResponseCode(String reserveResponseCode) {
        this.reserveResponseCode = reserveResponseCode;
    }

    @Override
    public String toString() {
        return "ClassPojo [delayMs = " + delayMs + ", reserveTransactionReference = " + reserveTransactionReference + ", targetIdentifier = " + targetIdentifier + ", sourceIdentifier = " + sourceIdentifier + ", reserveResponseCode = " + reserveResponseCode + "]";
    }
}
