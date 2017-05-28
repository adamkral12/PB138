package gui;

import core.Direction;
import core.Message;
import managers.MessageManager;
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
import managers.MessageManager;
import managers.LoadDataManager;
import managers.PriceManager;

/**
 *
 * @author adam
 * @UCO 433328
 */
public class MessageTableModel extends AbstractTableModel {
    
    private static final ResourceBundle texts = ResourceBundle.getBundle("i18n/texts");
    private static List<Message> messages = new ArrayList<>();

    
    public MessageTableModel(MessageTableModel messageModel) {
        MessageTableModel.RetrieveSwingWorker retrieveSwingWorker = new MessageTableModel.RetrieveSwingWorker(messageModel);
        retrieveSwingWorker.execute();
    }
    
    
    @Override
    public int getRowCount() {
        return messages.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Message message = messages.get(rowIndex);
        switch (columnIndex) {
            case 0:
                Calendar dateTime = message.getDateTime();
                return dateTime.get(Calendar.YEAR) + "-" + 
                        new SimpleDateFormat("MM").format(dateTime.getTime()) + "-" + 
                        new SimpleDateFormat("dd").format(dateTime.getTime()) + " " + 
                        new SimpleDateFormat("kk").format(dateTime.getTime()) + ":" +
                        dateTime.get(Calendar.MINUTE) + ":" + 
                        dateTime.get(Calendar.SECOND);
            case 1:
                return message.getDestination().getPrefix();
            case 2:
                return  message.getCallee();
            case 3:
                return message.getDestination().getName();
            case 4:
                return message.getDirection();
            case 5:
                return message.getLength();
            case 6:
                return message.getNote();
            case 7:
                return PriceManager.getPrice(message);
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

        private final MessageManager messageManager;
        private final WeakReference<MessageTableModel> tableModel;
        
        public RetrieveSwingWorker(MessageTableModel messagesModel){
            this.tableModel = new WeakReference<>(messagesModel);
            messageManager = LoadDataManager.getInstance().getMessageManager();
        }
        
        @Override
        protected Void doInBackground() throws Exception {            
            messages = messageManager.getAll();
        //    log.info("Retrieving employees: " + calls);
            return null;
        }

        @Override
        protected void done() {
            MessageTableModel messageTableModel = tableModel.get();
            
            if (messageTableModel != null) {
                messageTableModel.fireTableRowsInserted(0, messageTableModel.getRowCount() - 1);
            }
        }
    }
        
    private static class UpdateSwingWorker extends SwingWorker<Void, Void> {

        private final List<Message> messageList;
        private final WeakReference<MessageTableModel> tableModel; 
        
        public UpdateSwingWorker(List<Message> messageList, MessageTableModel messageModel) {
            this.tableModel = new WeakReference<>(messageModel);
            if (messageList != null) {
                this.messageList = messageList;
            } else {
                this.messageList = Collections.emptyList();
            }
        }

        @Override
        protected Void doInBackground() throws Exception {
            messages = this.messageList;
            return null;
        }

        @Override
        protected void done() {
            MessageTableModel messageModel = tableModel.get();
            if (messageModel != null){
                messageModel.fireTableDataChanged();
            }
        }
    }

    public void updateTable (List<Message> calls,  MessageTableModel messageModel){
        UpdateSwingWorker updateSwingWorker = new UpdateSwingWorker(calls, messageModel);
        updateSwingWorker.execute();       
        //setValueAt(aValue, rowIndex, columnIndex);
    }        

}
