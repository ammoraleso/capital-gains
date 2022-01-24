package application;


import controllers.OperationController;
import lombok.Getter;
import lombok.Setter;
import models.Tax;
import models.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    @Getter @Setter
    private static final List<String> output = new ArrayList<>();
    private static ArrayList<Tax> taxes = null;
    public static void main(String[] args) {


        // Read jsons from file
        List<String> lines = FileUtils.readLines("src/main/resources/operations.txt");
        Map<Integer,ArrayList<Transaction>> operations = OperationController.createOperations(lines);
        for (ArrayList<Transaction> value : operations.values()) {
            taxes = new ArrayList<>();
            taxes = OperationController.calculateTaxes(value);
            System.out.println(Tax.ObjectToJson(taxes));
        }
    }


}
