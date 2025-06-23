package ws.ivi.dyndns.netszatyor.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ws.ivi.dyndns.netszatyor.model.User;
import ws.ivi.dyndns.netszatyor.model.UserData;
import ws.ivi.dyndns.netszatyor.repository.UserDataRepository;

import java.util.Optional;

@Service
public class ProfileService {

    private final UserDataRepository adatRepository;

    public ProfileService(UserDataRepository adatRepository) {
        this.adatRepository = adatRepository;
    }

    public Optional<UserData> getAdatok(User felhasznalo) {
        return adatRepository.findByUser(felhasznalo);
    }

    @Transactional
    public void mentes(User felhasznalo, UserData frissAdat) {
        Optional<UserData> optional = adatRepository.findByUser(felhasznalo);

        if (optional.isPresent()) {
            UserData letezo = optional.get();

            letezo.setNev(frissAdat.getNev());
            letezo.setIranyitoszam(frissAdat.getIranyitoszam());
            letezo.setTelepules(frissAdat.getTelepules());
            letezo.setUtca(frissAdat.getUtca());
            letezo.setHazszam(frissAdat.getHazszam());
            letezo.setCimegyeb(frissAdat.getCimegyeb());
            letezo.setTelefon(frissAdat.getTelefon());
            letezo.setCeges(frissAdat.isCeges());
            letezo.setAdoszam(frissAdat.getAdoszam());
            letezo.setSzall_telepules(frissAdat.getSzall_telepules());
            letezo.setSzall_iranyitoszam(frissAdat.getSzall_iranyitoszam());
            letezo.setSzall_telepules(frissAdat.getSzall_telepules());
            letezo.setSzall_utca(frissAdat.getSzall_utca());
            letezo.setSzall_hazszam(frissAdat.getSzall_hazszam());
            letezo.setSzall_egyeb(frissAdat.getSzall_egyeb());

            adatRepository.save(letezo);
        } else {
            frissAdat.setUser(felhasznalo);
            adatRepository.save(frissAdat);
        }
    }
}
