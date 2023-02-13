package com.salesianostriana.meal.repository;

import com.salesianostriana.meal.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VentaRepository extends JpaRepository<Venta, UUID> {
}
