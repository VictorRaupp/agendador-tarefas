package com.victor.agendadortarefas.bussines;


import com.victor.agendadortarefas.bussines.dto.TarefaDTO;
import com.victor.agendadortarefas.bussines.mapper.TarefaConverter;
import com.victor.agendadortarefas.infrastructure.entity.TarefaEntity;
import com.victor.agendadortarefas.infrastructure.enuns.StatusTarefaEnum;
import com.victor.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.victor.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefasRepository tarefasRepository;
    private final TarefaConverter tarefaConverter;
    private final JwtUtil jwtUtil;

    public TarefaDTO gravarTarefa(String token, TarefaDTO dto){
        String email= jwtUtil.extrairEmailToken(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusTaferaEnum(StatusTarefaEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefaEntity entity = tarefaConverter.paraTarefaEntity(dto);

        return tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));
    }

    public List<TarefaDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInial,
                                                           LocalDateTime dataFinal){
        return tarefaConverter.paraListaTarefaDTO(
                tarefasRepository.findByDataEventoBetween(dataInial, dataFinal));
    }

    public List<TarefaDTO> buscaTarefaPorEmail(String token){
        String email= jwtUtil.extrairEmailToken(token.substring(7));

        return tarefaConverter.paraListaTarefaDTO(
                tarefasRepository.findByEmailUsuario(email));
    }
}
