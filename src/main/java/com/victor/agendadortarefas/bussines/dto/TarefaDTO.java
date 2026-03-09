package com.victor.agendadortarefas.bussines.dto
;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.victor.agendadortarefas.infrastructure.enuns.StatusTaferaEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarefaDTO {

    private String id;
    private String nomeTarefa;
    private String descricao;
    private LocalDateTime dataCriacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataEvento;
    private String emailUsuario;
    private LocalDateTime dataAlteracao;
    private StatusTaferaEnum statusTaferaEnum;
}
