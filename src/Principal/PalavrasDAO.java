package Principal;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PalavrasDAO {
	
	public ArrayList<String> retornaPalavras(){
		Connection con = ConnectionFactory.getConnection();
        
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ArrayList<String> palavras = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM palavras WHERE categoria_id = 3");
            rs = stmt.executeQuery();

            while (rs.next()) {
            	palavras.add(rs.getString("nome"));
            }

        } catch (SQLException ex) {
           
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return palavras;
		
	}
}
