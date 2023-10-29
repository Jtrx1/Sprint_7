package org.example.client;

import io.restassured.response.Response;
import org.example.model.CreateCourier;
import org.example.model.CreateOrderRequest;
import org.example.model.LoginCourierRequest;

import static org.example.config.ConfigURL.BASE_URL;

public class CourierApiClient extends BaseApiClient{
    public Response createCourier(CreateCourier createCourier) {
        return getPostSpec()
                .body(createCourier)
                .when()
                .post(BASE_URL + "/api/v1/courier");
    }
    public Response loginCourier(LoginCourierRequest loginCourierRequest) {
        return getPostSpec()
                .body(loginCourierRequest)
                .when()
                .post(BASE_URL + "/api/v1/courier/login");
    }
    public Response deleteCourier(String id){
        return getPostSpec()
                .when()
                .delete(BASE_URL + "/api/v1/courier/"+id);
    }

    public Response order(CreateOrderRequest createOrderRequest){
        return getPostSpec()
                .body(createOrderRequest)
                .when()
                .post(BASE_URL + "/api/v1/orders");
    }
    }

