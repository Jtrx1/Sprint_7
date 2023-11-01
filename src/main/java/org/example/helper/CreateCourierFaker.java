package org.example.helper;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import org.example.model.CreateCourier;

import java.util.Locale;

public class CreateCourierFaker {
    @Step("Генерируем фейкового курьера")
    public static CreateCourier getFakerCourier() {

        Faker faker = new Faker(new Locale("RU"));

        String login = faker.name().username();
        String password = faker.internet().password();
        String firstname = faker.name().firstName();

        return new CreateCourier(login, password, firstname);
    }
}





