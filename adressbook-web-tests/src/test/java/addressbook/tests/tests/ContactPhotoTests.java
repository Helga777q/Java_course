package addressbook.tests.tests;


import addressbook.tests.model.ContactData;
import addressbook.tests.model.Contacts;
import org.testng.annotations.Test;

import java.io.File;

public class ContactPhotoTests extends TestBase {


  @Test
  public void photoCreationContactTest (){
    app.goTo().homePage();
    File photo = new File("src/test/resources/test_photo.bmp");
    ContactData contact = new ContactData().withFirstName("Ross with photo").withLastName("Geller").withPhoto(photo);
    app.contact().create(contact);
    app.contact().returnToHomePage();
    Contacts listContacts = app.contact().all();
    contact.withId(listContacts.stream().mapToInt((c)-> c.getId()).max().getAsInt());
    app.contact().goToDetailsPageById(contact.getId());
    app.contact().isPhotoPresent();
  }


  @Test
  public void photoUpdateContactTest (){
    app.goTo().homePage();
    ContactData contact = new ContactData().withFirstName("Ross").withLastName("Geller"); // create contact without photo
    File photo = new File("src/test/resources/test_photo.bmp");
    app.contact().create(contact);
    Contacts listContacts = app.contact().all();
    contact.withId(listContacts.stream().mapToInt((c)-> c.getId()).max().getAsInt());
    contact.withPhoto(photo);
    app.contact().modify(contact, "edit"); // update contact with photo
    app.contact().goToDetailsPageById(contact.getId());
    app.contact().isPhotoPresent();

  }
}
