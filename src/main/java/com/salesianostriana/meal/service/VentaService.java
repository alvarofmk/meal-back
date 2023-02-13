package com.salesianostriana.meal.service;

import com.salesianostriana.meal.model.LineaVenta;
import com.salesianostriana.meal.model.Venta;
import com.salesianostriana.meal.model.dto.venta.LineaVentaRequestDTO;
import com.salesianostriana.meal.model.dto.venta.VentaRequestDTO;
import com.salesianostriana.meal.model.key.LineaVentaPK;
import com.salesianostriana.meal.repository.PlatoRepository;
import com.salesianostriana.meal.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository repository;
    private final PlatoRepository platoRepository;

    public Page<Venta> findAll(Pageable pageable){
        Page<Venta> result = repository.findAll(pageable);
        if(result.getTotalElements() == 0){
            throw new EntityNotFoundException();
        }
        return result;
    }

    public Venta findById(UUID id) {
        return repository.findById(id).orElseThrow(() ->new EntityNotFoundException());
    }

    public Venta add(Venta venta){
        return repository.save(venta);
    }

    public void deleteById(UUID id){
        repository.deleteById(id);
    }

    public Venta proccessVenta(VentaRequestDTO ventaDto) {
        return null;
    }

    public LineaVenta createLinea(LineaVentaRequestDTO lineaVentaRequestDTO){
        return platoRepository.findById(lineaVentaRequestDTO.getIdPlato())
                .map(p -> LineaVenta.builder()
                        .plato(p)
                        .cantidad(lineaVentaRequestDTO.getCantidad())
                        .subTotal(p.getPrecio() * lineaVentaRequestDTO.getCantidad())
                        .lineaVentaPK(new LineaVentaPK())
                        .build())
                .orElseThrow(() -> new EntityNotFoundException());
    }
/*
    public Page<Venta> findByRestaurant(UUID id, Pageable pageable){
        Page<Venta> result = repository.findByRestaurant(id ,pageable);
        if(result.getTotalElements() == 0){
            throw new EntityNotFoundException();
        }
        return result;
    }*/

}
