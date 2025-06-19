package ws.ivi.dyndns.netszatyor.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ckt")
public class Product {

    @Id
    @Column(name = "cktkod", length = 13)
    private String cktkod;

    @Column(name = "cktnev", length = 46)
    private String cktnev;

    @Column(name = "cktcsp", length = 6)
    private String cktcsp;

    @Column(name = "cktcsa", length = 6)
    private String cktcsa;

    @Column(name = "cktafa")
    private Integer cktafa;

    @Column(name = "cktsar", precision = 15, scale = 3)
    private BigDecimal cktsar;

    @Column(name = "cktfar", precision = 15, scale = 3)
    private BigDecimal cktfar;

    @Column(name = "cktkem", precision = 15, scale = 4)
    private BigDecimal cktkem;
}
