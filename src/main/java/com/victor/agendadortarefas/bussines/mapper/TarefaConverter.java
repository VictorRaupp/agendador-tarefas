package com.victor.agendadortarefas.bussines.mapper;


import com.victor.agendadortarefas.bussines.dto.TarefaDTO;
import com.victor.agendadortarefas.infrastructure.entity.TaferaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefaConverter {

    TaferaEntity paraTarefaEntity (TarefaDTO dto);

    TarefaDTO paraTarefaDTO (TaferaEntity entity);
}
