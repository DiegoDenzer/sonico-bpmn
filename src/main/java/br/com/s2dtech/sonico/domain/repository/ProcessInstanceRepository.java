package br.com.s2dtech.sonico.domain.repository;

import br.com.s2dtech.sonico.domain.model.ProcessInstance;

import java.util.Optional;

public interface ProcessInstanceRepository {
    void save(ProcessInstance instance);
    Optional<ProcessInstance> findById(String id);
}
