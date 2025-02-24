import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ReqresTests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    public void getUsersListTest() {
        Response response = RestAssured.given()
                .when()
                .get("/api/users?page=2")
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code должен быть 200");
    }

    @Test
    public void createUserTest() {
        String requestBody = "{\"name\": \"morpheus\", \"job\": \"leader\"}";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/users")
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 201, "Status code должен быть 201");
        Assert.assertTrue(response.getBody().asString().contains("morpheus"), "Имя пользователя должно содержать 'morpheus'");
    }

    @Test
    public void deleteUserTest() {
        Response response = RestAssured.given()
                .when()
                .delete("/api/users/2")
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 204, "Status code должен быть 204");
    }
}