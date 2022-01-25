package controllers;

import models.Tax;
import models.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OperationControllerTest {

    private static ArrayList<Transaction> operationWithLossPot = new ArrayList();
    private static List<String> lines = new ArrayList();
    private static List<String> badLines = new ArrayList();
    private static OperationController operationController = new OperationController();

    @BeforeAll
    private static void setUp() {
        operationWithLossPot.add(new Transaction("buy", 10, 10000));
        operationWithLossPot.add(new Transaction("sell", 2, 5000));
        operationWithLossPot.add(new Transaction("sell", 20, 2000));
        operationWithLossPot.add(new Transaction("sell", 20, 2000));
        operationWithLossPot.add(new Transaction("sell", 25, 1000));

        lines.add("[{\"operation\":\"buy\", \"unit-cost\":10, \"quantity\": 10000}, {\"operation\":\"sell\",\"unit-cost\":20, \"quantity\": 5000}]");
    }

    @Test
    public void calculateTaxesOk() {
        ArrayList<Tax> taxesTest = operationController.calculateTaxes(operationWithLossPot);
        assert taxesTest.size() == 5;
        assert taxesTest.get(0).getAmount() == 0;
        assert taxesTest.get(1).getAmount() == 0;
        assert taxesTest.get(2).getAmount() == 0;
        assert taxesTest.get(3).getAmount() == 0;
        assert taxesTest.get(4).getAmount() == 3000;
    }
    @Test
    public void createOperations() {
        Map<Integer,ArrayList<Transaction>> operations = OperationController.createOperations(lines);
        ArrayList<Transaction> operation = operations.get(0);
        assert operation.size() == 2;
        assert operation.get(0).getOperation().equals("buy");
        assert operation.get(1).getOperation().equals("sell");
    }

    @Test
    public void calculateTaxesSellWithError() {
        try {
            badLines.add("adsasdasdas");
            OperationController.createOperations(badLines);
        } catch (Exception e) {
            assert e.getMessage().equals("java.lang.IllegalStateException: Expected BEGIN_ARRAY but was STRING at line 1 column 1 path $");
        }
    }
}
