package controllers;

import models.Tax;
import models.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OperationController {

    private static Logger logger = LoggerFactory.getLogger(OperationController.class);
    private static Transaction baseTransaction;

    /**
     * Create a new map of operations from an array of lines or strings
     * @param lines Array of string to create the operation
     * @return Map of operations
     */
    public static Map<Integer,ArrayList<Transaction>> createOperations(List<String> lines){
        Map<Integer,ArrayList<Transaction>> operations = new HashMap();
        try {
            for (int index = 0; index < lines.size(); index++) {
                operations.put(index, (ArrayList<Transaction>) Transaction.convertJsonArrayToArrayList(lines.get(index), Transaction.class));
            };
        } catch (Exception ex) {
            logger.error("Exception converting Json Array to Array List", ex);
        }
        return operations;
    }

    /**
     * Calculate taxes from a list of transactions
     * @param operations List of transactions
     */
    public static ArrayList<Tax> calculateTaxes(ArrayList<Transaction> operations) {
        ArrayList<Tax> taxes = new ArrayList<>();
        baseTransaction = new Transaction();
        ArrayList<Transaction> operationBuy = operations.stream()
                .filter(transaction -> transaction.getOperation().equals("buy")).collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Transaction> operationSell = operations.stream()
                .filter(transaction -> transaction.getOperation().equals("sell")).collect(Collectors.toCollection(ArrayList::new));

        if(operationBuy.size() > 1) {
            // Calculate weighted-average price when we have more than one buy operation
            // Create base transaction with the average price
            baseTransaction = calculateWeightedAveragePrice(operationBuy);
        } else {
            // Create base transaction with the first buy operation
            baseTransaction = new Transaction("base",operationBuy.get(0).getUnitCost(),
                    operationBuy.get(0).getQuantity() );
        }

        // Iterate over the buy operations
        for (Transaction transaction : operationBuy) {
            transaction.setTax(new Tax());
            if(transaction.getOperation().equals("buy")) {
                // You do not pay any taxes for buying stocks;
                transaction.getTax().setAmount(0);
                taxes.add(transaction.getTax());
                continue;
            }
        }

        //Iterate over the sell operations
        for (Transaction transaction : operationSell) {
            transaction.setTax(new Tax());
            // You do not pay any taxes if the total amount (unit cost of selling x quantity) is less than or equal to R$ 20000.00
            if(transaction.getOperation().equals("sell")) {
                if(transaction.getUnitCost() * transaction.getQuantity() < 2000) {
                    transaction.getTax().setAmount(0);
                    taxes.add(transaction.getTax());
                    continue;
                }
                // (SellCost - BuyCost) * Q - > 0 profit y < 0 loss
                int totalOperationAmount = (transaction.getUnitCost() - baseTransaction.getUnitCost()) * transaction.getQuantity();
                // Profit
                if(totalOperationAmount > 0) {
                    int taxAmount = (int) (totalOperationAmount * 0.2);
                    transaction.getTax().setAmount(taxAmount);
                // Loss
                }else {
                    // Losses do not pay any taxes,
                    transaction.getTax().setAmount(0);
                }
            }
            taxes.add(transaction.getTax());
        }
        return taxes;
    }

    /**
     * Calculate weighted average price
     * @param operationBuy List of transactions with operation buy
     * @return Weighted average price
     */
    private static Transaction calculateWeightedAveragePrice(ArrayList<Transaction> operationBuy) {
        double weightedAveragePrice = 0;
        int totalStocks = 0;
        for (Transaction transaction : operationBuy) {
            weightedAveragePrice += transaction.getUnitCost() * transaction.getQuantity();
            totalStocks += transaction.getQuantity();
        }
        final Transaction transaction = new Transaction("base", (int) (weightedAveragePrice / totalStocks), totalStocks);
        return transaction;
    }
}