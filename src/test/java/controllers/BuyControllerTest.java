package controllers;

import models.Tax;
import models.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

public class BuyControllerTest {

    private BuyController buyControllerTest = new BuyController();
    private static ArrayList<Transaction> operationBuy = new ArrayList();
    private static ArrayList<Transaction> operationBuyAverage = new ArrayList();
    private static ArrayList<Transaction> operationWithoutBuy = new ArrayList();

    @BeforeAll
    private static void setUp() {
        operationBuyAverage.add(new Transaction("buy", 10, 10000));
        operationBuyAverage.add(new Transaction("buy", 25, 5000));

        operationBuy.add(new Transaction("buy", 10, 10000));
        operationBuy.add(new Transaction("sell", 15, 5000));

        operationWithoutBuy.add(new Transaction("asdasdasdasd", 10, 10000));
        operationWithoutBuy.add(new Transaction("sell", 15, 5000));
    }

    @Test
    public void calculateWeightedAveragePriceOk() {
        Transaction transactionTest = buyControllerTest.calculateWeightedAveragePrice(operationBuyAverage);
        assert transactionTest.getOperation().equals("base");
        assert transactionTest.getQuantity() == 15000;
        assert transactionTest.getUnitCost() == 15;
    }

    @Test
    public void calculateTaxesSellOkWithAverage() {
        operationBuyAverage.add(new Transaction("sell", 15, 10000));
        ArrayList<Tax> taxesTest = buyControllerTest.calculateTaxes(operationBuyAverage);
        assert taxesTest.size() == 2;
        assert taxesTest.get(0).getAmount() == 0;
        assert taxesTest.get(1).getAmount() == 0;
    }

    @Test
    public void calculateTaxesSellOk() {
        ArrayList<Tax> taxesTest = buyControllerTest.calculateTaxes(operationBuy);
        assert taxesTest.size() == 1;
        assert taxesTest.get(0).getAmount() == 0;
    }

    @Test
    public void calculateTaxesSellWithError() {
        try {
            buyControllerTest.calculateTaxes(operationWithoutBuy);
        } catch (Exception e) {
            assert e.getMessage().equals("Operation buy not found");
        }
    }
}
