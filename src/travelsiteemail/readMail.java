package travelsiteemail;


import java.util.List;
import java.util.Properties;
import java.util.Date;
import java.util.Locale;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import javax.mail.Flags;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.NoSuchProviderException;
import javax.mail.Address;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.ParseException;

import java.text.SimpleDateFormat;

import javax.mail.*;
import javax.mail.event.*;
import javax.mail.internet.*;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 


public final class readMail {

	
public static void fetch(String pop3Host, String storeType, String user,
      String password) {
      try {
         // create properties field
         Properties properties = new Properties();
         properties.put("mail.store.protocol", "imaps");
         properties.put("imap.gmail.com", pop3Host);
        
         Session emailSession = Session.getDefaultInstance(properties);
         // emailSession.setDebug(true);

 
         Store store = emailSession.getStore("imaps");

         store.connect(pop3Host, user, password);

         // create the folder object and open it
         Folder emailFolder = store.getFolder("INBOX");
         emailFolder.open(Folder.READ_ONLY);

         BufferedReader reader = new BufferedReader(new InputStreamReader(
	      System.in));

         // retrieve the messages from the folder in an array and print it
         Message[] messages = emailFolder.getMessages();
         System.out.println("messages.length---" + messages.length);

         for (int i = 0; i < messages.length; i++) {
            Message message = messages[i];
            System.out.println("---------------------------------");
            writePart(message);
            String line = reader.readLine();
            if ("YES".equals(line)) {
               message.writeTo(System.out);
            } else if ("QUIT".equals(line)) {
               break;
            }
         }

         // close the store and folder objects
         emailFolder.close(false);
         store.close();

      } catch (NoSuchProviderException e) {
         e.printStackTrace();
      } catch (MessagingException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static void main(String[] args) {

      String host = "imap.gmail.com";// change accordingly
      String mailStoreType = "imap";
      String username = "travelsite888@gmail.com";// change accordingly
      String password = "travelsite";// change accordingly

      //Call method fetch
      fetch(host, mailStoreType, username, password);

      
      
      
      //xml output  
      
      String test = "this is a test";
	   
	  try {
 
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("company");
		doc.appendChild(rootElement);
 
		// staff elements
		Element staff = doc.createElement("Staff");
		rootElement.appendChild(staff);
 
		// set attribute to staff element
		Attr attr = doc.createAttribute("id");
		attr.setValue("1");
		staff.setAttributeNode(attr);
 
		// shorten way
		// staff.setAttribute("id", "1");
 
		// firstname elements
		Element firstname = doc.createElement("firstname");
		firstname.appendChild(doc.createTextNode(test));
		staff.appendChild(firstname);
 
		// lastname elements
		Element lastname = doc.createElement("lastname");
		lastname.appendChild(doc.createTextNode("mook kim"));
		staff.appendChild(lastname);
 
		// nickname elements
		Element nickname = doc.createElement("nickname");
		nickname.appendChild(doc.createTextNode("mkyong"));
		staff.appendChild(nickname);
 
		// salary elements
		Element salary = doc.createElement("salary");
		salary.appendChild(doc.createTextNode("100000"));
		staff.appendChild(salary);
 
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		//StreamResult result = new StreamResult(new File("C:\\file.xml"));
 
		// Output to console for testing;
		 StreamResult result = new StreamResult(System.out);
 
		transformer.transform(source, result);
 
		System.out.println("File saved!");
 
	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
      
      //end xml output
      
      
   }

   /*
   * This method checks for content-type 
   * based on which, it processes and
   * fetches the content of the message
   */

	
   public static void writePart(Part p) throws Exception {
      if (p instanceof Message)
         //Call method writeEnvelope
         writeEnvelope((Message) p);

      System.out.println("----------------------------");
      System.out.println("CONTENT-TYPE: " + p.getContentType());

      //check if the content is plain text
      if (p.isMimeType("text/plain")) {
         System.out.println("This is plain text");
         System.out.println("---------------------------");
         System.out.println((String) p.getContent());
         
         
         String name = "";
         String deploc = "";
         String arrloc = "";
         String confnum = "";
         String tripdate = "";
         
         
         String messageContent = (String) p.getContent();
         
         int i = 0;
         while (true) {
           int found = messageContent.indexOf("Depart", i);
           if (found == -1) break;
           int start = found; 
           int end = messageContent.indexOf("\n", start);
           System.out.println(messageContent.substring(start, end));
           i = end + 1;  
         }
         
         while (true) {
             int found = messageContent.indexOf("Arrive", i);
             if (found == -1) break;
             int start = found; 
             int end = messageContent.indexOf("\n", start);
             System.out.println(messageContent.substring(start, end));
             i = end + 1;  
           }
         
         
         
         String messageContentLines[] = ((String) messageContent).split("\n");
         
         for ( String line:messageContentLines ) {
            
        	 
        	 if ( line.contains( "AIR Confirmation:" ) ) {
            	 String[] words = line.split(" ");
                 confnum = words[words.length-1];
             }
             if ( line.contains( "*Upcoming Trip:*" ) ) {
            	 String[] words = line.split(" ");
                 tripdate = words[words.length-3];
               
             }
             if ( line.startsWith( "Subject:" ) ) {
            	 String[] words = line.split(" ");
                 name = words[words.length-1];
       
             }
             if ( line.contains( "Depart" ) ) {          
            	    
            	  
            	String[] words = line.split("\n");
                deploc = words[words.length-1];
       
             }
             if ( line.contains( "Arrive" ) ) {
            	 String[] words = line.split("\n");
                 arrloc = words[words.length-1];
       
             }
             
            
          }
         

         
         System.out.println( confnum );
         System.out.println( tripdate );
         System.out.println( name);
         System.out.println( deploc);
         System.out.println( arrloc);
     
         
      } 
      
      
      //check if the content has attachment
      else if (p.isMimeType("multipart/*")) {
         System.out.println("This is a Multipart");
         System.out.println("---------------------------");
         Multipart mp = (Multipart) p.getContent();
         int count = mp.getCount();
         for (int i = 0; i < count; i++)
            writePart(mp.getBodyPart(i));
      } 
      //check if the content is a nested message
      else if (p.isMimeType("message/rfc822")) {
         System.out.println("This is a Nested Message");
         System.out.println("---------------------------");
         writePart((Part) p.getContent());
      } 
      //check if the content is an inline image
      else if (p.isMimeType("image/jpeg")) {
         System.out.println("--------> image/jpeg");
         Object o = p.getContent();

         InputStream x = (InputStream) o;
         // Construct the required byte array
         System.out.println("x.length = " + x.available());
         int i = 0;
         byte[] bArray = new byte[x.available()];

         while ((i = (int) ((InputStream) x).available()) > 0) {
            int result = (int) (((InputStream) x).read(bArray));
            if (result == -1)
            break;
         }
         FileOutputStream f2 = new FileOutputStream("/tmp/image.jpg");
         f2.write(bArray);
      } 
      else if (p.getContentType().contains("image/")) {
         System.out.println("content type" + p.getContentType());
         File f = new File("image" + new Date().getTime() + ".jpg");
         DataOutputStream output = new DataOutputStream(
            new BufferedOutputStream(new FileOutputStream(f)));
            com.sun.mail.util.BASE64DecoderStream test = 
                 (com.sun.mail.util.BASE64DecoderStream) p
                  .getContent();
         byte[] buffer = new byte[1024];
         int bytesRead;
         while ((bytesRead = test.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
         }
      } 
      
      //HTML output - exclude for now
    /*  else {
         Object o = p.getContent();
         if (o instanceof String) {
            System.out.println("This is a string");
            System.out.println("---------------------------");
            System.out.println((String) o);
         } 
         else if (o instanceof InputStream) {
            System.out.println("This is just an input stream");
            System.out.println("---------------------------");
            InputStream is = (InputStream) o;
            is = (InputStream) o;
            int c;
            while ((c = is.read()) != -1)
               System.out.write(c);
         } 
         else {
            System.out.println("This is an unknown type");
            System.out.println("---------------------------");
            System.out.println(o.toString());
         }
      }

  */ 
      
   
   }
   /*
   * This method would print FROM,TO and SUBJECT of the message
   */
	
	
   public static void writeEnvelope(Message m) throws Exception {
      System.out.println("This is the message envelope");
      System.out.println("---------------------------");
      Address[] a;

      // FROM
      if ((a = m.getFrom()) != null) {
         for (int j = 0; j < a.length; j++)
         System.out.println("FROM: " + a[j].toString());
      }

      // TO
      if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
         for (int j = 0; j < a.length; j++)
         System.out.println("TO: " + a[j].toString());
      }

      // SUBJECT
      if (m.getSubject() != null)
         System.out.println("SUBJECT: " + m.getSubject());

   }
   
   
   
 
 
	

   
   
   
   
   
   
   
   
   
   
}






