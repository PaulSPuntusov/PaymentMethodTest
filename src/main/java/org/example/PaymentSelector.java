package org.example;

import java.math.BigDecimal;
import java.util.List;

public class PaymentSelector {


    public PaymentMethod cheapest(BigDecimal sum, List<PaymentMethod> methods )  {
       PaymentMethod best = null;
       for(PaymentMethod method :methods) {
           if (method.isAvailable(sum)) {
               if (best == null || method.total(sum).compareTo(best.total(sum)) < 0) {
                   best = method;
               }
           }
       }
       if (best == null) {
           try {
               throw new Exception(" нет доступных способов оплаты ");
           } catch (Exception e) {
               throw new IllegalStateException(e);
           }
       }
       return best;
    }
}

