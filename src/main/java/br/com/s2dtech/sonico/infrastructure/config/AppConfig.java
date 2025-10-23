package br.com.s2dtech.sonico.infrastructure.config;

import br.com.s2dtech.sonico.application.port.BpmnParser;
import br.com.s2dtech.sonico.application.usecases.DeployProcessUseCase;
import br.com.s2dtech.sonico.application.usecases.StartProcessUseCase;
import br.com.s2dtech.sonico.domain.engine.ExecutionEngine;
import br.com.s2dtech.sonico.domain.repository.ProcessDefinitionRepository;
import br.com.s2dtech.sonico.domain.repository.ProcessInstanceRepository;
import br.com.s2dtech.sonico.infrastructure.parser.DefaultBpmnParser;
import br.com.s2dtech.sonico.infrastructure.persistence.InMemoryProcessDefinitionRepository;
import br.com.s2dtech.sonico.infrastructure.persistence.InMemoryProcessInstanceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ExecutionEngine executionEngine() {
        return new ExecutionEngine();
    }

    @Bean
    public BpmnParser bpmnParser() {
        return new DefaultBpmnParser();
    }

    @Bean
    public ProcessDefinitionRepository processDefinitionRepository() {
        return new InMemoryProcessDefinitionRepository();
    }

    @Bean
    public ProcessInstanceRepository processInstanceRepository() {
        return new InMemoryProcessInstanceRepository();
    }

    @Bean
    public DeployProcessUseCase deployProcessUseCase(BpmnParser parser, ProcessDefinitionRepository repository) {
        return new DeployProcessUseCase(parser, repository);
    }

    @Bean
    public StartProcessUseCase startProcessUseCase(ProcessDefinitionRepository defRepo,
                                                   ProcessInstanceRepository instRepo,
                                                   ExecutionEngine engine) {
        return new StartProcessUseCase(defRepo, instRepo, engine);
    }
}
