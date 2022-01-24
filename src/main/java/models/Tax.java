package models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

public class Tax extends JsonModel {

    @Getter @Setter
    @SerializedName(value = "tax")
    private Integer amount;
}
