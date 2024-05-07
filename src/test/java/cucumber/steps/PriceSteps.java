package cucumber.steps;

import static io.restassured.RestAssured.get;

import com.example.hexagonal.domain.models.Price;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;

public class PriceSteps {

    private LocalDateTime applicationDate;
    private Integer productId;
    private Integer brandId;
    private int responseStatusCode;
    private ResponseBody responseBody;

    @Given("a customer with the applicationDate {string}, the product id {string} and brand id {string}")
    public void a_customer_with_the_application_date_the_product_id_and_brand_id(String applicationDate, String productId,
        String brandId) {
        this.applicationDate = LocalDateTime.parse(applicationDate);
        this.productId = Integer.parseInt(productId);
        this.brandId = Integer.parseInt(brandId);
    }

    @When("the customer does the request GET a {string} with the specific params")
    public void the_customer_does_the_request_get_a_with_the_specific_params(String endpoint) {
        Response response = get(
            "http://localhost:8080" + endpoint + "?applicationDate=" + applicationDate + "&productId=" + productId + "&brandId=" +
                brandId);

        responseStatusCode = response.getStatusCode();
        responseBody = response.getBody();
    }

    @Then("the server should be response with {string}")
    public void the_server_should_be_response_with(String expectedStatusCode) {
        Assertions.assertEquals(Integer.parseInt(expectedStatusCode), responseStatusCode);
    }

    @And("return the next message {string}")
    public void return_the_next_message(String expectedMessage) {
        if (responseStatusCode != HttpStatus.OK.value()) {
            Assertions.assertTrue(((RestAssuredResponseImpl) responseBody).getGroovyResponse().asString().contains(expectedMessage));
        } else {
            Price price = responseBody.as(Price.class);
            Assertions.assertEquals(Double.parseDouble(expectedMessage), price.price());
        }
    }
}
