package com.sacom.integrateservice.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import java.math.BigDecimal;

public class Price {
    private String currency;
    private BigDecimal value;

    public Price() {
    }

    public Price(String currency, BigDecimal value) {
        this.currency = currency;
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    @XmlAttribute(name = "currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    @XmlValue
    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
