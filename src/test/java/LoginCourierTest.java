import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.client.CourierApiClient;
import org.example.model.LoginCourierRequest;
import org.example.model.LoginCourierResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoginCourierTest {
    private CourierApiClient courierApiClient;
    private LoginCourierRequest loginCourierRequest;

    @Before
    @Step("Подготавливаем тест")
    public void setUp() {
        courierApiClient = new CourierApiClient();
        loginCourierRequest = new LoginCourierRequest("m.tuganov", "12345678");
    }

    @Test
    @DisplayName("Успешная авторизация")
    @Step("Авторизация")
    @Description("Проверяем, что возвращается код 200 и id возвращается")
    public void successfulAuthorization() {
        Response createResponse = courierApiClient.loginCourier(loginCourierRequest);
        assertEquals(SC_OK, createResponse.statusCode());
        LoginCourierResponse loginCourierResponse = createResponse.as(LoginCourierResponse.class);
        assertNotNull(loginCourierResponse.getId());
    }

    @Test
    @DisplayName("Авторизация без логина")
    @Step("Авторизация")
    @Description("Проверяем, что возвращается код 400 и message")
    public void authorizationWithoutLogin() {
        loginCourierRequest.setLogin("");
        Response createResponse = courierApiClient.loginCourier(loginCourierRequest);
        assertEquals(SC_BAD_REQUEST, createResponse.statusCode());
        LoginCourierResponse loginCourierResponse = createResponse.as(LoginCourierResponse.class);
        String expected = "Недостаточно данных для входа";
        String actual = loginCourierResponse.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Авторизация без пароля")
    @Step("Авторизация")
    @Description("Проверяем, что возвращается код 400 и message")
    public void authorizationWithoutPassword() {
        loginCourierRequest.setPassword("");
        Response createResponse = courierApiClient.loginCourier(loginCourierRequest);
        assertEquals(SC_BAD_REQUEST, createResponse.statusCode());
        LoginCourierResponse loginCourierResponse = createResponse.as(LoginCourierResponse.class);
        String expected = "Недостаточно данных для входа";
        String actual = loginCourierResponse.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Авторизация неправильным логином")
    @Step("Авторизация")
    @Description("Проверяем, что возвращается код 404 и message")
    public void authorizationWithIncorrectLogin() {
        loginCourierRequest.setLogin(loginCourierRequest.getLogin() + "1");
        Response createResponse = courierApiClient.loginCourier(loginCourierRequest);
        assertEquals(SC_NOT_FOUND, createResponse.statusCode());
        LoginCourierResponse loginCourierResponse = createResponse.as(LoginCourierResponse.class);
        String expected = "Учетная запись не найдена";
        String actual = loginCourierResponse.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Авторизация неправильным паролем")
    @Step("Авторизация")
    @Description("Проверяем, что возвращается код 400 и message")
    public void authorizationWithIncorrectPassword() {
        loginCourierRequest.setLogin(loginCourierRequest.getPassword() + "1");
        Response createResponse = courierApiClient.loginCourier(loginCourierRequest);
        assertEquals(SC_NOT_FOUND, createResponse.statusCode());
        LoginCourierResponse loginCourierResponse = createResponse.as(LoginCourierResponse.class);
        String expected = "Учетная запись не найдена";
        String actual = loginCourierResponse.getMessage();
        assertEquals(expected, actual);
    }

}
