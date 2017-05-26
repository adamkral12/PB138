/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import core.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * This class will sort calls and messages
 * @author marek
 */
public class SortManager {
    
    /**
     * Helper method to compare primitive type int
     * @param a first int
     * @param b second int
     * @return -1 if a is smaller, 1 if b is smaller, 0 if both are equal
     */
    private static int compareInt(int a, int b) {
        return a > b ? +1 : a < b ? -1 : 0;
    }
    
    
    /**
     * Helper method to compare direction
     * Order: IN, OUT
     * @param a first direction
     * @param b second direction
     * @return 
     */
    private static int compareDirection(Direction a, Direction b) {
        if(a == b) {
            return 0;
        }
        
        if (a == Direction.IN) {
            return -1;
        } else {
            return 1;
        }
    }
    
    /**
     * Sorts given calls by given parameter
     * @param calls to be sorted
     * @param par by which the calls will be sorted
     * @return 
     */
    public static List<Call> sortCalls(List<Call> calls, SortParameter par) {
        List<Call> out = new ArrayList<>(calls);
        
        switch(par) {
            case ID:
                Collections.sort(out,
                        (c1, c2) -> compareInt(c1.getId(), c2.getId()));
                break;
            case LENGTH:
                Collections.sort(out,
                        (c1, c2) -> compareInt(c1.getLenght(), c2.getLenght()));
                break;
            case CALLEE:
                Collections.sort(out,
                        (c1, c2) -> c1.getCallee().compareTo(c2.getCallee()));
                break;
            case DEST:
                Collections.sort(out,
                        (c1, c2) -> c1.getDestination().getName()
                                .compareTo(c2.getDestination().getName()));
                break;
            case REG:
                Collections.sort(out,
                        (c1, c2) -> c1.getDestination().getRegion().getName()
                .compareTo(c2.getDestination().getRegion().getName()));
                break;
            case DIR:
                Collections.sort(out,
                        (c1, c2) -> compareDirection(c1.getDirection()
                                , c2.getDirection()));
                break;
            case DATE:
                Collections.sort(out
                        , (c1, c2) -> c1.getDateTime()
                                .compareTo(c2.getDateTime()));
                break;
            default: System.out.println("Wrong parameter");
            return null;
        }
        
        return out;

    }
    public static List<Call> sortCallsReversed(List<Call> calls, SortParameter par) {
        List<Call> out = sortCalls(calls, par);
        Collections.reverse(out);
        return out;
    }
    
    public static List<Message> sortMessages(List<Message> messages, SortParameter par) {
        List<Message> out = new ArrayList<>(messages);
        
        switch(par) {
            case ID:
                Collections.sort(out,
                        (m1, m2) -> compareInt(m1.getId(), m2.getId()));
                break;
            case LENGTH:
                Collections.sort(out,
                        (m2, m1) -> compareInt(m1.getLenght(), m2.getLenght()));
                break;
            case CALLEE:
                Collections.sort(out,
                        (m1, m2) -> m1.getCallee().compareTo(m2.getCallee()));
                break;
            case DEST:
                Collections.sort(out,
                        (m1, m2) -> m1.getDestination().getName()
                                .compareTo(m2.getDestination().getName()));
                break;
            case REG:
                Collections.sort(out,
                        (m1, m2) -> m1.getDestination().getRegion().getName()
                .compareTo(m2.getDestination().getRegion().getName()));
                break;
            case DIR:
                Collections.sort(out,
                        (m1, m2) -> compareDirection(m1.getDirection()
                                , m2.getDirection()));
                break;
            case DATE:
                Collections.sort(out
                        , (c1, c2) -> c1.getDateTime()
                                .compareTo(c2.getDateTime()));
                break;
            default: System.out.println("Wrong parameter");
            return null;
        }
        
        return out;

    }
    public static List<Message> sortMessagesReversed(List<Message> messages
            , SortParameter par) {
        List<Message> out = sortMessages(messages, par);
        Collections.reverse(out);
        return out;
    }
    /*
    
    TODO vieme to vyriesit nejko takto, aby bola len jedna metoda?
    pretoze List<Call> a List<Message> su rovnake typy, takze sa neda pretazit
    sort(List<Message>) a sort(List<Call>)
    
    ak nie tak tento komentar vymazte
    public static <T extends Call> List<T> sort(List<T> messages, SortParameter par) {
        List<T> out = new ArrayList<>(messages);
        
        switch(par) {
            case ID:
                Collections.sort(out,
                        (m1, m2) -> compareInt(m1.getId(), m2.getId()));
                break;
            case LENGTH:
                Collections.sort(out,
                        (m1, m2) -> compareInt(m1.getLenght(), m2.getLenght()));
                break;
            case CALLEE:
                Collections.sort(out,
                        (m1, m2) -> m1.getCallee().compareTo(m2.getCallee()));
                break;
            case DEST:
                Collections.sort(out,
                        (m1, m2) -> m1.getDestination().getName()
                                .compareTo(m2.getDestination().getName()));
                break;
            case REG:
                Collections.sort(out,
                        (m1, m2) -> m1.getDestination().getRegion().getName()
                .compareTo(m2.getDestination().getRegion().getName()));
                break;
            case DIR:
                Collections.sort(out,
                        (m1, m2) -> compareDirection(m1.getDirection()
                                , m2.getDirection()));
                break;
            case DATE:
                Collections.sort(out
                        , (c1, c2) -> c1.getDateTime()
                                .compareTo(c2.getDateTime()));
                break;
            default: System.out.println("Wrong parameter");
            return null;
        }
        
        return out;

    }
    */
    /*
    public static <T extends Message> List<T> sort(List<T> messages, SortParameter par) {
        List<T> out = new ArrayList<>(messages);
        
        switch(par) {
            case ID:
                Collections.sort(out,
                        (m1, m2) -> compareInt(m1.getId(), m2.getId()));
                break;
            case LENGTH:
                Collections.sort(out,
                        (m1, m2) -> compareInt(m1.getLenght(), m2.getLenght()));
                break;
            case CALLEE:
                Collections.sort(out,
                        (m1, m2) -> m1.getCallee().compareTo(m2.getCallee()));
                break;
            case DEST:
                Collections.sort(out,
                        (m1, m2) -> m1.getDestination().getName()
                                .compareTo(m2.getDestination().getName()));
                break;
            case REG:
                Collections.sort(out,
                        (m1, m2) -> m1.getDestination().getRegion().getName()
                .compareTo(m2.getDestination().getRegion().getName()));
                break;
            case DIR:
                Collections.sort(out,
                        (m1, m2) -> compareDirection(m1.getDirection()
                                , m2.getDirection()));
                break;
            case DATE:
                Collections.sort(out
                        , (c1, c2) -> c1.getDateTime()
                                .compareTo(c2.getDateTime()));
                break;
            default: System.out.println("Wrong parameter");
            return null;
        }
        
        return out;

    }
    
    */
    
}
