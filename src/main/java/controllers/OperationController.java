package controllers;

import models.Tax;
import models.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperationController extends AbstractOperation{

    private static Logger logger = LoggerFactory.getLogger(OperationController.class);

    private static BuyController buyController;
    private static SellController sellController;

    public OperationController() {
        buyController = new BuyController();
        sellController = new SellController();
    }

    /**
     * Create a new map of operations from an array of lines or strings
     * @param lines Array of string to create the operation
     * @return Map of operations
     */
    public static Map<Integer,ArrayList<Transaction>> createOperations(List<String> lines){
        Map<Integer,ArrayList<Transaction>> operations = new HashMap();
            for (int index = 0; index < lines.size(); index++) {
                try {
                operations.put(index, (ArrayList<Transaction>) Transaction.convertJsonArrayToArrayList(lines.get(index), Transaction.class));
                } catch (com.google.gson.JsonSyntaxException e) {
                    throw new com.google.gson.JsonSyntaxException(e.getMessage());
                } catch (Exception ex) {
                    logger.error("Exception converting Json Array to Array List", ex);
                } ;
            }
        return operations;
    }

    /**
     * Calculate taxes from a list of transactions
     * @param operations List of transactions
     */
    @Override
    public ArrayList<Tax> calculateTaxes(ArrayList<Transaction> operations) {
        taxes = buyController.calculateTaxes(operations);
        sellController.setBaseTransaction(buyController.getBaseTransaction());
        ArrayList<Tax> sellerTaxes = sellController.calculateTaxes(operations);
        taxes.addAll(sellerTaxes);
        return taxes;
    }
}