/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Gabriel Fernandez
 */

package ucf.assignments;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class InventoryItem implements Serializable {
    private BigDecimal price;
    private String serialNumber;
    private String name;

    public InventoryItem(BigDecimal price, String serialNumber, String name) {
        this.price = price;
        this.serialNumber = serialNumber;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
