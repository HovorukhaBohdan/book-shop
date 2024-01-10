# 📚 __***BOOKSHOP***__ 📚
___
**Welcome to my delightful bookstore application! This handy app has been carefully designed to simplify the process of finding, studying and buying a wide range of fascinating books. Whether you're on the hunt for bestsellers, niche genres, or literary treasures, our platform offers an intuitive experience tailored to book enthusiasts like you. Browse through our curated collection and seamlessly transition from finding your next favorite read to securely completing your purchase with just a few clicks📚✨**
___
## ⚙️ ___FUNCTIONALITY___ ⚙️
___
+ ✏️ ***User registration***
+ 🔑 ***Log in / out***
+ 📚 ***CRUD operations for books***
+ 🔎 ***Search books by parameters***
+ 💵 ***Order books***
___
## 👨‍💻 ___TECHNOLOGIES___ 👨‍💻
+ ***Spring boot***
+ ***Spring Data JPA***
+ ***Spring MVC***
+ ***Spring Security***
+ ***Hibernate***
+ ***MYSQL DB***
+ ***Test containers***
+ ***Maven***
___
## 🔻 ___ENDPOINTS___ 🔻
+ _`POST: /api/auth/registration` - endpoint for user registration (No authorities)_
+ _`POST: /api/auth/login` - endpoint for user login (No authorities)_
+ _`GET: /api/books` - endpoint to look at all the books (User authority)_
+ _`GET: /api/books/{id}` - endpoint for searching a specific book (User authority)_
+ _`POST: /api/books` - endpoint for creating a new book (Admin authority)_
+ _`PUT: /api/books/{id}` - endpoint for updating information about book (Admin authority)_
+ _`DELETE: /api/books/{id}` - endpoint for deleting books (Soft delete is used, Admin authority)_
+ _`GET: /api/books/search` - endpoint for searching books by parameters (User authority)_
+ _`GET: /api/categories` - endpoint to look at all categories (User authority)_
+ _`GET: /api/categories/{id}` - endpoint for searching a specific category (User authority)_
+ _`POST: /api/categories` - endpoint for creating a new category (Admin authority) (User authority)_
+ _`PUT: /api/categories/{id}` - endpoint for updating information about specific category (Admin authority)_
+ _`DELETE: /api/categories/{id}` - endpoint for deleting categories (Soft delete is used, Admin authority)_
+ _`GET: /api/categories/{id}/books` - endpoint to look at books with specific category (User authority)_
+ _`GET: /api/orders` - endpoint for getting orders history (User authority)_
+ _`POST: /api/orders` - endpoint for placing orders (User authority)_
+ _`PATCH: /api/orders/{id}` - endpoint for updating orders status (Admin authority)_
+ _`GET: /api/orders/{orderId}/items` - endpoint for getting order items from specific order (User authority)_
+ _`GET: /api/orders/{orderId}/items/{itemId}` - endpoint for getting specific item from certain order (User authority)_
+ _`GET: /api/cart` - endpoint for getting all items in your shopping cart (User authority)_
+ _`POST: /api/cart` - endpoint for adding items in your shopping cart (User authority)_
+ _`PUT: /api/cart/cart-items/{itemId}` - endpoint for updating items quantity (User authority)_
+ _`DELETE: /api/cart/cart-items/{itemId}` - endpoint for deleting items from your shopping cart (User authority)_
___
## 🚀 ___Getting Started___ 🚀
___
1. **Grab a copy: Start by making a local copy of the project's repository.**
2. **Database setup: Customize the database settings to match your environment in the application.properties file.**
3. **Table creation: Make sure the necessary database tables are automatically created using Liquibase.**
4. **Building and running: Bring the project to life using your favorite IDE or by running the command `mvn spring-boot:run`**
5. **Exploring the API: Access the API documentation through Swagger, available at http://localhost:8080/swagger-ui.html: http://localhost:8080/swagger-ui.html.**

## Have a good use 😇
