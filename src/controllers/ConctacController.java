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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import objetos.Conctac;
import objetos.Departamento;

@ManagedBean
@ViewScoped
public class ConctacController implements Serializable {

	private TreeNode root;
	private Map<Integer, TreeNode> nodesById;
	private TreeNode newChild;
	private int nivelNodoSeleccionado;
	private String output;
	private Departamento selectedDepartamento;

	@PostConstruct
	public void init() {
		listarDepartamentos();
	}

	private void listarDepartamentos() {
		root = new DefaultTreeNode("Root", null);
		nodesById = new HashMap<Integer, TreeNode>();

		try (Connection conn = DbConection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM conctac")) {

			while (rs.next()) {
				int idConctac = rs.getInt("codigoctac");
				String nombrecta = rs.getString("nombrectac");
				String numerocta = rs.getString("numeroctac");
				String ancestrocta = rs.getString("ancestroctac");
				String numaltcta = rs.getString("numaltctac");
				String ancaltcta = rs.getString("ancaltctac");
				int codigotct = rs.getInt("codigotcta");
				int codigoest = rs.getInt("codigoesta");

				Conctac objConcat = new Conctac(idConctac, nombrecta, numerocta, ancestrocta, numaltcta, ancaltcta,
						codigotct, codigoest);

				TreeNode node = new DefaultTreeNode(objConcat, null);
//				nodesById.put(objConcat.getVnumeroctac(), node);

//				if (departamento.getId_padre() == 0) {
//					// El departamento es raíz
//					root.getChildren().add(node);
//				} else {
//					// El departamento tiene un padre
//					TreeNode parentNode = nodesById.get(departamento.getId_padre());
//					if (parentNode == null) {
//						parentNode = new DefaultTreeNode("?", null);
//						nodesById.put(departamento.getId_padre(), parentNode);
//					}
//					parentNode.getChildren().add(node);
//				}
			}

			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		listarDepartamentos();
		System.out.println("lista dep");
		// listDepartamento = new ArrayList<>();

	}

}
