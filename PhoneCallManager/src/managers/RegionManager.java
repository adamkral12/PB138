/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import java.util.List;
import core.Region;
import java.io.File;
import java.util.ArrayList;
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
 * Manages regions
 * Parses them from XML file,stores them in field regions, retrieves them by
 * parameters
 * @author xkral3, xvalko, xmikova
 */
public class RegionManager {
    private final static String HOME_REGION = "EU";
    private static List<Region> regions;
    
    /**
     * Parses XML document containing regions.
     * @return List of all regions from XML in order they are stored, null if 
     * error happened
     */
    private static List<Region> parseRegionsFromXML() 
            throws XPathExpressionException {
        File regionsXML = new File("regions.xml");
        
        Document doc = FileManager.getDocument(regionsXML);
        
        XPath xPath = XPathFactory.newInstance().newXPath();
        
        NodeList pRegions = (NodeList)xPath.evaluate("/regions/region",
                    doc.getDocumentElement(),XPathConstants.NODESET);
        List<Region> outRegions = new ArrayList<>();
        
        for(int i = 0; i < pRegions.getLength(); i++) {
            Element e = (Element) pRegions.item(i);
            
            String rid = e.getAttribute("rid");
            String name = (String) xPath.evaluate("name/text()", e, 
                    XPathConstants.STRING);
            
            Double priceCallIncoming = Double.parseDouble((String) 
                    xPath.evaluate("prices/calls/incoming/text()",
                            e, XPathConstants.STRING));
            
            Double priceCallOutcoming = Double.parseDouble((String) 
                    xPath.evaluate("prices/calls/outcoming/text()",
                            e, XPathConstants.STRING));
            
            Double priceMessageIncoming = Double.parseDouble((String) 
                    xPath.evaluate("prices/messages/incoming/text()",
                            e, XPathConstants.STRING));
            
            Double priceMessageOutcoming = Double.parseDouble((String) 
                    xPath.evaluate("prices/messages/outcoming/text()",
                            e, XPathConstants.STRING));
            
            Region region = new Region(rid, name, priceCallIncoming,
                    priceCallOutcoming,priceMessageIncoming,
                    priceMessageOutcoming);
            
            outRegions.add(region);
        }
        
        
        return outRegions;
    }
    
    /**
     * Reloads regions from XML file and stores them in regions variable.
     * @return True - regions were successfully parsed and saved, False - else
     */
    public static boolean reloadRegions() {
        try {
            List<Region> parsedRegions = parseRegionsFromXML();
            if(parsedRegions != null) {
                RegionManager.regions = new ArrayList<>(parsedRegions);
                return true;
            } else {
                return false;
            }
        } catch (XPathExpressionException ex) {
            Logger.getLogger(RegionManager.class.getName()).log(Level.SEVERE, 
                    null, ex);
        }
        return false;
    }
    
    /**
     * Returns all regions
     * @return All regions
     */
    public List<Region> getAll() {
        return new ArrayList<>(RegionManager.regions);
    }
    
    /**
     * Returns region by its code
     * @param id - Code of a region
     * @return Region with code id, null otherwise
     */
    public Region getById(String id) {
        
        for (Region region : getAll()) {
            if(region.getId().equals(id)) {
                return region;
            }
        }
        return null;
    }
    
    /**
     * Returns region by its name
     * @param name - Name of a region
     * @return Region with name name, null otherwise
     */
    public Region getByName(String name) {
        for (Region region : getAll()) {
            if(region.getName().equals(name)) {
                return region;
            }
        }
        return null;
    }
    
    /**
     * Returns home region
     * @return home region
     */
    public Region getHomeRegion() {
        return getById(HOME_REGION);
    }
    
    
    
    
}
