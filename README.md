# TienDatShop

A full-stack social network application inspired by Threads, built with modern technologies.

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

- 🔐 **Authentication** - Register, Login, JWT token refresh
- 👤 **User Profile** - View and edit profile, avatar upload
- 📝 **Posts** - Create, view, like posts with images
- 💬 **Comments** - Real-time comments on posts
- 👥 **Follow System** - Follow/unfollow users
- 🔔 **Notifications** - Real-time notifications via SignalR
- 🔍 **Search** - Search for users
- 📱 **Responsive Design** - Mobile-friendly UI

## 🛠️ Getting Started

### Prerequisites

- Node.js 18+
- .NET 8 SDK
- MySQL Server


## 🚀 Backend Setup (TienDatShop)

# 1. Clone project
git clone https://github.com/your-username/TienDatShop.git

# 2. Open project with IntelliJ IDEA

# 3. Start Redis (required)
docker start redis || docker run -d --name redis -p 6379:6379 redis

# 4. Run application
- Open `TienDatShopApplication`
- Click ▶ Run (or Shift + F10)


## 🔗 API Endpoints

### Authentication

- `POST /api/auth/register/customer` - Register new customer
- `POST /api/auth/login` - User login
- `POST /api/auth/refresh-token` - Refresh JWT token

### Features



## 📄 License

This project is for educational purposes.

---

Made with ❤️ by AnhDuyN21