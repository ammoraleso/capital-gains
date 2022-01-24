package models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Transaction extends JsonModel{

    @Getter @Setter
    private String operation;

    @Getter @Setter
    @SerializedName(value = "unit-cost")
    private Integer unitCost;

    @Getter @Setter
    private Integer quantity;

    @Getter @Setter
    private Tax tax;

    public Transaction() {
    }

    public Transaction(String operation, Integer unitCost, Integer quantity) {
        this.operation = operation;
        this.unitCost = unitCost;
        this.quantity = quantity;
    }
}
