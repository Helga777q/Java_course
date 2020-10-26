package addressbook.tests.tests;

import addressbook.tests.model.GroupData;
import addressbook.tests.model.Groups;
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

public class GroupCreationTests extends TestBase {

 @DataProvider // reading of xml file
 public Iterator<Object[]> validGroups() throws IOException {
   BufferedReader reader = new BufferedReader((new FileReader(new File("src/test/resources/groups.xml"))));
   String xml = "";
   String line = reader.readLine();
   while(line != null){
     xml += line;
     line = reader.readLine();
   }
   XStream xstream = new XStream();
   xstream.processAnnotations(GroupData.class);
   List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml);
   return groups.stream().map((g)-> new Object[] {g}).collect(Collectors.toList()).iterator();
 }


  @Test(dataProvider = "validGroups")
  public void testGroupCreation(GroupData group)  {
    //GroupData group = new GroupData().withName(name).withHeader(header).withFooter(footer);
    app.goTo().groupPage();
    Groups before = app.group().all();
    app.group().create(group);
    Groups after = app.group().all();
    assertThat(after.size(), equalTo(before.size() + 1));
    //group.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt());
    assertThat(after, equalTo(before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));

  }


  @DataProvider // reading of CSV file
  public Iterator<Object[]> invalidGroups() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    BufferedReader reader = new BufferedReader((new FileReader(new File("src/test/resources/groupsnegative.csv"))));
    String line = reader.readLine();
    while(line != null){
      String[] split = line.split(",");
      list.add(new Object[] {new GroupData().withName(split[0]).withHeader(split[1]).withFooter(split[2])});
      line = reader.readLine();
    }
    return list.iterator();
  }

  @Test(dataProvider = "invalidGroups")
  public void testNegativeGroupCreation(GroupData group)  {
    app.goTo().groupPage();
    Groups before = app.group().all();
    app.group().create(group);
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(before));
  }
}
