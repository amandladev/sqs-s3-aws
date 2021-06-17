import model.OrdenVenta;
import service.OrdenService;

public class GenerarOrden {
    public static void main(String[] args) {
        OrdenService ordenService = new OrdenService();

        OrdenVenta ordenVenta = new OrdenVenta("Walther","Oseda",20);
        ordenService.create(ordenVenta);
    }
}
