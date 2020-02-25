package api.requestLibary.CORE;

import com.fasterxml.jackson.annotation.JsonRootName;

public class corePostProductPOJO {


    private String productId;

    private String active;

    private String description;

    private String supportToken;

    private Attributes attributes;

    private String shortDescription;

    private Pricing pricing;

    private String productTypeId;

    public corePostProductPOJO() {
    }

//    public corePostProductPOJO(String productId, String active, String description, String supportToken, Attributes[] attributes, String shortDescription, Pricing pricing, String productTypeId) {
//
//        this.productId = productId;
//        this.active = active;
//        this.description = description;
//        this.supportToken = supportToken;
//        this.attributes = attributes;
//        this.shortDescription = shortDescription;
//        this.pricing = pricing;
//        this.productTypeId = productTypeId;
//
//    }
//

    public corePostProductPOJO(String productId, String active, String description, String supportToken, Attributes attributes, String shortDescription, Pricing pricing, String productTypeId) {
        this.productId = productId;
        this.active = active;
        this.description = description;
        this.supportToken = supportToken;
        this.attributes = attributes;
        this.shortDescription = shortDescription;
        this.pricing = pricing;
        this.productTypeId = productTypeId;
    }



    public String getProductId ()
    {
        return productId;
    }

    public void setProductId (String productId)
    {
        this.productId = productId;
    }

    public String getActive ()
    {
        return active;
    }

    public void setActive (String active)
    {
        this.active = active;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getSupportToken ()
    {
        return supportToken;
    }

    public void setSupportToken (String supportToken)
    {
        this.supportToken = supportToken;
    }



//    public void setAttributes (Attributes[] attributes)
//    {
//        this.attributes = attributes;
//    }

    public String getShortDescription ()
    {
        return shortDescription;
    }

    public void setShortDescription (String shortDescription)
    {
        this.shortDescription = shortDescription;
    }

    public Pricing getPricing ()
    {
        return pricing;
    }

    public void setPricing (Pricing pricing)
    {
        this.pricing = pricing;
    }

    public String getProductTypeId ()
    {
        return productTypeId;
    }

    public void setProductTypeId (String productTypeId)
    {
        this.productTypeId = productTypeId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [productId = "+productId+", active = "+active+", description = "+description+", supportToken = "+supportToken+", attributes = "+attributes+", shortDescription = "+shortDescription+", pricing = "+pricing+", productTypeId = "+productTypeId+"]";
    }


}


