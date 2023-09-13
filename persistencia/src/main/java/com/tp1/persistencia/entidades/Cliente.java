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
@Table(name = "cliente" )

public class Cliente extends BaseEntidad{

    @Column(name = "nombre")
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;

    //Relacion uno a muchos con domicilio
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")

    @Builder.Default

    private List<Domicilio> domicilios = new ArrayList<>();

    //Relacion uno a muchos con pedido
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    @Builder.Default
    private List<Pedido> pedidos = new ArrayList<>();

    public void agregarDomicilios(Domicilio domi){
        domicilios.add(domi);
    }

    public void mostrarDomicilios(){
        System.out.println("Domicilios de "+nombre+ " "+apellido+": ");

        for (Domicilio domicilio: domicilios){
            System.out.println("Calle: "+domicilio.getCalle()+", Número: "+ domicilio.getNumero()+", Localidad: "+domicilio.getLocalidad());
        }
    }

    public void agregarPedidos(Pedido ped){
        pedidos.add(ped);
    }


}
