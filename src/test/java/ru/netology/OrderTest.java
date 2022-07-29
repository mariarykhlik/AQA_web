package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class OrderTest {

    @BeforeEach
    void openWebService() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTestSuccess() {
        $("[data-test-id=name] input").setValue("Петров-Водкин Иван");
        $("[data-test-id=phone] input").setValue("+79876543210");
        $("[data-test-id=agreement]").click();
        $("button").shouldBe(exactText("Продолжить")).click();
        $("[data-test-id=order-success]").shouldBe(exactText("Ваша заявка успешно отправлена!" +
                " Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTestErrorNameMessage() {
        $("[data-test-id=name] input").setValue("Abcd Efg");
        $("[data-test-id=phone] input").setValue("+79876543210");
        $("[data-test-id=agreement]").click();
        $("button").shouldBe(exactText("Продолжить")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(exactText("Имя и Фамилия указаные" +
                " неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestErrorPhoneMessage() {
        $("[data-test-id=name] input").setValue("Петров Иван");
        $("[data-test-id=phone] input").setValue("+7987654");
        $("[data-test-id=agreement]").click();
        $("button").shouldBe(exactText("Продолжить")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(exactText("Телефон указан неверно." +
                " Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestEmptyFieldMessage() {
        $("[data-test-id=name] input").setValue("Петров Иван");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $("button").shouldBe(exactText("Продолжить")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(exactText("Поле обязательно для " +
                "заполнения"));
    }

    @Test
    void shouldTestCheckBoxChecked() {
        $("[data-test-id=name] input").setValue("Петров Иван");
        $("[data-test-id=phone] input").setValue("+79876543210");
        $("button").shouldBe(exactText("Продолжить")).click();
        $("[data-test-id=agreement].input_invalid").shouldBe(exactText("Я соглашаюсь с условиями обработки и" +
                " использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }
}