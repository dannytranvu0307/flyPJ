# Sử dụng hình ảnh Maven chính thức của Apache để xây dựng ứng dụng
FROM maven:3.8.6-openjdk-17 AS build

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép các tệp POM và mã nguồn vào container
COPY pom.xml .
COPY src ./src

# Biên dịch và đóng gói ứng dụng
RUN mvn clean package -DskipTests

# Sử dụng hình ảnh JDK 17 chính thức của OpenJDK làm nền tảng
FROM openjdk:17-jdk-slim

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép file JAR từ giai đoạn build sang container
COPY --from=build /app/target/myapp-0.0.1-SNAPSHOT.jar /app/myapp.jar

# Cấu hình điểm vào cho container
ENTRYPOINT ["java", "-jar", "/app/myapp.jar"]
