# shopping-cart-event-sourced

![GitHub](https://img.shields.io/github/license/cbeardsmore/scart?style=plastic)

A shopping cart system in Java11 implementing Event Sourcing (ES) and Command Query Responsibility Segregation (CQRS)

### Docker

- To build:

`docker-compose build`

- To run:

`docker-compose up --build`


- To test via Postman while running:

```
npm install -g newman
./postman/test.sh
```

### Gradle

`gradle clean build`