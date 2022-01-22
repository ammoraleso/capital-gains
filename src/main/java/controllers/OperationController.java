package controllers;

import application.Main;
import models.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class OperationController {

    private static Logger logger = LoggerFactory.getLogger(OperationController.class);

    /**
     * Create a new map of operations from an array of lines or strings
     * @param lines Array of string to create the operation
     * @return Map of operations
     */
    public static Map<Integer,ArrayList<Transaction>> createOperations(List<String> lines){
        Map<Integer,ArrayList<Transaction>> operations = new HashMap();
        try {
            for (int index = 0; index < lines.size(); index++) {
                operations.put(index, Transaction.convertJsonArrayToArrayList(lines.get(index), Transaction.class));
            };
        } catch (Exception ex) {
            logger.error("Exception converting Json Array to Array List", ex);
        }
        return operations;
    }
}