package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService 
{
    private MessageDAO messageDAO;

    /*
    * Constructor with no provided DAO
    */
    public MessageService()
    {
        messageDAO = new MessageDAO();
    }

    /*
    * Constructor with provided DAO
    * 
    * @param accountDAO
    */
    public MessageService(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    /*
    * Creates new messages.
    * 
    * @param message
    *
    * @return Returns successfully created message or null upon failure.
    */
    public Message createMessage(Message message)
    {
        //Checking input for valid text and foreign key
        if(messageDAO.validMessageParameters(message.getMessage_text(), message.getPosted_by()) == false)
            return null;

        return messageDAO.createMessage(message);
    }

    /*
    * Returns a list of all existing messages.
    * 
    * @return A list of all existing messages.
    */
    public List<Message> getAllMessages()
    {
        return messageDAO.getAllMessages();
    }

    /*
    * Returns a message with a certain ID if it exists or null if it does not exist.
    * 
    * @param message
    *
    * @return Returns a message or null. 
    */
    public Message getMessageById(int messageId)
    {
        return messageDAO.getMessageById(messageId);
    }

    /*
    * Deletes a message with a certain ID if it exists.
    * 
    * @param message
    *
    * @return Returns a deleted message or null if message to delete
    *                 does not exist. 
    */
    public Message deleteMessageById(int messageId)
    {
        return messageDAO.deleteMessageById(messageId);
    }

    /*
    * Patches a message with a certain ID if it exists.
    * 
    * @param message
    *
    * @return Returns a patched message or null if message to patch
    *                 does not exist. 
    */
    public Message patchMessageById(int messageId)
    {
        return messageDAO.patchMessageById(messageId);
    }

    /*
    * Returns all messages associated with an account.
    * 
    * @param message
    *
    * @return Returns a list of messages. 
    */
    public List<Message> getAllAccountMessages(Message message)
    {
        return messageDAO.getAllAccountMessages(message.getPosted_by());
    }
}
