package api.requestLibary.USSD;

import api.testUtilities.dataBuilders.msisdnProvider;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlRootElement(name = "ussd")
public class ussdGeneric {

    private String msg;

    private String sessionid;

    private String msisdn;

    private String type;

    private String network;

    private String respType;

    private String respMessage;

    public ussdGeneric(String msg,String type, String network,String respType,String respMessage) {
        msisdnProvider x = new msisdnProvider();
        this.msg = msg;
        this.sessionid = UUID.randomUUID().toString();
        this.msisdn = x.getRandomDoubleBetweenRange();
        this.type = type;
        this.network = network;
        this.respType = respType;
        this.respMessage = respMessage;
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
        return "ClassPojo [msg = "+msg+", sessionid = "+sessionid+", msisdn = "+msisdn+", type = "+type+", network = "+network+"]";
    }

    public ussdGeneric() {
    }
}
