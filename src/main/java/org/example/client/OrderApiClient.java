package org.example.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.model.CreateOrderRequest;
import org.example.model.OrderCancelRequest;
public class OrderApiClient extends BaseApiClient{
    @Step("Создаем заказ, параметризированный тест")
    public Response order(CreateOrderRequest createOrderRequest){
        return getPostSpec()
                .body(createOrderRequest)
                .when()
                .post( "/api/v1/orders");
    }

    @Step("отменяем заказ")
    public void orderCancel(OrderCancelRequest track){
        getPostSpec()
                .body(track)
                .when()
                .put("/api/v1/orders/cancel");
    }
    @Step("GET запрос для формирования списка заказов")
    public Response orderList(){
        return getPostSpec()
                .and()
                .queryParam("limil", 30)
                .queryParam("page", 0)
                .get("/api/v1/orders");
    }

}
