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

- `POST /api/auth/register/customer` - Register new customer
- `POST /api/auth/login` - User login


### Features



## 📄 License

This project is for educational purposes.

---

Made with ❤️ by AnhDuyN21
