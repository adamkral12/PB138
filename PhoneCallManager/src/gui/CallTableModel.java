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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getColumnCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
