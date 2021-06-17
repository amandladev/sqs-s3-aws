package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.OrdenVenta;


public class OrdenConverter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String ordenToString(OrdenVenta ordenVenta){

        try {
            return objectMapper.writeValueAsString(ordenVenta);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OrdenVenta stringToOrden(String ordenString){

        try {
            return objectMapper.readValue(ordenString, OrdenVenta.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

}
