package br.com.s2dtech.sonico.domain.repository;

import br.com.s2dtech.sonico.domain.model.ProcessDefinition;

import java.util.Optional;

public interface ProcessDefinitionRepository {
    void save(ProcessDefinition definition);
    Optional<ProcessDefinition> findByKey(String processKey);
}
