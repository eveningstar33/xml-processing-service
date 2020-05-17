package com.sacom.integrateservice.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
public class ProductsOutput {
    private List<ProductOutput> product;

    public ProductsOutput() {
    }

    public ProductsOutput(List<ProductOutput> product) {
        this.product = product;
    }

    public List<ProductOutput> getProduct() {
        return product;
    }

    @XmlElement
    public void setProduct(List<ProductOutput> product) {
        this.product = product;
    }
}
