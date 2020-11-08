package addressbook.tests.tests;

import addressbook.tests.model.GroupData;
import addressbook.tests.model.Groups;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  @DataProvider // reading of xml file
  public Iterator<Object[]> validGroupsFromXml() throws IOException {
    try (BufferedReader reader = new BufferedReader((new FileReader(new File("src/test/resources/groups.xml"))))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(GroupData.class);
      List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml);
      return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider // reading of xml json file
  public Iterator<Object[]> validGroupsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader((new FileReader(new File("src/test/resources/groups.json"))))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>() {
      }.getType());
      return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider // reading of CSV file
  public Iterator<Object[]> invalidGroupsFromCsvFile() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    try (BufferedReader reader = new BufferedReader((new FileReader(new File("src/test/resources/groupsnegative.csv"))))) {
      String line = reader.readLine();
      while (line != null) {
        String[] split = line.split(",");
        list.add(new Object[]{new GroupData().withName(split[0]).withHeader(split[1]).withFooter(split[2])});
        line = reader.readLine();
      }
      return list.iterator();
    }
  }


  @Test(dataProvider = "validGroupsFromJson")
  public  void testGroupCreation(GroupData group) {
    app.goTo().groupPage();
    Groups before = app.db().groups();
    app.group().create(group);
    Groups after = app.db().groups();
    assertThat(app.group().count(), equalTo(before.size() + 1));
    assertThat(after, equalTo(before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));

  }



  @Test(dataProvider = "invalidGroupsFromCsvFile")
  public void testNegativeGroupCreation(GroupData group) {
    app.goTo().groupPage();
    Groups before = app.db().groups();
    app.group().create(group);
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.db().groups();
    assertThat(after, equalTo(before));
    verifyGroupListInUI();
  }
}
