package cucumber.steps;

import com.example.hexagonal.HexagonalApplication;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = HexagonalApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@ContextConfiguration
public class CucumberSpringConfig {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        try {
            String initSql = new String(Files.readAllBytes(Paths.get("src/test/resources/db/schema.sql")));
            String dataSql = new String(Files.readAllBytes(Paths.get("src/test/resources/db/data.sql")));
            jdbcTemplate.execute(initSql);
            jdbcTemplate.execute(dataSql);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
