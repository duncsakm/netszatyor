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
public class Ckt {

    @Id
    @Column(length = 13)
    private String cktkod;         // cikkszám (kulcs)

    @Column(length = 46)
    private String cktnev;         // megnevezés

    @Column(length = 6)
    private String cktcsp;         // főcsoport

    @Column(length = 6)
    private String cktcsa;         // alcsoport

    private Integer cktafa;        // ÁFA-kulcs

    @Column(precision = 15, scale = 3)
    private BigDecimal cktsar;     // beszerzési ár (bruttó)

    @Column(precision = 15, scale = 3)
    private BigDecimal cktfar;     // fogyasztói ár (bruttó)

    @Column(precision = 15, scale = 3)
    private BigDecimal cktakc;     // akciós ár (bruttó) → új mező

    @Column(precision = 15, scale = 4)
    private BigDecimal cktkem;     // készlet
}
