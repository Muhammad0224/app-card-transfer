package uz.pdp.appcardtransfer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Outcome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    private Card fromCard;

    @ManyToOne(optional = false)
    private Card toCard;

    @Column
    private Double amount;

    @Column(columnDefinition = "timestamp default now()")
    private Timestamp date = Timestamp.valueOf(LocalDateTime.now());

    @Column
    private Double commissionAmount;

}
