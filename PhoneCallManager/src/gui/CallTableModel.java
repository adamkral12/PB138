/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import core.Call;
import core.Direction;
import managers.LoadDataManager;
import managers.CallManager;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.table.AbstractTableModel;
import javax.swing.SwingWorker;
import javax.swing.table.TableRowSorter;
import managers.PriceManager;

/**
 *
 * @author adam
 */
public class CallTableModel extends AbstractTableModel {

    private static final ResourceBundle texts = ResourceBundle.getBundle("i18n/texts");
    private static List<Call> calls = new ArrayList<>();

    public CallTableModel(CallTableModel callModel) {
        RetrieveSwingWorker retrieveSwingWorker = new RetrieveSwingWorker(callModel);
        retrieveSwingWorker.execute();
    }
    @Override
    public int getRowCount() {
        return calls.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Call call = calls.get(rowIndex);
        switch (columnIndex) {
            case 0:
                Calendar dateTime = call.getDateTime();
                return dateTime.get(Calendar.YEAR) + "-" + 
                        new SimpleDateFormat("MM").format(dateTime.getTime()) + "-" + 
                        new SimpleDateFormat("dd").format(dateTime.getTime()) + " " + 
                        new SimpleDateFormat("kk").format(dateTime.getTime()) + ":" +
                        dateTime.get(Calendar.MINUTE) + ":" +
                        dateTime.get(Calendar.SECOND);
            case 1:
                return call.getDestination().getPrefix();
            case 2:
                return call.getCallee();
            case 3:
                return call.getDestination().getName();
            case 4:
                return call.getDirection();
            case 5:
                return call.getLength();
            case 6:
                return call.getNote();
            case 7:
                return PriceManager.getPrice(call);
            default:
                throw new IllegalArgumentException(texts.getString("COLUMNINDEX"));
        }
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return texts.getString("DATETIME");
            case 1:
                return texts.getString("PREFIX");                
            case 2:
                return texts.getString("CALLEE");                
            case 3:
                return texts.getString("DESTINATION");
            case 4:
                return texts.getString("DIRECTION");
            case 5:
                return texts.getString("LENGTH");
            case 6:
                return texts.getString("NOTE");
            case 7:
                return texts.getString("PRICE");
            default:
                throw new IllegalArgumentException(texts.getString("COLUMNINDEX"));
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return SimpleDateFormat.class;
            case 1:
                return String.class;
            case 2:
                return String.class;    
            case 3:
                return String.class;
            case 4:
                return Direction.class;
            case 5:
                return Integer.class;
            case 6:
                return String.class;
            case 7:
                return Double.class;
            default:
                throw new IllegalArgumentException(texts.getString("COLUMNINDEX"));
        }
    }
    
        private static class RetrieveSwingWorker extends SwingWorker<Void, Void> {

        private final CallManager callManager;
        private final WeakReference<CallTableModel> tableModel;
        
        public RetrieveSwingWorker(CallTableModel callModel){
            this.tableModel = new WeakReference<>(callModel);
            callManager = LoadDataManager.getInstance().getCallManager();
        }
        
        @Override
        protected Void doInBackground() throws Exception {            
            calls = callManager.getAll();
            return null;
        }

        @Override
        protected void done() {
            CallTableModel callTableModel = tableModel.get();
            
            if (callTableModel != null) {
                callTableModel.fireTableRowsInserted(0, callTableModel.getRowCount() - 1);
            }
        }
    }
        
    private static class UpdateSwingWorker extends SwingWorker<Void, Void> {

        private final List<Call> callList;
        private final WeakReference<CallTableModel> tableModel; 
        
        public UpdateSwingWorker(List<Call> callList, CallTableModel callModel) {
            this.tableModel = new WeakReference<>(callModel);
            if (callList != null) {
                this.callList = callList;
            } else {
                this.callList = Collections.emptyList();
            }

        }

        @Override
        protected Void doInBackground() throws Exception {
                calls = this.callList;
            return null;
        }

        @Override
        protected void done() {
            CallTableModel callModel = tableModel.get();
            if (callModel != null){
                callModel.fireTableDataChanged();
            }
        }
    }

    public void updateTable (List<Call> calls,  CallTableModel callModel){
        UpdateSwingWorker updateSwingWorker = new UpdateSwingWorker(calls, callModel);
        updateSwingWorker.execute();       
        //setValueAt(aValue, rowIndex, columnIndex);
    }
    
    
}
