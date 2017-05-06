/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.Date;

/**
 * Java Object for representing messages.
 * @author xkral3, xvalko, xmikova
 */
public final class Message {
    private final int id;
    private final int lenght;
    private final String callee;
    private final Country destination;
    private final Direction direction;
    private final Date dateTime;
    private final String note;

    public Message(int id, int lenght, String callee, Country destination, Direction direction, Date dateTime, String note) {
        this.id = id;
        this.lenght = lenght;
        this.callee = callee;
        this.destination = destination;
        this.direction = direction;
        this.dateTime = dateTime;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public int getLenght() {
        return lenght;
    }

    public String getCallee() {
        return callee;
    }

    public Country getDestination() {
        return destination;
    }

    public Direction getDirection() {
        return direction;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getNote() {
        return note;
    }
    
    
}
