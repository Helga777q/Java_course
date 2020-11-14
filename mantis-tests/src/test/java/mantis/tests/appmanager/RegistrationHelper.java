package mantis.tests.appmanager;

import mantis.tests.model.MailMessage;
import mantis.tests.model.UserData;
import org.openqa.selenium.By;
import ru.lanwen.verbalregex.VerbalExpression;

import java.util.List;

public class RegistrationHelper extends BaseHelper {


  public RegistrationHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String username, String email){
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    type(By.name("username"), username);
    type(By.name("email"), email);
    click(By.xpath("//input[@value='Signup']"));
  }


  public void finish(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.cssSelector(".bigger-110"));
  }

  public void pressResetByAdmin(UserData user) {
    goToUsersLists();
    goToEditUserPageById(user.getId());
    resetPassword();
  }

  public void login(UserData user){
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), user.getLogin());
    click(By.xpath("//input[@value='Login']"));
    type(By.name("password"), user.getPassword());
    click(By.xpath("//input[@value='Login']"));
  }

  public void goToUsersLists() {
    wd.get(app.getProperty("web.baseUrl") + "/manage_user_page.php");
  }

  public void goToEditUserPageById(int id) {
    click(By.cssSelector(String.format("a[href='manage_user_edit_page.php?user_id=%s']", id)));
  }

  public void resetPassword() {
    click(By.xpath("//input[@value='Reset Password']"));
  }

  public void logout() {
    wd.findElement(By.xpath("//div[@id='navbar-container']/div[2]/ul/li[3]/a/i[2]")).click();
    wd.findElement(By.linkText("Logout")).click();
  }



}
