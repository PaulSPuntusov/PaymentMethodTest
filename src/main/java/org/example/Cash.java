package org.example;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Objects;

public class Cash implements PaymentMethod {
    //BigInteger sum1;
    @Override
    public BigDecimal fee(BigDecimal sum) {
        return BigDecimal.valueOf(50).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        else return true;
    }

    public int hashCode() {
        return Objects.hash();
    }

    @Override
    public String toString() {
        return " Наличные ";
    }


}
