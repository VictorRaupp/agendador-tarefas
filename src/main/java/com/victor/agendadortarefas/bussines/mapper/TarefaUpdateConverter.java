package com.victor.agendadortarefas.bussines.mapper;

import com.victor.agendadortarefas.bussines.dto.TarefaDTO;
import com.victor.agendadortarefas.infrastructure.entity.TarefaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TarefaUpdateConverter {

    void updateTarefa(TarefaDTO dto, @MappingTarget TarefaEntity entity);
}
