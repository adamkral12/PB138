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
                System.out.println("Getting date and time for the call "
                        + "failed.");
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
     * Returns call by given id from all stored calls
     * @param id of a call
     * @return call with id id
     */
    public static Call getById(int id) {
        return getById(getAll(), id);
    }
    
    /**
     * Returns call by given id from given set of calls
     * @param list list of calls to get call from
     * @param id of a call
     * @return call with id id
     */
    public static Call getById(List<Call> list, int id) {
        for(Call call : list) {
            if(call.getId() == id) {
                return call;
            }
        }
        return null;
    }
    
    /**
     * Returns call(s) by given note from given set of calls
     * @param note of the calls
     * @return List of calls with given note (substring)
     */
    public static List<Call> getByNote(String note) {
        return getByNote(getAll(), note);
    }
    
        /**
     * Returns call(s) by given note from given set of calls
     * @param list list of calls to get call from
     * @param note of a call
     * @return List<Call> list of calls with note containing substring
     */
    public static List<Call> getByNote(List<Call> list, String note) {
        List<Call> out = new ArrayList<Call>();
        for(Call call : list) {
            if(call.getNote().toLowerCase().contains(note.toLowerCase())) {
                out.add(call);
            }
        }
        return out;
    }
    
    
    /**
     * Returns call(s) by given length from given set of calls
     * @param length of the calls
     * @return List of calls with given length (substring)
     */
    public static List<Call> getByLength(String length) {
        return getByLength(getAll(), length);
    }
    
        /**
     * Returns call(s) by given note from given set of calls
     * @param list list of calls to get call from
     * @param note of a call
     * @return List<Call> list of calls with note containing substring
     */
    public static List<Call> getByLength(List<Call> list, String length) {
        List<Call> out = new ArrayList<Call>();
        for(Call call : list) {
            String lengthString = String.valueOf(call.getLength());
            if(lengthString.toLowerCase().contains(length.toLowerCase())) {
                out.add(call);
            }
        }
        return out;
    }    
    
    /**
     * Returns list of calls with specific callee from all stored calls
     * @param callee of the calls
     * @return List of calls with given callee
     */
    public static List<Call> getByCallee(String callee) {
        return getByCallee(getAll(), callee);
    }
    
    /**
     * Returns list of calls with specific callee for given list of calls
     * @param list list of calls to filter from
     * @param callee of the calls
     * @return List of calls with given callee
     */
    public static List<Call> getByCallee(List<Call> list, String callee) {
        List<Call> out = new ArrayList();
        for(Call call : list) {
            System.out.println("Get by callee, callee = " + call.getCallee() + ", given string = " + callee);
            if(call.getCallee().toLowerCase().contains(callee.toLowerCase())) {
                out.add(call);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;
    }
    
    /**
     * Returns calls by given destination from all calls
     * @param country of the call
     * @return List of calls with specific country
     */
    public static List<Call> getByDestination(Country country) {
        return getByDestination(getAll(), country);
    }
    
    /**
     * Returns calls by given destination from given list of calls
     * @param list list of calls to filter from
     * @param country of the call
     * @return List of calls with specific country
     */
    public static List<Call> getByDestination(List<Call> list
            , Country country) {
        List<Call> out = new ArrayList();
        for(Call call : list) {
            if(call.getDestination().equals(country)) {
                out.add(call);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;
    }
    
    public static List<Call> getByDestination(List<Country> countries) {
        List<Call> out = new ArrayList();
        for(Call call : getAll()) {
            if(countries.contains(call.getDestination())) {
                out.add(call);
            }
        }
        return out;
    }
    
    /**
     * Returns calls by given direction from all calls
     * @param direction of the calls
     * @return List of calls
     */
    public static List<Call> getByDirection(Direction direction) {
        return getByDirection(getAll(), direction);
    }
    
    /**
     * Returns calls by given direction from given list of calls
     * @param list list of calls to filter from
     * @param direction of the calls
     * @return List of calls
     */
    public static List<Call> getByDirection(List<Call> list
            , Direction direction) {
        List<Call> out = new ArrayList();
        for(Call call : list) {
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
     * Returns calls with specific date from all calls
     * @param date of the call, must be the same as in call
     * @return List of calls
     */
    public static List<Call> getByDate(Calendar date) {
        return getByDate(getAll(), date);
    }
    
    /**
     * Returns calls with specific date from given list of calls
     * @param list list of calls to filter from
     * @param date of the call, must be the same as in call
     * @return List of calls
     */
    public static List<Call> getByDate(List<Call> list, Calendar date) {
        List<Call> out = new ArrayList();
        for(Call call : list) {
            if(call.getDateTime().equals(date)) {
                out.add(call);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;
    }
    
    public static List<Call> getByPrefix(List<Country> countries) {
        List<Call> out = new ArrayList<>();
        for(Call call : getAll()) {
            if(countries.contains(call.getDestination())) {
                out.add(call);
            }
        }
        return out;
            
    }
    
    /**
     * Get calls by specified Year, Month and Day in the year from all calls
     * Caution: Month starts from 0 - January
     * @param cal Calendar with a year, month and day to get calls for, it 
     * should have specified YEAR, MONTH and DAY
     * @return list of calls
     */
    public static List<Call> getByDay(Calendar cal) {
        return getByDay(getAll(), cal);
    }
    
    /**
     * Get calls by specified Year, Month and Day in the year from given 
     * list of calls
     * Caution: Month starts from 0 - January
     * @param list list of calls to filter from
     * @param cal Calendar with a year, month and day to get calls for, it 
     * should have specified YEAR, MONTH and DAY
     * @return list of calls
     */
    
    public static List<Call> getByDay(List<Call> list, Calendar cal) {
        List<Call> out = new ArrayList();
        for(Call call : list) {
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
    
    /**
     * Get calls by specified Year and Month in the year from all calls
     * Caution: Month starts from 0 - January
     * @param cal Calendar with a year and month to get calls for, it should 
     * have specified YEAR and MONTH
     * @return list of calls
     */
    
    public static List<Call> getByMonth(Calendar cal) {
        return getByMonth(getAll(), cal);
    }
    
    /**
     * Get calls by specified Year and Month in the year from given list of 
     * calls
     * Caution: Month starts from 0 - January
     * @param list list of calls to filter from
     * @param cal Calendar with a year and month to get calls for, it should 
     * have specified YEAR and MONTH
     * @return list of calls
     */
    
    public static List<Call> getByMonth(List<Call> list, Calendar cal) {
        List<Call> out = new ArrayList();
        for(Call call : list) {
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
    
    /**
     * Get calls by specified year from all calls
     * @param cal Calendar with a year to get calls for, it should have 
     * specified YEAR
     * @return list of calls
     */
    
    public static List<Call> getByYear(Calendar cal) {
        return getByYear(getAll(), cal);
    }
    
    /**
     * Get calls by specified year from given list of calls
     * @param list list of calls to filter from
     * @param cal Calendar with a year to get calls for, it should have 
     * specified YEAR
     * @return list of calls
     */
    
    public static List<Call> getByYear(List<Call> list, Calendar cal) {
        List<Call> out = new ArrayList();
        for(Call call : list) {
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
