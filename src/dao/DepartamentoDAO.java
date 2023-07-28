package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import conexion.ConexionBD;
import modelo.Centro;
import modelo.Departamento;

public class DepartamentoDAO {
	
	private ConexionBD conexion;
	
	public DepartamentoDAO() {
		this.conexion=new ConexionBD();
	}
	
	public ArrayList<Departamento> obtenerDepartamentos() {
    	// Obtenemos una conexion a la base de datos.
		Connection con = conexion.getConexion();
		Statement consulta = null;
		ResultSet resultado = null;
		ArrayList<Departamento> lista = new ArrayList<Departamento>();
		
		try {
			consulta = con.createStatement();
			resultado = consulta.executeQuery("select * from departamentos");
			
			// Bucle para recorrer todas las filas que devuelve la consulta
			while(resultado.next()) {
				
				int codDept = resultado.getInt("cod_departamento");
				int codCentro = resultado.getInt("cod_centro");
				String tipoDir = resultado.getString("tipo_dir");
				int presupuesto = resultado.getInt("presupuesto");
				String nombre = resultado.getString("nombre");
				
				Departamento dpto = new Departamento(codDept, codCentro, tipoDir,
						presupuesto, nombre);
				
				lista.add(dpto);
			}
			
		} catch (SQLException e) {
			System.out.println("Error al realizar la consulta sobre departamentos: "+e.getMessage());
		} finally {
			try {
				resultado.close();
				consulta.close();
				conexion.desconectar();
			} catch (SQLException e) {
				System.out.println("Error al liberar recursos: "+e.getMessage());
			} catch (Exception e) {
				
			}
		}
		return lista;
    }
	
	
	public int insertarDepartamento(Departamento dpto) throws SQLException {
    	// Obtenemos una conexion a la base de datos.
		Connection con = conexion.getConexion();
		PreparedStatement consulta = null;
		int resultado=0;
		
		try {
			consulta = con.prepareStatement("INSERT INTO departamentos(cod_departamento, cod_centro, tipo_dir,presupuesto, nombre) "
					+ " VALUES (?,?,?,?,?) ");
			
			
			consulta.setInt(1,dpto.getCodDepartamento());
			consulta.setInt(2, dpto.getCodCentro());
			consulta.setString(3, dpto.getTipoDir());
			consulta.setInt(4, dpto.getPresupuesto());
			consulta.setString(5, dpto.getNombre());
			
			resultado=consulta.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error al realizar la insercion del departamento: "
		        +e.getMessage());
			throw e;
		} finally {
			try {
				consulta.close();
				conexion.desconectar();
			} catch (SQLException e) {
				System.out.println("Error al liberar recursos: "+e.getMessage());
			} catch (Exception e) {
				
			}
		}
		return resultado;
    }

}
