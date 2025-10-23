package br.com.s2dtech.sonico.infrastructure.persistence;

import br.com.s2dtech.sonico.domain.model.ProcessDefinition;
import br.com.s2dtech.sonico.domain.repository.ProcessDefinitionRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProcessDefinitionRepository implements ProcessDefinitionRepository {

    private final Map<String, ProcessDefinition> db = new ConcurrentHashMap<>();

    @Override
    public void save(ProcessDefinition definition) {
        db.put(definition.getProcessKey(), definition);
    }

    @Override
    public Optional<ProcessDefinition> findByKey(String processKey) {
        return Optional.ofNullable(db.get(processKey));
    }
}
