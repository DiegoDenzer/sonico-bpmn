package br.com.s2dtech.sonico.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

@Getter
@Builder
public class ProcessDefinition {

    private String processKey;
    private Map<String, FlowNode> flowNodes; // Mapa de <ID, Nó>
    private Map<String, SequenceFlow> sequenceFlows;

    public Optional<FlowNode> getStartEvent() {
        return flowNodes.values().stream()
                .filter(node -> node instanceof StartEventNode)
                .findFirst();
    }

    public Optional<SequenceFlow> getOutgoingFlow(String nodeId) {
        return sequenceFlows.values().stream()
                .filter(flow -> flow.getSourceRef().equals(nodeId))
                .findFirst();
    }

    public Optional<FlowNode> getNodeById(String nodeId) {
        return Optional.ofNullable(flowNodes.get(nodeId));
    }
}
