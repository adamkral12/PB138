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
 * Manages calls
 * Parses them from XML file,stores them in field calls, retrieves them by
 * parameters
 * @author xkral3, xvalko, xmikova
 */
public class CallManager {
    private static List<Call> calls;
    
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
            String[] parsedDateAndTime = pDate.split("T");//T\\d{2}:\\d{2}:\\d{2}$");
            String[] parsedDate = parsedDateAndTime[0].split("-");
            String[] parsedTime = parsedDateAndTime[1].split(":");
            
            Date date = new Date(Integer.parseInt(parsedDate[0])-1900,
                                Integer.parseInt(parsedDate[1])-1,
                                Integer.parseInt(parsedDate[2]),
                                Integer.parseInt(parsedTime[0]),
                                Integer.parseInt(parsedTime[1]),
                                Integer.parseInt(parsedTime[2]));

            Call call = new Call(cid, length, callee, destination, direction,
                                date, note);
            
            
            outCalls.add(call);
        }
        
        if (outCalls.isEmpty()) {
            return null;
        }
        return outCalls;
    }
    
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
    
    public List<Call> getAll() {
        return new ArrayList<>(calls);
    }
    
    public Call getById(int id) {
        for(Call call : getAll()) {
            if(call.getId() == id) {
                return call;
            }
        }
        return null;
    }
    
    public List<Call> getByCallee(String callee) {
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
    
    public List<Call> getByDestination(Country country) {
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
    
    public List<Call> getByDirection(Direction direction) {
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
    
    public List<Call> getByDate(Date date) {
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
    
    
}
