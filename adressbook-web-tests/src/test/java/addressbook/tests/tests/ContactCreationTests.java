package addressbook.tests.tests;

import addressbook.tests.model.ContactData;
import addressbook.tests.model.Contacts;
import addressbook.tests.model.GroupData;
import addressbook.tests.model.Groups;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  // Test Data for the tests - from CSV file
  @DataProvider
  public Iterator<Object[]> contactFromCsvFile() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")))) {
      String line = reader.readLine();
      while (line != null) {
        String[] split = line.split(";");
        list.add(new Object[]{new ContactData().withFirstName(split[0]).withLastName(split[1]).withAddress(split[2])
                .withEmail(split[3]).withEmailSecond(split[4]).withEmailThird(split[5])
                .withMobile(split[6]).withHome(split[7]).withWork(split[8])
                .withBirthDate(split[9]).withBirthMonth(split[10]).withBirthYear(split[11])});
        line = reader.readLine();
      }
      return list.iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> contactsFromXmlFile() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(ContactData.class);
      List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
      return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> contactsFromJsonFile() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
      }.getType());
      return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
    }
  }




  @Test(dataProvider = "contactFromCsvFile")
  public void testContactCreationWithCsvFile(ContactData contact) {
    Contacts before = app.db().contacts();
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));
    verifyContactListUI();
  }




  @Test(dataProvider = "contactsFromXmlFile")
  public void testContactCreationXMmlFile(ContactData contact) {
    Contacts before = app.db().contacts();
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    //verifyContactListUI();
  }




  @Test(dataProvider = "contactsFromJsonFile")
  public void testContactCreationJsonFile(ContactData contact) {
    Contacts before = app.db().contacts();
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    verifyContactListUI();
  }


  // TestData for negative tests created via DataProvider
  @DataProvider
  public Iterator<Object[]> negativeContactsTest() {
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[]{new ContactData().withFirstName("First Name'").withLastName("LastName").withEmail("test@mail.com")
    .withEmailSecond("test").withEmailThird("yyy@mail.com").withMobile("+111").withHome("+22222").withWork("+222222")
    .withBirthDate("12").withBirthMonth("December").withBirthYear("1990")});
    list.add(new Object[]{new ContactData().withFirstName("First Name2").withLastName("LastName2'").withEmail("test2@mail.com")
            .withEmailSecond("test").withEmailThird("yyy@mail.com").withMobile("+111").withHome("+22222").withWork("+222222")
            .withBirthDate("1").withBirthMonth("December").withBirthYear("1967")});
    list.add(new Object[]{new ContactData().withFirstName("First Name3'").withLastName("LastName3'").withEmail("test3@mail.com")
            .withEmailSecond("test").withEmailThird("yyy@mail.com").withMobile("+111").withHome("+22222").withWork("+222222")
            .withBirthDate("13").withBirthMonth("December").withBirthYear("1980")});
    return list.iterator();
  }


  @Test(dataProvider = "negativeContactsTest")
  public void testNegativeContactCreation(ContactData contact) {
    Contacts before = app.db().contacts();
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before));
    verifyContactListUI();
  }


  //creation of contact with Group
  @Test
  public void testContactCreationWithGroup(){
    if (app.db().groups().size()==0){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test").withHeader("test").withFooter("test"));
      app.goTo().homePage();
    }
    Groups groupsListDb = app.db().groups();
    ContactData newContact = new ContactData().withFirstName("First Name with group").withLastName("LastName").withAddress("test address")
            .withEmail("test@mail.com").withEmailSecond("test").withEmailThird("yyy@mail.com")
            .withMobile("+111").withHome("+22222").withWork("+222222")
            .withBirthDate("12").withBirthMonth("December").withBirthYear("1990")
            .inGroup(groupsListDb.iterator().next());
    Contacts before = app.db().contacts();
    app.contact().create(newContact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(
            before.withAdded(newContact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    verifyContactListUI();
  }


}