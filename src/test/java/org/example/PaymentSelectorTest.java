package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentSelectorTest {
    private Wallet wallet;

    @BeforeEach
    public void setup(){
        wallet = new Wallet();
    }

    @ParameterizedTest
    @CsvSource({
            "0,org.example.Card",
            "9,org.example.Card",
            "10,org.example.Sbp",
            "11,org.example.Sbp",
    })
    @DisplayName("Проверка выбора выгодного варианта платежа")
    public void choiceTest(BigDecimal sum, Class<? extends PaymentMethod> expectedType){
        Sbp sbp = new Sbp();
        Cash cash = new Cash();
        Card card = new Card("1111222233334444");
        assertTrue(wallet.add(sbp),"СБП должен добавиться");
        assertTrue(wallet.add(cash),"наличка должна добавиться");
        assertTrue(wallet.add(card), "Карта должна добавиться после налички и СБП");
        PaymentMethod best = new PaymentSelector().cheapest(sum,wallet.all());
        assertInstanceOf(expectedType,best);
    }

    @Test
    @DisplayName("Проверкаповедения до выбора способов оплаты")
    public void emptyWalletTest() {
        assertThrows(IllegalStateException .class, () -> new PaymentSelector().cheapest(BigDecimal.valueOf(5), wallet.all()));
    }

    @Test
    @DisplayName("Проверка выбора карты, как единственного средства оплаты")
    public void onlyCardAvailableTest() {
        Card card = new Card("1111222233334444");
        assertTrue(wallet.add(card), "Карта должна добавиться");
        PaymentMethod best = new PaymentSelector().cheapest(BigDecimal.valueOf(5),wallet.all());
        assertEquals(card,best);
    }

    @Test
    @DisplayName("Проверка отказа оплаты СБП на сумме меньше 10 руб")
    public void onlySbpTest() {
        Sbp sbp = new Sbp();
        assertTrue(wallet.add(sbp),"СБП должен добавиться");
        assertThrows(IllegalStateException.class, () -> new PaymentSelector().cheapest(BigDecimal.valueOf(5), wallet.all()));
    }

}
