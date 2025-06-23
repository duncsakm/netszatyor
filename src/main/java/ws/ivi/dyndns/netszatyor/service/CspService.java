package ws.ivi.dyndns.netszatyor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ws.ivi.dyndns.netszatyor.model.Csp;
import ws.ivi.dyndns.netszatyor.repository.CspRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CspService {

    private final CspRepository cspRepository;

    public List<Csp> findAll() {
        return cspRepository.findAll();
    }
}
