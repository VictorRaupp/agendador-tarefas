package com.victor.agendadortarefas.infrastructure.repository;

import com.victor.agendadortarefas.infrastructure.entity.TaferaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefasRepository extends MongoRepository<TaferaEntity, String> {
}
