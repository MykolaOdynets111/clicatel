package api.requestLibary.CORE;

public class Pricing {

    private String maximumAmount;

    private String minimumAmount;

    private String increment;

    private String type;

    public Pricing() {
    }

    public Pricing(String maximumAmount, String minimumAmount, String increment, String type){
        this.maximumAmount = maximumAmount;
        this.minimumAmount = minimumAmount;
        this.increment = increment;
        this.type= type;
    }

    public String getMaximumAmount ()
    {
        return maximumAmount;
    }

    public void setMaximumAmount (String maximumAmount)
    {
        this.maximumAmount = maximumAmount;
    }

    public String getMinimumAmount ()
    {
        return minimumAmount;
    }

    public void setMinimumAmount (String minimumAmount)
    {
        this.minimumAmount = minimumAmount;
    }

    public String getIncrement ()
    {
        return increment;
    }

    public void setIncrement (String increment)
    {
        this.increment = increment;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [maximumAmount = "+maximumAmount+", minimumAmount = "+minimumAmount+", increment = "+increment+", type = "+type+"]";
    }

}
