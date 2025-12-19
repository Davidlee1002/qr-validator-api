# 1. 换用 Eclipse Temurin 镜像 (这是目前最推荐的 JDK 17 镜像)
FROM eclipse-temurin:17-jdk-jammy

# 2. 设定工作目录
WORKDIR /app

# 3. 复制 Jar 包
COPY target/*.jar app.jar

# 4. 暴露端口
EXPOSE 8080

# 5. 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]
