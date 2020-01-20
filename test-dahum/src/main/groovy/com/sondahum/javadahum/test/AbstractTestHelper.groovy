package com.sondahum.javadahum.test

import com.sondahum.javadahum.TestDahumMockApplicationStarter;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestDahumMockApplicationStarter.class)
abstract class AbstractTestHelper {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

}