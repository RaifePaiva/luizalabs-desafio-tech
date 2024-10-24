# LuizaLabs - Desafio técnico - Vertical Logística

Este projeto visa construir uma API utilizando Java + Quarkus para atender os requisitos do desafio técnico proposto pela LuizaLabs.

## O Desafio: 

A aplicação deve:

 - Receber um arquivo com pedidos desnormalizados via API REST.
 - Processar os dados para transformá-los em um JSON normalizado.
 - Suportar filtros por ID de pedido e intervalo de data.


## Configuração do Projeto:

- Java 17
- Gradle 8.10 ou superior
- Quarkus 3.5

## Estrutura do Projeto: 

- Neste projeto foi utilizado uma arquitetura simples em camadas, separando as responsabilidades por camada para facilitar a escrita de testes.

```declarative

.
├── src
│   ├── main
│   │   ├── java
│   │   │   └── br
│   │   │       └── com
│   │   │           └── magalu
│   │   │               ├── controller
│   │   │               │   └── OrderController.java
│   │   │               ├── model
│   │   │               │   ├── OrderModel.java
│   │   │               │   ├── ProductModel.java
│   │   │               │   └── UserModel.java
│   │   │               ├── service
│   │   │               │   ├── ProcessorService.java
│   │   │               │   └── impl
│   │   │               │       └── FileProcessorServiceImpl.java
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── java
│           └── br
│               └── com
│                   └── magalu
│                       └── OrderControllerTest.java
├── build.gradle
└── README.md

````

## Iniciar aplicação:

Para iniciar a aplicação quarkus, execute o comando abaixo:

```shell script
./gradlew quarkusDev
```

## Executar testes

Para executar os testes, execute o comando abaixo:

```shell script
./gradlew test
```

> **_NOTE:_**  Será disponibilizado acesso a rota, atraves do host:  <http://localhost:8080>.

## Swagger:

 - Para acessar o swagger api, acesse o link: [Swagger UI](http://localhost:8080/swagger-ui/#/Order/post_order)

## Exemplo de Entrada (Arquivo):

````declarative
0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308
0000000075                                  Bobbie Batz00000007980000000002     1578.5720211116
0000000049                               Ken Wintheiser00000005230000000003      586.7420210903
0000000014                                 Clelia Hills00000001460000000001      673.4920211125
0000000057                          Elidia Gulgowski IV00000006200000000000     1417.2520210919
````

## Exemplo de Saída:

````json
[
  {
    "user_id":1,
    "name":"Zarelli",
    "orders":[
      {
        "order_id":123,
        "total":"1024.48",
        "date":"2021-12-01",
        "products":[
          {
            "product_id":111,
            "value":"512.24"
          },
          {
            "product_id":122,
            "value":"512.24"
          }
        ]
      }
    ]
  },
  {
    "user_id":2,
    "name":"Medeiros",
    "orders":[
      {
        "order_id":12345,
        "total":"512.48",
        "date":"2020-12-01",
        "products":[
          {
            "product_id":111,
            "value":"256.24"
          },
          {
            "product_id":122,
            "value":"256.24"
          }
        ]
      }
    ]
  }
]
````

## Sobre o Quarkus

A utilização do framework foi determinada com base em sua simplicidade ao declarar os recursos e abstrações para este desafio,
podendo assim focar no desenvolvimento da lógica de negócio e não com a infra.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
