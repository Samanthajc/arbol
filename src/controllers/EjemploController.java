package controllers;

import java.sql.Connection;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class EjemploController {
    
    private String mensaje = "Hola desde el controlador";

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public void testConection() {
    	try {
    		Connection conn =  DbConection.getConnection();
    		System.out.println("conexion establecida: "+conn);
    	} catch (SQLException e) {
    		System.out.println("conexion fallida: "+e);

    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	System.out.println("saludos desde login");
    }
}

