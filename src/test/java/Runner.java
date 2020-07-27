import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void main(String[] args) {

        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG tng = new TestNG();
        tng.addListener(tla);

        XmlSuite suite = new XmlSuite();
        suite.setName("Wildberries");

        List<String> files = new ArrayList<String>() {{
            add("src/main/resources/wildberries.xml");
        }};
        suite.setSuiteFiles(files);

        List<XmlSuite> suites = new ArrayList();
        suites.add(suite);
        tng.setXmlSuites(suites);

        tng.run();
    }
}
