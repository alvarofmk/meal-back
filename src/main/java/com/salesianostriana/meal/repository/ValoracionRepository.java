package com.salesianostriana.meal.repository;

import com.salesianostriana.meal.model.Valoracion;
import com.salesianostriana.meal.model.key.ValoracionPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValoracionRepository extends JpaRepository<Valoracion, ValoracionPK> {
}
