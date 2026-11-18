package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface PaymentMethod {
BigDecimal fee(BigDecimal sum);
    default BigDecimal total(BigDecimal sum) {
            return sum.add(fee(sum)).setScale(2, RoundingMode.HALF_UP);
    };
    default boolean isAvailable(BigDecimal sum) {
        return true;
    }
}
