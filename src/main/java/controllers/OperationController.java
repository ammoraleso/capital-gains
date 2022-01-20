package controllers;

import application.Main;
import com.google.gson.Gson;
import models.Account;
import models.Operation;
import models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class OperationController {

    /**
     * Create a new operation from an array of lines or strings
     * @param lines Array of string to create the operation
     * @return The created operation
     */
    public static Operation createOperation(List<String> lines){
        Operation operations = new Operation();
        try {
            lines.stream().forEach(line -> {
                Account account;
                Transaction transaction;
                if(line.contains("account")){
                    account = Account.convertStringToJson(line, Account.class);
                    operations.getAccounts().add(account);
                    return;
                }
                if(line.contains("transaction")){
                    transaction = Transaction.convertStringToJson(line, Transaction.class);
                    operations.getTransactions().add(transaction);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return operations;
    }

    public static void validateOperation(final Operation operation ) {
        validateAccounts(operation.getAccounts());
    }

    /**
     * Validate the accounts of an operation
     * @param accounts The accounts to validate
     */
    private static void validateAccounts(ArrayList<Account> accounts) {
        // Initialize violations to pointing to the accounts
        if(accounts != null && accounts.size() > 0){
            accounts.stream().forEach(account -> {
              account.setViolations(new ArrayList<>());
            });
        }

        for(int index = 0; index < accounts.size(); index++){
            String json;
            Gson gson = new Gson();
            if(index != 0 && accounts.get(0).getViolations().size() == 0) {
                accounts.get(0).getViolations().add("account-already-initialized");
            }
            json = gson.toJson(accounts.get(0));
            Main.getOutput().add(json);
        }
    }
}