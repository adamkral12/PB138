/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import core.Country;
import core.Region;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
 * Manages countries
 * Parses them from XML file,stores them in field countries, retrieves them by
 * parameters
 * @author xkral3, xvalko, xmikova
 */
public class CountryManager {
    private final static String HOME_COUNTRY = "CZE";
    private static List<Country> countries;
    
    private static List<Country> parseCountriesFromXML() 
            throws XPathExpressionException {
        File countriesXML = new File("countries.xml");
        
        Document doc = FileManager.getDocument(countriesXML);
        
        XPath xPath = XPathFactory.newInstance().newXPath();
        
        NodeList pCountries = (NodeList)xPath.evaluate("/countries/country",
                    doc.getDocumentElement(),XPathConstants.NODESET);
        List<Country> outCountries = new ArrayList<>();
        
        for(int i = 0; i < pCountries.getLength(); i++) {
            Element e = (Element) pCountries.item(i);
            
            String cid = e.getAttribute("cid");
            
            String name = (String) xPath.evaluate("name/text()", e, 
                    XPathConstants.STRING);
            Region region = new RegionManager().getById((String) 
                    xPath.evaluate("region/text()", e, 
                    XPathConstants.STRING));
            String prefix = (String) xPath.evaluate("prefix/text()", e, 
                    XPathConstants.STRING);
            
            Country country = new Country(cid, name, region, prefix);
            
            
            outCountries.add(country);
        }
        return outCountries;
    }
        
    public static boolean reloadCountries() {
        try {
            List<Country> parsedCountries = parseCountriesFromXML();
            if(parsedCountries != null) {
                CountryManager.countries = new ArrayList<>(parsedCountries);
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
    
    public List<Country> getAll() {
        return new ArrayList<>(countries);
    }
    
    public Country getById(String id) {
        for (Country country : countries) {
            if (country.getId().equals(id)) {
                return country;
            }
        }
        return null;
    }
    
    public List<Country> getByRegion(Region region) {
        List<Country> out = new ArrayList<>();
        for (Country country : countries) {
            if(country.getRegion().equals(region)) {
                out.add(country);
            }
        }
        return out;
    }
    
    public List<Country> getByPrefix(String prefix) {
        List<Country> out = new ArrayList<>();
        for (Country country : countries) {
            if(country.getPrefix().equals(prefix)) {
                out.add(country);
            }
        }
        return out;
    }
    
    public Country getHomeCountry() {
        return getById(HOME_COUNTRY);
    }
    
    
}
