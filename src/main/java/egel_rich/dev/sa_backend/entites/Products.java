package egel_rich.dev.sa_backend.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "Products")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Products(int id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    private  String name;

    @Column(name = "description")
    private  String description;

    @Column(name = "price", nullable = false)
    private  int price;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private  Date updatedAt;

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Sentiments> sentimentsList;

}
