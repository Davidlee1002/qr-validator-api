# --------- 第一阶段：编译构建 (Build Stage) ---------
# 使用带有 Maven 和 JDK 17 的官方镜像
FROM maven:3.9.6-eclipse-temurin-17 AS build

# 设置工作目录
WORKDIR /app

# 把项目所有文件复制进去
COPY . .

# 执行 Maven 打包 (跳过测试以加快速度)
RUN mvn clean package -DskipTests

# --------- 第二阶段：运行环境 (Run Stage) ---------
# 使用轻量级的 JDK 17 运行镜像
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# 从第一阶段(build)把生成的 jar 包复制过来
# 注意：这里我们精确指定文件名，防止复制错
COPY --from=build /app/target/qr-validator-api-0.0.1-SNAPSHOT.jar app.jar

# 暴露端口
EXPOSE 8080

# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]
