package api.requestLibary.MWM;
/**
 *Ping payload object
 * Author: Adam Bethlehem
 */
public class mwmPingPOJO {
    private String vendorId;

    public mwmPingPOJO(String vendorId) {
        this.vendorId = vendorId;
    }

    public mwmPingPOJO() {
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public String toString() {
        return "ClassPojo [vendorId = " + vendorId + "]";
    }
}
