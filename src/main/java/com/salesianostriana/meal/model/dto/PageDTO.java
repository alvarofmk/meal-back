package com.salesianostriana.meal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class PageDTO<T> {

    private List<T> contenido;
    private long numeroResultados;
    private int numeroPaginas;
    private int paginaActual;

    public PageDTO<T> of(Page page){
        this.contenido = page.getContent();
        this.numeroPaginas = page.getTotalPages();
        this.numeroResultados = page.getTotalElements();
        this.paginaActual = page.getNumber();
        return this;
    }

}
