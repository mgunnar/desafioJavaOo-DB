# Desafio Técnico Java - 2023 🚀

Este projeto foi desenvolvido como parte de um desafio técnico, com o objetivo de avaliar habilidades em Java e Orientação a Objetos. O desafio propôs a implementação de funcionalidades relacionadas a clientes, contas e transações financeiras.

## Visão Geral 👁‍🗨

O projeto utiliza Java 17 e incorpora práticas modernas de desenvolvimento, incluindo o uso de records, Lombok para redução de código boilerplate, e padrões de design, como Service, Repository e Controllers. Além disso, o código faz uso do polimorfismo para manipular diferentes tipos de clientes.

## Tecnologias Utilizadas 🛠️

- Java 17
- Spring Boot (para serviços RESTful, persistência e injeção de dependência)
- Hibernate (para mapeamento objeto-relacional)
- Lombok (para redução de código boilerplate)
- Maven (para construção e gerenciamento de dependências)

## Configuração do Ambiente de Desenvolvimento 🛠️

### Pré-requisitos

- Java 17
- Maven

### Instalação

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/seu-projeto.git
```
cd seu-projeto

1.  Execute o comando Maven para construir o projeto:

```bash
    mvn clean install
```


2.  Configure o banco de dados no arquivo de propriedades (`application.properties` ou `application.yml`), conforme necessário.

Como Usar 🚀
------------

O projeto oferece serviços relacionados a clientes e contas, com endpoints RESTful para criar clientes, contas, realizar transações e obter informações financeiras.

Exemplo de Uso:

1.  Criar um novo cliente:

   ```bash


    POST /v1/clientes/
```

```bash

    {
      "nome": "Nome do Cliente",
      "tipoCliente": "A",
      "cpf": "12345"
    }
```


2.  Procurar contas de um cliente:

```bash

    GET v1/clientes/{cpf}/conta

```

```bash
    {
      "cpfCliente": "12345678901"
    }
```

3.  Realizar transação:

```bash
    POST /v1/compras
```

```bash
    {
      "numeroContaOrigem": "00001",
      "valor": 100.0
    }
```

Estrutura do Projeto 🏗️
------------------------

O projeto está organizado seguindo a arquitetura de Service, Repository e Controllers.

-   Service: Contém a lógica de negócios.
-   Repository: Responsável pela interação com o banco de dados.
-   Controller: Gerencia as requisições HTTP e as respostas.

Contribuições 🤝
----------------

Contribuições são bem-vindas! Se você deseja melhorar ou adicionar recursos ao projeto, sinta-se à vontade para enviar pull requests. Certifique-se de seguir as diretrizes de contribuição.

Licença 📝
----------

Este projeto é licenciado sob a [Sua Licença](https://chat.openai.com/c/URL_DA_SUA_LICENCA). Veja o arquivo `LICENSE` para mais detalhes.
