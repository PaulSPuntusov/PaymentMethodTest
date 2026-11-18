package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {
    @Test
    @DisplayName("Проверка процента, взимаемого при оплате по карте")
    public void test_1000() {
        Wallet wallet = new Wallet();
        Card card = new Card("3345678901234567");
        wallet.add(card);
        PaymentSelector choise = new PaymentSelector();
        BigDecimal amount = BigDecimal.valueOf(1000);
        PaymentMethod best = choise.cheapest(amount,wallet.all());
        BigDecimal result = best.total(amount);
        assertEquals(0,BigDecimal.valueOf(1015).compareTo(result)
                , "Проверка итоговой суммы платежа по карте в 1015р при оплате товара стоимостью 1000р");
    }


}
