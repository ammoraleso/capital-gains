package models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Account extends JsonModel{

    @Getter @Setter
    @SerializedName(value = "active-card")
    private Boolean activeCard;

    @Getter @Setter
    @SerializedName(value = "available-limit")
    private Integer availableLimit;

    @Getter @Setter
    private ArrayList<String> violations;

    /**
     * Generic Constructor
     */
    public Account() {
    }

    /**
     * Constructor for GSON Library
     */
    public Account(Boolean activeCard, Integer availableLimit, ArrayList<String> violations) {
        this.activeCard = activeCard;
        this.availableLimit = availableLimit;
        this.violations = violations;
    }
}
