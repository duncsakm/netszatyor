package ws.ivi.dyndns.netszatyor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ws.ivi.dyndns.netszatyor.model.Ckt;

import java.util.List;

public interface CktRepository extends JpaRepository<Ckt, String> {

    @Query(value = "SELECT * FROM ckt ORDER BY RANDOM() LIMIT 9", nativeQuery = true)
    List<Ckt> findRandom9();
}
