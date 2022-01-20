package models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Transaction extends JsonModel{

    @Getter @Setter
    private String merchant;

    @Getter @Setter
    private Integer amount;

    @Getter @Setter
    private Date time;
}
