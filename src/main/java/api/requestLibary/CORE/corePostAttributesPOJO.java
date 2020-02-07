package api.requestLibary.CORE;

public class corePostAttributesPOJO {

    private String regex;

    private String name;

    private String id;

    private String type;

    public corePostAttributesPOJO(){

    }

    public corePostAttributesPOJO(String regex, String name, String id, String type){

        this.regex = regex;
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public String getRegex ()
    {
        return regex;
    }

    public void setRegex (String regex)
    {
        this.regex = regex;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
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
        return "ClassPojo [regex = "+regex+", name = "+name+", id = "+id+", type = "+type+"]";
    }
}
