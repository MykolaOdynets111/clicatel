package api.requestLibary.MWM;
/**
 *Ping Behaviour payload object
 * This object object is used to set system behaviour on the request
 * Author: Adam Bethlehem
 */
public class mwmPingBehaviourControlPOJO {
    private String httpStatus;

    public mwmPingBehaviourControlPOJO(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public mwmPingBehaviourControlPOJO() {
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return "ClassPojo [httpStatus = " + httpStatus + "]";
    }
}
