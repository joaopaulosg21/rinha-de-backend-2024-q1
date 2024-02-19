package projeto.api.backend2.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import projeto.api.backend2.model.Client;

public class ClientMapper implements RowMapper<Client> {

    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        Client client = new Client();
        client.setId(rs.getInt("id"));
        client.setLimite(rs.getInt("limite"));
        client.setSaldo(rs.getInt(("saldo")));

        return client;
    }
    
}
