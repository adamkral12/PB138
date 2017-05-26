/*
 * Creates data instance
 * This class overseees that there is only
 * one instance of managers
 */
package gui;

import managers.CallManager;
import managers.MessageManager;


/**
 *
 * @author adam
 */
public class Data {
    
    private static Data instance;
    private final CallManager callManager;
    private final MessageManager messageManager;
    
    private Data() {
        callManager = new CallManager();
        messageManager = new MessageManager();
    }
    
    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }
    
    public CallManager getCallManager() {
        return callManager;
    }
    
    public MessageManager getMessageManager() {
        return messageManager;
    }

}
