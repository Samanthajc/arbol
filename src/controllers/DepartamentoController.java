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

import objetos.Departamento;

@ManagedBean
@ViewScoped
public class DepartamentoController implements Serializable {

	// private List<Departamento> listDepartamento;
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
				ResultSet rs = stmt.executeQuery("SELECT * FROM departamento")) {

			while (rs.next()) {
				// Departamento data = new Departamento();
				// data.setId(resultS.getInt("id"));
				int idDepartamento = rs.getInt("id");
				int idDepartamentoPadre = rs.getInt("id_padre");
				String nombreDepartamento = rs.getString("nombre");
				String descripcionDepartamento = rs.getString("descripcion");

				Departamento departamento = new Departamento(idDepartamento, nombreDepartamento,
						descripcionDepartamento, idDepartamentoPadre);

				TreeNode node = new DefaultTreeNode(departamento, null);
				nodesById.put(departamento.getId(), node);

				if (departamento.getId_padre() == 0) {
					// El departamento es raíz
					root.getChildren().add(node);
				} else {
					// El departamento tiene un padre
					TreeNode parentNode = nodesById.get(departamento.getId_padre());
					if (parentNode == null) {
						parentNode = new DefaultTreeNode("?", null);
						nodesById.put(departamento.getId_padre(), parentNode);
					}
					parentNode.getChildren().add(node);
				}
			}

			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("lista dep");
		// listDepartamento = new ArrayList<>();

	}

	public void agregarNivel(Departamento node) {
		selectedDepartamento = node;
		System.out.println("nodo idpadre : ");

		// int idPadreSelect = selectedDepartamento.getId_padre();
		// System.out.println("nodo idpadre : " + idPadreSelect);
		try (Connection conn = DbConection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO departamento (nombre, descripcion, id_padre) VALUES (?, ?, ?)")) {
			pstmt.setString(1, nombreNuevoSubnivel);
			pstmt.setString(2, "descripcion");

			pstmt.setNull(3, java.sql.Types.INTEGER);

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		listarDepartamentos();
		nombreNuevoSubnivel = "";
	}

	private String nombreNuevoSubnivel;

	public String getNombreNuevoSubnivel() {
		return nombreNuevoSubnivel;
	}

	public void setNombreNuevoSubnivel(String nombreNuevoSubnivel) {
		this.nombreNuevoSubnivel = nombreNuevoSubnivel;
	}

	public void agregarSubnivel(Departamento node) {
		System.out.println("Nombre ingresado: " + nombreNuevoSubnivel);
		selectedDepartamento = node;
		int idSelect = selectedDepartamento.getId();

		// if(idPadreSelect==0) {
		//// idPadreSelect=(Integer) null;
		// }
		System.out.println("nodo ID: " + idSelect);
		try (Connection conn = DbConection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO departamento (nombre, descripcion, id_padre) VALUES (?, ?, ?)")) {
			pstmt.setString(1, nombreNuevoSubnivel);
			pstmt.setString(2, "descripcion");
			pstmt.setInt(3, idSelect);

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		listarDepartamentos();
	}

	public void eliminarNodo(Departamento node) {
		selectedDepartamento = node;
		int idSelect = selectedDepartamento.getId();

		System.out.println("eliminado ID: " + idSelect);
		try (Connection conn = DbConection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM departamento where id=?")) {

			pstmt.setInt(1, idSelect);

			pstmt.executeUpdate();

			System.out.println("eliminado con exito: " + idSelect);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		listarDepartamentos();

	}

	public Departamento getSelectedDepartamento() {
		return selectedDepartamento;
	}

	public void setSelectedDepartamento(Departamento selectedDepartamento) {
		this.selectedDepartamento = selectedDepartamento;
	}

	public TreeNode getRoot() {
		return root;
	}

}
