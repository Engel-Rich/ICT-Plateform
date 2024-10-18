package egel_rich.dev.sa_backend.mappers;

import egel_rich.dev.sa_backend.dto.ClientDTO;
import egel_rich.dev.sa_backend.entites.Clients;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ClientDTOMapper implements Function<Clients, ClientDTO> {


    @Override
    public ClientDTO apply(Clients client) {
        return  new ClientDTO(client.getId(),client.getEmail(), client.getName(), client.getAge());
    }
}
