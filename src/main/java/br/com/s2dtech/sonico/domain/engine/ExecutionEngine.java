package br.com.s2dtech.sonico.domain.engine;

import br.com.s2dtech.sonico.domain.model.*;

/**
 * Serviço de Domínio. É o coração que executa a lógica do BPMN.
 * Não tem estado, apenas lógica.
 */
public class ExecutionEngine {

    public ProcessInstance start(ProcessDefinition definition) {
        ProcessInstance instance = new ProcessInstance(definition.getProcessKey());

        FlowNode startEvent = definition.getStartEvent()
                .orElseThrow(() -> new IllegalStateException("Processo não tem Start Event"));

        System.out.println("Iniciando instância " + instance.getId() + " em " + startEvent.getName());

        // Move o token do StartEvent para o próximo nó
        takeOutgoingFlow(instance, definition, startEvent);

        return instance;
    }

    /**
     * Continua a execução de uma instância que está "parada" em um nó.
     * Por hora em memoria antes de persistir
     */
    public void execute(ProcessInstance instance, ProcessDefinition definition) {
        FlowNode currentNode = definition.getNodeById(instance.getCurrentNodeId())
                .orElse(null);

        if (currentNode == null) {
            System.err.println("Erro: Nó " + instance.getCurrentNodeId() + " não encontrado.");
            instance.setStatus(ProcessInstance.Status.FAILED);
            return;
        }

        System.out.println("Executando nó: " + currentNode.getName() + " (ID: " + currentNode.getId() + ")");

        // Lógica de execução baseada no tipo de nó
        if (currentNode instanceof ServiceTaskNode) {
            // "Executa" a tarefa de serviço
            System.out.println("--- LOG: Executando Service Task '" + currentNode.getName() + "' ---");
            takeOutgoingFlow(instance, definition, currentNode);

        } else if (currentNode instanceof EndEventNode) {
            // Processo terminou
            System.out.println("Processo " + instance.getId() + " finalizado em " + currentNode.getName());
            instance.setStatus(ProcessInstance.Status.COMPLETED);

        } else {
            // Outros tipos de nós (StartEvent, Gateways, etc.)
            // Por enquanto, apenas seguimos o fluxo
            takeOutgoingFlow(instance, definition, currentNode);
        }
    }

    private void takeOutgoingFlow(ProcessInstance instance, ProcessDefinition definition, FlowNode node) {
        SequenceFlow flow = definition.getOutgoingFlow(node.getId())
                .orElseThrow(() -> new IllegalStateException("Nó " + node.getId() + " não tem fluxo de saída."));

        // Move o "token" para o próximo nó
        instance.setCurrentNodeId(flow.getTargetRef());

        // Continua a execução recursivamente
        // (Isso funciona porque não temos "wait states" como UserTasks ainda)
        execute(instance, definition);
    }
}