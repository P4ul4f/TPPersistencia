package com.tp1.persistencia;

import com.tp1.persistencia.entidades.*;
import com.tp1.persistencia.enums.EstadoPedido;
import com.tp1.persistencia.enums.FormaPago;
import com.tp1.persistencia.enums.TipoEnvio;
import com.tp1.persistencia.enums.TipoProducto;
import com.tp1.persistencia.repositorios.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class PersistenciaApplication {
	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	DomicilioRepository domicilioRepository;

	@Autowired
	PedidoRepository pedidoRepository;

	@Autowired
	DetallePedidoRepository detallePedidoRepository;

	@Autowired
	FacturaRepository facturaRepository;

	@Autowired
	RubroRepository rubroRepository;

	@Autowired
	ProductoRepository productoRepository;

	public static void main(String[] args) {
		SpringApplication.run(PersistenciaApplication.class, args);

		System.out.println("Estoy probando");
	}

	//Instaciar entidades con sus asociaciones
	@Transactional
	@Bean
	CommandLineRunner initCliente(ClienteRepository clienteRepository, DomicilioRepository domicilioRepository,
								  PedidoRepository pedidoRepository, DetallePedidoRepository detallePedidoRepository,
								  FacturaRepository facturaRepository, RubroRepository rubroRepository,
								  ProductoRepository productoRepository){
		return args -> {

			System.out.println("Carga de clientes y domicilios");

			//Instaciar domicilios
			Domicilio domicilio1 = Domicilio.builder()
					.calle("España")
					.numero(777)
					.localidad("Mendoza")
					.build();

			Domicilio domicilio2 = Domicilio.builder()
					.calle("San Martín")
					.numero(888)
					.localidad("Godoy Cruz")
					.build();

			//Instanciar pedidos
			Pedido pedido1 = Pedido.builder()
					.estado(EstadoPedido.INICIADO)
//					.fecha()
					.tipoEnvio(TipoEnvio.DELIVERY)
					.total(26.6)
					.build();

			Pedido pedido2 = Pedido.builder()
					.estado(EstadoPedido.PREPARACION)
//					.fecha()
					.tipoEnvio(TipoEnvio.DELIVERY)
					.total(34.5)
					.build();

			//Instanciar clientes
			Cliente cliente1 = Cliente.builder()
					.nombre("Paula")
					.apellido("Ferreyra")
					.telefono("2617772228")
					.email("paula@gmail.com")
					.build();

			Cliente cliente2 = Cliente.builder()
					.nombre("Giuliana")
					.apellido("Ozan")
					.telefono("2613456789")
					.email("giuliana@gmail.com")
					.build();

			//Asocio cliente con su domicilio
			cliente1.agregarDomicilios(domicilio1);
			cliente2.agregarDomicilios(domicilio2);
			//Asocio cliente con su pedido
			cliente1.agregarPedidos(pedido1);
			cliente2.agregarPedidos(pedido2);

			//Instanciar detallePedido
			DetallePedido detallePedido1 = DetallePedido.builder()
					.cantidad(2)
					.subtotal(14.3)
					.build();

			//Asocio pedido con su detallePedido
			pedido1.agregarDetallePedido(detallePedido1);

			DetallePedido detallePedido2 = DetallePedido.builder()
					.cantidad(1)
					.subtotal(12.3)
					.build();

			//Asocio pedido con su detallePedido
			pedido2.agregarDetallePedido(detallePedido2);

			//Instanciar factura asociada a pedido
			Factura factura1 = Factura.builder()
					.numero(1)
//					.fecha()
					.descuento(3.4)
					.formaPago(FormaPago.EFECTIVO)
					.total(25)
					.build();

			pedido1.setFactura(factura1);

			Factura factura2 = Factura.builder()
					.numero(2)
//					.fecha()
					.descuento(2.3)
					.formaPago(FormaPago.TARJETA)
					.total(35)
					.build();

			pedido2.setFactura(factura2);


			//Instanciar rubros y los asocio a productos
			Producto producto1 = Producto.builder()
					.tipo(TipoProducto.MANUFACTURADO)
					.tiempoEstimadoCocina(2)
					.denominacion("Hamburguesa")
					.precioVenta(5.7)
					.precioCompra(4.2)
					.stockActual(13)
					.stockMinimo(3)
					.unidadMedida("Gramos")
					.receta("Elaborada con carne vacuna")
					.build();

			Rubro rubro1 = Rubro.builder()
					.denominacion("Comida")
					.build();

			// Creo el rubro uno y agrego los productos 1 y 2
			rubro1.agregarProductos(producto1);
			productoRepository.save(producto1);

			//Asocio el detalle pedido con su producto
			detallePedido1.setProducto(producto1);

			// Crear productos para el segundo rubro
			Producto producto2 = Producto.builder()
					.tipo(TipoProducto.INSUMO)
					.tiempoEstimadoCocina(2)
					.denominacion("Cerveza")
					.precioVenta(6.0)
					.precioCompra(4.5)
					.stockActual(10)
					.stockMinimo(2)
					.unidadMedida("Litro")
					.receta("Elaborada en base a miel")
					.build();

			// Creo el segundo rubro y agrego los productos
			Rubro rubro2 = Rubro.builder()
					.denominacion("Bebida")
					.build();

			rubro2.agregarProductos(producto2);

			productoRepository.save(producto2);
			//Asocio el detalle pedido con su producto
			detallePedido2.setProducto(producto2);

			Producto productoRecuperado = productoRepository.findById(producto1.getId()).orElse(null);

			if(productoRecuperado != null){
				System.out.println("Nombre: "+productoRecuperado.getTipo());
				productoRecuperado.setReceta("Ni idea");
			}

			//Guardo las instancias creadas en sus repositorios
			clienteRepository.save(cliente1);
			clienteRepository.save(cliente2);
			pedidoRepository.save(pedido1);
			pedidoRepository.save(pedido2);
			detallePedidoRepository.save(detallePedido1);
			detallePedidoRepository.save(detallePedido2);
			facturaRepository.save(factura1);
			facturaRepository.save(factura2);
			rubroRepository.save(rubro1);
			rubroRepository.save(rubro2);

			Cliente clienteRecuperado = clienteRepository.findById(cliente1.getId()).orElse(null);

			if(clienteRecuperado != null){
				System.out.println("Nombre: "+clienteRecuperado.getNombre());
				System.out.println("Apellido: "+clienteRecuperado.getApellido());
				System.out.println("Telefono: "+clienteRecuperado.getTelefono());
				System.out.println("Email: "+clienteRecuperado.getEmail());
				clienteRecuperado.mostrarDomicilios();
			}

		};
	}

}
