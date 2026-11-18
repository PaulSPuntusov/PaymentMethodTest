package org.example;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Objects;

public class Card implements PaymentMethod {
    String cardNumber;
    @Override
    public BigDecimal fee(BigDecimal sum) {
        return sum.multiply(BigDecimal.valueOf(15)).divide(BigDecimal.valueOf(1000)).setScale(2, RoundingMode.HALF_UP);
    }



    public Card(String cardNumber)  {
        if (cardNumber == null || !cardNumber.matches("\\d{16}")) {
            throw new IllegalArgumentException(" Номер карты должен состоять из 16-ти цифр");
        }
            this.cardNumber = cardNumber;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return cardNumber.equals(((Card)o).cardNumber);
    }
    @Override
    public int hashCode() {
        return Objects.hash(cardNumber);
    }

    @Override
    public String toString() {
        return " карта *" +
                 cardNumber.substring(12) ;
    }
}
