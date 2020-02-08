package api.requestLibary.CORE;

import java.util.Properties;

public class corePostClientPOJO {

    private String ctxLimitTotal;

    private String clientId;

    private String[] clickatellSystem;

    private String clickatellAccountId;

    private String clientName;

    private String countryCode;

    private String active;

    private String timezoneId;

    private Properties properties;

    public corePostClientPOJO(){

    }

    public corePostClientPOJO(String active, String clickatellAccountId, String[] clickatellSystem, String clientId, String clientName, String countryCode, String ctxLimitTotal, Properties properties, String timezoneId){

        this.ctxLimitTotal = ctxLimitTotal;
        this.clientId = clientId;
        this.clickatellSystem = clickatellSystem;
        this.clickatellAccountId = clickatellAccountId;
        this.clientName = clientName;
        this.countryCode = countryCode;
        this.active = active;
        this.timezoneId = timezoneId;
        this.properties = properties;

    }

    public String getCtxLimitTotal ()
    {
        return ctxLimitTotal;
    }

    public void setCtxLimitTotal (String ctxLimitTotal)
    {
        this.ctxLimitTotal = ctxLimitTotal;
    }

    public String getClientId ()
    {
        return clientId;
    }

    public void setClientId (String clientId)
    {
        this.clientId = clientId;
    }

    public String[] getClickatellSystem ()
    {
        return clickatellSystem;
    }

    public void setClickatellSystem (String[] clickatellSystem)
    {
        this.clickatellSystem = clickatellSystem;
    }

    public String getClickatellAccountId ()
    {
        return clickatellAccountId;
    }

    public void setClickatellAccountId (String clickatellAccountId)
    {
        this.clickatellAccountId = clickatellAccountId;
    }

    public String getClientName ()
    {
        return clientName;
    }

    public void setClientName (String clientName)
    {
        this.clientName = clientName;
    }

    public String getCountryCode ()
    {
        return countryCode;
    }

    public void setCountryCode (String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getActive ()
    {
        return active;
    }

    public void setActive (String active)
    {
        this.active = active;
    }

    public String getTimezoneId ()
    {
        return timezoneId;
    }

    public void setTimezoneId (String timezoneId)
    {
        this.timezoneId = timezoneId;
    }

    public Properties getProperties ()
    {
        return properties;
    }

    public void setProperties (Properties properties)
    {
        this.properties = properties;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ctxLimitTotal = "+ctxLimitTotal+", clientId = "+clientId+", clickatellSystem = "+clickatellSystem+", clickatellAccountId = "+clickatellAccountId+", clientName = "+clientName+", countryCode = "+countryCode+", active = "+active+", timezoneId = "+timezoneId+", properties = "+properties+"]";
    }

}
