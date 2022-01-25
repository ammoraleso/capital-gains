package controllers;

import models.Tax;
import models.Transaction;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SellController extends AbstractOperation {

    int lossPot = 0;

    public ArrayList<Tax> calculateTaxes(ArrayList<Transaction> operations) {
        ArrayList<Transaction> operationSell = operations.stream()
                .filter(transaction -> transaction.getOperation() != null && !transaction.getOperation().isEmpty()
                        && transaction.getOperation().equals("sell"))
                .collect(Collectors.toCollection(ArrayList::new));

        //Iterate over the sell operations
        for (Transaction transaction : operationSell) {
            transaction.setTax(new Tax());
            // You do not pay any taxes if the total amount (unit cost of selling x quantity) is less than or equal to R$ 20000.00
            if(transaction.getUnitCost() * transaction.getQuantity() < 2000) {
                transaction.getTax().setAmount(0);
                taxes.add(transaction.getTax());
                continue;
            }
            // (SellCost - BuyCost) * Q - > 0 profit y < 0 loss
            int totalOperationAmount = (transaction.getUnitCost() - baseTransaction.getUnitCost()) * transaction.getQuantity();
            totalOperationAmount = totalOperationAmount - lossPot;
            // Profit
            if(totalOperationAmount > 0) {
                int taxAmount = (int) (totalOperationAmount * 0.2);
                transaction.getTax().setAmount(taxAmount);
                // Loss
            }else {
                // Losses do not pay any taxes,
                lossPot = totalOperationAmount * -1;
                transaction.getTax().setAmount(0);
            }
            taxes.add(transaction.getTax());
        }
        return taxes;
    }
}
