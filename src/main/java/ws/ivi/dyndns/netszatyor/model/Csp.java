package ws.ivi.dyndns.netszatyor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "csp")
@Getter
@Setter
@NoArgsConstructor
public class Csp {

    @Id
    private String cspkod;

    private String cspnev;
}
