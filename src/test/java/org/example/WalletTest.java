package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WalletTest {
    private Wallet wallet;


    @BeforeEach
    public void setup(){
        wallet = new Wallet();
    }


    @Test
    @DisplayName("Дубликаты карты не добавляются")
    public void cardDuplicate(){
        Card card1 = new Card("1111222233334444");
        Card card2 = new Card("1111222233334444");
        assertTrue(wallet.add(card1),"Уникальная карта добавлена");
        assertFalse(wallet.add(card2), "Дубликат карты не добавлен");
        assertEquals(1,wallet.all().size(), "В кошельке только один метод оплаты");
    }

    @Test
    @DisplayName("Напрямую в List ничего добавить нельзя")
    public void immutableList(){
        Card card1 = new Card("1111222233334444");
        Card card2 = new Card("1111222233335555");
        wallet.add(card1);
        assertThrows(UnsupportedOperationException.class,() -> wallet.all().add(card2));
    }

    @Test
    @DisplayName("Дубликаты СБП не добавляются")
    public void sbpDuplicate(){
        Sbp sbp1 = new Sbp();
        Sbp sbp2 = new Sbp();
        assertTrue(wallet.add(sbp1),"Добавлен первый метод СБП");
        assertFalse(wallet.add(sbp2)," Второй метод СБП не добавдяется");
        assertEquals(1,wallet.all().size(),"Добавлен только один метод");
    }

    @Test
    @DisplayName("Карта после СБП и налички добавляются корректно")
    public void cardAfterAll(){
        Sbp sbp = new Sbp();
        Cash cash = new Cash();
        Card card = new Card("1111222233334444");
        assertTrue(wallet.add(sbp),"СБП должен добавиться");
        assertTrue(wallet.add(cash),"карта должна добавиться");
        assertTrue(wallet.add(card), "Карта должна добавиться после налички и СБП");
        assertTrue(wallet.all().contains(card), "Карта должна быть в кошельке после добавления налички и СБП");
        assertEquals(3, wallet.all().size(), "все три метода добавились");
    }
}
