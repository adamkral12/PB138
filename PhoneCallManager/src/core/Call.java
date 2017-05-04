/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;
import java.util.Date;

/**
 *
 * @author xkral3, xvalko, xmikova
 */
public final class Call {
    private final int id;
    private final int lenght;
    private final String callee;
    private final Country destination;
    private final Direction direction;
    private final Date dateTime;
    private final String note;

    public Call(int id, int lenght, String callee, Country destination, Direction direction, Date dateTime, String note) {
        this.id = id;
        this.lenght = lenght;
        this.callee = callee;
        this.destination = destination;
        this.direction = direction;
        this.dateTime = dateTime;
        this.note = note;
    }
    
    public final int getId() {
        return id;
    }

    public final int getLenght() {
        return lenght;
    }

    public final String getCallee() {
        return callee;
    }

    public final Country getDestination() {
        return destination;
    }

    public final Direction getDirection() {
        return direction;
    }

    public final Date getDateTime() {
        return dateTime;
    }

    public String getNote() {
        return note;
    }
    
}
