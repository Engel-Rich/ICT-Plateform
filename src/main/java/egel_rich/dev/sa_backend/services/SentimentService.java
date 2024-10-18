package egel_rich.dev.sa_backend.services;

import egel_rich.dev.sa_backend.entites.Clients;
import egel_rich.dev.sa_backend.entites.Products;
import egel_rich.dev.sa_backend.entites.Sentiments;
import egel_rich.dev.sa_backend.enums.SentimentType;
import egel_rich.dev.sa_backend.repositorys.SentimentRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Random;

import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class SentimentService {
    final private SentimentRepository sentimentRepository;
    final private ClientService clientService;
    final private ProductService productService;

    @PersistenceContext
    final private EntityManager entityManager;

    public Page<Sentiments> getAllSentiments(SentimentType type,
                                             Integer currentPage,
                                             Integer pageSize,
                                             Integer clientID,
                                             Integer productID,
                                             Integer starAge,
                                             Integer endAge,
                                             String text) {

        System.out.println("Current page dans le service : " + currentPage + ", Page size: " + pageSize);

        Pageable pageable =  PageRequest.of(currentPage-1, pageSize);

        boolean haveAges = starAge!=null && endAge != null;

        if(clientID==null && productID==null){
            if (type == null) {
                if(haveAges){
                    return  this.sentimentRepository.
                            findByClient_AgeBetweenAndType(starAge,endAge,type,pageable);
                }
                if(text!=null ){
                    return  this.sentimentRepository.
                            findByTextContainingAndType(text,type,pageable);
                }
                return this.sentimentRepository.
                        findAll(pageable);
            }
            if(text!=null){
                return  this.sentimentRepository.
                        findByTextContaining(text,pageable);
            }
            return this.sentimentRepository.
                    findByType(type, pageable);
        }
        if (clientID!=null && productID==null){

            if(type!=null){
                return  this.sentimentRepository
                        .findByClient_IdAndType(clientID, type,pageable);
            }
            return  this.sentimentRepository.
                    findByClient_Id(clientID, pageable);

        } else if (clientID==null) {
            if(haveAges){
                if(type!=null){
                    if(text!=null){
                    return this.sentimentRepository.
                            findByProducts_IdAndClient_AgeBetweenAndTextContainingAndTypeOrderByProducts_PriceDesc(
                                productID,
                                starAge,
                                endAge,
                                text,
                                type,
                                pageable
                        );
                    }
                    return  this.sentimentRepository.findByProducts_IdAndClient_AgeBetweenAndType(
                            productID,
                            starAge,
                            endAge,
                            type,
                            pageable
                    );
                }

                return  this.sentimentRepository.
                        findByProducts_IdAndClient_AgeBetweenOrderByProducts_PriceDesc(
                                productID,
                                starAge,
                                endAge,
                                pageable
                        );
            }

            if(type!=null){
                if (text!=null){
                    return this.sentimentRepository.
                            findByProducts_IdAndTextContainingAndTypeOrderByProducts_PriceDesc(
                                    productID,text,type,pageable);
                }
                return  this.sentimentRepository.findByProducts_IdAndType(productID,type,pageable);
            }

            return  this.sentimentRepository.
                    findByProducts_Id(productID, pageable);
        }else {
            return this.sentimentRepository
                    .findByProducts_IdAndClient_Id(productID,clientID,pageable);
        }
    }

    public void deleteSentiment(int id) {
        this.sentimentRepository.deleteById(id);
    }

    public Sentiments findById(int id) {
        return this.sentimentRepository.findById(id).orElseThrow(() -> new RuntimeException("Ce sentiment n'est pas trouve"));
    }

    public Sentiments createSentiment(Sentiments sentiments) {
        return this.sentimentRepository.save(sentiments);
    }



    //    @PostConstruct
    @Transactional
    public void initializeSentimentList() {
        Random randomInt = new Random();
        final List<Integer> dataList = IntStream.range(1, 100).boxed().toList();
        final List<Integer> prroduct = IntStream.range(1, 100).boxed().toList();
        Faker faker = new Faker();
        IntStream.range(1, 1000).mapToObj(index -> {
            int preproduction = randomInt.nextInt(prroduct.size());
            int randomClient = randomInt.nextInt(dataList.size());
            System.out.println("preproduction : "+ prroduct.indexOf(preproduction) + " randomClient: "+ dataList.indexOf(randomClient));
            final Products products = this.productService.findById(prroduct.get(preproduction));
            final Clients clients = this.clientService.findById(dataList.get(randomClient));
            SentimentType typeName = randomClient % 3 == 0 ? SentimentType.NEUTRAL
                    : randomClient % 2 == 0 ? SentimentType.POSITIVE
                    : SentimentType.NEGATIVE;
            final String  text = faker.lorem().sentence(5);
            System.out.println(text);
            return new Sentiments(
                   text,
                    typeName,
                    clients,
                    products
            );
        }).forEach(this.sentimentRepository::save);
    }


}


// ajout des changements pour voir