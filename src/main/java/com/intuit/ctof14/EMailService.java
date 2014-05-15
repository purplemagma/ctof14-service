package com.intuit.ctof14;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;
import com.intuit.ctof14.ReviewService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("v1/email")
@Api(value = "v1/email", description = "email service")
public class EMailService
{

   private static final String SMTP_USER = "<your_gmail>";

   private static final String SMTP_USER_PASS = "<gmail_app_password>";

  @POST
  @Consumes("application/json")
  @ApiOperation(value="send e-mail", notes="send an e-mail", response=Response.class)
  public Response sendEMail(Notification email) {

  	    // Recipient's email ID needs to be mentioned.
	      String to = email.getRecipient();

	      // Sender's email ID needs to be mentioned
	      String from = email.getSender();
	      final String username = SMTP_USER;
	      final String password = SMTP_USER_PASS;

	      // Assuming you are sending email through relay.jangosmtp.net
	      String host = "smtp.gmail.com";

	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "587");

	      // Get the Session object.
	      Session session = Session.getInstance(props,
	      new javax.mail.Authenticator() {
	         protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	         }
	      });

	      try {
	         // Create a default MimeMessage object.
	         Message message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.setRecipients(Message.RecipientType.TO,
	         InternetAddress.parse(to));

	         // Set Subject: header field
	         message.setSubject(email.getSubject());

	         // Now set the actual message
	         message.setText(email.getMessage());

	         // Send message
	         Transport.send(message);

	         System.out.println("Sent message successfully....");
         
    }catch(Exception e) {
      return Response.status(500).build();
    }
          // Return success
         JSONObject obj = new JSONObject();
         obj.put("result", "success");
         return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
    }

/*   public Response findBestRestaurant()
   {
      HttpRequest request = null;
      try
      {
         // Setup request to gateway activity feed end point using the very handy 
         // Unirest library http://unirest.io/
         request =
            Unirest.get(TestServiceConsumer.baseFeedUri + "/v1/feeds").header("accept", "application/json")
               .header("Authorization", Application.getAuthorizationHeader());

         // Make request to gateway
         HttpResponse<JsonNode> jsonResponse = request.asJson();
         JsonNode body = jsonResponse.getBody();

         // Get the array of feed elements
         JSONArray feed = body.getObject().getJSONArray("feed");

         String winningRestaurant = "";

         // Iterate through feed list finding best restaurant
         for (int index = 0; index < feed.length(); index++)
         {
            JSONObject item = feed.getJSONObject(index);
            String itemContent = item.has("content") ? item.getString("content") : "";
            // Write your code here to choose the best restaurant by setting winningRestaurant to your choice
            //
            //
            //
            //
            // .........................................................
         }

         // Return winner
         JSONObject obj = new JSONObject();
         obj.put("result", winningRestaurant);
         return Response.ok(obj.toString(), MediaType.APPLICATION_JSON).build();
      }
      catch (Throwable e)
      {
         return Response.status(500).build();
      }
   }
*/
}