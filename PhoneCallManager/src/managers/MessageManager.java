/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import core.Country;
import core.Direction;
import java.util.List;
import core.Message;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Manages messages
 * Parses them from XML file, stores them in field messages, retrieves them 
 * by parameters
 * @author xkral3, xvalko, xmikova
 */
public class MessageManager {
    private static List<Message> messages;
    
    /**
     * Parses XML document containing messages.
     * @return List of all messages from XML in order they are stored, null if 
     * error happened
     */
    private static List<Message> parseMessagesFromXML() 
            throws XPathExpressionException {
        File messagesXML = new File("messages.xml");
        
        Document doc = FileManager.getDocument(messagesXML);
        if (doc == null) {
            return null;
        }
        XPath xPath = XPathFactory.newInstance().newXPath();
        
        NodeList pMessages = (NodeList) xPath.evaluate("/messages/message",
                doc.getDocumentElement(),XPathConstants.NODESET);
        List<Message> outMessages = new ArrayList<>();
        
        for(int i = 0; i < pMessages.getLength(); i++) {
            Element e = (Element) pMessages.item(i);
            
            int mid = Integer.parseInt(e.getAttribute("mid"));
            
            int length = Integer.parseInt((String) 
                    xPath.evaluate("length/text()", e, 
                    XPathConstants.STRING));
            String callee = (String) xPath.evaluate("callee/text()", e, 
                    XPathConstants.STRING);
            Country destination = new CountryManager().getById((String) 
                    xPath.evaluate("destination/text()", e, 
                    XPathConstants.STRING));
            Direction direction;
            switch (((String) xPath.evaluate("direction/text()", e, 
                    XPathConstants.STRING)).toUpperCase()) {
                case "OUT": direction = Direction.OUT;
                            break;
                case "IN" : direction = Direction.IN;
                            break;
                default : direction = null;
            }
            
            String note = (String) xPath.evaluate("note/text()", e, 
                    XPathConstants.STRING);
            String pDate = (String) xPath.evaluate("dateTime/text()", e, 
                    XPathConstants.STRING);
            String[] parsedDateAndTime = pDate.split("T");//T\\d{2}:\\d{2}:\\d{2}$");
            String[] parsedDate = parsedDateAndTime[0].split("-");
            String[] parsedTime = parsedDateAndTime[1].split(":");
            
            Date date = new Date(Integer.parseInt(parsedDate[0])-1900,
                                Integer.parseInt(parsedDate[1])-1,
                                Integer.parseInt(parsedDate[2]),
                                Integer.parseInt(parsedTime[0]),
                                Integer.parseInt(parsedTime[1]),
                                Integer.parseInt(parsedTime[2]));
            
            Message message = new Message(mid, length, callee, destination, 
                    direction, date, note);
            
            outMessages.add(message);
        }
        if(outMessages.isEmpty()) {
            return null;
        }
        return outMessages;
    }
    
    /**
     * Reloads messages from XML file and stores them in messages variable.
     * @return True - messages were successfully parsed and saved, False - else
     */
    public static boolean reloadMessages() {
        try {
            List<Message> parsedMessages = parseMessagesFromXML();
            if(parsedMessages != null) {
                MessageManager.messages = new ArrayList<>(parsedMessages);
                return true;
            } else {
                return false;
            }
        } catch (XPathExpressionException ex) {
            Logger.getLogger(MessageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Returns all messages
     * @return all messages
     */
    public List<Message> getAll() {
        return new ArrayList<>(messages);
    }
    
    /**
     * Returns message by given id
     * @param id of the message
     * @return message with id id
     */
    public Message getById(int id) {
        for(Message message : getAll()) {
            if(message.getId() == id) {
                return message;
            }
        }
        return null;
    }
    
    /**
     * Returns messages with a specific callee
     * @param callee of the messages
     * @return List of messages
     */
    public List<Message> getByCallee(String callee) {
        List<Message> out = new ArrayList<>();
        for(Message message : getAll()) {
            if(message.getCallee().equals(callee)) {
                out.add(message);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        
        return out;
    }
    
    /**
     * Returns messages by given destination
     * @param country of the message
     * @return  List of messages
     */
    public List<Message> getByDestination(Country country) {
        List<Message> out = new ArrayList<>();
        for(Message message : getAll()) {
            if(message.getDestination().equals(country)) {
                out.add(message);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;
    }
    
    /**
     * Returns messages by given direction
     * @param direction of the messages
     * @return List of messages
     */
    public List<Message> getByDirection(Direction direction) {
        List<Message> out = new ArrayList<>();
        for(Message message : getAll()) {
            if(message.getDirection() == direction) {
                out.add(message);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;
    }
    
    /**
     * Return messages with specific date
     * @param date of the message
     * @return List of messages
     */
    public List<Message> getByDate(Date date) {
        List<Message> out = new ArrayList<>();
        for(Message message : getAll()) {
            if(message.getDateTime().equals(date)) {
                out.add(message);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;        
    }
}
