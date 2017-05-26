/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonecallmanager;

import core.Call;
import core.Country;
import core.Direction;
import core.Message;
import core.Region;
import core.SortParameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import managers.CallManager;
import managers.CountryManager;
import managers.LoadDataManager;
import managers.MessageManager;
import managers.PriceManager;
import managers.RegionManager;
import managers.SortManager;

/**
 *
 * @author marek
 */
public class Testing {
    
    private static void 
    
    public static void testing() {
        
        System.out.println("Loading data");
        LoadDataManager.loadData();
        System.out.println("---TEST START--");
        
        // <editor-fold defaultstate="collapsed" desc="TEST: List all objects, do basic stuff if them">
        if(false) {
            System.out.println("TEST: List all objects, do basic stuff if them");
            
            
            System.out.println("Regions:");

            //RegionManager rm = new RegionManager();
            //RegionManager.reloadRegions();

            for (Region region : RegionManager.getAll()) {
                System.out.println(region);
            }
            System.out.println("id = NA");
            System.out.println(RegionManager.getById("NA"));
            System.out.println("Home region:");
            System.out.println(RegionManager.getHomeRegion());

            System.out.println("Countries");
            //CountryManager cm = new CountryManager();
            //CountryManager.reloadCountries();

            for (Country country : CountryManager.getAll()) {
                System.out.println(country);
            }
            System.out.println("region AS");

            for (Country country : CountryManager.getByRegion(RegionManager.getById("AS"))) {
                System.out.println(country);
            }
            System.out.println("prefix 421");

            System.out.println(CountryManager.getByPrefix("+421").get(0));

            System.out.println("Calls:");
            //CallManager cam = new CallManager();
            //CallManager.reloadCalls();

            for (Call call : CallManager.getAll()) {
                System.out.println(call);
            }
            System.out.println("id = 1");
            System.out.println(CallManager.getById(1));

            System.out.println("IN");
            for (Call call : CallManager.getByDirection(Direction.IN)) {
                System.out.println(call);
            }

            System.out.println("callee 905678678");
            for (Call call : CallManager.getByCallee("905678678")) {
                System.out.println(call);
            }

            System.out.println("destination MEX");
            for (Call call : CallManager.getByDestination(CountryManager.getById("MEX"))) {
                System.out.println(call);
            }

            System.out.println("Messages:");
            //MessageManager mm = new MessageManager();
            //MessageManager.reloadMessages();

            for (Message message : MessageManager.getAll()) {
                System.out.println(message);
            }
            System.out.println("id = 2");
            System.out.println(MessageManager.getById(2));

            System.out.println("OUT");
            for (Message message : MessageManager.getByDirection(Direction.OUT)) {
                System.out.println(message);
            }

            System.out.println("callee 905678678");
            for (Message message : MessageManager.getByCallee("905678678")) {
                System.out.println(message);
            }

            System.out.println("destination MEX");
            for (Message message : MessageManager.getByDestination(CountryManager.getById("MEX"))) {
                System.out.println(message);
            }

        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="TEST : Price tests">
        if(false){
            System.out.println("Testing prices");

            Double[] callPrices = {0.0, 2.0, 0.0, 0.0, 1.0};

            for(Call call : CallManager.getAll()) {
                System.out.println("Price of call " + call.getId() + " : "
                        + PriceManager.getPrice(call)+ 
                        ", should  be " + callPrices[call.getId()]);
            }

            Double[] messagePrices = {0.0, 0.0, 2.0, 1.0, 1.0, 2.0};

            for(Message message : MessageManager.getAll()) {
                System.out.println("Price of message " + message.getId() + " : "
                        + PriceManager.getPrice(message)+ 
                        ", should  be " + messagePrices[message.getId()]);
            }
        }
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="TEST : Sort tests">
        if(true) {
            System.out.println("SORTING TESTS");

            System.out.println("Sort calls by id");

            List<Call> unsortedCalls = new ArrayList<>();
            unsortedCalls.add(CallManager.getById(3));
            unsortedCalls.add(CallManager.getById(2));
            unsortedCalls.add(CallManager.getById(4));
            unsortedCalls.add(CallManager.getById(1));

            for (Call call : SortManager.sort(unsortedCalls, SortParameter.ID)) {
                System.out.println(call);
            }

            System.out.println("Sort callsby id, reversed order");
            Collections.shuffle(unsortedCalls);

            for (Call call : SortManager.sortReversed(unsortedCalls
                    , SortParameter.ID)) {
                System.out.println(call);
            }
            
            System.out.println("Sort calls by Length");
            Collections.shuffle(unsortedCalls);

            for (Call call : SortManager.sortReversed(unsortedCalls
                    , SortParameter.LENGTH)) {
                System.out.println(call);
            }
        
        }
        // </editor-fold>
        
        System.out.println("---TEST END---");
    }
    
}
