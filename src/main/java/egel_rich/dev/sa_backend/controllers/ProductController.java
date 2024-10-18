package egel_rich.dev.sa_backend.controllers;

import egel_rich.dev.sa_backend.entites.Products;
import egel_rich.dev.sa_backend.enums.SentimentType;
import egel_rich.dev.sa_backend.services.ProductService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping(path = "product")
public class ProductController {

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
//
//    public ProductController() {
//    }

    private final ProductService productService;

    @GetMapping
    public PagedModel<?> findProducts(@RequestParam(required = false, name = "name") String name,
                                      @RequestParam(name = "page", defaultValue = "1") Integer currentPage,
                                      @RequestParam(name = "per_page", defaultValue = "20") Integer pageSize,
                                      @RequestParam(name = "client_id", required = false) Integer clientId,
                                      @RequestParam(name = "start_age", required = false) Integer startAge,
                                      @RequestParam(name = "end_age", required = false) Integer     endAge,
                                      @RequestParam(name = "start_price", required = false) Integer startPrice,
                                      @RequestParam(name = "end_price", required = false) Integer endPrice,
                                      @RequestParam(name = "sentiment_type", required = false) SentimentType type,
                                      PagedResourcesAssembler<Products> assembler
    ) {
        Page<Products> productsPage;
        if (clientId != null)
            productsPage = this.productService.findDistinctBySentiment_Client_Id(clientId, type, startAge, endAge, startPrice, endPrice, currentPage, pageSize);
        else productsPage = this.productService.findProducts(name, currentPage, pageSize);
        return assembler.toModel(productsPage);
    }

    @GetMapping(path = "{id}")
    public Products findProductsBiDI(@PathVariable(name = "id") int id) {
        return this.productService.findById(id);
    }

//    @GetMapping(path = "sentiment/{id}")
//    public Products findBySentimentId(@PathVariable(name = "id") Integer id) {
//        return this.productService.findDistinctBySentiment_Client_Id(id);
//    }


    @PostMapping
    public Products createProduct(@RequestBody(required = true) Products products) {
        return this.productService.createProduct(products);
    }

}
