package ws.ivi.dyndns.netszatyor.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Ckt termek;

    private int mennyiseg;

    // Ez a konstruktor kell, ha csak termék és mennyiség alapján példányosítasz
    public CartItem(Ckt termek, int mennyiseg) {
        this.termek = termek;
        this.mennyiseg = mennyiseg;
    }

    public BigDecimal getOsszesen() {
        if (termek == null || termek.getCktakc() == null) {
            return BigDecimal.ZERO;
        }
        return termek.getCktakc().multiply(BigDecimal.valueOf(mennyiseg));
    }
}
