package projeto.api.backend2.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import projeto.api.backend2.dto.TransactionDTO;

public class TransactionMapper implements RowMapper<TransactionDTO> {

    @Override
    public TransactionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TransactionDTO(rs.getInt("id"),
                rs.getInt("client_id"),
                rs.getInt("valor"),
                rs.getString("tipo"),
                rs.getString("descricao"),
                rs.getTimestamp("realizada_em"));
    }

}
