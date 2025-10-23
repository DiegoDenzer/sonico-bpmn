package br.com.s2dtech.sonico.application.usecases;

import br.com.s2dtech.sonico.application.port.BpmnParser;
import br.com.s2dtech.sonico.domain.model.ProcessDefinition;
import br.com.s2dtech.sonico.domain.repository.ProcessDefinitionRepository;

public class DeployProcessUseCase {

    private final BpmnParser bpmnParser;
    private final ProcessDefinitionRepository repository;

    public DeployProcessUseCase(BpmnParser bpmnParser, ProcessDefinitionRepository repository) {
        this.bpmnParser = bpmnParser;
        this.repository = repository;
    }

    public ProcessDefinition execute(String bpmnXml) throws Exception {
        ProcessDefinition definition = bpmnParser.parse(bpmnXml);
        repository.save(definition);
        System.out.println("Processo '" + definition.getProcessKey() + "' salvo no repositório.");
        return definition;
    }
}