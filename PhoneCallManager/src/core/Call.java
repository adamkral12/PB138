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
public class Call {
    private int lenght;
    private String callee;
    private Country destination;
    private Direction direction;
    private Date dateTime;
    private String note;

    public Call(int lenght, String callee, Country destination, Direction direction, Date dateTime, String note) {
        this.lenght = lenght;
        this.callee = callee;
        this.destination = destination;
        this.direction = direction;
        this.dateTime = dateTime;
        this.note = note;
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public String getCallee() {
        return callee;
    }

    public void setCallee(String callee) {
        this.callee = callee;
    }

    public Country getDestination() {
        return destination;
    }

    public void setDestination(Country destination) {
        this.destination = destination;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
}
