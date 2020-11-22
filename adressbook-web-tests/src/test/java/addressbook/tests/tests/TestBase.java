package addressbook.tests.tests;

import addressbook.tests.appmanager.ApplicationManager;
import addressbook.tests.model.ContactData;
import addressbook.tests.model.Contacts;
import addressbook.tests.model.GroupData;
import addressbook.tests.model.Groups;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.security.acl.Group;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@Listeners(MyTestListener.class)
public class TestBase {

  Logger logger = LoggerFactory.getLogger(TestBase.class);

  protected static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser",BrowserType.FIREFOX));

  @BeforeSuite( alwaysRun = true)
  public void setUp(ITestContext context) throws Exception {
    app.init();
    context.setAttribute("app", app);
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    app.stop();
  }


  @BeforeMethod
  public void logTestStart(Method m, Object[] p){

    logger.info("Start test "+ m.getName()+ " with parameters " + Arrays.asList(p));


  }

  @AfterMethod (alwaysRun = true)
  public void logTestStop(Method m){
    logger.info("Stop test "+m.getName());
  }


  public void verifyGroupListInUI() {
    if (Boolean.getBoolean("verifyUI")) {
      Groups dbGroups = app.db().groups();
      Groups uiGroups = app.group().all();
      assertThat(uiGroups, equalTo(dbGroups.stream()
              .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
              .collect(Collectors.toSet())));
    }
  }

  public void verifyContactListUI() {
    if (Boolean.getBoolean("verifyUI")) {
      Contacts dbContacts = app.db().contacts();
      Contacts uiContacts = app.contact().all();
      assertThat(uiContacts, equalTo(dbContacts.stream()
              .map((c) -> new ContactData().withId(c.getId()).withFirstName(c.getFirstName()).withLastName(c.getLastName()).withAddress(c.getAddress()))
              .collect(Collectors.toSet())));
    }
  }

}
