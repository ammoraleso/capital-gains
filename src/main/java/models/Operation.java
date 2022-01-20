package models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Operation extends JsonModel{

    @Setter @Getter
    private ArrayList<Transaction> transactions;

    @Setter @Getter
    private ArrayList<Account> accounts;

    public Operation() {
        this.transactions = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }
}
