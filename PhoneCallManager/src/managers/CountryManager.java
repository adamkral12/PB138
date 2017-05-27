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
    
    /**
     * Parses countries from XML files
     * @return List of all calls from XML in order they are stored, 
     * null if error happened
     * @throws XPathExpressionException from xPath
     */
    private static List<Country> parseCountriesFromXML() 
            throws XPathExpressionException {
        File countriesXML = new File("countries.xml");
        
        Document doc = FileManager.getDocument(countriesXML);
        if(doc == null) {
            return null;
        }
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
        
        if(outCountries.isEmpty()) {
            return null;
        }
        return outCountries;
    }
    
    /**
     * Reload countries from XML file and stores them in countries variable
     * @return True - countries were successfully parsed and saved, False - else
     */
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
            Logger.getLogger(CountryManager.class.getName()).log(Level.SEVERE, 
                    null, ex);
        }
        return false;
    }
    
    /**
     * Returns all countries
     * @return All countries
     */
    public static List<Country> getAll() {
        return new ArrayList<>(countries);
    }
    
    /**
     * Returns country by its code
     * @param id - Code of a country
     * @return country with code id, null otherwise
     */
    public static Country getById(String id) {
        for (Country country : countries) {
            if (country.getId().equals(id)) {
                return country;
            }
        }
        return null;
    }
    
        /**
     * Returns country by its name (or substring of name)
     * @param name - name of a country
     * @return country with code id, null otherwise
     */
    public static Country getByName(String name) {
        for (Country country : countries) {
            if (country.getId().contains(name)) {
                return country;
            }
        }
        return null;
    }
    
    /**
     * Returns countries in region
     * @param region - region
     * @return Countries under region region, null otherwise
     */
    public static List<Country> getByRegion(Region region) {
        List<Country> out = new ArrayList<>();
        for (Country country : countries) {
            if(country.getRegion().equals(region)) {
                out.add(country);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;
    }
    
    /**
     * Returns country of given prefix
     * @param prefix - prefix of country
     * @return List of countries, because USA and Canada have the same, 
     * null otherwise
     */
    public static List<Country> getByPrefix(String prefix) {
        List<Country> out = new ArrayList<>();
        for (Country country : countries) {
            if(country.getPrefix().toLowerCase().contains(prefix.toLowerCase())) {
                out.add(country);
            }
        }
        if(out.isEmpty()) {
            return null;
        }
        return out;
    }
    
    /**
     * Returns home country
     * @return home country
     */
    public static Country getHomeCountry() {
        return getById(HOME_COUNTRY);
    }
    
    
}
