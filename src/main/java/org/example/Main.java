package org.example;

import java.math.BigDecimal;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)  {

        Wallet wallet = new Wallet();
        Card card = new Card("3345678901234567");
        System.out.println(card + (wallet.add(card) ? " добавлена" : " уже сохранена"));
        card = new Card("2345678901234567");
        System.out.println(card + (wallet.add(card) ? " добавлена" : " уже сохранена"));
        card = new Card("2345678901234567");
        System.out.println(card + (wallet.add(card) ? " добавлена" : " уже сохранена"));
        Cash cash = new Cash();
        System.out.println(cash + (wallet.add(cash) ? " добавлен" : " уже сохранен"));
        cash = new Cash();
        System.out.println(cash + (wallet.add(cash) ? " добавлен" : " уже сохранен"));
        Sbp sbp = new Sbp();
        System.out.println(sbp + (wallet.add(sbp) ? " добавлен" : " уже сохранен"));
        sbp = new Sbp();
        System.out.println(sbp + (wallet.add(sbp) ? " добавлен" : " уже сохранен"));
        card = new Card("4345678901234567");
        System.out.println(card + (wallet.add(card) ? " добавлена" : " уже сохранена"));
        card = new Card("4345678901234567");
        System.out.println(card + (wallet.add(card) ? " добавлена" : " уже сохранена"));


        PaymentSelector choise = new PaymentSelector();
        BigDecimal amount = BigDecimal.valueOf(5);
        PaymentMethod best = choise.cheapest(amount,wallet.all());
        System.out.println("Оптимальный способ оплаты: "+best+" Итоговая сумма: "+best.total(amount));
        amount = BigDecimal.valueOf(1000);
        best = choise.cheapest(amount,wallet.all());
        System.out.println("Оптимальный способ оплаты: "+best+" Итоговая сумма: "+best.total(amount));
    }
}