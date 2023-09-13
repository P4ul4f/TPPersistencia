package com.tp1.persistencia.entidades;

import com.tp1.persistencia.enums.FormaPago;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Factura extends BaseEntidad{

    private int numero;
    private Date fecha;
    private double descuento;
    private FormaPago formaPago;
    private int total;

    @OneToOne
    @JoinColumn(name = "pedido_id", unique = true)
    private Pedido pedido;
}
