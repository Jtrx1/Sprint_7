import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.example.client.OrderApiClient;
import org.junit.Before;
import org.junit.Test;


import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class ListOfOrdersTest {
    private OrderApiClient orderApiClient;

    @Before
    @Step("Подготавливаем тест")
    public void setUp() {
       orderApiClient = new OrderApiClient();

    }

    @Test
    @DisplayName("Проверяем список заказов")
    @Description("Проверяем, что возвращается код 200 и поле orders не пустое")
    @Step("Проверяем что поле заказов не пустое")
    public void ordersListTest() {
        orderApiClient.orderList()
                .then()
                .assertThat().statusCode(SC_OK)
                .and()
                .body("orders", notNullValue());

    }
}
