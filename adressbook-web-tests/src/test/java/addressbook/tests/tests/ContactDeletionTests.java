package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public  void contactDeletionPreconditions(){
    //check that the contact exists, if not - create the new one
    if (app.contact().list().size()==0){
      //check that the group exist, if not - create the new one
      app.goTo().groupPage();
      if(app.group().list().size()==0){
        app.group().create(new GroupData()
                .withName("Test").withHeader("test contact deletion"));
      }
      app.contact().create(new ContactData()
                      .withFirstName("Mon").withLastName("Bing").withAddress("Ny - some place 2/1").withHomePhone("+16666666").withEmail("friends@email.com").withGroup("Test")
      );
    }

  }


  @Test
  public void testContactDeletionHomePage() throws Exception {
   List<ContactData> before = app.contact().list();
   int index = before.size()-1;
   app.contact().delete(index, "home");
   List<ContactData> after = app.contact().list();
   Assert.assertEquals(after.size(), before.size()-1);
   before.remove(index);
   Assert.assertEquals(after, before);

  }
  @Test
  public void testContactDeletionEditPage() throws Exception {
    List<ContactData> before = app.contact().list();
    int index = before.size()-1;
    app.contact().delete(index, "edit");
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size()-1);
    before.remove(index);
    Assert.assertEquals(after, before);
  }

  @Test
  public void testContactDeletionViewEditPage() throws Exception {
    List<ContactData> before = app.contact().list();
    int index = before.size()-1;
    app.contact().delete(index, "details");
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size()-1);
    before.remove(index);
    Assert.assertEquals(after, before);
  }


  @Test
  public void testAllContactsDeletionHome() throws Exception {
    app.contact().delete(0, "homeAll");
    //int after = app.getContactHelper().getContactCount();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), 0);
  }

}
