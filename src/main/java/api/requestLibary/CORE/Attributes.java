package api.requestLibary.CORE;

import org.dom4j.Attribute;

public class Attributes {

    private String attributeId;

    private String value;

    public Attributes() {
    }

    public Attributes(String attributeId, String value){

        this.attributeId = attributeId;
        this.value = value;

    }


    public String getValue ()
    {
        return value;
    }

    public void setValue (String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [attributeId = "+attributeId+", value = "+value+"]";
    }

}
