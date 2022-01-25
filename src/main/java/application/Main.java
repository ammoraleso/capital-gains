package application;



import controllers.OperationController;
import models.Tax;
import models.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileUtils;

import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    private static ArrayList<Tax> taxes = null;

    public static void main(String[] args) {
            try {
                logger.info("Starting application");
                // Read jsons from file
                List<String> lines = FileUtils.readLines("src/main/resources/operations.txt");
                // Parse jsons to objects
                Map<Integer,ArrayList<Transaction>> operations = OperationController.createOperations(lines);
                for (ArrayList<Transaction> value : operations.values()) {
                    try {
                        taxes = new ArrayList<>();
                        taxes = OperationController.calculateTaxes(value);
                        FileUtils.writeToFile(Tax.ObjectToJson(taxes) + "\n","src/main/resources/output.txt");
                        System.out.println(Tax.ObjectToJson(taxes));
                    } catch (IllegalArgumentException e){
                        logger.error("Error at line: " + Transaction.ObjectToJson(value) + " " + e.getMessage());
                    } catch (Exception e){
                        logger.error("Error: ", e);
                    }
                }
            } catch (Exception e) {
                logger.error("Error Initializing application: ", e);
            }
            logger.info("Finishing application");
    }

}
