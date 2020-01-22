package requestLibary.PWM;

public class pwmConfimrationPojo {
    private String reserveFundsTxnRef;

    private String clientTxnRef;

    private String reserveAmount;

    private String raasTxnRef;

    private String responseCode;

    private String timestamp;

    public pwmConfimrationPojo(String reserveFundsTxnRef, String clientTxnRef, String reserveAmount, String raasTxnRef, String responseCode, String timestamp) {
        this.reserveFundsTxnRef = reserveFundsTxnRef;
        this.clientTxnRef = clientTxnRef;
        this.reserveAmount = reserveAmount;
        this.raasTxnRef = raasTxnRef;
        this.responseCode = responseCode;
        this.timestamp = timestamp;
    }

    public pwmConfimrationPojo() {
    }

    public String getReserveFundsTxnRef() {
        return reserveFundsTxnRef;
    }

    public void setReserveFundsTxnRef(String reserveFundsTxnRef) {
        this.reserveFundsTxnRef = reserveFundsTxnRef;
    }

    public String getClientTxnRef() {
        return clientTxnRef;
    }

    public void setClientTxnRef(String clientTxnRef) {
        this.clientTxnRef = clientTxnRef;
    }

    public String getReserveAmount() {
        return reserveAmount;
    }

    public void setReserveAmount(String reserveAmount) {
        this.reserveAmount = reserveAmount;
    }

    public String getRaasTxnRef() {
        return raasTxnRef;
    }

    public void setRaasTxnRef(String raasTxnRef) {
        this.raasTxnRef = raasTxnRef;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ClassPojo [reserveFundsTxnRef = " + reserveFundsTxnRef + ", clientTxnRef = " + clientTxnRef + ", reserveAmount = " + reserveAmount + ", raasTxnRef = " + raasTxnRef + ", responseCode = " + responseCode + ", timestamp = " + timestamp + "]";
    }
}
