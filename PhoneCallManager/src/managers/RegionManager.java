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
 * Parses them from XML file, retrieves them by parameters
 * @author marek
 */
public class RegionManager {
    
    private static List<Region> regions;
    /**
     * Parses XML document containing regions.
     * @return List of all regions from XML in order they are stored, null if error happened
     */
    private static List<Region> parseRegionsFromXML() {
        return null;
    }
    
    /**
     * Reloads regions from Xml file and stores them in regions variable.
     * @return True - regions were successfully parsed and saved, False - else
     */
    public static boolean reloadRegions() {
        RegionManager.regions = parseRegionsFromXML();
        return regions != null;
        
    }
    
    public List<Region> getAll() {
        return new ArrayList<>(RegionManager.regions);
    }
    
    public Region getById(int id) {
        
        for (Region region : getAll()) {
            if(region.getId() == id) {
                return region;
            }
        }
        return null;
    }
    
    public Region getByName(String name) {
        for (Region region : getAll()) {
            if(region.getName().equals(name)) {
                return region;
            }
        }
        return null;
    }
    
    public Region getBy
    
    
}
