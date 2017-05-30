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
 * @author xkral3, xvalko, xmikova
 */
public class SortManager {
    
    /**
     * Helper method to compare primitive type integer
     * @param a first integer
     * @param b second integer
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
     * @return sorted list list of calls, null if wrong parameter is given
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
                        (c1, c2) -> compareInt(c1.getLength(), c2.getLength()));
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
    /**
     * Reverses the list of sorted calls.
     * @param calls calls to be sorted
     * @param par sorting parameter
     * @return sorted collection of calls
     */
    public static List<Call> sortCallsReversed(List<Call> calls, SortParameter par) {
        List<Call> out = sortCalls(calls, par);
        Collections.reverse(out);
        return out;
    }
     
    /**
     * Sorts messages by given parameter.
     * @param messages messages to be sorted
     * @param par sorting parameter
     * @return sorted message list
     */
    public static List<Message> sortMessages(List<Message> messages, SortParameter par) {
        List<Message> out = new ArrayList<>(messages);
        
        switch(par) {
            case ID:
                Collections.sort(out,
                        (m1, m2) -> compareInt(m1.getId(), m2.getId()));
                break;
            case LENGTH:
                Collections.sort(out,
                        (m2, m1) -> compareInt(m1.getLength(), m2.getLength()));
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
    /**
     * Reverses list of sorted messages
     * @param messages messages to be sorted
     * @param par sorting parameter
     * @return sorted collection of calls
     */
    public static List<Message> sortMessagesReversed(List<Message> messages
            , SortParameter par) {
        List<Message> out = sortMessages(messages, par);
        Collections.reverse(out);
        return out;
    }  
}
