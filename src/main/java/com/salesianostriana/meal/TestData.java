package com.salesianostriana.meal;

import com.salesianostriana.meal.model.Restaurante;
import com.salesianostriana.meal.repository.PlatoRepository;
import com.salesianostriana.meal.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestData {

    private final RestauranteRepository restauranteRepository;
    private final PlatoRepository platoRepository;

    @PostConstruct
    public void initData(){
        restauranteRepository.saveAll(
                List.of(
                        Restaurante.builder()
                                .nombre("McDonalds")
                                .apertura(LocalTime.NOON)
                                .cierre(LocalTime.MIDNIGHT)
                                .coverImgUrl("https://enriqueortegaburgos.com/wp-content/uploads/2022/08/MCDONALD%C2%B4S-ESTRATEGIAS-DE-NEGOCIO-6.jpg")
                                .descripcion("Mcdonald things")
                                .build(),
                        Restaurante.builder()
                                .nombre("Dominos")
                                .apertura(LocalTime.NOON)
                                .cierre(LocalTime.MIDNIGHT)
                                .coverImgUrl("https://d500.epimg.net/cincodias/imagenes/2022/08/09/companias/1660075627_280059_1660078369_noticia_normal.jpg")
                                .descripcion("Dominos things")
                                .build()
                )
        );
    }

}
