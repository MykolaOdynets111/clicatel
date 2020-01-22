package api.requestLibary.PWM;

public class pwmConfirmationBehaviourPOJO {
    private String delayMs;

    private String targetIdentifier;

    private String sourceIdentifier;

    private String httpStatus;

    public pwmConfirmationBehaviourPOJO(String delayMs, String targetIdentifier, String sourceIdentifier, String httpStatus) {
        this.delayMs = delayMs;
        this.targetIdentifier = targetIdentifier;
        this.sourceIdentifier = sourceIdentifier;
        this.httpStatus = httpStatus;
    }

    public pwmConfirmationBehaviourPOJO() {
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

    public String getSourceIdentifier ()
    {
        return sourceIdentifier;
    }

    public void setSourceIdentifier (String sourceIdentifier)
    {
        this.sourceIdentifier = sourceIdentifier;
    }

    public String getHttpStatus ()
    {
        return httpStatus;
    }

    public void setHttpStatus (String httpStatus)
    {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [delayMs = "+delayMs+", targetIdentifier = "+targetIdentifier+", sourceIdentifier = "+sourceIdentifier+", httpStatus = "+httpStatus+"]";
    }
}
