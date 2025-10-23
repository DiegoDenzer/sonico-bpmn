package br.com.s2dtech.sonico.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SequenceFlow {
    private String id;
    private String sourceRef;
    private String targetRef;
}
