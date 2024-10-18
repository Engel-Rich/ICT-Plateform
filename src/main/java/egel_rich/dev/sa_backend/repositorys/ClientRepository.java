package egel_rich.dev.sa_backend.repositorys;


import egel_rich.dev.sa_backend.entites.Clients;
import egel_rich.dev.sa_backend.enums.SentimentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Clients, Integer> {
    Clients findByEmail(String email);

    Page<Clients> findBySentimentsList_Id(Integer id, Pageable pageable);

    Page<Clients> findByAgeGreaterThan(Integer startAge, Pageable pageable);

    Page<Clients> findByAgeLessThan(Integer endAge, Pageable pageable);

    Page<Clients> findBySentimentsList_Products_Id(Integer id, Pageable pageable);

    Page<Clients> findBySentimentsList_Products_IdAndSentimentsList_Type(Integer productID, SentimentType type, Pageable pageable);

    Page<Clients> findByAndSentimentsList_TypeAndAgeBetween(
            SentimentType type,
            Integer startedAge,
            Integer endAge,
            Pageable pageable
    );

    Page<Clients> findByAgeBetween(Integer startedAge, Integer endAge, Pageable pageable);

    Page<Clients> findBySentimentsList_Products_PriceBetween(Integer startedPrice, Integer endPrice, Pageable pageable);


}

