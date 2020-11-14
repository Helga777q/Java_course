package mantis.tests.tests;

import mantis.tests.model.MailMessage;
import mantis.tests.model.UserData;
import org.testng.annotations.Test;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class ResetPasswordTests extends TestBase {



  @Test
  public void passwordResetTest() throws MessagingException, IOException {
    app.registration().login(new UserData().withLogin("administrator").withPassword("root"));
    UserData user = app.db().users()
            .stream().filter((u)->(!u.getLogin().equals("administrator")))
            .collect(Collectors.toList()).iterator().next();
    app.james().initTelnetSession();
    app.james().deleteEmails(user.getLogin(), "password"); //delete previous emails for user
    app.registration().pressResetByAdmin(user);
    List<MailMessage> mailMessages = app.james().waitForMail(user.getLogin(), "password", 60000);
    app.james().closeTelnetSession();
    String confirmationLink = app.james().findConfirmationLink(mailMessages, user.getEmail());
    String newPassword = "password2";
    app.registration().finish(confirmationLink, newPassword);
    app.newSession().login(user.getLogin(), newPassword);
  }


}
