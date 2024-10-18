package egel_rich.dev.sa_backend.repositorys;

import egel_rich.dev.sa_backend.entites.Products;
import egel_rich.dev.sa_backend.enums.SentimentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Products, Integer> {

    Page<Products> findByNameContainingOrderByPrice(String name, Pageable pageable);

    Page<Products> findByPriceBetweenAndNameContainingOrderByPrice(int startPrice, int endPrice, String name, Pageable pageable);

    Page<Products> findByPriceGreaterThan(int price, Pageable pageable);

    Page<Products> findByPriceLessThan(int price, Pageable pageable);

    Page<Products> findDistinctBySentimentsList_Client_AgeBetweenAndPriceBetweenOrderByPriceDesc(Integer ageStart,
                                                                                                 Integer ageEnd,
                                                                                                 Integer priceStart,
                                                                                                 Integer priceEnd,
                                                                                                 Pageable pageable);

    Page<Products> findDistinctBySentimentsList_Client_AgeBetween(Integer ageStart,
                                                                  Integer ageEnd,
                                                                  Pageable pageable);

    Page<Products> findDistinctByPriceBetweenOrderByPrice(Integer priceStart,
                                                        Integer priceEnd,
                                                        Pageable pageable);

    Page<Products> findDistinctBySentimentsList_Client_AgeBetweenAndSentimentsList_TypeAndPriceBetweenOrderByPriceDesc(Integer ageStart,
                                                                                                                       Integer ageEnd,
                                                                                                                       SentimentType type,
                                                                                                                       Integer priceStart,
                                                                                                                       Integer priceEnd,
                                                                                                                       Pageable pageable);

    Page<Products> findDistinctBySentimentsList_Client_AgeBetweenAndSentimentsList_TypeOrderByPriceDesc(Integer ageStart,
                                                                                                           Integer ageEnd,
                                                                                                           SentimentType type,
                                                                                                           Pageable pageable);

    Page<Products> findDistinctBySentimentsList_TypeAndPriceBetweenOrderByPriceDesc(SentimentType type,
                                                                                    Integer priceStart,
                                                                                    Integer priceEnd,
                                                                                    Pageable pageable);

    //    @Query(value = "SELECT p.description,  p.id, p.price,  p.name, p.created_at, p.updated_at "+
//                    "FROM Avis AS a inner join Products AS p "+
//                    "ON a.product_id= p.id "+
//                    "WHERE a.id=:id ", nativeQuery = true)
    Page<Products> findDistinctBySentimentsList_Client_Id(Integer id, Pageable pageable);

    Page<Products> findDistinctBySentimentsList_Client_IdAndSentimentsList_Type(Integer id, SentimentType type, Pageable pageable);

    Page<Products> findByPriceBetween(Integer startPrice, Integer endPrice, Pageable pageable);


}
