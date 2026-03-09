package com.victor.agendadortarefas.bussines.mapper;


import com.victor.agendadortarefas.bussines.dto.TarefaDTO;
import com.victor.agendadortarefas.infrastructure.entity.TarefaEntity;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefaConverter {

    TarefaEntity paraTarefaEntity (TarefaDTO dto);

    TarefaDTO paraTarefaDTO (TarefaEntity entity);

    List<TarefaEntity> paraListaTarefaEntity (List<TarefaDTO> dtos);

    List<TarefaDTO> paraListaTarefaDTO (List<TarefaEntity> entities);
}


