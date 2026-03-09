package com.victor.agendadortarefas.infrastructure.repository;

import com.victor.agendadortarefas.infrastructure.entity.TarefaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TarefasRepository extends MongoRepository<TarefaEntity, String> {

    public List<TarefaEntity> findByDataEventoBetween(LocalDateTime dataInical,
                                                      LocalDateTime dataFinal);

    public List<TarefaEntity> findByEmailUsuario (String email);
}


