package ws.ivi.dyndns.netszatyor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ws.ivi.dyndns.netszatyor.model.Csa;
import ws.ivi.dyndns.netszatyor.repository.CsaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CsaService {

    private final CsaRepository csaRepository;

    public List<Csa> findAll() {
        return csaRepository.findAll();
    }
}
