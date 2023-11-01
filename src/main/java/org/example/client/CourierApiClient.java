package org.example.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.model.CreateCourier;
import org.example.model.LoginCourierRequest;

public class CourierApiClient extends BaseApiClient {

    @Step("Создаем курьера")
    public Response createCourier(CreateCourier createCourier) {
        return getPostSpec()
                .body(createCourier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Авторизуемся курьером")
    public Response loginCourier(LoginCourierRequest loginCourierRequest) {
        return getPostSpec()
                .body(loginCourierRequest)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Удаляем курьера")
    public Response deleteCourier(String id) {
        return getPostSpec()
                .when()
                .delete("/api/v1/courier/" + id);
    }


}

