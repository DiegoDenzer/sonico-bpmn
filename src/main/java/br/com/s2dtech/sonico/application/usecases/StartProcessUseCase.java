package br.com.s2dtech.sonico.application.usecases;

import br.com.s2dtech.sonico.domain.engine.ExecutionEngine;
import br.com.s2dtech.sonico.domain.model.ProcessDefinition;
import br.com.s2dtech.sonico.domain.model.ProcessInstance;
import br.com.s2dtech.sonico.domain.repository.ProcessDefinitionRepository;
import br.com.s2dtech.sonico.domain.repository.ProcessInstanceRepository;

public class StartProcessUseCase {

    private final ProcessDefinitionRepository definitionRepository;
    private final ProcessInstanceRepository instanceRepository;
    private final ExecutionEngine executionEngine;

    public StartProcessUseCase(ProcessDefinitionRepository definitionRepository,
                               ProcessInstanceRepository instanceRepository,
                               ExecutionEngine executionEngine) {
        this.definitionRepository = definitionRepository;
        this.instanceRepository = instanceRepository;
        this.executionEngine = executionEngine;
    }

    public ProcessInstance execute(String processKey) {
        ProcessDefinition definition = definitionRepository.findByKey(processKey)
                .orElseThrow(() -> new RuntimeException("Processo '" + processKey + "' não encontrado."));

        ProcessInstance instance = executionEngine.start(definition);

        // Salva o estado final da instância (que já deve ser COMPLETED)
        instanceRepository.save(instance);

        return instance;
    }
}