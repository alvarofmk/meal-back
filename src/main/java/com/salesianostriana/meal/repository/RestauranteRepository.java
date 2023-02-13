package com.salesianostriana.meal.repository;

import com.salesianostriana.meal.model.Restaurante;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, UUID> {

    @EntityGraph("restaurante-con-platos")
    @Query("SELECT r FROM Restaurante r WHERE r.id = :id")
    public Optional<Restaurante> findOneWithMenu(UUID id);

}
