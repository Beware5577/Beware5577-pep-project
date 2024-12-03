package DAO;

import static org.mockito.ArgumentMatchers.nullable;

import java.util.ArrayList;
import java.util.List;
import Util.ConnectionUtil;
import java.sql.*;

import Model.Message;
import Model.Account;

public class MessageDAO {

/*
* Checks if a message text and account id are valid 
* parameters for a new message's creation.
*
* @param messageText
* @param accountId
*
* @return Returns a boolean based on the validity of the
*         provided message text and account ID for a message's creation.
*/    
public boolean validMessageParameters(String messageText, int accountId)
{
    //Checking String validity
    if(messageText == "" || messageText.length() > 255)
        return false;

    //Checking if corresponding account exists
    if(getAccountById(accountId) == null)
        return false;

    return true;
}

/*
* Searches the Account table for an entry with the
* provided ID.
*
* @param accountId
*
* @return Returns an account if one matching the provided
*         ID exists, otherwise returns null.
*/
public Account getAccountById(int accountId)
{
    //Connecting to database
    Connection connection = ConnectionUtil.getConnection();

    try
    {
        //SQL Statement
        String sql = "SELECT * FROM Account WHERE account_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        //Setting prepared statement's parameter
        ps.setInt(1, accountId);

        //Getting result of SQL statement and returning the result
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            Account account = new Account(rs.getInt("account_id"),
                              rs.getString("username"),
                              rs.getString("password"));
            return account;
        }
    }
    catch(SQLException e)
    {
        System.out.println(e.getMessage());
    }

    return null;
}

/*
* Creates a message entry in the Message table and 
* returns it.
*
* @param message
*
* @return Returns a newly created message entry.
*         Returns null upon failure.
*/
public Message createMessage(Message message)
{
    //Connecting to database
    Connection connection = ConnectionUtil.getConnection();

    try
    {
        //SQL Statement
        String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES(?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);

        //Setting prepared statement's parameters
        ps.setInt(1, message.getPosted_by());
        ps.setString(2, message.getMessage_text());
        ps.setLong(3, message.getTime_posted_epoch());

        ps.executeUpdate();

    }
    catch(SQLException e)
    {
        System.out.println(e.getMessage());
    }

    //Getting updated message entry for return
    return getMessageByTimePostedMessageTextAndAccountId(message.getTime_posted_epoch(), message.getMessage_text(), message.getPosted_by());
}

/*
* Searches the Message table for a message matching
* the provided time, text, and account ID.
*
* @param timePosted
* @param messageText
* @param accountId
*
* @return Returns a message entry with the
*         provided time posted, text, and account ID.
*         Returns null upon failure.
*/
public Message getMessageByTimePostedMessageTextAndAccountId(long timePosted, String messageText, int accountId)
{

    /*
    * Note: 
    * This method is necessary when creating new messages.
    * The Message object passed in to createMessage lacks a message_id,
    * this method finds the assigned message_id by using the other elements in the object. 
    */

    //Connecting to database
    Connection connection = ConnectionUtil.getConnection();

    try
    {
        //SQL Statement
        String sql = "SELECT * FROM Message WHERE time_posted_epoch = ? AND message_text = ? AND posted_by = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        //Setting prepared statement's parameters
        ps.setLong(1, timePosted);
        ps.setString(2, messageText);
        ps.setInt(3, accountId);

        //Getting result of SQL statement and returning the result
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            Message message = new Message(rs.getInt("message_id"),
                              rs.getInt("posted_by"),
                              rs.getString("message_text"),
                              rs.getLong("time_posted_epoch"));
            return message;
        }
    }
    catch(SQLException e)
    {
        System.out.println(e.getMessage());
    }

    return null;
}

