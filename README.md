# Software Architecture Practice Exercises - Week 10: MICROSERVICE

## Submission Information

- **Full Name**: Dương Hoàng Lan Anh
- **Student ID**: 21087481

---

# Sales Management System

## Overview

The Sales Management System is a microservices-based application designed to manage various aspects of a sales process, including products, orders, customers, payments, inventory, and shipping. Built using **Java Spring Boot**, the system leverages a microservices architecture to ensure modularity, scalability, and fault tolerance. Each service is containerized using **Docker** and communicates through a centralized **API Gateway**. The system uses **MariaDB** as the database for each service and incorporates **Resilience4j** for fault tolerance mechanisms such as Circuit Breaker, Retry, Rate Limiter, and Time Limiter.

## System Architecture

The system consists of the following microservices, each responsible for a specific domain:

1. **Product Service**: Manages product information (name, price, description, stock).
2. **Order Service**: Handles order creation, viewing, and cancellation. Interacts with Product and Inventory Services.
3. **Customer Service**: Manages customer information (name, address, phone, email).
4. **Payment Service**: Processes payments and handles refunds. Interacts with Order Service.
5. **Inventory Service**: Manages product stock levels. Updated by Order Service.
6. **Shipping Service**: Manages shipping and tracking of orders. Interacts with Order Service.
7. **API Gateway**: Acts as the entry point for all client requests, routing them to the appropriate service.

### Architecture Diagram

```
+-------------------+
|    API Gateway    | (Port: 8080)
+-------------------+
          |
          | Routes requests
          |
+-------------------+-------------------+-------------------+
| Product Service   | Order Service     | Customer Service  | ...
| (Port: 8081)      | (Port: 8082)      | (Port: 8083)      |
| MariaDB           | MariaDB           | MariaDB           |
+-------------------+-------------------+-------------------+
| Payment Service   | Inventory Service | Shipping Service  |
| (Port: 8084)      | (Port: 8085)      | (Port: 8086)      |
| MariaDB           | MariaDB           | MariaDB           |
+-------------------+-------------------+-------------------+
```

### Technologies Used

- **Java 17**
- **Spring Boot 3.2.0**: Framework for building microservices.
- **Spring Cloud Gateway**: API Gateway for routing requests.
- **Spring Data JPA**: Database operations.
- **MariaDB**: Relational database for each service.
- **Docker**: Containerization for services and databases.
- **Resilience4j**: Fault tolerance (Circuit Breaker, Retry, Rate Limiter, Time Limiter).
- **Spring Cloud OpenFeign**: Inter-service communication.
- **Lombok**: Reduces boilerplate code.
- **Maven**: Dependency management and build tool.

## Project Structure

The project is organized into a root directory with subdirectories for each service:

```
sales-management-system/
├── api-gateway/
├── product-service/
├── order-service/
├── customer-service/
├── payment-service/
├── inventory-service/
├── shipping-service/
├── docker-compose.yml
```

Each service follows this structure:

```
<service-name>/
├── src/main/java/com/salesmanagement/<service-name>/
│   ├── <ServiceName>Application.java
│   ├── configs/
│   ├── controllers/
│   ├── entities/
│   ├── repositories/
│   ├── services/
│   ├── services/impl/
│   ├── clients/ (for Feign clients, if applicable)
│   ├── dto/ (for data transfer objects, if applicable)
├── src/main/resources/
│   ├── application.yml
├── Dockerfile
├── docker-compose.yml
├── pom.xml
```

## Setup Instructions

### Prerequisites

- **Java 17** or later
- **Maven 3.8+**
- **Docker** and **Docker Compose**
- A tool for API testing (e.g., **Postman**, **curl**)

### Steps to Run the System

1. **Clone the Repository** (if applicable):
   ```bash
   git clone https://github.com/dhlananhh/SoftwareArchitecture_Week10
   cd SoftwareArchitecture_Week10/
   ```

