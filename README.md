# TienDatShop

A scalable backend system for a local food shop, supporting full e-commerce flow including product management, cart, order processing, secure JWT authentication with Redis, and online payment via VNPay.

## 🚀 Tech Stack

- **Spring Boot** – Build RESTful APIs
- **Spring Data JPA (Hibernate)** – ORM for database interaction
- **PostgreSQL (Supabase)** – Cloud database
- **Redis** – Caching & performance optimization
- **JWT (JSON Web Token)** – Secure authentication
- **Docker** – Containerized services (Redis)
- **Maven** – Build & dependency management

## 📁 Project Structure

```
TienDatShop/
└── src/
    └── main/
        ├── java/com/example/TienDatShop/
        │   ├── config/        # System configuration (Security, Redis, VNPAY)
        │   ├── controller/    # REST API endpoints
        │   ├── dto/           # Data Transfer Objects
        │   ├── entity/        # JPA Entities (database mapping)
        │   ├── exception/     # Global exception handling
        │   ├── repository/    # Data access layer (JPA Repositories)
        │   ├── service/       # Business logic layer
        │   │   ├── impl/      # Service implementations
        │   │   └── mapper/    # DTO ↔ Entity mapping
        │   ├── util/          # Utility classes (JWT, Redis, helpers)
        │   └── ServletInitializer
        │
        └── resources/
            ├── application.properties   # Application configuration
```

## ✨ Features

- **Authentication & Security** - JWT-based authentication, Redis token storage 
- **Product Management** - Create, Update, Delete, Read
- **Shopping Flow** - Cart management, Order processing, VNPay payment integration
- **Promotion System** - Discount & promotion codes
- **Review System** - Product reviews


## 🛠️ Getting Started

### Prerequisites

- Java 17
- Maven
- Docker (for running Redis)
- PostgreSQL (Supabase)
- IntelliJ IDEA (recommended IDE)


## 🚀 Backend Setup (TienDatShop)

1. Clone project
 ```bash
git clone https://github.com/AnhDuyN21/TienDatShop.git
```
2. Open project with IntelliJ IDEA

4. Install docker

5. Start Redis (required)
docker start redis || docker run -d --name redis -p 6379:6379 redis

6. Run application
- Open `TienDatShopApplication`
- Click ▶ Run (or Shift + F10)


## 🔗 API Endpoints

### Authentication

- `GET /api/auth/register/customer` - Register new customer
- `GET /api/auth/login` - User login

### Features
1. Brand
- `GET /api/brands` - Get all
- `GET /api/brands/{brandId}` - Get by id
- `POST /api/brands` - Create 
- `PATCH /api/brands/{brandId}` - Update
3. Cart
- `GET /api/carts` - Get all
- `GET /api/carts/{cartId}` - Get by id
- `POST /api/carts` - Create 
- `PATCH /api/carts/{cartId}` - Update
- `POST /api/carts/{cartId}/approve` - Approve cart to create order
4. Image
- `POST /api/images` - Create 
- `GET /api/images/product/{productId}` - Get by product id
5. Order
- `GET /api/orders` - Get all
- `GET /api/orders/{orderId}` - Get by id
- `POST /api/orders` - Create 
6. Payment
- `POST /api/payment/create-payment` - Create payment for order
7. Product
- `GET /api/products/all` - Get all
- `GET /api/products/{productId}` - Get by id
- `POST /api/products` - Create 
- `PATCH /api/products/{productId}` - Update
9. Promotion
- `GET /api/promotions` - Get all
- `GET /api/promotions/{promotionId}` - Get by id
- `POST /api/promotions` - Create 
- `PATCH /api/promotions/{promotionId}` - Update
10. Review
- `GET /api/reviews/product/{productId}` - Get review by product id
- `POST /api/reviews` - Create 
- `PUT /api/reviews/{reviewId}` - Update
11. Customer
- `GET /api/customers` - Get all
- `GET /api/customers/{customerId}` - Get by id
- `PATCH /api/customers/{customerId}` - Update
12. Admin
- `GET /api/admins` - Get all
- `GET /api/admins/{adminId}` - Get by id
- `POST /api/admins` - Create 
- `PATCH /api/admins/{adminId}` - Update

## 🔐 Demo Credentials

For convenience in testing the Admin features, you can use the following default account:

| Role  | Email | Password |
| :--- | :--- | :--- |
| **Admin** | `admin@gmail.com` | `123` |

## 📄 License

This project is for educational purposes.

---

Made with ❤️ by AnhDuyN21
