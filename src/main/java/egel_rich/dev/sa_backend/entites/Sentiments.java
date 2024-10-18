package egel_rich.dev.sa_backend.entites;
import egel_rich.dev.sa_backend.enums.SentimentType;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Avis")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class Sentiments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @Column(name = "text",nullable = false)
    private  String text;

    public Sentiments(String text, SentimentType type, Clients client, Products products) {
        this.text = text;
        this.type = type;
        this.client = client;
        this.products = products;
    }

    @Column(name = "type",nullable = false)
    private SentimentType type;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "client_id",referencedColumnName = "id",nullable = false)
    private Clients client;

    @ManyToOne(cascade = CascadeType.ALL)

    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private  Products products;

}
