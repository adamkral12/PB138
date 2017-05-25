/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

/**
 *
 * @author pmikova
 */
public class LoadDataManager {
    public static void loadData() {
        RegionManager.reloadRegions();
        CountryManager.reloadCountries();
        CallManager.reloadCalls();
        MessageManager.reloadMessages();
    }
}
