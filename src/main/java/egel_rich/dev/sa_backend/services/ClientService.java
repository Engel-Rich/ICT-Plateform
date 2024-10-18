package egel_rich.dev.sa_backend.services;


import egel_rich.dev.sa_backend.dto.ClientDTO;
import egel_rich.dev.sa_backend.entites.Clients;
import egel_rich.dev.sa_backend.enums.SentimentType;
import egel_rich.dev.sa_backend.mappers.ClientDTOMapper;
import egel_rich.dev.sa_backend.repositorys.ClientRepository;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Service
public class ClientService {

    final private ClientRepository clientRepository;
    final private ClientDTOMapper clientDTOMapper;

    public ClientService(ClientRepository clientRepository, ClientDTOMapper clientDTOMapper) {
        this.clientRepository = clientRepository;
        this.clientDTOMapper = clientDTOMapper;
    }

    public Clients createClient(Clients clients) {
        return clientRepository.save(clients);
    }

    public Page<ClientDTO> findAll(Integer page,
                                   Integer size,
                                   Integer productId,
                                   Integer sentimentID,
                                   Integer startAge,
                                   Integer endAge,
                                   Integer startPrice,
                                   Integer endPrice, SentimentType sentimentType) {
        final Pageable pageable = PageRequest.of(page - 1, size);
        if (productId != null && sentimentID == null) {
            if (sentimentType != null)
                return this.clientRepository.findBySentimentsList_Products_IdAndSentimentsList_Type(productId, sentimentType, pageable).map(clientDTOMapper);
            return this.clientRepository.findBySentimentsList_Products_Id(productId, pageable).map(clientDTOMapper);
        } else if (productId == null && sentimentID != null) {
            return this.clientRepository.findBySentimentsList_Id(sentimentID, pageable).map(clientDTOMapper);
        }
        if (startAge != null && startPrice == null) {
            if (endAge == null)
                return this.clientRepository.findByAgeGreaterThan(startAge, pageable).map(clientDTOMapper);
            if (endAge < startAge)
                throw new RuntimeException("L'age  final ne peut etre inferieur a l'age de debut");
            if (sentimentType != null) return this.clientRepository.findByAndSentimentsList_TypeAndAgeBetween(
                    sentimentType,
                    startAge,
                    endAge,
                    pageable).map(clientDTOMapper);
            return this.clientRepository.findByAgeBetween(startAge, endAge, pageable).map(clientDTOMapper);
        } else if (startAge == null && startPrice != null) {
            if (endPrice == null) throw new RuntimeException("Vous devez renseigner un price finale");
            if (endPrice < startPrice)
                throw new RuntimeException("Le prix final ne peut etre inferieur au prix de debut");
            return this.clientRepository.findBySentimentsList_Products_PriceBetween(startPrice, endPrice, pageable).map(clientDTOMapper);
        } else if (startAge == null && endAge != null) {
            return this.clientRepository.findByAgeLessThan(endAge, pageable).map(clientDTOMapper);
        }
        return this.clientRepository.
                findAll(pageable).
                map(clientDTOMapper);
    }


    public ClientDTO findClientById(int id) {
        final Optional<Clients> client = this.clientRepository.findById(id);
        return client.map(clientDTOMapper).orElseThrow(() -> new RuntimeException("Le client n'a pas ete trouve "));
    }

    public Clients findById(int id) {
        System.out.println("Id Qui Arrive " + id);
        final Optional<Clients> client = this.clientRepository.findById(id);
        System.out.println(client.isPresent());
        return client.orElseThrow(() -> new RuntimeException("Le client " + id + " n'a pas ete trouve "));
    }

    public Clients updateClient(int id, Clients clients) {
        final Clients clientsFromDB = new Clients(this.findClientById(id));
        if (clients.getName() != null) clientsFromDB.setEmail(clients.getEmail());
        if (clients.getName() != null) clientsFromDB.setName(clients.getName());

        return this.clientRepository.save(clientsFromDB);
    }


//        @PostConstruct
//    public void initialiseDATAClient() {
//        final Faker faker = new Faker();
//        IntStream.rangeClosed(1, 100).
//                mapToObj(index -> {
//                    LocalDate birthDate = faker.date().birthdayLocalDate(15, 80);
//                    LocalDate now = LocalDate.now();
//                    Period period = Period.between(birthDate, now);
//                    return new Clients(faker.internet().emailAddress(), faker.name().fullName(), period.getYears());
//                }).
//                forEach(this.clientRepository::save);
//    }
}
