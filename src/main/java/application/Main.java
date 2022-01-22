package application;


import controllers.OperationController;
import lombok.Getter;
import lombok.Setter;
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

    public static void main(String[] args) {

        // Read jsons from file
        List<String> lines = FileUtils.readLines("src/main/resources/operations.txt");
        Map<Integer,ArrayList<Transaction>> operations = OperationController.createOperations(lines);
        operations.forEach((index,transaction) -> {
            logger.info("Operation-" + index + ": ");
            logger.info( transaction.toString());
        });
    }


}
