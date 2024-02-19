package projeto.api.backend2.dto;

import java.sql.Timestamp;

public record TransactionDTO(int id, int client_id, int valor, String tipo, String descricao,
        Timestamp realizada_em) {

}
