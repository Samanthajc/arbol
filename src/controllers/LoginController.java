package controllers;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import objetos.Departamento;

@ManagedBean
@SessionScoped
public class LoginController {

    private String usuario;
    private String clave;
       
    
    public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}




	public String getClave() {
		return clave;
	}


	public void setClave(String clave) {
		this.clave = clave;
	}


	public String ingresar() {
        String sql = "SELECT * FROM sisusuario WHERE loginusua = ? AND claveusua = ? ";
        String result="";

        try (Connection conn = DbConection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            stmt.setString(2, clave);
            
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
            	System.out.println("log correcto");
            	result= "arbol";
            } else {
            	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario y/o contraseña incorrectos", null));
                result= null;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
	}
}