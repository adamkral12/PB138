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
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
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
    
    
    
    public static void testing() {
        
        System.out.println("Loading data");
        LoadDataManager loadDataManager = LoadDataManager.getInstance();
        System.out.println("---TEST START--");
        //true - run test, false - dont ; in if statement
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
        if(false) {
            System.out.println("SORTING TESTS");
            // <editor-fold defaultstate="collapsed" desc="TEST : Sorting calls">
            if(true) {

                System.out.println("Sort calls by id");

                List<Call> unsortedCalls = CallManager.getAll();
                Collections.shuffle(unsortedCalls);

                for (Call call : SortManager.sortCalls(unsortedCalls, SortParameter.ID)) {
                    System.out.println(call);
                }

                System.out.println("Sort callsby id, reversed order");
                Collections.shuffle(unsortedCalls);

                for (Call call : SortManager.sortCallsReversed(unsortedCalls
                        , SortParameter.ID)) {
                    System.out.println(call);
                }

                System.out.println("Sort calls by Length");
                Collections.shuffle(unsortedCalls);

                for (Call call : SortManager.sortCalls(unsortedCalls
                        , SortParameter.LENGTH)) {
                    System.out.println(call);
                }

                System.out.println("Sort call by Length, reversed order");

                Collections.shuffle(unsortedCalls);

                for (Call call : SortManager.sortCallsReversed(unsortedCalls
                        , SortParameter.LENGTH)) {
                    System.out.println(call);
                }

                System.out.println("Sort calls by Callee");
                Collections.shuffle(unsortedCalls);

                for (Call call : SortManager.sortCalls(unsortedCalls
                        , SortParameter.CALLEE)) {
                    System.out.println(call);
                }

                System.out.println("Sort call by Callee, reversed order");

                Collections.shuffle(unsortedCalls);

                for (Call call : SortManager.sortCallsReversed(unsortedCalls
                        , SortParameter.CALLEE)) {
                    System.out.println(call);
                }

                System.out.println("Sort calls by Destination");
                Collections.shuffle(unsortedCalls);

                for (Call call : SortManager.sortCalls(unsortedCalls
                        , SortParameter.DEST)) {
                    System.out.println(call);
                }

                System.out.println("Sort call by Destination, reversed order");

                Collections.shuffle(unsortedCalls);

                for (Call call : SortManager.sortCallsReversed(unsortedCalls
                        , SortParameter.DEST)) {
                    System.out.println(call);
                }

                System.out.println("Sort calls by Region");
                Collections.shuffle(unsortedCalls);

                for (Call call : SortManager.sortCalls(unsortedCalls
                        , SortParameter.REG)) {
                    System.out.println(call);
                }

                System.out.println("Sort call by Region, reversed order");

                Collections.shuffle(unsortedCalls);

                for (Call call : SortManager.sortCallsReversed(unsortedCalls
                        , SortParameter.REG)) {
                    System.out.println(call);
                }

                System.out.println("Sort calls by Direction, IN first");
                Collections.shuffle(unsortedCalls);

                for (Call call : SortManager.sortCalls(unsortedCalls
                        , SortParameter.DIR)) {
                    System.out.println(call);
                }

                System.out.println("Sort call by Direction, reversed order, "
                        + "OUT first");

                Collections.shuffle(unsortedCalls);

                for (Call call : SortManager.sortCallsReversed(unsortedCalls
                        , SortParameter.DIR)) {
                    System.out.println(call);
                }

                System.out.println("Sort calls by Date");
                Collections.shuffle(unsortedCalls);

                for (Call call : SortManager.sortCalls(unsortedCalls
                        , SortParameter.DATE)) {
                    System.out.println(call);
                }

                System.out.println("Sort call by Date, reversed order");

                Collections.shuffle(unsortedCalls);

                for (Call call : SortManager.sortCallsReversed(unsortedCalls
                        , SortParameter.DATE)) {
                    System.out.println(call);
                }
            
            }
            // </editor-fold>
            
            
            // <editor-fold defaultstate="collapsed" desc="TEST : Sorting calls">
            if(false) {
                
                System.out.println("Sort messages by id");

                List<Message> unsortedMessages = MessageManager.getAll();
                Collections.shuffle(unsortedMessages);

                for (Message message : SortManager.sortMessages(unsortedMessages, SortParameter.ID)) {
                    System.out.println(message);
                }

                System.out.println("Sort messages by id, reversed order");
                Collections.shuffle(unsortedMessages);

                for (Message message : SortManager.sortMessagesReversed(unsortedMessages
                        , SortParameter.ID)) {
                    System.out.println(message);
                }

                System.out.println("Sort messages by Length");
                Collections.shuffle(unsortedMessages);

                for (Message message : SortManager.sortMessagesReversed(unsortedMessages
                        , SortParameter.LENGTH)) {
                    System.out.println(message);
                }

                System.out.println("Sort messages by Length, reversed order");

                Collections.shuffle(unsortedMessages);

                for (Message message : SortManager.sortMessages(unsortedMessages
                        , SortParameter.LENGTH)) {
                    System.out.println(message);
                }

                System.out.println("Sort messages by Callee");
                Collections.shuffle(unsortedMessages);

                for (Message message : SortManager.sortMessages(unsortedMessages
                        , SortParameter.CALLEE)) {
                    System.out.println(message);
                }

                System.out.println("Sort message by Callee, reversed order");

                Collections.shuffle(unsortedMessages);

                for (Message message : SortManager.sortMessagesReversed(unsortedMessages
                        , SortParameter.CALLEE)) {
                    System.out.println(message);
                }

                System.out.println("Sort message by Destination");
                Collections.shuffle(unsortedMessages);

                for (Message message : SortManager.sortMessages(unsortedMessages
                        , SortParameter.DEST)) {
                    System.out.println(message);
                }

                System.out.println("Sort message by Destination, reversed order");

                Collections.shuffle(unsortedMessages);

                for (Message message : SortManager.sortMessagesReversed(unsortedMessages
                        , SortParameter.DEST)) {
                    System.out.println(message);
                }

                System.out.println("Sort message by Region");
                Collections.shuffle(unsortedMessages);

                for (Message message : SortManager.sortMessages(unsortedMessages
                        , SortParameter.REG)) {
                    System.out.println(message);
                }

                System.out.println("Sort message by Region, reversed order");

                Collections.shuffle(unsortedMessages);

                for (Message message : SortManager.sortMessagesReversed(unsortedMessages
                        , SortParameter.REG)) {
                    System.out.println(message);
                }

                System.out.println("Sort message by Direction, IN first");
                Collections.shuffle(unsortedMessages);

                for (Message message : SortManager.sortMessages(unsortedMessages
                        , SortParameter.DIR)) {
                    System.out.println(message);
                }

                System.out.println("Sort message by Direction, reversed order, "
                        + "OUT first");

                Collections.shuffle(unsortedMessages);

                for (Message message : SortManager.sortMessagesReversed(unsortedMessages
                        , SortParameter.DIR)) {
                    System.out.println(message);
                }

                System.out.println("Sort message by Date");
                Collections.shuffle(unsortedMessages);

                for (Message message : SortManager.sortMessages(unsortedMessages
                        , SortParameter.DATE)) {
                    System.out.println(message);
                }

                System.out.println("Sort message by Date, reversed order");

                Collections.shuffle(unsortedMessages);

                for (Message message : SortManager.sortMessagesReversed(unsortedMessages
                        , SortParameter.DATE)) {
                    System.out.println(message);
                }
            }
            // </editor-fold>
        
        }
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="TEST : get by day,month,year">
        if(true) {
            System.out.println("Get by date: 2017-05-01");
            System.out.println("Calls");
            Calendar cal = Calendar.getInstance();
            //POZOR: mesiace sa cisluju od 0
            cal.set(2017,4,1);
            
            
            System.out.println("Get by day");
            
            for(Call call : CallManager.getByDay(cal)) {
                System.out.println(call);
            }
            System.out.println("Get by month");
            for(Call call : CallManager.getByMonth(cal)) {
                System.out.println(call);
            }
            System.out.println("Get by year");
            for(Call call : CallManager.getByYear(cal)) {
                System.out.println(call);
            }
            System.out.println("Messages");
            
            
            System.out.println("Get by day");
            
            for(Message message : MessageManager.getByDay(cal)) {
                System.out.println(message);
            }
            System.out.println("Get by month");
            for(Message message : MessageManager.getByMonth(cal)) {
                System.out.println(message);
            }
            System.out.println("Get by year");
            for(Message message : MessageManager.getByYear(cal)) {
                System.out.println(message);
            }
            
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="TEST : Filter tests">
        if(false) {
            System.out.println("Filter tests");
            
            if(true) {
                System.out.println("All calls");
                List<Call> list = CallManager.getAll();
             /*   for(Call call : list) {
                    System.out.println(call);
                }
                System.out.println("Filter calls by OUTcoming");

                list = CallManager.getByDirection(list, Direction.OUT);
                for(Call call : list) {
                    System.out.println(call);
                }
               */
           /*     System.out.println("Filter calls by callee");
              //  list = CallManager.getAll();
                list = CallManager.getByCallee(list, "+ 420");
                if (null == list) {
                    System.out.println("no calls");
                }
                for(Call call : list) {
                    System.out.println(call);
                }
*/
                System.out.println("than by destination Mexico");
                list = CallManager.getByDestination(list, CountryManager.getById("MEX"));
                for(Call call : list) {
                    System.out.println(call);
                }
            }
            if(true) {
                System.out.println("All messages");

                List<Message> list = MessageManager.getAll();
                for(Message message : list) {
                    System.out.println(message);
                }
                
                System.out.println("Filter by date 29.4.2017");
                Calendar cal = Calendar.getInstance();
                cal.set(2017, 3, 29);
                list = MessageManager.getByDay(list, cal);
                for(Message message : list) {
                    System.out.println(message);
                }
                
                System.out.println("then by country Czech republic");
                list = MessageManager.getByDestination(list, CountryManager.getById("CZE"));
                for(Message message : list) {
                    System.out.println(message);
                }
            }
        }
        // </editor-fold>

        System.out.println("---TEST END---");
    }
    
}
