package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    @Test
    @DisplayName("Комиссия карты 1,5% от суммы 1000 равна 15.00")
    public void feeIs15PercentOfAmount() {
        Card card = new Card("3345678901234567");
        assertEquals(0,BigDecimal.valueOf(15).compareTo(card.fee(BigDecimal.valueOf(1000))));
    }

    @ParameterizedTest
    @CsvSource({
            "1000, 15.00",
            "1001, 15.02",
            "99,    1.49",
            "0,     0.00"
    })
    void feeCalculatedCorrectly(BigDecimal sum, BigDecimal expectedFee) {
        Card card = new Card("1111222233334444");
        assertEquals(expectedFee, card.fee(sum));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 15, 17, 20})
    void wrongLengthRejected(int length) {
        String cardNumber = "1".repeat(length);
            assertThrows(IllegalArgumentException.class, () -> new Card(cardNumber));
    }

    @ParameterizedTest
    @NullSource()
    void nullLengthRejected(String cardNumber) {
        assertThrows(IllegalArgumentException.class, () -> new Card(cardNumber));
    }

    @Test
    void equalCards(){
        Card card1 = new Card("1111222233334444");
        Card card2 = new Card("1111222233334444");
        Card card3 = new Card("1111222233334445");
        assertAll (
                () ->    assertEquals(card1,card2," Одинаковые номера равны"),
                () -> assertNotEquals(card1,card3, "разные номера — не равны"),
                () -> assertEquals(card1.hashCode(),card1.hashCode(),"равные объекты — равный hashCode")
        );

    }

    @Test
    void numberMask(){
        Card card1 = new Card("1111222233334444");
        boolean b = " карта *4444".equals(card1.toString());
        assertEquals(true, b);
        assertAll(
                () -> assertTrue(card1.toString().equals(" карта *4444")),
        () -> assertFalse(card1.toString().contains("1111222233334444"))
        );
    }
}
