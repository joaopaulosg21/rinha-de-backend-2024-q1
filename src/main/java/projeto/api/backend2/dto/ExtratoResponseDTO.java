package projeto.api.backend2.dto;

import java.util.List;

public record ExtratoResponseDTO(SaldoDTO saldo, List<TransactionDTO> ultimas_transacoes) {
    
}
