package Controller;

import static org.mockito.ArgumentMatchers.nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Model.Account;
import Model.Message;

import Service.AccountService;
import Service.MessageService;

import java.util.List;


/**
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController 
{ 
    AccountService accountService;
    MessageService messageService;

    /*
     * Constructor
     */
    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        
        //Endpoints
        app.post("/register", this::postRegistrationHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::patchMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAccountMessagesHandler);

        return app;
    }

    /**
     * Handler to post a new registration.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postRegistrationHandler(Context context) throws JsonProcessingException
    {
        
        //Mapping object and calling the account service to attempt to register an account
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(),Account.class);
        Account registeredAccount = accountService.registerAccount(account);    
        
        //Checking if account was successfully registered
        if(registeredAccount != null)
        {
            context.json(mapper.writeValueAsString(registeredAccount));
        }
        else
            context.status(400);      
    }


    /**
     * Handler to post a login.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postLoginHandler(Context context) throws JsonProcessingException
    {
        //Mapping object and calling the account service to attempt an account login
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(),Account.class);
        Account loggedInAccount = accountService.logInAccount(account);

        //Checking if account was successfully logged in
        if(loggedInAccount != null)
        {
            context.json(mapper.writeValueAsString(loggedInAccount));
        }
        else
            context.status(401);

    } 


    /**
     * Handler to post a message.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postMessagesHandler(Context context) throws JsonProcessingException
    {
        //Mapping the object to get message ID
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(),Message.class);
        Message createdMessage = messageService.createMessage(message);

        //Checking if message was successfully created
        if(createdMessage != null)
        {
            context.json(mapper.writeValueAsString(createdMessage));
        }
        else
            context.status(400);
    }


    /**
     * Handler to get all messages.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response. 
     */
    private void getAllMessagesHandler(Context context)
    {
        //Calling method to return all messages
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }


    /**
     * Handler to get a message by a message ID.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     *  
     */
    private void getMessageByIdHandler(Context context)
    {
        //Calling method to get a message with a given ID
        Message message = messageService.getMessageById(Integer.parseInt(context.pathParam("message_id")));
        
        //Checking if message was found
        if(message != null)
            context.json(message);
    }


    /**
     * Handler to delete a message by a message ID.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     *
     */
    private void deleteMessageByIdHandler(Context context)
    {
        //Calling method to delete a message with a given ID
        Message message = messageService.deleteMessageById(Integer.parseInt(context.pathParam("message_id")));

        //Checking if message was found
        if(message != null)
            context.json(message);
    }


    /**
     * Handler to patch a message by a message ID.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void patchMessageByIdHandler(Context context) throws JsonProcessingException
    {
        //Mapping the object to get message text to update
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(),Message.class);

        //Patching message
        Message updatedMessage = messageService.patchMessageById(Integer.parseInt(context.pathParam("message_id")), message.getMessage_text());

        //Checking if message was successfully updated
        if(updatedMessage != null)
        {
            context.json(updatedMessage);
        }
        else
            context.status(400);
    }


    /**
     * Handler to get all messages from an account.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAccountMessagesHandler(Context context)
    {
        //Calling method to return all messages associated with an account
        List<Message> messages = messageService.getAllAccountMessages(Integer.parseInt(context.pathParam("account_id")));
        context.json(messages);
    }

}