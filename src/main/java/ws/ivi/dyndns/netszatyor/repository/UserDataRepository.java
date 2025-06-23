package ws.ivi.dyndns.netszatyor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ws.ivi.dyndns.netszatyor.model.UserData;
import ws.ivi.dyndns.netszatyor.model.User;

import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
    Optional<UserData> findByUser(User user);
}
