package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
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
        app.get("/messages/{message_id}", this::getMessageIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageIdHandler);
        app.patch("/messages/{message_id}", this::patchMessageIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAccountMessagesHandler);

        app.start();

        return app;
    }

    /**
     * Handler to post a new registration.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postRegistrationHandler(Context context) throws JsonProcessingException{
        context.json("sample text");
    }


    /**
     * Handler to post a new login.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postLoginHandler(Context context) throws JsonProcessingException{} 

    /**
     * Handler to post a message.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postMessagesHandler(Context context) throws JsonProcessingException{}

    /**
     * Handler to get all messages.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void getAllMessagesHandler(Context context) throws JsonProcessingException{}

    /**
     * Handler to get a message by a message ID.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void getMessageIdHandler(Context context) throws JsonProcessingException{}

    /**
     * Handler to delete a message by a message ID.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void deleteMessageIdHandler(Context context) throws JsonProcessingException{}

    /**
     * Handler to patch a message by a message ID.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void patchMessageIdHandler(Context context) throws JsonProcessingException{}

    /**
     * Handler to get all messages from an account.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * 
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void getAccountMessagesHandler(Context context) throws JsonProcessingException{}

}