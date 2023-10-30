import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.client.OrderApiClient;
import org.example.model.CreateOrderRequest;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.*;

import org.example.model.CreateOrderResponse;
import org.example.model.OrderCancelRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class CreateOrderTest {
    private OrderApiClient orderApiClient;
    private String track;
    String[] colors;

    public CreateOrderTest(String[] colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    @Step("Подготавливаем тест, параметризация")
    public static Object[][] getColors() {
        String[] color_black = {"BLACK"};
        String[] color_grey = {"GREY"};
        String[] color_all = {"BLACK", "GREY"};
        String[] color_none = {""};
        return new Object[][]{
                {color_black},
                {color_grey},
                {color_all},
                {color_none}
        };
    }

    @Before
    @Step("Подготавливаем тест")
    public void setUp() {
        orderApiClient = new OrderApiClient();

    }

    @Test
    @DisplayName("Создаем заказы. Тест с параметрами")
    @Description("Проверяем, что возвращается код 200 и есть track")
    @Step("Создаем заказ")
    public void successCreateOrderTest() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest(
                "Ivan",
                "Ivanov",
                "Russia",
                "Kreml",
                "+79170000000",
                2,
                "2024-01-01",
                "Без комментариев",
                colors
        );
        Response createResponse = orderApiClient.order(createOrderRequest);
        assertEquals(SC_CREATED, createResponse.statusCode());
        CreateOrderResponse createOrderResponse = createResponse.as(CreateOrderResponse.class);
        track = createOrderResponse.getTrack();
        assertNotNull(createOrderResponse.getTrack());
    }

    @After
    public void teardown() {
        if (track != null) {
            OrderCancelRequest orderCancelRequest = new OrderCancelRequest(track);
            orderApiClient.orderCancel(orderCancelRequest);
        }
    }
}
