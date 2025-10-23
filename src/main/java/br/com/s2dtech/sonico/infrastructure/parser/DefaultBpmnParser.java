package br.com.s2dtech.sonico.infrastructure.parser;

import br.com.s2dtech.sonico.application.port.BpmnParser;
import br.com.s2dtech.sonico.domain.model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class DefaultBpmnParser implements BpmnParser {

    private static final String BPMN_PROCESS = "process";
    private static final String BPMN_START_EVENT = "startEvent";
    private static final String BPMN_END_EVENT = "endEvent";
    private static final String BPMN_SERVICE_TASK = "serviceTask";
    private static final String BPMN_SEQUENCE_FLOW = "sequenceFlow";

    @Override
    public ProcessDefinition parse(String xml) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xml)));
        doc.getDocumentElement().normalize();

        Element process = (Element) doc.getElementsByTagName(BPMN_PROCESS).item(0);

        String processKey = process.getAttribute("id");

        Map<String, FlowNode> flowNodes = new HashMap<>();
        Map<String, SequenceFlow> sequenceFlows = new HashMap<>();

        // Parse Start Events
        NodeList startNodes = process.getElementsByTagName(BPMN_START_EVENT);
        for (int i = 0; i < startNodes.getLength(); i++) {
            Element el = (Element) startNodes.item(i);
            String id = el.getAttribute("id");
            String name = el.getAttribute("name");
            flowNodes.put(id, new StartEventNode(id, name));
        }

        // Parse End Events
        NodeList endNodes = process.getElementsByTagName(BPMN_END_EVENT);
        for (int i = 0; i < endNodes.getLength(); i++) {
            Element el = (Element) endNodes.item(i);
            String id = el.getAttribute("id");
            String name = el.getAttribute("name");
            flowNodes.put(id, new EndEventNode(id, name));
        }

        // Parse Service Tasks
        NodeList taskNodes = process.getElementsByTagName(BPMN_SERVICE_TASK);
        for (int i = 0; i < taskNodes.getLength(); i++) {
            Element el = (Element) taskNodes.item(i);
            String id = el.getAttribute("id");
            String name = el.getAttribute("name");
            flowNodes.put(id, new ServiceTaskNode(id, name));
        }

        // Parse Sequence Flows
        NodeList flowNodesList = process.getElementsByTagName(BPMN_SEQUENCE_FLOW);
        for (int i = 0; i < flowNodesList.getLength(); i++) {
            Element el = (Element) flowNodesList.item(i);
            String id = el.getAttribute("id");
            String sourceRef = el.getAttribute("sourceRef");
            String targetRef = el.getAttribute("targetRef");
            sequenceFlows.put(id, new SequenceFlow(id, sourceRef, targetRef));
        }

        return ProcessDefinition.builder()
                .processKey(processKey)
                .flowNodes(flowNodes)
                .sequenceFlows(sequenceFlows)
                .build();
    }
}