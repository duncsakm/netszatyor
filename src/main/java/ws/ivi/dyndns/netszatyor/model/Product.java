package ws.ivi.dyndns.netszatyor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private String cikkszam;

    private String nev;
    private BigDecimal ar;
    private int keszlet;
}