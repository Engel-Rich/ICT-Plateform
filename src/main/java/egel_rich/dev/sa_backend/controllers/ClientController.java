package egel_rich.dev.sa_backend.controllers;

import egel_rich.dev.sa_backend.dto.ClientDTO;
import egel_rich.dev.sa_backend.entites.Clients;
import egel_rich.dev.sa_backend.enums.SentimentType;
import egel_rich.dev.sa_backend.services.ClientService;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "client")
@AllArgsConstructor
public class ClientController {

    final private  ClientService clientService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Clients createClient(@RequestBody Clients clients) {
        return clientService.createClient(clients);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Page<ClientDTO> findAll(
            @RequestParam(name = "page_size", defaultValue = "25", required = false) Integer pageSize,
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer currentPage,
            @RequestParam(name = "sentiment_id" ,required = false) Integer sentimentID,
            @RequestParam(name = "product_id", required = false) Integer productID,
            @RequestParam(name = "start_age", required = false) Integer startAge,
            @RequestParam(name = "end_age", required = false) Integer endAge,
            @RequestParam(name = "start_price", required = false) Integer startPrice,
            @RequestParam(name = "end_price", required = false) Integer endPrice,
            @RequestParam(name = "sentiment_type", required = false) SentimentType sentimentType
    ) {
        return this.clientService.findAll(currentPage,pageSize, productID,sentimentID,startAge,endAge,startPrice,endPrice, sentimentType);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE, path = "{id}")
    public ClientDTO findClientById(@PathVariable int id) {
        return this.clientService.findClientById(id);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, path = "{id}")
    public Clients updateClient(@RequestBody Clients clients, @PathVariable int id) {
        System.out.println(clients.getEmail());
        return this.clientService.updateClient(id, clients);
    }
}


/*
Les trois facons de gerer les erreurs sont :
- En passant directement par un try catch dans ce cas on peut retourner un ResponseEntity
- En creant une methode pour gerer les erreur en particulier d'un controller errorHandler

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({RuntimeException.class, ChangeSetPersister.NotFoundException.class})
    public  ErrorModel handlerException(Exception exception) {
        return new ErrorModel(exception.getMessage(), null);
    }
}
- en utilisant un controller advice pour capturer l'ensemble des erreur d'une application
* */