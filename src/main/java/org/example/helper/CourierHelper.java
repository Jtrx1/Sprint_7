package org.example.helper;

import org.example.client.CourierApiClient;
import org.example.model.LoginCourierRequest;
import org.example.model.LoginCourierResponse;

public class CourierHelper {
    CourierApiClient courierApiClient = new CourierApiClient();

    public LoginCourierResponse login(LoginCourierRequest loginCourierRequest) {
        return courierApiClient.loginCourier(loginCourierRequest).then().statusCode(200).and().extract().as(LoginCourierResponse.class);
    }
}
