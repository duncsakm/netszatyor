package ws.ivi.dyndns.netszatyor.repository;

import ws.ivi.dyndns.netszatyor.model.FeldolgozasStatusz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeldolgozasStatuszRepository extends JpaRepository<FeldolgozasStatusz, Long> {

    // Legfrissebb státusz lekérése fájlnév alapján
    Optional<FeldolgozasStatusz> findTopByFajlNevOrderByFrissitveDesc(String fajlNev);
}
