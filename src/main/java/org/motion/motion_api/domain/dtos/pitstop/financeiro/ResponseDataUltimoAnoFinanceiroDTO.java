package org.motion.motion_api.domain.dtos.pitstop.financeiro;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class ResponseDataUltimoAnoFinanceiroDTO {
    private String mes;
    private Double entradas;
    private Double saidas;
    private Double saldo;
}
