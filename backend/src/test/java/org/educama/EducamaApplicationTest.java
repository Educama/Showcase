package org.educama;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests for {@link EducamaApplication}.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EducamaApplicationTest {

    /**
     * This test verifies that the Spring Context is created successfully, i.e.
     * the application starts successfully.
     */
    @Test
    public void contextLoads() {
    }

}
