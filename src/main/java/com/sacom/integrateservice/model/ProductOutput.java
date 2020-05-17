package com.sacom.integrateservice.model;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlType(propOrder = { "description", "gtin", "price", "orderid"  })
public class ProductOutput {
    private String gtin;
    private String description;
    private Price price;
    private String supplier;
    private Date date;
    private int orderid;

    public ProductOutput() {
    }

    public ProductOutput(String gtin, String description, Price price, String supplier, Date date, int orderid) {
        this.gtin = gtin;
        this.description = description;
        this.price = price;
        this.supplier = supplier;
        this.date = date;
        this.orderid = orderid;
    }

    public String getGtin() {
        return gtin;
    }

    //    @XmlElement
    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public String getDescription() {
        return description;
    }

    //    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

    public Price getPrice() {
        return price;
    }

    //    @XmlElement
    public void setPrice(Price price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    @XmlTransient
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Date getDate() {
        return date;
    }

    @XmlTransient
    public void setDate(Date date) {
        this.date = date;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }
}
