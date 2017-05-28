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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
            Calendar cal = Calendar.getInstance();
            try {
                cal.setTime(df.parse(pDate));
            } catch (ParseException ex) {
                System.out.println("Getting time and date for the message "
                        + "failed.");
            }
                        
            Message message = new Message(mid, length, callee, destination, 
                    direction, cal, note);
            
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
            Logger.getLogger(MessageManager.class.getName()).log(Level.SEVERE
                    , null, ex);
        }
        return false;
    }
    
    /**
     * Returns all messages
     * @return all messages
     */
    public static List<Message> getAll() {
        return new ArrayList<>(messages);
    }
    
    /**
     * Returns message by given id from all stored messages
     * @param id of a message
     * @return message with id id
     */
    public static Message getById(int id) {
        return getById(getAll(), id);
    }
    
    /**
     * Returns message by given id from given set of messages
     * @param list list of messages to get message from
     * @param id of the message
     * @return message with id id
     */
    public static Message getById(List<Message> list, int id) {
        for(Message message : list) {
            if(message.getId() == id) {
                return message;
            }
        }
        return null;
    }
    
    /**
     * Returns message(s) by given note from given set of messages
     * @param note of the mesages
     * @return List of messages with given note (substring)
     */
    public static List<Message> getByNote(String note) {
        return getByNote(getAll(), note);
    }
    
        /**
     * Returns message(s) by given note from given set of messages
     * @param list list of messages to get message from
     * @param note of a message
     * @return List<Message> list of messages with note containing substring
     */
    public static List<Message> getByNote(List<Message> list, String note) {
        List<Message> out = new ArrayList<Message>();
        for(Message message : list) {
            if(message.getNote().toLowerCase().contains(note.toLowerCase())) {
                out.add(message);
            }
        }
        return out;
    }    
    
    /**
     * Returns list of messages by specific callee from all messages
     * @param callee of the messages
     * @return List of messages
     */
    public static List<Message> getByCallee(String callee) {
        return getByCallee(getAll(), callee);
    }
    
    /**
     * Returns messages with a specific callee from given list of messages
     * @param list list of messages to filter from
     * @param callee of the messages
     * @return List of messages
     */
    public static List<Message> getByCallee(List<Message> list, String callee) {
        List<Message> out = new ArrayList<>();
        for(Message message : list) {
            if(message.getCallee().toLowerCase().contains(callee.toLowerCase())) {
                out.add(message);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        
        return out;
    }
    
    /**
     * Returns message(s) by given length from given set of messages
     * @param legnth of the messages
     * @return List of messages with given length (substring)
     */
    public static List<Message> getByLength(String length) {
        return getByLength(getAll(), length);
    }
    
        /**
     * Returns message(s) by given note from given set of messages
     * @param list list of messages to get message from
     * @param note of a message
     * @return List<Message> list of messages with note containing substring
     */
    public static List<Message> getByLength(List<Message> list, String length) {
        List<Message> out = new ArrayList<Message>();
        for(Message message : list) {
            String lengthString = String.valueOf(message.getLength());
            if(lengthString.toLowerCase().contains(length.toLowerCase())) {
                out.add(message);
            }
        }
        return out;
    }      
    
    /**
     * Returns messages by given destination from all stored calls
     * @param country of the message
     * @return List of messages
     */
    public static List<Message> getByDestination(Country country) {
        return getByDestination(getAll(), country);
    }
    
    /**
     * Returns messages by given destination from given list of messages
     * @param list list of messages to filter from
     * @param country of the message
     * @return  List of messages
     */
    public static List<Message> getByDestination(List<Message> list
            , Country country) {
        List<Message> out = new ArrayList<>();
        for(Message message : list) {
            if(message.getDestination().equals(country)) {
                out.add(message);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;
    }
    
    public static List<Message> getByDestination(List<Country> countries) {
        List<Message> out = new ArrayList();
        for(Message message : getAll()) {
            if(countries.contains(message.getDestination())) {
                out.add(message);
            }
        }
        return out;
    }
    
    /**
     * Returns messages by given direction from all stored messages
     * @param direction of the message
     * @return List of messages
     */
    
    public static List<Message> getByDirection(Direction direction) {
        return getByDirection(getAll(), direction);
    }
    
    /**
     * Returns messages by given direction from given list of messages
     * @param list list of messages to filter from
     * @param direction of the messages
     * @return List of messages
     */
    public static List<Message> getByDirection(List<Message> list
            , Direction direction) {
        List<Message> out = new ArrayList<>();
        for(Message message : list) {
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
     * Get calls by specified Year, Month, Day and hour in the year from all calls
     * Caution: Month starts from 0 - January
     * @param cal Calendar with a year, month and day to get calls for, it 
     * should have specified YEAR, MONTH, DAY and HOUR
     * @return list of calls
     */
    public static List<Message> getByMinute(Calendar cal) {
        return getByMinute(getAll(), cal);
    }
    
    /**
     * Get calls by specified Year, Month, Day and Hour in the year from given 
     * list of calls
     * Caution: Month starts from 0 - January
     * @param list list of calls to filter from
     * @param cal Calendar with a year, month and day to get calls for, it 
     * should have specified YEAR, MONTH, DAY annd HOUR
     * @return list of calls
     */
    
    public static List<Message> getByMinute(List<Message> list, Calendar cal) {
        List<Message> out = new ArrayList();
        for(Message call : list) {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd'T'hh:mm");
            if(fmt.format(cal.getTime())
                    .equals(fmt.format(call.getDateTime().getTime()))) {
                out.add(call);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;
    
    }
    
        /**
     * Get calls by specified Year, Month, Day and hour in the year from all calls
     * Caution: Month starts from 0 - January
     * @param cal Calendar with a year, month and day to get calls for, it 
     * should have specified YEAR, MONTH, DAY and HOUR
     * @return list of calls
     */
    public static List<Message> getByHour(Calendar cal) {
        return getByHour(getAll(), cal);
    }
    
    /**
     * Get calls by specified Year, Month, Day and Hour in the year from given 
     * list of calls
     * Caution: Month starts from 0 - January
     * @param list list of calls to filter from
     * @param cal Calendar with a year, month and day to get calls for, it 
     * should have specified YEAR, MONTH, DAY annd HOUR
     * @return list of calls
     */
    
    public static List<Message> getByHour(List<Message> list, Calendar cal) {
        List<Message> out = new ArrayList();
        for(Message call : list) {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd'T'hh");
            if(fmt.format(cal.getTime())
                    .equals(fmt.format(call.getDateTime().getTime()))) {
                out.add(call);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;
    
    }
    
    /**
     * Return messages with specific date from all stored messages
     * @param date of the message
     * @return List of messages
     */
    public static List<Message> getByDate(Calendar date) {
        return getByDate(getAll(), date);
    }
    
    /**
     * Return messages with specific date from given messages
     * @param list list of messages to filter from
     * @param date of the message
     * @return List of messages
     */
    public static List<Message> getByDate(List<Message> list, Calendar date) {
        List<Message> out = new ArrayList<>();
        for(Message message : list) {
            if(message.getDateTime().equals(date)) {
                out.add(message);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;        
    }
    
    public static List<Message> getByPrefix(List<Country> countries) {
        
        if (countries == null) {
            return null;
        }
        
        List<Message> out = new ArrayList<>();
        
        for(Message message : getAll()) {
            if(countries.contains(message.getDestination())) {
                out.add(message);
            }
        }
        return out;
            
    }
    
    /**
     * Get messages by specified Year, Month and Day in the year from all 
     * messages
     * Caution: Month starts from 0 - January
     * @param cal Calendar with a year, month and day to get messages for, it 
     * should have specified YEAR, MONTH and DAY
     * @return list of messages
     */
    public static List<Message> getByDay(Calendar cal) {
        return getByDay(getAll(), cal);
    }
    
    /**
     * Get messages by specified Year, Month and Day in the year from given 
     * list of messages
     * Caution: Month starts from 0 - January
     * @param list list of messages to filter from
     * @param cal Calendar with a year, month and day to get messages for, it 
     * should have specified YEAR, MONTH and DAY
     * @return list of messages
     */
    public static List<Message> getByDay(List<Message> list, Calendar cal) {
        List<Message> out = new ArrayList();
        for(Message message : list) {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
            if(fmt.format(cal.getTime())
                    .equals(fmt.format(message.getDateTime().getTime()))) {
                out.add(message);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;
    
    }
    
    /**
     * Get messages by specified Year and Month in the year from all messages
     * Caution: Month starts from 0 - January
     * @param cal Calendar with a year and month to get messages for, it should 
     * have specified YEAR and MONTH
     * @return list of messages
     */
    public static List<Message> getByMonth(Calendar cal) {
        return getByMonth(getAll(), cal);
    }
    
    /**
     * Get messages by specified Year and Month in the year from given list of 
     * messages
     * Caution: Month starts from 0 - January
     * @param list list of messages to filter from
     * @param cal Calendar with a year and month to get messages for, it should 
     * have specified YEAR and MONTH
     * @return list of messages
     */
    public static List<Message> getByMonth(List<Message> list, Calendar cal) {
        List<Message> out = new ArrayList();
        for(Message message : list) {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMM");
            if(fmt.format(cal.getTime())
                    .equals(fmt.format(message.getDateTime().getTime()))) {
                out.add(message);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;
    }
    /**
     * Get messages by specified year from all messages
     * @param cal Calendar with a year to get messages for, it should have 
     * specified YEAR
     * @return list of message
     */
    public static List<Message> getByYear(Calendar cal) {
        return getByYear(getAll(), cal);
    }
    
    /**
     * Get messages by specified year from given list of messages
     * @param list list of messages to filter from
     * @param cal Calendar with a year to get messages for, it should have 
     * specified YEAR
     * @return List of messages
     */
    public static List<Message> getByYear(List<Message> list, Calendar cal) {
        List<Message> out = new ArrayList();
        for(Message message : list) {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy");
            if(fmt.format(cal.getTime())
                    .equals(fmt.format(message.getDateTime().getTime()))) {
                out.add(message);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;
    }
}
