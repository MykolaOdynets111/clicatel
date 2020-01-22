package requestLibary.PWM;

public class pwmLookIpPOJO {
    private String raasTransactionReference;

    public pwmLookIpPOJO(String raasTransactionReference) {
        this.raasTransactionReference = raasTransactionReference;
    }

    public pwmLookIpPOJO() {
    }

    public String getRaasTransactionReference ()
    {
        return raasTransactionReference;
    }

    public void setRaasTransactionReference (String raasTransactionReference)
    {
        this.raasTransactionReference = raasTransactionReference;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [raasTransactionReference = "+raasTransactionReference+"]";
    }
}
