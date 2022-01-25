package controllers;

import models.Tax;
import models.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SellControllerTest {

    private SellController sellControllerTest = new SellController();
    private static ArrayList<Transaction> operationSellLessThan2000 = new ArrayList();
    private static ArrayList<Transaction> operationSellWithProfit = new ArrayList();
    private static ArrayList<Transaction> operationWithLossPot = new ArrayList();
    private static BuyController buyControllerTest = new BuyController();

    @BeforeAll
    private static void setUp() {
        operationSellLessThan2000.add(new Transaction("buy", 10, 100));
        operationSellLessThan2000.add(new Transaction("sell", 15, 50));
        operationSellLessThan2000.add(new Transaction("sell", 15, 50));

        operationSellWithProfit.add(new Transaction("buy", 10, 10000));
        operationSellWithProfit.add(new Transaction("sell", 20, 5000));
        operationSellWithProfit.add(new Transaction("sell", 5, 5000));

        operationWithLossPot.add(new Transaction("buy", 10, 10000));
        operationWithLossPot.add(new Transaction("sell", 2, 5000));
        operationWithLossPot.add(new Transaction("sell", 20, 2000));
        operationWithLossPot.add(new Transaction("sell", 20, 2000));
        operationWithLossPot.add(new Transaction("sell", 25, 1000));
    }

    @Test
    public void calculateTaxesWithTotalAmountLessThan20000() {
        ArrayList<Tax> taxesTest = sellControllerTest.calculateTaxes(operationSellLessThan2000);
        assert taxesTest.size() == 2;
        assert taxesTest.get(0).getAmount() == 0;
        assert taxesTest.get(1).getAmount() == 0;
    }

    @Test
    public void calculateTaxesWithProfit() {
        buyControllerTest.calculateTaxes(operationSellWithProfit);
        sellControllerTest.setBaseTransaction(buyControllerTest.getBaseTransaction());
        ArrayList<Tax> taxesTest = sellControllerTest.calculateTaxes(operationSellWithProfit);
        assert taxesTest.size() == 2;
        assert taxesTest.get(0).getAmount() == 10000;
        assert taxesTest.get(1).getAmount() == 0;
    }

    @Test
    public void calculateTaxesWithLossPot() {
        buyControllerTest.calculateTaxes(operationWithLossPot);
        sellControllerTest.setBaseTransaction(buyControllerTest.getBaseTransaction());
        ArrayList<Tax> taxesTest = sellControllerTest.calculateTaxes(operationWithLossPot);
        assert taxesTest.size() == 4;
        assert taxesTest.get(0).getAmount() == 0;
        assert taxesTest.get(1).getAmount() == 0;
        assert taxesTest.get(2).getAmount() == 0;
        assert taxesTest.get(3).getAmount() == 3000;
    }
}
