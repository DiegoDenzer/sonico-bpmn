package br.com.s2dtech.sonico.infrastructure.api;

import br.com.s2dtech.sonico.application.usecases.DeployProcessUseCase;
import br.com.s2dtech.sonico.application.usecases.StartProcessUseCase;
import br.com.s2dtech.sonico.domain.model.ProcessDefinition;
import br.com.s2dtech.sonico.domain.model.ProcessInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProcessController {

    private final DeployProcessUseCase deployProcessUseCase;
    private final StartProcessUseCase startProcessUseCase;

    record DeployRequest(String bpmnXml) {}

    @PostMapping("/deploy")
    public ResponseEntity<?> deploy(@RequestBody String bpmnXml) {
        try {
            ProcessDefinition definition = deployProcessUseCase.execute(bpmnXml);
            return ResponseEntity.ok(Map.of("processKey", definition.getProcessKey()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Falha no parse do BPMN: " + e.getMessage());
        }
    }

    @PostMapping("/process-definition/{key}/start")
    public ResponseEntity<?> start(@PathVariable String key) {
        try {
            ProcessInstance instance = startProcessUseCase.execute(key);
            return ResponseEntity.ok(Map.of(
                    "instanceId", instance.getId(),
                    "status", instance.getStatus()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}

