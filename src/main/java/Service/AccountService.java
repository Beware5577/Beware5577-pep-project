package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService 
{

    private AccountDAO accountDAO;
    
    /*
    * Constructor with no provided DAO
    */
    public AccountService()
    {
        accountDAO = new AccountDAO();
    }

    /*
    * Constructor with provided DAO
    * 
    * @param accountDAO
    */
    public AccountService(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

    /*
    * Registers new accounts.
    * 
    * @param account
    *
    * @return Returns successfully registered account or null upon failure.
    */
    public Account registerAccount(Account account)
    {
        //Checking input to see if the parameters are valid
        if(accountDAO.validAccountParameters(account.getUsername(), account.getPassword()) == false)
            return null;

        return accountDAO.registerAccount(account);
    }

    /*
    * Logs into existing accounts.
    * 
    * @param account
    *
    * @return Returns successfully logged in account or null upon failure.
    */
    public Account logInAccount(Account account)
    {
        return accountDAO.logInAccount(account.getUsername(), account.getPassword());
    }

}
