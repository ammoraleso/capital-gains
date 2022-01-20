package application;


import controllers.OperationController;
import lombok.Getter;
import lombok.Setter;
import utils.FileUtils;
import models.Operation;
import java.util.ArrayList;
import java.util.List;

public class Main {

    @Getter @Setter
    private static final List<String> output = new ArrayList<>();

    public static void main(String[] args) {
        // Read jsons from file
        List<String> lines = FileUtils.readLines("src/main/resources/operations.txt");
        Operation operation = OperationController.createOperation(lines);
        OperationController.validateOperation(operation);
        output.stream().forEach(System.out::println);
    }
}
