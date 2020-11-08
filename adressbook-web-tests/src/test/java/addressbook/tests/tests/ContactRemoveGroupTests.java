package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.Contacts;
import addressbook.tests.model.GroupData;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ContactRemoveGroupTests extends TestBase {


  @BeforeTest
  public void preconditions(){
    //check contacts exists
    if (app.db().contacts().size()==0){
      app.contact().create(new ContactData()
              .withFirstName("Monica").withLastName("Geller").withAddress("NY Central perk")
              .withEmail("mon@friends").withEmailSecond("email22@test").withEmailThird("email@test.com")
              .withHome("+1111").withMobile("+222-333").withWork("+1-0000")
              .withBirthDate("11").withBirthMonth("October").withBirthYear("2001")
      );
    }
    //check group exists
    if (app.db().groups().size()==0){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("Test1").withHeader("test for update").withFooter("test for update"));
      app.goTo().homePage();
    }

  }

  @Test
  public void removeContactFromGroupTest(){
    Contacts contacts = app.db().contacts();
    ContactData contact = contacts.iterator().next();
    //if (contact.getGroups().size()==0){
    //  GroupData group = app.db().groups().iterator().next();
    //  app.contact().addContactToGroup(contact,group);
  //  }
    GroupData group = contact.getGroups().iterator().next();
    app.contact().groupContactPageById(contact);
    app.contact().selectContactById(contact.getId());
    app.contact().removeFromGroup();
    app.contact().returnToGroupContactPage(group);
    System.out.println(contact);
    ContactData contactDb = app.db().contacts().stream().filter((c)-> c.getId()==contact.getId()).collect(Collectors.toList()).get(0);
    System.out.println(contactDb);






  }


}
