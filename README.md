# 路亚鱼种百科网站

这是一个完整的路亚鱼种百科网站，包含前端（Vue.js）和后端（Spring Boot）的全栈应用。

## 项目结构

```
lure-fish-website/
├── backend/          # Spring Boot 后端
│   ├── src/main/java/com/lurefish/
│   ├── pom.xml       # Maven 配置文件
│   └── src/main/resources/
├── frontend/         # Vue.js 前端
│   ├── src/          # 源代码
│   ├── index.html    # 入口文件
│   ├── package.json  # 依赖配置
│   └── vite.config.js # Vite 配置
├── INSTALL.md        # 环境安装指南
└── README.md         # 项目说明
```

## 功能特性

### 前端功能
- 响应式设计，支持移动设备
- 鱼种卡片展示
- 鱼种详情查看（模态框）
- 管理后台（增删改查）
- 数据导入/导出

### 后端功能
- RESTful API
- H2嵌入式数据库
- 自动数据初始化
- CORS支持

### 鱼种数据模型
- 鱼种名称、学名、描述
- 栖息环境、适宜季节、活动水层
- 作钓难度、推荐装备
- 适用路亚饵、作钓技巧、小贴士
- 尺寸范围、体重范围

## 快速开始

### 1. 安装必要环境
请参考 [INSTALL.md](INSTALL.md) 安装以下环境：
- Java 17
- Maven
- Node.js 18+
- npm

### 2. 启动后端服务
```bash
cd backend
mvn clean install
mvn spring-boot:run
```
后端将在 http://localhost:8080 启动。

### 3. 启动前端服务
```bash
cd frontend
npm install
npm run dev
```
前端将在 http://localhost:5173 启动。

### 4. 访问应用
- 首页：http://localhost:5173
- 管理后台：http://localhost:5173/admin
- API文档：http://localhost:8080/api/fish
- H2数据库控制台：http://localhost:8080/h2-console

## API接口

### 鱼种管理 API
```
GET    /api/fish          # 获取所有鱼种
GET    /api/fish/{id}     # 获取单个鱼种
POST   /api/fish          # 创建鱼种
PUT    /api/fish/{id}     # 更新鱼种
DELETE /api/fish/{id}     # 删除鱼种
```

## 数据库

项目使用 H2 嵌入式数据库：
- 数据文件：`backend/data/lurefish.mv.db`
- 自动初始化：启动时如果没有数据会自动创建3条示例数据
- 访问控制台：http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:file:./data/lurefish`
  - 用户名: `sa`
  - 密码: (空)

## 开发指南

### 前端开发
```bash
cd frontend
npm run dev      # 开发模式
npm run build    # 生产构建
npm run preview  # 预览生产构建
```

### 后端开发
```bash
cd backend
mvn compile      # 编译
mvn test         # 运行测试
mvn spring-boot:run  # 运行应用
```

### 添加新的鱼种字段
1. 在后端 `Fish.java` 实体类中添加字段
2. 更新数据库迁移（JPA会自动更新）
3. 在前端组件中更新表单和显示逻辑

## 部署

### 后端部署
```bash
cd backend
mvn clean package
java -jar target/lure-fish-backend-1.0.0.jar
```

### 前端部署
```bash
cd frontend
npm run build
# 将 dist/ 目录部署到静态文件服务器
```

## 技术栈

### 后端
- Spring Boot 3.4.3
- Spring Data JPA
- H2 Database
- Lombok
- Maven

### 前端
- Vue.js 3
- Vite 5
- Axios
- Bootstrap 5
- Bootstrap Icons
- Font Awesome

## 常见问题

### 1. 端口冲突
- 修改后端端口：编辑 `backend/src/main/resources/application.properties` 中的 `server.port`
- 修改前端端口：编辑 `frontend/vite.config.js` 中的 `server.port`

### 2. 数据库连接失败
- 检查 `backend/data/` 目录是否存在
- 确保有写入权限

### 3. 前端无法连接后端
- 确保后端服务正在运行
- 检查 Vite 代理配置 `frontend/vite.config.js`

### 4. CORS 错误
- 后端已经配置了 CORS，允许来自 `http://localhost:5173` 的请求
- 如果需要其他域名，更新 `FishController.java` 中的 `@CrossOrigin` 注解

## 贡献

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 联系方式

如有问题或建议，请通过项目 Issues 页面反馈。