package uz.pdp.appcardtransfer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransferDto {
    private Integer fromCard;
    private Integer toCard;
    private Double amount;
}
