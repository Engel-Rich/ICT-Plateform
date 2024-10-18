package egel_rich.dev.sa_backend.services;


import egel_rich.dev.sa_backend.entites.Products;
import egel_rich.dev.sa_backend.enums.SentimentType;
import egel_rich.dev.sa_backend.repositorys.ProductRepository;
import jakarta.annotation.PostConstruct;
//import jakarta.persistence.EntityNotFoundException;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
import net.datafaker.Faker;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    final private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Products> findProducts(String name, Integer currentPage, Integer pageSize) {
        final Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        if (name != null) return this.productRepository.findByNameContainingOrderByPrice(name, pageable);
        return this.productRepository.findAll(pageable);
    }

    public Products findById(int id) {
        final Optional<Products> productsOptional = this.productRepository.findById(id);
        return productsOptional.orElseThrow(() -> new RuntimeException("Ce produit n'existe pas"));
    }

    public Products createProduct(Products products) {
        return this.productRepository.save(products);
    }

    /* Obtenir la liste des  produits comente par un client en utilisant  l'ID du client
     * Sachant que l'id du client est dans le sentiment qui est dans le produit*/

    public Page<Products> findDistinctBySentiment_Client_Id(Integer id,
                                                            SentimentType type,
                                                            Integer ageStart,
                                                            Integer ageEnd,
                                                            Integer priceStart,
                                                            Integer priceEnd,
                                                            Integer currentPage,
                                                            Integer pageSize) {

//findDistinctBySentimentsList_Client_AgeBetweenAndSentimentsList_TypeAndPriceBetweenOrderByPriceDesc(int ageStart, int ageEnd,SentimentType type,Integer priceStart, Integer priceEnd, Pageable pageable);
        final Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        if (type != null) {
            if (ageStart != null && ageEnd != null && priceStart == null && priceEnd == null) {
                return this.productRepository.findDistinctBySentimentsList_Client_AgeBetweenAndSentimentsList_TypeOrderByPriceDesc(ageStart, ageEnd, type, pageable);
            }
            if (ageStart == null && ageEnd == null && priceStart != null && priceEnd != null) {
                return this.productRepository.findDistinctBySentimentsList_TypeAndPriceBetweenOrderByPriceDesc(type, priceStart, priceEnd, pageable);
            }
            if (ageStart != null && ageEnd != null && priceStart != null && priceEnd != null) {
                return this.productRepository.findDistinctBySentimentsList_Client_AgeBetweenAndSentimentsList_TypeAndPriceBetweenOrderByPriceDesc(ageStart, ageEnd, type, priceStart, priceEnd, pageable);
            }
            return this.productRepository.findDistinctBySentimentsList_Client_IdAndSentimentsList_Type(id, type, pageable);
        } else {
            if (ageStart != null && ageEnd != null && priceStart == null && priceEnd == null) {
                System.out.println("I am age ageStart != null && ageEnd != null && priceStart == null && priceEnd == null  ");
                return this.productRepository.findDistinctBySentimentsList_Client_AgeBetween(ageStart, ageEnd, pageable);
            }
            if (ageStart == null && ageEnd == null && priceStart != null && priceEnd != null) {
                System.out.println("I am ageStart == null && ageEnd == null && priceStart != null && priceEnd != null ");
                return this.productRepository.findDistinctByPriceBetweenOrderByPrice(priceStart, priceEnd, pageable);
            }
            if (ageStart != null && ageEnd != null && priceStart != null && priceEnd != null) {
                return this.productRepository.findDistinctBySentimentsList_Client_AgeBetweenAndPriceBetweenOrderByPriceDesc(ageStart, ageEnd, priceStart, priceEnd, pageable);
            }
        }
        return this.productRepository.findDistinctBySentimentsList_Client_Id(id, pageable);
    }


//        @PostConstruct
//    public void initializeProductData() {
//        Faker faker = new Faker();
//        for (int i = 0; i < 100; i++) {
//            Products product = new Products();
//            product.setName(faker.commerce().productName());
//            product.setDescription(faker.commerce().department());
//            product.setPrice(i == 0 ? 1000 : i * 100);
//            try {
//                this.productRepository.save(product);
//            } catch (DataIntegrityViolationException e) {
//                System.out.println(e.getMessage());
//                // Throw my Exception here
//            }
//        }
//    }
}