2. **Build Each Service**:
   Navigate to each service directory and build the JAR file:
   ```bash
   cd <service-name>
   mvn clean package
   cd ..
   ```
   Repeat for `api-gateway`, `product-service`, `order-service`, `customer-service`, `payment-service`, `inventory-service`, and `shipping-service`.

3. **Run Docker Compose**:
   From the root directory, start all services and databases:
   ```bash
   docker-compose up --build
   ```
   This will:
   - Build Docker images for each service.
   - Start MariaDB containers for each service.
   - Start the API Gateway and all microservices.
   - Create a bridge network (`sales-network`) for inter-service communication.

4. **Verify Services**:
   Check that services are running:
   - API Gateway: `http://localhost:8080`
   - Product Service: `http://localhost:8081`
   - Order Service: `http://localhost:8082`
   - Customer Service: `http://localhost:8083`
   - Payment Service: `http://localhost:8084`
   - Inventory Service: `http://localhost:8085`
   - Shipping Service: `http://localhost:8086`

5. **Stop the System**:
   To stop all containers:
   ```bash
   docker-compose down
   ```

## API Endpoints

All requests are routed through the **API Gateway** at `http://localhost:8080`. Below are the key endpoints for each service.

### Product Service (`/api/products`)

- **POST /api/products**: Create a new product.
  ```json
  {
    "name": "Laptop",
    "description": "High-performance laptop",
    "price": 999.99,
    "stock": 10
  }
  ```
- **GET /api/products/{id}**: Get product by ID.
- **GET /api/products**: Get all products.
- **PUT /api/products/{id}**: Update product.
- **DELETE /api/products/{id}**: Delete product.

### Order Service (`/api/orders`)

- **POST /api/orders**: Create a new order.
  ```json
  {
    "customerId": 1,
    "productId": 1,
    "quantity": 2
  }
  ```
- **GET /api/orders/{id}**: Get order by ID.
- **GET /api/orders**: Get all orders.
- **PUT /api/orders/{id}/cancel**: Cancel an order.

### Customer Service (`/api/customers`)

- **POST /api/customers**: Create a new customer.
  ```json
  {
    "name": "John Doe",
    "address": "123 Main St",
    "phone": "555-1234",
    "email": "john@example.com"
  }
  ```
- **GET /api/customers/{id}**: Get customer by ID.
- **GET /api/customers**: Get all customers.
- **PUT /api/customers/{id}**: Update customer.
- **DELETE /api/customers/{id}**: Delete customer.

### Payment Service (`/api/payments`)

- **POST /api/payments**: Process a payment.
  ```json
  {
    "orderId": 1,
    "amount": 1999.98
  }
  ```
- **GET /api/payments/{id}**: Get payment by ID.
- **POST /api/payments/{id}/refund**: Refund a payment.

### Inventory Service (`/api/inventory`)

- **POST /api/inventory**: Add inventory for a product.
  ```json
  {
    "productId": 1,
    "stock": 100
  }
  ```
- **GET /api/inventory/{productId}**: Get inventory by product ID.
- **PUT /api/inventory/{productId}**: Update inventory.

### Shipping Service (`/api/shippings`)

- **POST /api/shippings**: Create a shipping record.
  ```json
  {
    "orderId": 1,
    "trackingNumber": "TRACK123"
  }
  ```
- **GET /api/shippings/{id}**: Get shipping by ID.
- **PUT /api/shippings/{id}**: Update shipping status.
  ```json
  {
    "status": "SHIPPED"
  }
  ```

## Fault Tolerance

The system uses **Resilience4j** to handle failures gracefully:

- **Circuit Breaker**: Prevents cascading failures by opening the circuit when failure thresholds are exceeded.
- **Retry**: Automatically retries failed requests up to a maximum number of attempts.
- **Rate Limiter**: Limits the number of requests per second to prevent overloading.
- **Time Limiter**: Sets timeouts to prevent long-running requests.

Configuration is defined in each service's `application.yml`. For example:

