import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class ListOfOrdersTest {

    @Before
    public void setUp() {
        RestAssured.baseURI="https://qa-scooter.praktikum-services.ru/api/v1/orders";

    }
    @Test
    @DisplayName("Проверяем список заказов")
    @Description("Проверяем, что возвращается код 200 и поле orders не пустое")
    public void OrdersListTest(){
        given()
                .contentType(ContentType.JSON)
                .and()
                .queryParam("limit", 30)
                .queryParam("page", 0)
                .get()
                .then()
                .assertThat().statusCode(SC_OK)
                .and()
                .body("orders", notNullValue());

    }
}
