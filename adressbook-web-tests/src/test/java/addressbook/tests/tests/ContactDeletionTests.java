package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.Contacts;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public  void contactDeletionPreconditions(){
    //check that the contact exists in DB, if not - create the new one
    if (app.db().contacts().size()==0){
      app.contact().create(new ContactData()
              .withFirstName("Monica").withLastName("Geller").withAddress("NY Central perk")
              .withEmail("mon@friends").withEmailSecond("email22@test").withEmailThird("email@test.com")
              .withHome("+1111").withMobile("+222-333").withWork("+1-0000")
              .withBirthDate("11").withBirthMonth("October").withBirthYear("2001")
      );
    }

  }


  @Test
  public void testContactDeletionHomePage() throws Exception {
   Contacts before = app.db().contacts();
   ContactData deletedContact = before.iterator().next();
   app.contact().delete(deletedContact, "home");
   assertThat(app.contact().count(), equalTo(before.size()-1));
   Contacts after = app.db().contacts();
   assertThat(after, equalTo(before.without(deletedContact)));
   verifyContactListUI();


  }
  @Test
  public void testContactDeletionEditPage() throws Exception {
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact, "edit");
    assertThat(app.contact().count(), equalTo(before.size()-1));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(deletedContact)));
    verifyContactListUI();
  }

  @Test
  public void testContactDeletionViewEditPage() throws Exception {
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact, "details");
    assertThat(app.contact().count(), equalTo(before.size()-1));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(deletedContact)));
    verifyContactListUI();
  }


  @Test
  public void testAllContactsDeletionHome() throws Exception {
    app.contact().deleteAll();
    assertThat(app.contact().count(), equalTo(0));
    assertThat(app.db().contacts().size(), equalTo(0)); //check from DB
  }

}
