package br.com.s2dtech.sonico.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Representa uma execução *única* de uma ProcessDefinition.
 * Este é o nosso Agregado (DDD) principal
 */
@Getter
public class ProcessInstance {

    public enum Status {
        RUNNING, COMPLETED, FAILED
    }

    private String id;
    private String processDefinitionKey;

    @Setter
    private Status status;

    // O "token" de execução. Indica em qual nó estamos parados.
    @Setter
    private String currentNodeId;

    public ProcessInstance(String processDefinitionKey) {
        this.id = UUID.randomUUID().toString();
        this.processDefinitionKey = processDefinitionKey;
        this.status = Status.RUNNING;
    }
}