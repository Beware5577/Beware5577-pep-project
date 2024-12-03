package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {

/*
* Checks if a username and password are valid parameters for a
* new account registration.
*
* @param username
* @param password
*
* @return Returns a boolean based on the validity of the
*         provided username and password for an account registration.
*/
public boolean validAccountParameters(String username, String password)
{
    //Checking if username is blank and password is at least 4 characters
    if(username.equals("") == true || password.length() < 4)
        return false;

    //Checking if username and password are over the character limit
    if(username.length() > 255 || password.length() > 255)
        return false;

    //Checking if account with provided username exists
    if(getAccountByUsername(username) != null)
        return false;

    return true;
}

/*
* Searches the Account table for an entry with the
* provided username.
*
* @param username
*
* @return Returns an account if one matching the provided
*         username exists, otherwise returns null.
*/
public Account getAccountByUsername(String username)
{
    //Connecting to database
    Connection connection = ConnectionUtil.getConnection();

    try
    {
        //SQL Statement
        String sql = "SELECT * FROM Account WHERE username = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        //Setting prepared statement's parameter
        ps.setString(1, username);

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
* Creates an entry in the Account table to register
* an account.
*
* @param account
*
* @return Returns an account if the account registration is
*         successful, otherwise returns null.
*/
public Account registerAccount(Account account)
{
    //Connecting to database
    Connection connection = ConnectionUtil.getConnection();

    try
    {
        //SQL Statement
        String sql = "INSERT INTO Account (username, password) VALUES(?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);

        //Setting prepared statement's parameters
        ps.setString(1, account.getUsername());
        ps.setString(2, account.getPassword());

        ps.executeUpdate();

    }
    catch(SQLException e)
    {
        System.out.println(e.getMessage());
    }

    //Getting updated account entry for return
    return getAccountByUsername(account.getUsername());
}

/*
* Searches the Account table for an entry with the
* provided username and password.
*
* @param username
* @param password
*
* @return Returns an account if one matching the provided
*         username and password exists, otherwise returns null.
*/
public Account logInAccount(String username, String password)
{
    //Connecting to database
    Connection connection = ConnectionUtil.getConnection();

    try
    {
        //SQL Statement
        String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        //Setting prepared statement's parameters
        ps.setString(1, username);
        ps.setString(2, password);

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
}
