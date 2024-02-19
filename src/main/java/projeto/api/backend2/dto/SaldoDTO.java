package projeto.api.backend2.dto;

import java.time.LocalDateTime;

public record SaldoDTO(int total, LocalDateTime data_extrato, int limite) {
    
}
