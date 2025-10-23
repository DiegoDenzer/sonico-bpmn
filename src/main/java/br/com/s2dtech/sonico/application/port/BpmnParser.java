package br.com.s2dtech.sonico.application.port;

import br.com.s2dtech.sonico.domain.model.ProcessDefinition;

public interface BpmnParser {
    ProcessDefinition parse(String xml) throws Exception;
}