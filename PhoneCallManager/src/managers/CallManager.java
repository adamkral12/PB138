/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import core.Call;
import core.Country;
import core.Direction;
import java.util.List;
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
 * Manages calls
 * Parses them from XML file,stores them in field calls, retrieves them by
 * parameters
 * @author xkral3, xvalko, xmikova
 */
public class CallManager {
    private static List<Call> calls;
    
    /**
     * Parses XML document containing calls.
     * @return List of all calls from XML in order they are stored, null if 
     * error happened
     */
    private static List<Call> parseCallsFromXML() 
            throws XPathExpressionException {
        File callsXML = new File("calls.xml");
        
        Document doc = FileManager.getDocument(callsXML);
        if(doc == null) {
            return null;
        }
        XPath xPath = XPathFactory.newInstance().newXPath();
        
        NodeList pCalls = (NodeList)xPath.evaluate("/calls/call",
                    doc.getDocumentElement(),XPathConstants.NODESET);
        List<Call> outCalls = new ArrayList<>();
        
        for(int i = 0; i < pCalls.getLength(); i++) {
            Element e = (Element) pCalls.item(i);
            
            int cid = Integer.parseInt(e.getAttribute("cid"));
            
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
                System.out.println("Getting date and time for the call failed.");
            }

            Call call = new Call(cid, length, callee, destination, direction,
                                cal, note);
            
            
            outCalls.add(call);
        }
        
        if (outCalls.isEmpty()) {
            return null;
        }
        return outCalls;
    }
    
    /**
     * Reloads calls from XML file and stores them in calls variable.
     * @return True - calls were successfully parsed and saved, False - else
     */
    public static boolean reloadCalls() {
        try {
            List<Call> parsedCalls = parseCallsFromXML();
            if(parsedCalls != null) {
                CallManager.calls = new ArrayList<>(parsedCalls);
                return true;
            } else {
                return false;
            }
        } catch (XPathExpressionException ex) {
            Logger.getLogger(CallManager.class.getName()).log(Level.SEVERE, 
                    null, ex);
        }
        return false;
    }
    
    /**
     * Returns all calls
     * @return List of all calls
     */
    public static List<Call> getAll() {
        return new ArrayList<>(calls);
    }
    
    /**
     * Returns call by given id
     * @param id of a call
     * @return call with id id
     */
    public static Call getById(int id) {
        for(Call call : getAll()) {
            if(call.getId() == id) {
                return call;
            }
        }
        return null;
    }
    
    /**
     * Returns list of calls with specific callee
     * @param callee of the calls
     * @return List of calls with given callee
     */
    public static List<Call> getByCallee(String callee) {
        List<Call> out = new ArrayList();
        for(Call call : getAll()) {
            if(call.getCallee().equals(callee)) {
                out.add(call);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;
    }
    
    /**
     * Returns calls by given destination
     * @param country of the call
     * @return List of calls with specific country
     */
    public static List<Call> getByDestination(Country country) {
        List<Call> out = new ArrayList();
        for(Call call : getAll()) {
            if(call.getDestination().equals(country)) {
                out.add(call);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;
    }
    
    /**
     * Returns calls by given direction
     * @param direction of the calls
     * @return List of calls
     */
    public static List<Call> getByDirection(Direction direction) {
        List<Call> out = new ArrayList();
        for(Call call : getAll()) {
            if(call.getDirection() == direction) {
                out.add(call);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;
    }
    
    /**
     * Returns calls with specific date
     * @param date of the call, must be the same as in call
     * @return List of calls
     */
    public static List<Call> getByDate(Calendar date) {
        List<Call> out = new ArrayList();
        for(Call call : getAll()) {
            if(call.getDateTime().equals(date)) {
                out.add(call);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;
    }
    
    public static List<Call> getByDay(Calendar cal) {
        List<Call> out = new ArrayList();
        for(Call call : getAll()) {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
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
    
    public static List<Call> getByMonth(Calendar cal) {
        List<Call> out = new ArrayList();
        for(Call call : getAll()) {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMM");
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
    
    public static List<Call> getByYear(Calendar cal) {
        List<Call> out = new ArrayList();
        for(Call call : getAll()) {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy");
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
    
    
    
    
}
