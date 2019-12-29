# shopping-cart-event-sourced

![GitHub](https://img.shields.io/github/license/cbeardsmore/scart?style=plastic)

A shopping cart system in Java11 implementing Event Sourcing (ES) and Command Query Responsibility Segregation (CQRS)

### Command Endpoints

- Create a new Cart:

```
POST /cart/create
RESPONSE:
{
	"aggregateId": "UUID"
}
```

- Add a Product:

```
POST /cart/{cartId}
REQUEST: 
{
	"productId": "UUID",
	"name": "Samsung TV",
	"price": 49.99,
	"quantity: 2
}
```

- Remove a Product:

```
DELETE /cart/{cartId}/product/{productId}
```

- Checkout:

```
POST /cart/{cartId}/checkout
RESPONSE:
{
	"totalPrice": 199.97
}
```

### Query Endpoints

- Get total price of all checked out carts:

```
GET /carts/total
RESPONSE:
{
	"totalPrice": 199.97
}
```

- Get total price of a specific cart:

```
GET /cart/{cartId}
RESPONSE:
{
	"totalPrice": 199.97
}
```

- Get top 5 most popular products:

```
GET /product/popular
RESPONSE:
{
	"popularProducts": [
		{
			"productId": "UUID"
			"quantity": 430
		}
	]
}
```

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

- To build via gradle:

`gradle clean build`