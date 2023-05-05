package controllers;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import objetos.Departamento;

@ManagedBean
@RequestScoped
public class ArbolController implements Serializable{
	
	private List<Departamento> listDepartamento;
	
	@PostConstruct
	public void init() {
		listarDepartamentos();
	}
	
	

	public void listarDepartamentos() {
		System.out.println("lista dep");
		listDepartamento = new ArrayList<>();
		
		try (Connection conn =  DbConection.getConnection();
	    		Statement statemnt = conn.createStatement();
	    		ResultSet resultS=statemnt.executeQuery("SELECT * FROM departamento")) {
			while(resultS.next()) {
              Departamento data = new Departamento();
              data.setId(resultS.getInt("id"));
              data.setNombre(resultS.getString("nombre"));
              data.setDescripcion(resultS.getString("descripcion"));
              data.setId_padre(resultS.getInt("id_padre"));
            
              listDepartamento.add(data);
          }
			
		    resultS.close();
          statemnt.close();
          conn.close();
          
    	} catch (SQLException e) {
    		System.out.println("conexion fallida: "+e);
    		e.printStackTrace();
    	}
    	
	}
	

	public List<Departamento> getListDepartamento() {
		return listDepartamento;
	}


}
