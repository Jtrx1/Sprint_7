
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.client.CourierApiClient;
import org.example.helper.CourierHelper;
import org.example.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.example.helper.CreateCourierFaker.getFakerCourier;
import static org.junit.Assert.*;

public class CreateCourierTest {
    private CreateCourier createCourierRequest;
    private LoginCourierRequest loginCourierRequest;
    private CourierApiClient courierApiClient;
    private CourierHelper courierHelper;
    private String courierId;

    @Before
    @Step("Подготавливаем тесты")
    public void setUp() {
        courierApiClient = new CourierApiClient();
        courierHelper = new CourierHelper();
        createCourierRequest = getFakerCourier();
        loginCourierRequest = new LoginCourierRequest(createCourierRequest.getLogin(), createCourierRequest.getPassword());
    }

    @Test
    @DisplayName("Создаем курьера")
    @Description("Проверяем, что возвращается код 201 и id не пустой")
    public void creatingCourier() {
        Response createResponse = courierApiClient.createCourier(createCourierRequest);
        assertEquals(SC_CREATED, createResponse.statusCode());
        CreateCourierResponse createCourierResponse = createResponse.as(CreateCourierResponse.class);
        assertTrue(createCourierResponse.getOk());
        LoginCourierResponse loginCourierResponse = courierHelper.login(loginCourierRequest);
        assertNotNull(loginCourierResponse.getId());
        courierId = loginCourierResponse.getId();

    }

    @Test
    @DisplayName("Создаем двух одинаковых курьеров")
    @Description("Проверяем, что возвращается код 409 и соответствие message")
    public void creatingIdenticalCouriers() {
        Response createResponse = courierApiClient.createCourier(createCourierRequest);
        createResponse = courierApiClient.createCourier(createCourierRequest);
        CreateCourierResponse createCourierResponse = createResponse.as(CreateCourierResponse.class);
        LoginCourierResponse loginCourierResponse = courierHelper.login(loginCourierRequest);
        assertEquals(SC_CONFLICT, createResponse.statusCode());
        createCourierResponse = createResponse.as(CreateCourierResponse.class);
        courierId = loginCourierResponse.getId();
        String expected = "Этот логин уже используется. Попробуйте другой.";
        String actual = createCourierResponse.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Создаем курьера без пароля")
    @Description("Проверяем, что возвращается код 400 и соответствие message")
    public void creatingCourierWithoutPassword() {
        createCourierRequest.setPassword("");
        Response createResponse = courierApiClient.createCourier(createCourierRequest);
        assertEquals(SC_BAD_REQUEST, createResponse.statusCode());
        CreateCourierResponse createCourierResponse = createResponse.as(CreateCourierResponse.class);
        String expected = "Недостаточно данных для создания учетной записи";
        String actual = createCourierResponse.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Создаем курьера без логина")
    @Description("Проверяем, что возвращается код 400 и соответствие message")
    public void creatingCourierWithoutLogin() {
        createCourierRequest.setLogin("");
        Response createResponse = courierApiClient.createCourier(createCourierRequest);
        assertEquals(SC_BAD_REQUEST, createResponse.statusCode());
        CreateCourierResponse createCourierResponse = createResponse.as(CreateCourierResponse.class);
        String expected = "Недостаточно данных для создания учетной записи";
        String actual = createCourierResponse.getMessage();
        assertEquals(expected, actual);
    }

    @After
    @Step("Удаляем курьеров, если он создавался")
    public void teardown() {
        if (courierId != null) {
            Response deleteResponse = courierApiClient.deleteCourier(courierId);
            assertEquals(SC_OK, deleteResponse.getStatusCode());
        }
    }
}
