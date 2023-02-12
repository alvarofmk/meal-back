package com.salesianostriana.meal;

import com.salesianostriana.meal.model.Plato;
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
        Restaurante r1 = Restaurante.builder()
                .nombre("McDonalds")
                .apertura(LocalTime.NOON)
                .cierre(LocalTime.MIDNIGHT)
                .coverImgUrl("https://enriqueortegaburgos.com/wp-content/uploads/2022/08/MCDONALD%C2%B4S-ESTRATEGIAS-DE-NEGOCIO-6.jpg")
                .descripcion("Mcdonald things")
                .build();
        Restaurante r2 = Restaurante.builder()
                .nombre("Dominos")
                .apertura(LocalTime.NOON)
                .cierre(LocalTime.MIDNIGHT)
                .coverImgUrl("https://d500.epimg.net/cincodias/imagenes/2022/08/09/companias/1660075627_280059_1660078369_noticia_normal.jpg")
                .descripcion("Dominos things")
                .build();

        Plato p1 = Plato.builder()
                .nombre("Bigmac")
                .precio(7)
                .descripcion("Yo que se")
                .imgUrl("Una imagen")
                .build();
        Plato p2 = Plato.builder()
                .nombre("Patatas deluxe")
                .precio(2)
                .descripcion("Las mejores patatas")
                .imgUrl("Una imagen")
                .build();
        Plato p3 = Plato.builder()
                .nombre("CBO")
                .precio(6.50)
                .descripcion("El cielo en la tierra")
                .imgUrl("Una imagen")
                .build();
        Plato p4 = Plato.builder()
                .nombre("Cremozza")
                .precio(9)
                .descripcion("Lo unico bueno")
                .imgUrl("Una imagen")
                .build();
        Plato p5 = Plato.builder()
                .nombre("Hawaiiana")
                .precio(9)
                .descripcion("Ew")
                .imgUrl("Una imagen")
                .build();

        p1.addRestaurante(r1);
        p2.addRestaurante(r1);
        p3.addRestaurante(r1);
        p4.addRestaurante(r2);
        p5.addRestaurante(r2);

        restauranteRepository.save(r1);
        restauranteRepository.save(r2);
        platoRepository.save(p1);
        platoRepository.save(p2);
        platoRepository.save(p3);
        platoRepository.save(p4);
        platoRepository.save(p5);
    }

}
