/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.Objects;

/**
 * Java Object for representing regions.
 * @author xkral3, xvalko, xmikova
 */
public final class Region {
    private final String id;
    private final String name;
    private final double priceCallIncoming;
    private final double priceCallOutcoming;
    private final double priceMessageIncoming;
    private final double priceMessageOutcoming;

    public Region(String id, String name, double priceCallIncoming, double priceCallOutcoming, double priceMessageIncoming, double priceMessageOutcoming) {
        this.id = id;
        this.name = name;
        this.priceCallIncoming = priceCallIncoming;
        this.priceCallOutcoming = priceCallOutcoming;
        this.priceMessageIncoming = priceMessageIncoming;
        this.priceMessageOutcoming = priceMessageOutcoming;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPriceCallIncoming() {
        return priceCallIncoming;
    }

    public double getPriceCallOutcoming() {
        return priceCallOutcoming;
    }

    public double getPriceMessageIncoming() {
        return priceMessageIncoming;
    }

    public double getPriceMessageOutcoming() {
        return priceMessageOutcoming;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof Region) {
            Region region = (Region) o;
            if(region.getId().equals(this.id)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public int hashCode() {
        int hash = Objects.hashCode(this.id)^Objects.hashCode(name);
        return hash;
    }
    
}
