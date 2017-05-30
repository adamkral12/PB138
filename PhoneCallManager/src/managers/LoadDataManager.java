/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

/**
 * Singleton, that loads data from XML files, returns all managers
 * @author xkral3, xvalko, xmikova
 */
public class LoadDataManager {
    
    private static LoadDataManager instance;
    private final RegionManager regionManager;
    private final CountryManager countryManager;
    private final CallManager callManager;
    private final MessageManager messageManager;
    
    private LoadDataManager() {
        regionManager = new RegionManager();
        countryManager = new CountryManager();
        callManager = new CallManager();
        messageManager = new MessageManager();
        this.loadData();
    }
    /**
     * Singleton help method
     * @return if the class is already initiated, the instance, otherwise just 
     *         returns the instance
     */
    public static LoadDataManager getInstance() {
        if (instance == null) {
            instance = new LoadDataManager();
        }
        return instance;
    }
    /**
     * Fills data from XML to classes
     */
    private void loadData() {
        regionManager.reloadRegions();
        countryManager.reloadCountries();
        callManager.reloadCalls();
        messageManager.reloadMessages();
    }
    
    public RegionManager getRegionManager() {
        return regionManager;
    }
    
    public CountryManager getCountryManager() {
        return countryManager;
    }
    
    public CallManager getCallManager() {
        return callManager;
    }
    
    public MessageManager getMessageManager() {
        return messageManager;
    }
}
