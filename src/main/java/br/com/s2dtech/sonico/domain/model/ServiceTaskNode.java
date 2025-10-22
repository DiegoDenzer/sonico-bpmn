package br.com.s2dtech.sonico.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceTaskNode implements FlowNode {
    private String id;
    private String name;
}
