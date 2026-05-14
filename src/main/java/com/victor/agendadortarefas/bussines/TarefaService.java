package com.victor.agendadortarefas.bussines;


import com.victor.agendadortarefas.bussines.dto.TarefaDTO;
import com.victor.agendadortarefas.bussines.mapper.TarefaConverter;
import com.victor.agendadortarefas.bussines.mapper.TarefaUpdateConverter;
import com.victor.agendadortarefas.infrastructure.entity.TarefaEntity;
import com.victor.agendadortarefas.infrastructure.enuns.StatusTarefaEnum;
import com.victor.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
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
    private final TarefaUpdateConverter tarefaUpdateConverter;

    public TarefaDTO gravarTarefa(String token, TarefaDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusTarefaEnum(StatusTarefaEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefaEntity entity = tarefaConverter.paraTarefaEntity(dto);

        return tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));
    }

    public List<TarefaDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInial,
                                                           LocalDateTime dataFinal) {
        return tarefaConverter.paraListaTarefaDTO(
                tarefasRepository.findByDataEventoBetweenAndStatusTarefaEnum(dataInial, dataFinal, StatusTarefaEnum.PENDENTE)
        );
    }

    public List<TarefaDTO> buscaTarefaPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        return tarefaConverter.paraListaTarefaDTO(
                tarefasRepository.findByEmailUsuario(email));
    }

    public void deletaTaferaPorId(String id) {
        try {
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("erro ao deletar tarefa por id " +
                    id, e.getCause());
        }
    }

    public TarefaDTO alteraStatus(StatusTarefaEnum status, String id) {
        try {
            TarefaEntity entity = tarefasRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException(
                            "Tarefa não encontrada " + id));
            entity.setStatusTarefaEnum(status);
            return tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(
                    "Erro ao altera status da terfa " + e.getCause());
        }
    }

    public TarefaDTO updateTarefa(TarefaDTO dto, String id) {
        try {
            TarefaEntity entity = tarefasRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException(
                            "Tarefa não encontrada " + id));
            tarefaUpdateConverter.updateTarefa(dto, entity);
            return tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(
                    "Erro ao altera status da terfa " + e.getCause());
        }

    }
}
