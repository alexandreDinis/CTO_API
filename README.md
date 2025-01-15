# CTO-API - Sistema de CRM para Profissionais de Reparação Automotiva

A **CTO-API** é uma plataforma de CRM (Customer Relationship Management) voltada para profissionais que atuam na área de reparação automotiva, como serviços de martelinho de ouro, pintura, funilaria e outros reparos automotivos. A API oferece uma série de funcionalidades projetadas para facilitar a gestão de clientes, orçamentos e serviços prestados.

## Funcionalidades Principais:

### 1. Cadastro de Usuários e Segurança
A API permite o cadastro de usuários, com a segurança proporcionada pelo **Java Security**, que criptografa as senhas dos usuários, garantindo a proteção dos dados sensíveis.

### 2. Gestão de Clientes
O sistema permite o cadastro completo de clientes, incluindo informações detalhadas e histórico de serviços prestados.

### 3. Cadastro de Veículos e Serviços
É possível cadastrar os veículos que passaram por serviços de reparo, detalhando as peças substituídas, o tipo de serviço realizado, o valor do serviço, descontos aplicados e outras informações relevantes. O usuário pode filtrar dados por cliente, ordem de serviço ou até mesmo pela placa do veículo para consultar o histórico de serviços realizados.

### 4. Orçamentos e Faturamento
O usuário pode gerar orçamentos detalhados, armazená-los e, ao concluir o serviço, atualizar o valor final, incluindo descontos ou preço cheio. A API também fornece relatórios detalhados de faturamento, permitindo ao usuário acompanhar o valor arrecadado por dia, mês e ano.

### 5. Gestão de Frota e Abastecimento
Para profissionais que utilizam veículos no trabalho, a API oferece um módulo de gerenciamento de frota. O usuário pode cadastrar os veículos utilizados e registrar abastecimentos, detalhando a placa do veículo, tipo de combustível, valor abastecido e quilometragem. Com essas informações, é possível gerar relatórios sobre o desempenho dos veículos, como a quantidade de quilometragem rodada e o custo de abastecimento, além de realizar análises sobre a eficiência de cada tipo de combustível.

### 6. Manutenção Preventiva
A API oferece a possibilidade de cadastrar manutenções relacionadas aos veículos. Caso essas manutenções dependam da quilometragem do veículo, a API notificará o usuário sobre a necessidade de realizar a manutenção no momento apropriado.

### 7. Relatórios e Análises
A API gera relatórios detalhados, incluindo:
- Quantidade de carros atendidos em um intervalo de tempo escolhido.
- Valores de orçamentos e serviços realizados, com filtros por período.
- Ranking dos melhores clientes, ajudando o profissional a identificar quais clientes geram mais receita e a focar nesses clientes.
- Acompanhamento de faturamento por cliente em um intervalo de tempo especificado.

### 8. Melhorias Contínuas
A API está em constante evolução, com melhorias sendo implementadas regularmente. Se você está interessado em contribuir com o projeto, entre em contato para mais informações sobre como colaborar.

---

A **CTO-API** é uma solução poderosa e prática para profissionais da área automotiva que buscam otimizar a gestão de seus serviços, melhorar o relacionamento com os clientes e alcançar maior eficiência operacional.
# Requisitos para Rodar a API

## Tecnologias Utilizadas

- **Java 17**  
  A API é construída em Java 17. Certifique-se de ter o **JDK** instalado em sua máquina.

- **Banco de Dados MySQL**  
  A API utiliza o **MySQL** versão 8.0. Certifique-se de ter o banco de dados configurado e funcionando corretamente.

- **Variáveis de Ambiente**  
  As variáveis de ambiente estão configuradas no arquivo `application.properties` do código-fonte. Configure as variáveis necessárias em sua máquina de acordo com a sua infraestrutura.

## Passos para Rodar a Aplicação

1. **Instalar o JDK**  
   Instale o **JDK 17** (Java 8 ou superior também pode funcionar, mas recomendamos a versão mais recente).

2. **Instalar Maven ou Gradle**  
   A API pode ser executada utilizando **Maven** ou **Gradle**. Se não tiver um dos dois instalados, faça a instalação do Maven ou do Gradle em sua máquina.

3. **Configurar o Banco de Dados**  
   Se necessário, configure o banco de dados **MySQL** na sua máquina, criando as tabelas necessárias e ajustando as configurações de conexão no arquivo `application.properties`.

4. **Rodar a Aplicação**  
   Após as configurações, você pode rodar a aplicação usando Maven ou Gradle. Se o projeto for empacotado em um **JAR**, você pode executá-lo diretamente.

   **Com Maven**:
   ```bash
   mvn spring-boot:run
### Acesse o Swagger UI:
Abra seu navegador e vá até o endereço:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### O que você verá:
Ao acessar o Swagger UI, você verá uma interface onde todas as rotas da API são listadas, com informações sobre os endpoints, parâmetros e respostas. A interface permite que você:

- **Visualize os endpoints**: Veja todos os métodos HTTP (GET, POST, PUT, DELETE) disponíveis para sua API.
- **Envie requisições diretamente**: Você pode interagir com a API, enviando requisições diretamente do Swagger UI. Basta clicar no botão de "Try it out" para preencher os parâmetros necessários e enviar a requisição.
- **Verifique a documentação detalhada**: O Swagger exibe os parâmetros, tipos de dados e códigos de status possíveis para cada endpoint.

### Usando o Swagger UI:
1. **Escolha um endpoint**: Selecione um endpoint (ex: `GET /api/usuarios`).
2. **Clique em "Try it out"**: Isso habilita os campos de entrada (se houver).
3. **Preencha os parâmetros**: Se o endpoint exigir parâmetros, como ID ou dados para envio, insira os valores necessários.
4. **Clique em "Execute"**: O Swagger enviará a requisição para o servidor e exibirá a resposta diretamente na interface.

### Resumo de Passos:
- Inicie a API com Maven ou Gradle.
- Acesse o Swagger UI pelo link [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).
- Visualize, interaja e faça requisições aos endpoints da sua API.

Isso torna o processo de testar e explorar sua API mais simples e rápido, sem a necessidade de ferramentas externas como Postman.
