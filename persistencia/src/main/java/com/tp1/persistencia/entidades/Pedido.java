package com.tp1.persistencia.entidades;

import com.tp1.persistencia.enums.EstadoPedido;
import com.tp1.persistencia.enums.TipoEnvio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pedido")

public class Pedido extends BaseEntidad{
    private EstadoPedido estado;
    private Date fecha;
    private TipoEnvio tipoEnvio;
    private double total;

    //Relacion uno a muchos con pedidodetalle
    @OneToMany (cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id")

    @Builder.Default
    private List<DetallePedido> detallePedidos = new ArrayList<>();

    //Relacion uno a cero/uno a Factura
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Factura factura;

    public void agregarDetallePedido(DetallePedido detalle){
        detallePedidos.add(detalle);
    }


}
