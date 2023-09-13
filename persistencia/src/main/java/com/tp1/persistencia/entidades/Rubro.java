package com.tp1.persistencia.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "rubro")

public class Rubro extends BaseEntidad{
    private String denominacion;

    //Relacion uno a muchos con pedidodetalle
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "rubro_id")

    @Builder.Default
    private List<Producto> productos = new ArrayList<>();

    public void agregarProductos(Producto produ){
        productos.add(produ);
    }
}
