package projeto.api.backend2.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projeto.api.backend2.dto.ExtratoResponseDTO;
import projeto.api.backend2.dto.ResponseDTO;
import projeto.api.backend2.dto.SaldoDTO;
import projeto.api.backend2.dto.TransactionDTO;
import projeto.api.backend2.exceptions.UnprocessableEntity;
import projeto.api.backend2.mappers.ClientMapper;
import projeto.api.backend2.mappers.TransactionMapper;
import projeto.api.backend2.model.Client;

@Service
@Transactional
public class ClientsService {
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public ClientsService(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    public Client findClientById(long clientId) {
        return jdbcTemplate.queryForObject("SELECT * FROM clients WHERE id =" + clientId + " FOR UPDATE",
                new ClientMapper());
    }

    public ResponseDTO create(Map<String, String> body, long id) {
        this.validations(body);
        Client client = this.findClientById(id);

        if (body.get("tipo").equals("c")) {
            return this.credito(client, body);
        }

        return this.debito(client, body);
    }

    public ResponseDTO credito(Client client, Map<String, String> body) {
        SimpleJdbcCall call = new SimpleJdbcCall(dataSource).withFunctionName("funcoes");
        SqlParameterSource in = new MapSqlParameterSource().addValue("cliente_id", client.getId())
                .addValue("valor_d", body.get("valor"))
                .addValue("descricao", body.get("descricao"))
                .addValue("tipo", "c");
        int response = call.executeFunction(Integer.class, in);

        if (response == 0) {
            throw new UnprocessableEntity();
        }
        Client updated = this.findClientById(client.getId());

        return new ResponseDTO(client.getLimite(), updated.getSaldo());
    }

    public ResponseDTO debito(Client client, Map<String, String> body) {
        SimpleJdbcCall call = new SimpleJdbcCall(dataSource).withFunctionName("funcoes");
        SqlParameterSource in = new MapSqlParameterSource().addValue("cliente_id", client.getId())
                .addValue("valor_d", body.get("valor"))
                .addValue("descricao", body.get("descricao"))
                .addValue("tipo", "d");
        int response = call.executeFunction(Integer.class, in);

        if (response == 0) {
            throw new UnprocessableEntity();
        }

        Client updated = this.findClientById(client.getId());

        return new ResponseDTO(client.getLimite(), updated.getSaldo());
    }

    public ExtratoResponseDTO extrato(long userId) {
        Client client = this.findClientById(userId);

        List<TransactionDTO> list = jdbcTemplate.query(
                "SELECT * FROM transactions WHERE transactions.client_id=" + client.getId()
                        + " ORDER BY realizada_em DESC LIMIT 10",
                new TransactionMapper());

        if (list.size() == 0) {
            SaldoDTO saldo = new SaldoDTO(0, LocalDateTime.now(), client.getLimite());
            return new ExtratoResponseDTO(saldo, list);
        }

        SaldoDTO saldo = new SaldoDTO(client.getSaldo(), LocalDateTime.now(), client.getLimite());
        return new ExtratoResponseDTO(saldo, list);
    }

    private static boolean isInteger(Object valor) {
        try {
            Integer.parseInt(String.valueOf(valor));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void validations(Map<String, String> body) {
        if (body.get("descricao") == null || body.get("valor") == null || body.get("tipo") == null) {
            throw new UnprocessableEntity();
        }

        if (body.get("descricao").toString().length() < 1 || body.get("descricao").toString().length() > 10) {
            throw new UnprocessableEntity();
        }

        if (!isInteger(body.get("valor"))) {
            throw new UnprocessableEntity();
        }

        if ((!body.get("tipo").equals("c") && !body.get("tipo").equals("d"))
                || Integer.parseInt(body.get("valor")) < 0) {
            throw new UnprocessableEntity();
        }
    }
}
