package com.victor.agendadortarefas.bussines;


import com.victor.agendadortarefas.bussines.dto.TarefaDTO;
import com.victor.agendadortarefas.bussines.mapper.TarefaConverter;
import com.victor.agendadortarefas.infrastructure.entity.TaferaEntity;
import com.victor.agendadortarefas.infrastructure.enuns.StatusTaferaEnum;
import com.victor.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.victor.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefasRepository tarefasRepository;
    private final TarefaConverter tarefaConverter;
    private final JwtUtil jwtUtil;

    public TarefaDTO gravarTarefa(String token, TarefaDTO dto){
        String email= jwtUtil.extrairEmailToken(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusTaferaEnum(StatusTaferaEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TaferaEntity entity = tarefaConverter.paraTarefaEntity(dto);

        return tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));
    }
}
