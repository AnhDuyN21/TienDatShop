# Sử dụng image Java chính thức (JDK 17)
FROM eclipse-temurin:17-jdk

# Thư mục làm việc trong container
WORKDIR /app

# Copy file jar từ target vào container
COPY target/*.jar app.jar

# Expose port 8080 (hoặc port bạn dùng)
EXPOSE 8080

# Lệnh khởi động Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
