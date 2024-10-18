package egel_rich.dev.sa_backend.repositorys;

import egel_rich.dev.sa_backend.entites.Products;
import egel_rich.dev.sa_backend.entites.Sentiments;
import egel_rich.dev.sa_backend.enums.SentimentType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SentimentRepository extends JpaRepository<Sentiments,Integer> {

    Page<Sentiments> findByType(SentimentType type, Pageable pageable);

    Page<Sentiments> findByTextContaining(String name,Pageable pageable);

    Page<Sentiments> findByTextContainingAndType(String name,SentimentType type,Pageable pageable);

    Page<Sentiments> findByProducts_Id(int id,Pageable pageable);

    Page<Sentiments> findByProducts_IdAndClient_Id(int product,int client, Pageable pageable);

    Page<Sentiments> findByClient_Id(int id,Pageable pageable);

    Page<Sentiments> findByClient_IdAndType(int id,SentimentType type, Pageable pageable);

    Page<Sentiments> findByProducts_IdAndType(int id, SentimentType type,Pageable pageable);

    Page<Sentiments> findByClient_AgeBetweenAndType(int startAge, int andAge,   SentimentType type,Pageable pageable);

    Page<Sentiments> findByProducts_IdAndClient_AgeBetweenAndType(int id,int startAge, int endAge, SentimentType type,Pageable pageable);

    Page<Sentiments> findByProducts_IdAndClient_AgeBetweenAndTextContainingAndTypeOrderByProducts_PriceDesc(int id, int startAge, int endAge, String text, SentimentType type,Pageable pageable);

    Page<Sentiments> findByProducts_IdAndClient_AgeBetweenOrderByProducts_PriceDesc(int productId, int startAge, int endAge,Pageable pageable);

    Page<Sentiments> findByProducts_IdAndTextContainingAndTypeOrderByProducts_PriceDesc(int id, String text, SentimentType type,Pageable pageable);

//    Optional<Products> findProductById(Integer id);

}
 