package br.com.s2dtech.sonico.infrastructure.persistence;

import br.com.s2dtech.sonico.domain.model.ProcessInstance;
import br.com.s2dtech.sonico.domain.repository.ProcessInstanceRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProcessInstanceRepository implements ProcessInstanceRepository {

    private final Map<String, ProcessInstance> db = new ConcurrentHashMap<>();

    @Override
    public void save(ProcessInstance instance) {
        db.put(instance.getId(), instance);
    }

    @Override
    public Optional<ProcessInstance> findById(String id) {
        return Optional.ofNullable(db.get(id));
    }
}