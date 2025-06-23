package ws.ivi.dyndns.netszatyor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "csa")
@Getter
@Setter
@NoArgsConstructor
public class Csa {

    @Id
    private String csakod;

    private String csanev;
}
