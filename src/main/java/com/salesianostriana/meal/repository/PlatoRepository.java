package com.salesianostriana.meal.repository;

import com.salesianostriana.meal.model.Plato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, UUID>, JpaSpecificationExecutor<Plato> {

    @Query("SELECT p FROM Plato p WHERE p.restaurante.id = :id")
    public Page<Plato> findByRestaurant(UUID id, Pageable pageable);

}
