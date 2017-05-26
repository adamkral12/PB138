/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import core.*;

/**
 *
 * @author marek
 */
public class PriceManager {
    private static final double PRICE = 1.0;
    
    public static double getPrice(Call call) {
        Region reg = call.getDestination().getRegion();
        switch(call.getDirection()) {
            case IN:
                //destination of our customer
                return PRICE * reg.getPriceCallIncoming()
                        * (Math.ceil(call.getLenght() / 60.0));
                
            case OUT:
                //destionation of callee
                return PRICE * reg.getPriceCallOutcoming()
                        * (Math.ceil(call.getLenght() / 60.0));
        }
        return -1;
    }
    
    public static double getPrice(Message message) {
        Region reg = message.getDestination().getRegion();
        switch(message.getDirection()) {
            case IN:
                //destination of our customer
                return PRICE * reg.getPriceMessageIncoming()
                        * (Math.ceil(message.getLenght() / 160.0));
                
            case OUT:
                //destionation of callee
                return PRICE * reg.getPriceMessageOutcoming()
                        * (Math.ceil(message.getLenght() / 160.0));
        }
        return -1;
    }
}
