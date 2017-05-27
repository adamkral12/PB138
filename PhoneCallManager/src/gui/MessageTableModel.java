package gui;

import core.Direction;
import core.Message;
import managers.MessageManager;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.table.AbstractTableModel;
import javax.swing.SwingWorker;
import managers.MessageManager;
import managers.LoadDataManager;

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
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Message message = messages.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return message.getDateTime().getTime(); 
            case 1:
                return message.getDestination().getPrefix();
            case 2:
                return  message.getCallee();
            case 3:
                return message.getDestination().getName();
            case 4:
                return message.getDirection();
            case 5:
                return message.getLenght();
            case 6:
                return message.getNote();
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
            default:
                throw new IllegalArgumentException(texts.getString("COLUMNINDEX"));
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Date.class;
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
            System.out.println("updating messages " + messages);
            this.tableModel = new WeakReference<>(messageModel);
            this.messageList = messageList;
        }

        @Override
        protected Void doInBackground() throws Exception {
            System.out.println("Updating messages: " + messages);
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
