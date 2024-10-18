package egel_rich.dev.sa_backend.controllers;


import egel_rich.dev.sa_backend.dto.ClientDTO;
import egel_rich.dev.sa_backend.entites.Clients;
import egel_rich.dev.sa_backend.entites.Products;
import egel_rich.dev.sa_backend.entites.Sentiments;
import egel_rich.dev.sa_backend.enums.SentimentType;
import egel_rich.dev.sa_backend.services.ClientService;
import egel_rich.dev.sa_backend.services.ProductService;
import egel_rich.dev.sa_backend.services.SentimentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping(path = "sentiments", produces = MediaType.APPLICATION_JSON_VALUE)

public class SentimentController {

    private SentimentService sentimentService;
    private ClientService clientService;
    private ProductService productService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Sentiments createSentiment(@RequestBody Sentiments sentiments) {
        System.out.print("Type de sentiment : " + sentiments.toString());
        final Clients clients =  clientService.findById(sentiments.getClient().getId());
        final Products products = this.productService.findById(sentiments.getProducts().getId());
        sentiments.setClient(clients);
        sentiments.setProducts(products);
        return this.sentimentService.createSentiment(sentiments);
    }

    @GetMapping
    public Page<Sentiments> getAllSentiments(@RequestParam(required = false) SentimentType type,
                                             @RequestParam(required = false,name = "page",defaultValue = "1") Integer currentPage,
                                             @RequestParam(required = false, name = "per_page",defaultValue = "10" ) Integer pageSize,
                                             @RequestParam(required = false, name = "product") Integer product,
                                             @RequestParam(required = false, name = "client") Integer client,
                                             @RequestParam(required = false, name = "start_age") Integer startAge,
                                             @RequestParam(required = false, name = "end_age") Integer endAge,
                                             @RequestParam(required = false, name = "text") String text
                                             ) {
        System.out.println("Current page in the controller : " + currentPage + ", Page size: " + pageSize);
        return this.sentimentService.getAllSentiments(
                type,
                currentPage,
                pageSize,
                client,
                product,
                startAge,
                endAge,text
        );
    }


    @DeleteMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void deleteSentiment(@PathVariable int id) {
        sentimentService.deleteSentiment(id);
    }

    @PostMapping(path = "init", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void initSentiments() {
        sentimentService.initializeSentimentList();
    }

    @GetMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Sentiments findById(@PathVariable int id) {
        return sentimentService.findById(id);
    }


}
