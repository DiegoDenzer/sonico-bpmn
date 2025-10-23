# Projeto "Sonico" BPMN Engine

![Java](https://img.shields.io/badge/Java-25+-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=spring)
![JPA/Hibernate](https://img.shields.io/badge/JPA-Hibernate-orange)
![BPMN 2.0](https://img.shields.io/badge/BPMN%202.0-Compatible-blue)

Um projeto para criar uma engine de fluxo de trabalho (workflow) leve e "do zero", compatível com um subconjunto da especificação BPMN 2.0, utilizando Java e o ecossistema Spring.

## 🎯 Objetivo

O objetivo deste projeto é construir uma engine BPMN funcional, focada em aprendizado e customização. A ideia não é competir com soluções robustas como Camunda ou Flowable, mas sim entender profundamente os conceitos de orquestração de processos, máquinas de estado, transações e concorrência envolvidos na construção de tal sistema.

## 🛠️ Stack Tecnológica

* **Linguagem:** Java 25+ (ou Kotlin)
* **Framework:** Spring Boot 3+
* **Persistência:** Spring Data JPA (Hibernate)
* **Banco de Dados (sugerido):** PostgreSQL / H2 (para testes)
* **API:** Spring Web (RESTful API)
* **Build:** Maven (ou Gradle)
* **Modelagem:** [bpmn.io](https://bpmn.io/) (para criar os arquivos `.bpmn` que serão lidos pela engine)

## 🗺️ Arquitetura Central

A engine será composta pelos seguintes módulos principais:

1.  **Parser (Analisador):** Responsável por ler o arquivo `.bpmn` (XML) e transformá-lo em um modelo de processo executável em memória (POJOs).
2.  **Motor de Execução (Execution Core):** O coração do sistema. Gerencia o estado das instâncias de processo, a lógica de "tokens" de execução e a semântica dos elementos BPMN.
3.  **Camada de Persistência:** Salva o estado das instâncias, tarefas e variáveis no banco de dados, garantindo que processos longos possam sobreviver a reinicializações.
4.  **Gerenciador de Tarefas (Tasklist):** Lida especificamente com `User Tasks`, pausando a execução do processo e esperando por intervenção humana.
5.  **Executor de Jobs (Job Executor):** Lida com tarefas assíncronas, como `Service Tasks` (via workers externos) e `Timer Events`.
6.  **API Pública:** Uma camada REST para interagir com a engine (iniciar processos, completar tarefas, consultar estado).

## 🚀 Roteiro (Roadmap) do MVP

O desenvolvimento será iterativo, começando pelo subconjunto mais simples do BPMN.

### Versão 0.1 (O "Hello World" do BPMN)
* [x] Implementar o Parser de XML para um modelo em memória.
* [x] Motor de Execução **apenas em memória** (sem persistência).
* [x] Suporte aos elementos:
    * `startEvent` (None)
    * `sequenceFlow`
    * `serviceTask` (com execução síncrona, ex: logar no console)
    * `endEvent` (None)

### Versão 0.2 (Persistência é Chave)
* [ ] Introduzir a camada de Persistência (JPA/Hibernate).
* [ ] Motor de Execução salva o estado em "pontos de espera" (wait states).
* [ ] Conceito de Transações e "Asynchronous Continuations".

### Versão 0.3 (Interação Humana)
* [ ] Implementar o Gerenciador de Tarefas.
* [ ] Adicionar API de Tasklist (`GET /tasks`, `POST /tasks/{id}/complete`).
* [ ] Suporte ao elemento:
    * `userTask`

### Versão 0.4 (Decisões e Condições)
* [ ] Adicionar suporte a variáveis de processo.
* [ ] Implementar lógica de expressões (ex: JUEL ou SpEL).
* [ ] Suporte ao elemento:
    * `exclusiveGateway` (XOR)

### Versão 0.5 (Concorrência)
* [ ] Implementar a lógica de "fork" (divisão) e "join" (junção) de tokens.
* [ ] Suporte ao elemento:
    * `parallelGateway` (AND)

### Versões Futuras (Backlog)
* [ ] `timerEvent` (Intermediário e de Borda)
* [ ] `errorEvent` (Borda e Fim)
* [ ] `subProcess` (Simples e Transacional)
* [ ] `messageEvent` (Correlação de mensagens)
* [ ] ... e muito mais!

## ⚙️ Como Executar (Exemplo)

```bash
# 1. Clonar o repositório
git clone [https://github.com/seu-usuario/seu-projeto.git](https://github.com/seu-usuario/seu-projeto.git)
cd seu-projeto

# 2. Compilar com Maven
./mvnw clean package

# 3. Executar o JAR (com Spring Boot)
java -jar target/corvus-bpmn-0.0.1-SNAPSHOT.jar
