/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import core.Call;
import managers.CallManager;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.table.AbstractTableModel;
import javax.swing.SwingWorker;

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
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Call call = calls.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return call.getDateTime(); 
            case 1:
                return call.getCallee();
            case 2:
                return call.getDestination();
            case 3:
                return call.getDirection();
            case 4:
                return call.getLenght();
            case 5:
                return call.getNote();
            default:
                throw new IllegalArgumentException(texts.getString("COLUMNINDEX"));
        }
    }
    
        private static class RetrieveSwingWorker extends SwingWorker<Void, Void> {

        private final CallManager callManager;
        private final WeakReference<CallTableModel> tableModel;
        
        public RetrieveSwingWorker(CallTableModel callModel){
            this.tableModel = new WeakReference<>(callModel);
            callManager = Data.getInstance().getCallManager();
        }
        
        @Override
        protected Void doInBackground() throws Exception {            
            calls = callManager.getAll();
        //    log.info("Retrieving employees: " + calls);
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
    
}
