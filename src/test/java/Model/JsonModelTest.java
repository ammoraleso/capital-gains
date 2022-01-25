package Model;

import models.JsonModel;
import models.Transaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class JsonModelTest {

    private static String lines = "[{\"operation\":\"buy\", \"unit-cost\":10, \"quantity\": 10000}, {\"operation\":\"sell\",\"unit-cost\":20, \"quantity\": 5000}]";
    Transaction transaction = new Transaction("buy", 10, 100);

    @Test
    public void convertJsonArrayToArrayListOk() {
        ArrayList<Transaction> transactions = (ArrayList<Transaction>) JsonModel.convertJsonArrayToArrayList(lines, Transaction.class);
        assert transactions.size() == 2;
        assert transactions.get(0).getOperation().equals("buy");
        assert transactions.get(1).getOperation().equals("sell");
    }

    @Test
    public void convertObjectToJson() {
        String json = Transaction.ObjectToJson(transaction);
        assert json.equals("{\"operation\":\"buy\",\"unit-cost\":10,\"quantity\":100}");
    }
}
