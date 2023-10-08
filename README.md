# Desafio Java Inter

### Descrição do projeto

- Desenvolver aplicação utilizando Java e os frameworks Spring ou Micronaut;
- Utilizar Maven;

## >>  Serviço de Remessa

• Temos 2 tipos de usuários: Pessoa Física (PF) e Pessoa Jurídica (PJ). 
Ambos possuem carteira em Real e em Dólar e realizam remessa entre eles.

##### Requisitos:
**Requisito Funcional:** Deverá ser criado uma API RESTFul que fará uma remessa entre os usuários. Uma remessa constitui de uma operação de conversão de moeda de Real para Dólar e depois de uma operação de transferência do valor convertido.

**Critérios de Aceite: ** 

1 -  A cotação da moeda deverá ser consultada através da API publica

https://dadosabertos.bcb.gov.br/dataset/dolaramericano-usd-todos-os-boletins-diarios/resource/22ab054cb3ff-4864-82f7-b2815c7a77ec.

Obs.: utilizar o campo “cotacaoCompra” da resposta da API.

2 - Utilizar um banco em memória de sua preferência.

3 - Os usuários deverão ter uma carteira de saldo em Real e saldo em Dólar.

4 - Validar se o usuário tem saldo antes da remessa.

5 - Os usuários deverão ser criados com nome completo, e-mail, senha, CPFpara PF e CNPJ para PJ.

6 -  Os e-mails, CPF e CNPJ’s deverão ser únicos.

7 - PF’s possuem um limite de 10 mil reais transacionados por dia.

8 - PJ’s possuem um limite de 50 mil reais transacionados por dia.

9 - Não há restrição de remessa entre PF’s e PJ’s e vice-versa.

10 - A cotação de moeda não funciona aos finais de semana, sendo assim, o serviço deverá consultar a última cotação de moeda obtida.
**[Não implementado]**

11 -  A operação de transferência deve ser uma transação (ou seja, revertida em qualquer caso de inconsistência) e o dinheiro deve voltar para a carteira do usuário que envia.

12 - O Desafio deverá ser desenvolvido de forma incremental, por exemplo, deixe tratamento de cotação nos finais de semana por último.

------------
### Tecnologias utilizadas

Springboot

H2 Database

OpenAI

Jmeter

Postman


------------
### Inicialização

1 - Clonar o projeto no github e abrir no Spring Tools Suite 4

2 - Clicar com o botão direito no projeto desafio

3 - Clicar em Maven

4 - Clicar em Update Project

5 - Clicar com o botão direito no projeto desafio

6 - Clicar em Run as..

7 - Clicar em Maven Install

8 - Acessar aba Boot Dashboard

9 - Dentro de local selecionar o projeto desafio

10 - Clicar em start


Ou


1 - Acessar o seu maven via linha de comandos

2 - Executar o comando mvn install na pasta do projeto

3 - Executar o comando  java -jar desafio-0.0.1-SNAPSHOT.jar


###### Rodandos os testes

Usando IDE:

1 - Clicar com o botão direito no projeto desafio

2 - Clicar em Run as..

3 - Clicar em Maven Test


Usando o Maven externo:
1 - Acessar o seu maven via linha de comandos

2 - Executar o comando mvn test na pasta do projeto


###### Para acessar a documentação da API

1- Inicie a aplicação e acesse os links abaixo:

http://localhost:8080/api-docs

http://localhost:8080/swagger-ui/index.html


------------
### Implementações futuras

Monitoramento com Pinpoint API

Teste de Integração

Teste de Stress

Docker


------------
### Colaboradores

##### Júlio César de Paula Silva

------------
### Status do projeto

###### Em andamento
