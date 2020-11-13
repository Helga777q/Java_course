package mantis.tests.appmanager;

import mantis.tests.model.MailMessage;
import org.apache.commons.net.telnet.TelnetClient;

import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JamesHelper {
  private ApplicationManager app;
  private TelnetClient telnet;
  private Session mailSession;
  private Store store;
  private String mailserver;
  private InputStream in;
  private PrintStream out;
  

  public JamesHelper(ApplicationManager app){
    this.app =app;
    telnet= new TelnetClient();
    mailSession=Session.getDefaultInstance(System.getProperties());
  }

  public void createUser(String name, String password){
    initTelnetSession();
    write("adduser " + name + " " + password);
    String result = readUntil("User " + name + " added");
    closeTelnetSession();
  }

  private void initTelnetSession() {
    mailserver = app.getProperty("mailserver.host");
    int port = Integer.parseInt(app.getProperty("mailserver.port"));
    String login = app.getProperty("mailserver.adminlogin");
    String password = app.getProperty("mailserver.adminpassword");

    try {
      telnet.connect(mailserver, port);
      in = telnet.getInputStream();
      out = new PrintStream(telnet.getOutputStream());
    } catch (Exception e){
      e.printStackTrace();
    }

    //first login attempt
    readUntil("Login id:");
    write("test");
    readUntil("Password:");
    write("test2");


    //second login attempt
    readUntil("Login id:");
    write(login);
    readUntil("Password:");
    write(password);

    //welcome text read
    readUntil("Welcome " +login+". HELP for a list of commands" );

  }


  private void closeTelnetSession(){
    write("quit");
  }

  private String readUntil(String pattern){
    try{
      char lastChar = pattern.charAt(pattern.length()-1);
      StringBuffer sb=new StringBuffer();
      char ch = (char) in.read();
      while (true){
        System.out.print(ch);
        sb.append(ch);
        if (ch==lastChar){
          if (sb.toString().endsWith(pattern)){
            return sb.toString();
          }
        }
        ch = (char) in.read();
      }
      
    }catch(Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private void write(String value) {
    try {
      out.println(value);
      out.flush();
      System.out.println(value);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public List<MailMessage> waitForMail (String username, String password, long timeout) throws MessagingException {
    long now = System.currentTimeMillis();
    while (System.currentTimeMillis()<now+timeout){
      List<MailMessage> allMail = getAllMail(username, password);
      if (allMail.size()>0){
        return allMail;
      }
      try{
        Thread.sleep(1000);
      }catch (InterruptedException e){
        e.printStackTrace();
      }

    }
    throw new Error ("No mail");
  }

  public List<MailMessage> getAllMail(String username, String password) throws MessagingException {
    Folder inbox = openInbox(username, password);
    List<MailMessage> messages = Arrays.asList(inbox.getMessages())
            .stream().map((m)-> toModelMail(m)).collect(Collectors.toList());
    closeFolder(inbox);
    return messages;
  }

  public static MailMessage toModelMail(Message m){
    try {
      return new MailMessage(m.getAllRecipients()[0].toString(), (String) m.getContent());
    } catch (MessagingException e){
      e.printStackTrace();
      return null;
    }catch (IOException e){
      e.printStackTrace();
      return null;
    }

  }

  private Folder openInbox (String username, String password) throws MessagingException{
    store= mailSession.getStore("pop3");
    store.connect(mailserver, username, password);
    Folder folder = store.getDefaultFolder().getFolder("INBOX");
    folder.open(Folder.READ_WRITE);
    return folder;
  }

  private void  closeFolder(Folder folder) throws  MessagingException{
    folder.close(true);
    store.close();
  }
  
  


}
