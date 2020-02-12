package api.requestLibary.CORE;

public class corePOSTVendorPOJO {

    private String backoff;

    private String active;

    private String maximumRequestsPerPeriod;

    private String countryId;

    private String currencyAlphaCode;

    private String retryDelay;

    private String maxRetries;

    private String retryDelayMax;

    private String name;

    private String countryName;

    private String id;

    private String currencyId;

    private String requestsPeriod;

    public corePOSTVendorPOJO(){

    }

    public corePOSTVendorPOJO(String backoff, String active, String maximumRequestsPerPeriod, String countryId, String currencyAlphaCode, String retryDelay, String maxRetries, String retryDelayMax, String name, String countryName, String id, String currencyId, String requestsPeriod){
        this.backoff = backoff;
        this.active = active;
        this.maximumRequestsPerPeriod = maximumRequestsPerPeriod;
        this.countryId = countryId;
        this.currencyAlphaCode = currencyAlphaCode;
        this.retryDelay = retryDelay;
        this.maxRetries = maxRetries;
        this.retryDelayMax = retryDelayMax;
        this.name = name;
        this.countryName = countryName;
        this.id = id;
        this.currencyId = currencyId;
        this.requestsPeriod = requestsPeriod;
    }


    public String getBackoff ()
    {
        return backoff;
    }

    public void setBackoff (String backoff)
    {
        this.backoff = backoff;
    }

    public String getActive ()
    {
        return active;
    }

    public void setActive (String active)
    {
        this.active = active;
    }

    public String getMaximumRequestsPerPeriod ()
    {
        return maximumRequestsPerPeriod;
    }

    public void setMaximumRequestsPerPeriod (String maximumRequestsPerPeriod)
    {
        this.maximumRequestsPerPeriod = maximumRequestsPerPeriod;
    }

    public String getCountryId ()
    {
        return countryId;
    }

    public void setCountryId (String countryId)
    {
        this.countryId = countryId;
    }

    public String getCurrencyAlphaCode ()
    {
        return currencyAlphaCode;
    }

    public void setCurrencyAlphaCode (String currencyAlphaCode)
    {
        this.currencyAlphaCode = currencyAlphaCode;
    }

    public String getRetryDelay ()
    {
        return retryDelay;
    }

    public void setRetryDelay (String retryDelay)
    {
        this.retryDelay = retryDelay;
    }

    public String getMaxRetries ()
    {
        return maxRetries;
    }

    public void setMaxRetries (String maxRetries)
    {
        this.maxRetries = maxRetries;
    }

    public String getRetryDelayMax ()
    {
        return retryDelayMax;
    }

    public void setRetryDelayMax (String retryDelayMax)
    {
        this.retryDelayMax = retryDelayMax;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getCountryName ()
    {
        return countryName;
    }

    public void setCountryName (String countryName)
    {
        this.countryName = countryName;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCurrencyId ()
    {
        return currencyId;
    }

    public void setCurrencyId (String currencyId)
    {
        this.currencyId = currencyId;
    }

    public String getRequestsPeriod ()
    {
        return requestsPeriod;
    }

    public void setRequestsPeriod (String requestsPeriod)
    {
        this.requestsPeriod = requestsPeriod;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [backoff = "+backoff+", active = "+active+", maximumRequestsPerPeriod = "+maximumRequestsPerPeriod+", countryId = "+countryId+", currencyAlphaCode = "+currencyAlphaCode+", retryDelay = "+retryDelay+", maxRetries = "+maxRetries+", retryDelayMax = "+retryDelayMax+", name = "+name+", countryName = "+countryName+", id = "+id+", currencyId = "+currencyId+", requestsPeriod = "+requestsPeriod+"]";
    }

}
