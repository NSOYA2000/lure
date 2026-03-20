# 环境安装指南

## 1. 安装 Java 开发环境

### 检查是否已安装 Java
```bash
java -version
```

### 安装 Java 17（如果未安装）
- **macOS**: 使用 Homebrew 安装
  ```bash
  brew install openjdk@17
  brew link --force openjdk@17
  ```

- **Windows**: 从 [Oracle JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) 或 [OpenJDK](https://adoptium.net/) 下载安装程序

- **Linux (Ubuntu/Debian)**:
  ```bash
  sudo apt update
  sudo apt install openjdk-17-jdk
  ```

## 2. 安装 Maven

### 检查是否已安装 Maven
```bash
mvn -v
```

### 安装 Maven
- **macOS**: 使用 Homebrew
  ```bash
  brew install maven
  ```

- **Windows**: 从 [Maven官网](https://maven.apache.org/download.cgi) 下载二进制包，解压并添加 `MAVEN_HOME` 到系统环境变量

- **Linux (Ubuntu/Debian)**:
  ```bash
  sudo apt install maven
  ```

## 3. 安装 Node.js 和 npm

### 检查是否已安装 Node.js
```bash
node --version
npm --version
```

### 安装 Node.js（如果未安装）
- **macOS**: 使用 Homebrew
  ```bash
  brew install node
  ```

- **Windows/Linux**: 从 [Node.js官网](https://nodejs.org/) 下载安装程序

## 4. 安装 Vue CLI

```bash
npm install -g @vue/cli
```

或者使用 Vue CLI 的快速原型开发功能，我们也可以使用 Vite（推荐）。

## 5. 项目结构

项目已经分为两个部分：
- `backend/` - Spring Boot 后端
- `frontend/` - Vue.js 前端

## 6. 运行后端

### 首次运行前安装依赖
```bash
cd backend
mvn clean install
```

### 启动后端服务
```bash
mvn spring-boot:run
```

后端将在 http://localhost:8080 启动。

## 7. 运行前端

### 安装前端依赖
```bash
cd frontend
npm install
```

### 启动前端开发服务器
```bash
npm run dev
```

前端将在 http://localhost:5173 启动。

## 8. 访问应用

1. 打开浏览器访问 http://localhost:5173
2. 管理后台：http://localhost:5173/admin

## 9. 数据库访问

应用使用 H2 嵌入式数据库，数据存储在 `backend/data/` 目录。

可以在应用运行时访问 H2 控制台：
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:file:./data/lurefish`
- 用户名: `sa`
- 密码: (空)

## 10. 故障排除

### Maven 构建失败
- 检查 Java 版本是否为 17
- 清理 Maven 缓存：`mvn clean`

### Node.js 依赖安装失败
- 清理 npm 缓存：`npm cache clean --force`
- 删除 node_modules 重新安装：`rm -rf node_modules && npm install`

### 端口被占用
- 修改后端端口：在 `backend/src/main/resources/application.properties` 中设置 `server.port=8081`
- 修改前端端口：在 `frontend/vite.config.js` 中配置

## 11. 生产环境构建

### 构建后端
```bash
cd backend
mvn clean package
# 生成的 JAR 文件在 target/lure-fish-backend-1.0.0.jar
```

### 构建前端
```bash
cd frontend
npm run build
# 生成的文件在 dist/ 目录
```

---

如果遇到任何问题，请检查以上步骤或查看对应工具的官方文档。