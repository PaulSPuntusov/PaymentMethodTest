package org.example;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Wallet {
    private final Set<PaymentMethod> methods = new LinkedHashSet<>();
    public boolean add(PaymentMethod method) {
        return methods.add(method);
    }


    public List<PaymentMethod> all() {
        return List.copyOf(methods);
    }
}
