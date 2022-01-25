package controllers;

import models.Tax;
import models.Transaction;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class BuyController extends AbstractOperation {

    /**
     * Calculate weighted average price
     * @param operationBuy List of transactions with operation buy
     * @return Weighted average price
     */
    private Transaction calculateWeightedAveragePrice(ArrayList<Transaction> operationBuy) {
        double weightedAveragePrice = 0;
        int totalStocks = 0;
        for (Transaction transaction : operationBuy) {
            weightedAveragePrice += transaction.getUnitCost() * transaction.getQuantity();
            totalStocks += transaction.getQuantity();
        }
        final Transaction transaction = new Transaction("base", (int) (weightedAveragePrice / totalStocks), totalStocks);
        return transaction;
    }

    @Override
    public ArrayList<Tax> calculateTaxes(ArrayList<Transaction> operations) {
        baseTransaction = new Transaction();

        ArrayList<Transaction> operationBuy = operations.stream()
                .filter(transaction -> transaction.getOperation() != null && !transaction.getOperation().isEmpty()
                        && transaction.getOperation().equals("buy"))
                .collect(Collectors.toCollection(ArrayList::new));

        if (operationBuy.size() == 0) {
            throw new IllegalArgumentException("Operation buy not found");
        }
        if(operationBuy.size() > 1) {
            // Calculate weighted-average price when we have more than one buy operation
            // Create base transaction with the average price
            baseTransaction = calculateWeightedAveragePrice(operationBuy);
        } else {
            // Create base transaction with the first buy operation
            baseTransaction = new Transaction("base",operationBuy.get(0).getUnitCost(),
                    operationBuy.get(0).getQuantity() );
        }
        for (Transaction transaction : operationBuy) {
            transaction.setTax(new Tax());
            if(transaction.getOperation().equals("buy")) {
                // You do not pay any taxes for buying stocks;
                transaction.getTax().setAmount(0);
                taxes.add(transaction.getTax());
            }
        }
        return taxes;
    }
}
