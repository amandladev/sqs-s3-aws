package service;

import model.OrdenVenta;
import service.aws.SQSManager;

public class OrdenService {

    OrdenConverter ordenConverter = new OrdenConverter();
    SQSManager sqsManager = new SQSManager();

    public void create(OrdenVenta ordenVenta){

        System.out.println("Se guardaron los datos!");
        sendMessage(ordenVenta);

    }

    private void sendMessage(OrdenVenta ordenVenta) {
        String ordenVentatoString = ordenConverter.ordenToString(ordenVenta);
        sqsManager.sendOrdenMessage(ordenVentatoString);
    }

}
