/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import java.util.List;
import core.Region;
import java.util.ArrayList;

/**
 * Manages regions
 * Parses them from XML file,stores them in field regions, retrieves them by
 * parameters
 * @author marek
 */
public class RegionManager {
    private final static String HOME_REGION = "EU";
    private static List<Region> regions;
    /**
     * Parses XML document containing regions.
     * @return List of all regions from XML in order they are stored, null if 
     * error happened
     */
    private static List<Region> parseRegionsFromXML() {
        
        return null;
    }
    
    /**
     * Reloads regions from XML file and stores them in regions variable.
     * @return True - regions were successfully parsed and saved, False - else
     */
    public static boolean reloadRegions() {
        List<Region> parsedRegions = parseRegionsFromXML();
        if(parsedRegions != null) {
            RegionManager.regions = new ArrayList<>(parsedRegions);
            return true;
        } else {
            return false;
        }
        
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
