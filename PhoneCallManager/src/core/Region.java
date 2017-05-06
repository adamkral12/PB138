/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 * Java Object for representing regions.
 * @author xkral3, xvalko, xmikova
 */
public final class Region {
    private final int id;
    private final String name;
    private final double priceCallIncoming;
    private final double priceCallOutcoming;
    private final double priceMessageIncoming;
    private final double priceMessageOutcoming;

    public Region(int id, String name, double priceCallIncoming, double priceCallOutcoming, double priceMessageIncoming, double priceMessageOutcoming) {
        this.id = id;
        this.name = name;
        this.priceCallIncoming = priceCallIncoming;
        this.priceCallOutcoming = priceCallOutcoming;
        this.priceMessageIncoming = priceMessageIncoming;
        this.priceMessageOutcoming = priceMessageOutcoming;
    }

    public int getId() {
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
    
}
