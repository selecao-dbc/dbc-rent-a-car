
# Como executar...


## docker-compose
Executar os seguintes comandos no diretório da aplicação:
- mvn package
- docker-compose up --build

http://localhost:8585/api/swagger-ui.html#/

## maven run
Neste caso, será necessário alterar a Connection String que está no arquivo application.properties, após, executar a seguinte linha de comando:
- mvn spring-boot:run

## Preparar database
Para otimizar a criação de alguns dados, podemos executar o script inicial que está no diretório de resources da aplicação.

Abrir o pgAdmin em: http://localhost:16543/browser/
- Login: pgadmin4@pgadmin.org
- Password: admin

Dados de conexão:
- Host: rent-a-car-postgres
- Port: 5432
- Maintenance DataBase: trac_cadastros
- Username: postgres
- Password: postgres

#

# Seleção de desenvolvedores java

O projeto em questão possui uma implementação parcial de uma API para uma locadora de automóveis. O projeto foi desenvolvido usando spring boot, spring-data-jpa e banco postgresal.

## Passos para submissão da solução
 1. Crie um fork desse repositório em sua conta pessoal do github
 2. Resolva os itens descritos na próxima sessão
 3. Suba o código para o seu fork 
 4. Submeta um *pull request* para o nosso repositório
 
## Tasks
As seguintes tasks devem ser resolvidas na solução submetida:
Temos repositórios para marca, modelo, veiculo(carro ou moto), locação, reserva, etc. Precisamos de métodos para:
1. gerenciamento de reservas e locaçes - onde podemos criar, alterar ou cancelar reservas ou locaçes dependendo de acordo com os seguintes requisitos:  
 1.1. Reservas e locações estão sujeitos a disponibilidade de veículos do modelo requisitado. Podem existir n exemplares (veículos) do modelo selecionado.  
 1.2. Reservas e locações devem ser feitas informando o modelo do veículo e as datas inicial e final  
 
## O que estamos avaliando
Os seguintes itens estarão sendo avaliados:
1. Clareza e organização de código
2. Boas práticas:  
 2.1. Código legível e bem escrito  
 2.2. Sequencia de implementação  
 2.3. Commits - prefira commits menores e com comentários relevantes  
 2.4. Testes unitários - Toda a camada de persistência já foi implementada com spring-data-jpa, porém não temos serviço (regra de negócios) e os endpoints solicitados. Utilizar TDD e refletir isso em seus commits será um enorme diferencial.  


## Configuração do ambiente
Para configuração do projeto os seguintes pre-requisitos:
 1. java 8 ou superior
 2. postgres 8 ou superior
 