```yaml
resilience4j:
  circuitbreaker:
    instances:
      <serviceName>:
        slidingWindowSize: 10
        failureRateThreshold: 50
        waitDurationInOpenState: 10000
        permittedNumberOfCallsInHalfOpenState: 3
  retry:
    instances:
      <serviceName>:
        maxAttempts: 3
        waitDuration: 500
  ratelimiter:
    instances:
      <serviceName>:
        limitForPeriod: 10
        limitRefreshPeriod: 1000
        timeoutDuration: 0
  timelimiter:
    instances:
      <serviceName>:
        timeoutDuration: 2000
```

## Inter-service Communication

Services communicate using **Spring Cloud OpenFeign** for synchronous REST calls. For example:
- **Order Service** calls **Product Service** to validate product availability and **Inventory Service** to update stock.
- **Payment Service** and **Shipping Service** call **Order Service** to validate order status.

## Database

Each service uses its own **MariaDB** database:
- `product_db`: Stores product data.
- `order_db`: Stores order data.
- `customer_db`: Stores customer data.
- `payment_db`: Stores payment data.
- `inventory_db`: Stores inventory data.
- `shipping_db`: Stores shipping data.

Databases are configured in each service's `docker-compose.yml` and `application.yml`.

## Testing the System

1. **Start the System**:
   ```bash
   docker-compose up --build
   ```

2. **Test APIs**:
   Use Postman or curl to send requests to `http://localhost:8080`. Example workflow:
   - Create a customer: `POST /api/customers`
   - Create a product: `POST /api/products`
   - Add inventory: `POST /api/inventory`
   - Create an order: `POST /api/orders`
   - Process payment: `POST /api/payments`
   - Create shipping: `POST /api/shippings`
   - Update shipping status: `PUT /api/shippings/{id}`

3. **Simulate Failures**:
   - Stop a service (e.g., `docker stop product-service`) to test Circuit Breaker and Retry.
   - Send rapid requests to test Rate Limiter.
   - Monitor logs for Time Limiter timeouts.

4. **View Logs**:
   Check Docker logs for debugging:
   ```bash
   docker-compose logs <service-name>
   ```

## Scaling

To scale a service, use Docker Compose:
```bash
docker-compose up --scale <service-name>=2
```
For example, to run two instances of the Product Service:
```bash
docker-compose up --scale product-service=2
```

## Troubleshooting

- **Service Not Starting**: Check `docker-compose logs <service-name>` for errors. Ensure Maven build was successful (`mvn clean package`).
- **Database Connection Issues**: Verify MariaDB containers are running (`docker ps`) and environment variables in `docker-compose.yml` match `application.yml`.
- **API Gateway Routing Issues**: Ensure routes in `api-gateway/application.yml` match service ports.
- **Feign Client Errors**: Check Feign client URLs and ensure target services are running.

## Future Improvements

- **Service Discovery**: Integrate **Eureka** or **Consul** for dynamic service discovery.
- **Asynchronous Communication**: Use **Kafka** or **RabbitMQ** for event-driven communication.
- **Authentication/Authorization**: Add **Spring Security** with JWT or OAuth2.
- **Monitoring**: Integrate **Prometheus** and **Grafana** for metrics and monitoring.
- **Logging**: Centralize logs using **ELK Stack** or **Loki**.
- **CI/CD**: Set up a pipeline using **GitHub Actions** or **Jenkins**.

## License

This project is licensed under the MIT License.

## Contact

For questions or contributions, please contact the project maintainers.


---

### Explanation
This `README.md` provides:
- A clear **overview** of the system and its purpose.
- A detailed **architecture** section with a diagram and technology stack.
- A **project structure** breakdown to help navigate the codebase.
- Step-by-step **setup instructions** for running the system.
- A comprehensive list of **API endpoints** with example payloads.
- Details on **fault tolerance**, **inter-service communication**, and **database** usage.
- Instructions for **testing**, **scaling**, and **troubleshooting**.
- Suggestions for **future improvements** to enhance the system.
