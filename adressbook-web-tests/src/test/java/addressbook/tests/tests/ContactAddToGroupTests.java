package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.Contacts;
import addressbook.tests.model.GroupData;
import addressbook.tests.model.Groups;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddToGroupTests extends TestBase {

  @BeforeTest
  public void preConditions(){
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
  public void addContactToGroupTest(){
    Contacts contacts = app.db().contacts();
    Groups groups = app.db().groups();
    GroupData group = groups.iterator().next();
    ContactData contact = contacts.iterator().next();
    if (contact.getGroups().stream().filter((g)-> g.getId()==group.getId()).collect(Collectors.toList()).size()==0){
      app.goTo().homePage();
      app.contact().addContactToGroup(contact, group);
      ContactData contactDb = app.db().contacts().stream().filter((c)-> c.getId()== contact.getId()).collect(Collectors.toList()).get(0);
      assertThat(contact.inGroup(group).getGroups().size(), equalTo(contactDb.getGroups().size()));
      assertThat(contact.inGroup(group), equalTo(contactDb));
    } else {
      app.goTo().groupPage();
      GroupData newGroup =new GroupData().withName(String.format("Test %s", contact.getId())).withHeader("test for update").withFooter("test for update");
      app.group().create(newGroup);
      app.goTo().homePage();
      int groupId= app.db().groups().stream().mapToInt((g) -> g.getId()).max().getAsInt();
      app.contact().addContactToGroup(contact, newGroup.withId(groupId));
      ContactData contactDb = app.db().contacts().stream().filter((c)-> c.getId()== contact.getId()).collect(Collectors.toList()).get(0);
      assertThat(contact.inGroup(newGroup.withId(groupId)).getGroups().size(), equalTo(contactDb.getGroups().size()));
      assertThat(contact.inGroup(newGroup.withId(groupId)), equalTo(contactDb));
    }


  }


}
