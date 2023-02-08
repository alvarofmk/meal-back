package com.salesianostriana.meal.repository;

import com.salesianostriana.meal.model.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, UUID>, JpaSpecificationExecutor<Plato> {
}
