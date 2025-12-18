# 1. 基础镜像：使用轻量级的 OpenJDK 17
FROM openjdk:17-jdk-slim

# 2. 设定工作目录
WORKDIR /app

# 3. 将 Maven 打包生成的 jar 包复制到容器中
# 注意：这里假设打包后的名字包含 snapshot，我们把它重命名为 app.jar
COPY target/*.jar app.jar

# 4. 暴露端口 (RapidAPI 和云服务通常需要知道端口)
EXPOSE 8080

# 5. 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]
