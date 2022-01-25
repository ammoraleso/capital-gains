package controllers;

import lombok.Getter;
import lombok.Setter;
import models.Tax;
import models.Transaction;

import java.util.ArrayList;

public abstract class AbstractOperation {
    @Getter @Setter
    public ArrayList<Tax> taxes = new ArrayList<>();

    @Getter @Setter
    public Transaction baseTransaction = new Transaction();

    public abstract ArrayList<Tax> calculateTaxes(ArrayList<Transaction> operations);
}