/*
* Gets a list of all messages in the Message table.
* 
* @return Returns a list of messages.
*/
public List<Message> getAllMessages()
{
    //Connecting to database
    Connection connection = ConnectionUtil.getConnection();

    //Creating output list
    List<Message> messageList = new ArrayList<>();

    try
    {
        //SQL Statement
        String sql = "SELECT * FROM Message";
        PreparedStatement ps = connection.prepareStatement(sql);

        //Getting result of SQL statement
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            Message message = new Message(rs.getInt("message_id"),
                              rs.getInt("posted_by"),
                              rs.getString("message_text"),
                              rs.getLong("time_posted_epoch"));
            messageList.add(message);
            
        }
    }
    catch(SQLException e)
    {
        System.out.println(e.getMessage());
    }

    //Returned list may or may not be empty
    return messageList;
}

/*
* Searches the Message table for a message matching
* the provided message ID.
*
* @param messageId
*
* @return Returns a message entry with the
*         provided message ID.
*         Returns null upon failure.
*/
public Message getMessageById(int messageId)
{
    //Connecting to database
    Connection connection = ConnectionUtil.getConnection();

    try
    {
        //SQL Statement
        String sql = "SELECT * FROM Message WHERE message_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        //Setting prepared statement parameter
        ps.setInt(1, messageId);

        //Getting result of SQL statement and returning the result
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            Message  message = new Message(rs.getInt("message_id"),
                              rs.getInt("posted_by"),
                              rs.getString("message_text"),
                              rs.getLong("time_posted_epoch"));
            return message;
        }
    }
    catch(SQLException e)
    {
        System.out.println(e.getMessage());
    }

    return null;
}

/*
* Deletes a message in the Message table if
* the corresponding message ID exists.
*
* @param messageId
*
* @return Returns the deleted message entry.
*         Returns null upon failure.
*/
public Message deleteMessageById(int messageId)
{
    //Connecting to database
    Connection connection = ConnectionUtil.getConnection();

    //Object holding message to be deleted
    Message deletedMessage = getMessageById(messageId);

    //Checking if message to delete exists
    if(deletedMessage == null)
        return null;

    try
    {
        //SQL Statement
        String sql = "DELETE FROM Message WHERE message_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        //Setting prepared statement parameter
        ps.setInt(1, messageId);

        //Executing delete and returning deleted message
        ps.executeUpdate();
        return deletedMessage;
    }
    catch(SQLException e)
    {
        System.out.println(e.getMessage());
    }

    return null;
}

/*
* Updates a message's text in the Message Table.
*
* @param messageId
* @param updateText
*
* @return Returns the updated message.
*         Returns null upon failure.
*/
public Message patchMessageById(int messageId, String updateText)
{
    //Connecting to database
    Connection connection = ConnectionUtil.getConnection();

    //Checking if update text is valid
    if(updateText == "" || updateText.length() > 255)
        return null;

    //Checking if message to be updated exists
    if(getMessageById(messageId) == null)
        return null;

    try
    {
        //SQL Statement
        String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        //Setting prepared statement's parameters
        ps.setString(1, updateText);
        ps.setInt(2, messageId);

        //Getting result of SQL statement and returning updated message
        ps.executeUpdate();
        return getMessageById(messageId);
    }
    catch(SQLException e)
    {
        System.out.println(e.getMessage());
    }

    return null;
}

/*
* Gets a list of all messages in the Message table
* posted by a specific account.
* 
* @param accountId
*
* @return Returns a list of messages from a specific account.
*/
public List<Message> getAllAccountMessages(int accountId)
{
    //Connecting to database
    Connection connection = ConnectionUtil.getConnection();

    //Creating output list
    List<Message> messageList = new ArrayList<>();

    try
    {
        //SQL Statement
        String sql = "SELECT * FROM Message WHERE posted_by = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        //Setting prepared statement parameter
        ps.setInt(1, accountId);

        //Getting result of SQL statement and returning the result
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
            Message message = new Message(rs.getInt("message_id"),
                              rs.getInt("posted_by"),
                              rs.getString("message_text"),
                              rs.getLong("time_posted_epoch"));
            messageList.add(message);
        }
    }
    catch(SQLException e)
    {
        System.out.println(e.getMessage());
    }

    //Returned list may or may not be empty
    return messageList;
}

}
