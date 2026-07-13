package Models;

import java.util.ArrayList;
import java.util.List;

public class GestorVentas {
    private List<Producto> catalogo;

    public GestorVentas() {
        this.catalogo = new ArrayList<>();
    }

    public void registrarProducto(String nombre, double precio, int stock) {
        Producto existente = buscarProducto(nombre);
        if (existente != null) {
            existente.setStock(existente.getStock() + stock);
        } else {
            catalogo.add(new Producto(nombre, precio, stock));
        }
    }

    public Producto buscarProducto(String nombre) {
        for (Producto p : catalogo) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    public List<Producto> getCatalogo() {
        return catalogo;
    }

    public String realizarEnvio(String origen, String destino, String nombreProducto, int cantidad, GrafoPesado<String> grafo, javax.swing.JTextField txtPrecioFinal) {
        Producto producto = buscarProducto(nombreProducto);
        if (producto == null) {
            return "Error: El producto '" + nombreProducto + "' no existe en el catálogo.";
        }

        if (producto.getStock() < cantidad) {
            return "Error: Stock insuficiente. Solo quedan " + producto.getStock() + " unidades.";
        }

        Dijkstra<String> dijkstra = new Dijkstra<>(grafo);
        //saco el costo del envio y le sumo el total del producto
        dijkstra.ejecutarDijkstra(origen, destino);
        double costoEnvio = dijkstra.getCostoMinimo(destino);

        if (costoEnvio == Double.MAX_VALUE) {
            return "Error: No existe ninguna ruta que conecte " + origen + " con " + destino + ".";
        }

        List<String> ruta = dijkstra.getRutaOptima(destino);

        double costoProductos = producto.getPrecio() * cantidad;
        double total = costoProductos + costoEnvio;

        producto.setStock(producto.getStock() - cantidad);

        txtPrecioFinal.setText(String.valueOf(total));

        StringBuilder sb = new StringBuilder();
        sb.append("📋 RESUMEN DE ENVÍO Y COMPRA\n");
        sb.append("==================================\n");
        sb.append("Producto enviado: ").append(nombreProducto).append("\n");
        sb.append("Cantidad: ").append(cantidad).append(" unidades\n");
        sb.append("Ruta de entrega: ");
        for (int i = 0; i < ruta.size(); i++) {
            sb.append(ruta.get(i));
            if (i < ruta.size() - 1) {
                sb.append(" -> ");
            }
        }
        sb.append("\n\n");
        sb.append("Costo de Productos:  ").append(costoProductos).append(" Bs.\n");
        sb.append("Costo de Transporte: ").append(costoEnvio).append(" Bs. (Dijkstra)\n");
        sb.append("----------------------------------\n");
        sb.append("TOTAL FACTURADO:     ").append(total).append(" Bs.\n\n");
        sb.append("Stock global restante: ").append(producto.getStock()).append(" unidades.");

        return sb.toString();
    }
}
