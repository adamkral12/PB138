/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author xkral3, xvalko, xmikova
 */
final public class Country {
    private final int id;
    private final String name;
    private final Region region;
    private final String prefix;

    public Country(int id, String name, Region region, String prefix) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.prefix = prefix;
    }

    public final int getId() {
        return id;
    }

    public final String getName() {
        return name;
    }

    public final Region getRegion() {
        return region;
    }

    public final String getPrefix() {
        return prefix;
    }
    
    
}
