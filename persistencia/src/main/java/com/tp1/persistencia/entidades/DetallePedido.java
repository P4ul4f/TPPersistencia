package com.tp1.persistencia.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "detallePedido")

public class DetallePedido extends BaseEntidad{

    private int cantidad;
    private double subtotal;

    @ManyToOne
    @JoinColumn(name = "detallePedido_id")

    private Producto producto;


}
