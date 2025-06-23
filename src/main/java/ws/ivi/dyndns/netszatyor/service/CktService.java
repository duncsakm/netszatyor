package ws.ivi.dyndns.netszatyor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ws.ivi.dyndns.netszatyor.model.Ckt;
import ws.ivi.dyndns.netszatyor.repository.CktRepository;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CktService {

    private final CktRepository cktRepository;

    public Page<Ckt> getFilteredProducts(String keres, String csp, String csa, Pageable pageable) {
        return cktRepository.findByFilters(keres, csp, csa, pageable);
    }

    public List<Ckt> getRandom9() {
        return cktRepository.findRandom9();
    }
}
