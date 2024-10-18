package egel_rich.dev.sa_backend.entites;


import com.fasterxml.jackson.annotation.JsonIgnore;
import egel_rich.dev.sa_backend.dto.ClientDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "Clients")
public class Clients {

    public Clients(String email, String name, int age) {
        this.email = email;
        this.name = name;
        this.age = age;
    }

    public Clients(int id) {
        this.id = id;
    }

    public Clients(ClientDTO dto) {
        this.email = dto.email();
        this.name = dto.name();
        this.id = dto.id();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private  String email;

    @Column()
    private  int age;

    @Column()
    private  String name;

    @Column(name = "created_at")
    private Date createdAt ;

    @Column(name = "updated_at")
    private Date updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    List<Sentiments> sentimentsList;

}
