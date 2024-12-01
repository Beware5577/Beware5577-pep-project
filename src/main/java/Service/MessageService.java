package Service;

import Model.Message;
import DAO.MessageDAO;

import static org.junit.Assert.fail;

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
    public Message getMessageById(Message message)
    {
        return messageDAO.getMessageById(message.getMessage_id());
    }

    /*
    * Deletes a message with a certain ID if it exists.
    * 
    * @param message
    *
    * @return Returns a deleted message or null if message to delete
    *                 does not exist. 
    */
    public Message deleteMessageById(Message message)
    {
        return messageDAO.deleteMessageById(message.getMessage_id());
    }

    /*
    * Patches a message with a certain ID if it exists.
    * 
    * @param message
    *
    * @return Returns a patched message or null if message to patch
    *                 does not exist. 
    */
    public Message patchMessageById(Message message)
    {
        return messageDAO.patchMessageById(message.getMessage_id());
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
