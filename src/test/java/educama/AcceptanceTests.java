package educama;

import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import net.serenitybdd.jbehave.SerenityStories;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EducamaApplication.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=8081" })
public class AcceptanceTests extends SerenityStories {
}
