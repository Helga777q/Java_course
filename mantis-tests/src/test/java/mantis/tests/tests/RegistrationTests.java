package mantis.tests.tests;

import mantis.tests.model.MailMessage;
import mantis.tests.model.UserData;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase {

  //@BeforeMethod
  public void startMailServer(){
    app.mail().start();
  }

  @Test
  public void testRegistration() throws IOException, MessagingException {
    long now =System.currentTimeMillis();
    String email = String.format("user%s@localhost", now);
    String user = String.format("user%s",now);
    String password = "password";
    app.james().createUser(new UserData().withLogin(user).withPassword(password));
    app.registration().start(user, email);
    //List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
    List<MailMessage> mailMessages = app.james().waitForMail(user, password, 60000);
    String confirmationLink = app.james().findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password);
    assertTrue(app.newSession().login(user, password));
  }



  //@AfterMethod(alwaysRun = true)
  public void stopMailServer(){
    app.mail().stop();
  }

}
