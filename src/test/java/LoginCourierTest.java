import io.qameta.allure.Description;
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
    public void setUp(){
        courierApiClient = new CourierApiClient();
        loginCourierRequest = new LoginCourierRequest("m.tuganov", "12345678");
    }
    @Test
    @DisplayName("Успешная авторизация")
    @Description("Проверяем, что возвращается код 200 и id возвращается")
    public void successfulAuthorization(){
        Response createResponse = courierApiClient.loginCourier(loginCourierRequest);
        assertEquals(SC_OK, createResponse.statusCode());
        LoginCourierResponse loginCourierResponse = createResponse.as(LoginCourierResponse.class);
        assertNotNull(loginCourierResponse.getId());
    }

    @Test
    @DisplayName("Авторизация без логина")
    @Description("Проверяем, что возвращается код 400 и message")
    public void AuthorizationWithoutLogin(){
        loginCourierRequest.setLogin("");
        Response createResponse = courierApiClient.loginCourier(loginCourierRequest);
        assertEquals(SC_BAD_REQUEST, createResponse.statusCode());
        LoginCourierResponse loginCourierResponse = createResponse.as(LoginCourierResponse.class);
        String expected = "Недостаточно данных для входа";
        String actual =loginCourierResponse.getMessage();
        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Авторизация без пароля")
    @Description("Проверяем, что возвращается код 400 и message")
    public void AuthorizationWithoutPassword(){
        loginCourierRequest.setPassword("");
        Response createResponse = courierApiClient.loginCourier(loginCourierRequest);
        assertEquals(SC_BAD_REQUEST, createResponse.statusCode());
        LoginCourierResponse loginCourierResponse = createResponse.as(LoginCourierResponse.class);
        String expected = "Недостаточно данных для входа";
        String actual =loginCourierResponse.getMessage();
        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Авторизация неправильным логином")
    @Description("Проверяем, что возвращается код 404 и message")
    public void AuthorizationWithIncorrectLogin(){
        loginCourierRequest.setLogin(loginCourierRequest.getLogin()+"1");
        Response createResponse = courierApiClient.loginCourier(loginCourierRequest);
        assertEquals(SC_NOT_FOUND, createResponse.statusCode());
        LoginCourierResponse loginCourierResponse = createResponse.as(LoginCourierResponse.class);
        String expected = "Учетная запись не найдена";
        String actual =loginCourierResponse.getMessage();
        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Авторизация неправильным паролем")
    @Description("Проверяем, что возвращается код 400 и message")
    public void AuthorizationWithIncorrectPassword(){
        loginCourierRequest.setLogin(loginCourierRequest.getPassword()+"1");
        Response createResponse = courierApiClient.loginCourier(loginCourierRequest);
        assertEquals(SC_NOT_FOUND, createResponse.statusCode());
        LoginCourierResponse loginCourierResponse = createResponse.as(LoginCourierResponse.class);
        String expected = "Учетная запись не найдена";
        String actual =loginCourierResponse.getMessage();
        assertEquals(expected, actual);
    }

}
