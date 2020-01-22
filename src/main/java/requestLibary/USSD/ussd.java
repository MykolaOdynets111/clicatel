package requestLibary.USSD;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlRootElement(name = "ussd")

public class ussd {
    //@XmlElement(name = "msisdn")
    private String msisdn;
   // @XmlElement(name = "sessinid")
    private String sessionid;
  //  @XmlElement(name = "type")
    private String type;
  //  @XmlElement(name = "network")
    private String network;
 //   @XmlElement(name = "msg")
    private String msg;



    public ussd(String msisdn,String type, String network, String msg) {
        String guid = UUID.randomUUID().toString();
//        String msisdnRandom =(String) Math.random();
        this.msisdn = msisdn;
        this.sessionid = guid;
        this.type = type;
        this.network = network;
        this.msg = msg;
    }

    public ussd(String msisdn, String sessionID, String type, String network, String msg) {

        this.msisdn = msisdn;
        this.sessionid = sessionID;
        this.type = type;
        this.network = network;
        this.msg = msg;
    }

    public ussd() {
    }

    public String getMsg ()
    {
        return msg;
    }

    public void setMsg (String msg)
    {
        this.msg = msg;
    }

    public String getSessionid ()
    {
        return sessionid;
    }

    public void setSessionid (String sessionid)
    {
        this.sessionid = sessionid;
    }

    public String getMsisdn ()
    {
        return msisdn;
    }

    public void setMsisdn (String msisdn)
    {
        this.msisdn = msisdn;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getNetwork ()
    {
        return network;
    }

    public void setNetwork (String network)
    {
        this.network = network;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [msisdn = "+msisdn+", sessionid = "+sessionid+", type = "+type+", network = "+network +"msg = " +msg +"]";

    }
}
