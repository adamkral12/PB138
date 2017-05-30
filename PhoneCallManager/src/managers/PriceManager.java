/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import core.*;

/**
 * Calculates prices of calls and messages.
 * @author xkral3, xvalko, xmikova
 */
public class PriceManager {
    private static final double PRICE = 1.0;
    
    /**
     * calculates price of a call
     * @param call of which price will be calculated
     * @return price of a call, -1 if direction was not specified
     */
    public static double getPrice(Call call) {
        Region reg = call.getDestination().getRegion();
        switch(call.getDirection()) {
            case IN:
                //destination of our customer
                return PRICE * reg.getPriceCallIncoming()
                        * (Math.ceil(call.getLength() / 60.0));
                
            case OUT:
                //destionation of callee
                return PRICE * reg.getPriceCallOutcoming()
                        * (Math.ceil(call.getLength() / 60.0));
        }
        return -1;
    }
    
    /**
     * Calculates price of a message
     * @param message of which price will be calculated
     * @return price of a message, -1 if direction was not specified
     */
    public static double getPrice(Message message) {
        Region reg = message.getDestination().getRegion();
        switch(message.getDirection()) {
            case IN:
                //destination of our customer
                return PRICE * reg.getPriceMessageIncoming()
                        * (Math.ceil(message.getLength() / 160.0));
                
            case OUT:
                //destionation of callee
                return PRICE * reg.getPriceMessageOutcoming()
                        * (Math.ceil(message.getLength() / 160.0));
        }
        return -1;
    }
}
