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
+ Spring Framework: `Spring boot`, `Spring Data JPA`, `Spring MVC`, `Spring Security`;
+ Database Management: `Hibernate`, `MySQL DB`;
+ Testing: `JUnit 5`, `Mockito`, `TestContainers`;
+ Additional instruments: `Maven`, `Lombok`, `Mapstruct`.
___
## 🔻 ___ENDPOINTS___ 🔻
### Authentication controller:
+ _`POST: /api/auth/registration` - endpoint for user registration (No authorities)_
+ _`POST: /api/auth/login` - endpoint for user login (No authorities)_
### Book controller:
+ _`GET: /api/books` - endpoint to look at all the books (User authority)_
+ _`GET: /api/books/{id}` - endpoint for searching a specific book (User authority)_
+ _`POST: /api/books` - endpoint for creating a new book (Admin authority)_
+ _`PUT: /api/books/{id}` - endpoint for updating information about book (Admin authority)_
+ _`DELETE: /api/books/{id}` - endpoint for deleting books (Soft delete is used, Admin authority)_
+ _`GET: /api/books/search` - endpoint for searching books by parameters (User authority)_
### Category controller:
+ _`GET: /api/categories` - endpoint to look at all categories (User authority)_
+ _`GET: /api/categories/{id}` - endpoint for searching a specific category (User authority)_
+ _`POST: /api/categories` - endpoint for creating a new category (Admin authority) (User authority)_
+ _`PUT: /api/categories/{id}` - endpoint for updating information about specific category (Admin authority)_
+ _`DELETE: /api/categories/{id}` - endpoint for deleting categories (Soft delete is used, Admin authority)_
+ _`GET: /api/categories/{id}/books` - endpoint to look at books with specific category (User authority)_
### Order controller:
+ _`GET: /api/orders` - endpoint for getting orders history (User authority)_
+ _`POST: /api/orders` - endpoint for placing orders (User authority)_
+ _`PATCH: /api/orders/{id}` - endpoint for updating orders status (Admin authority)_
+ _`GET: /api/orders/{orderId}/items` - endpoint for getting order items from specific order (User authority)_
+ _`GET: /api/orders/{orderId}/items/{itemId}` - endpoint for getting specific item from certain order (User authority)_
### Shopping cart controller:
+ _`GET: /api/cart` - endpoint for getting all items in your shopping cart (User authority)_
+ _`POST: /api/cart` - endpoint for adding items in your shopping cart (User authority)_
+ _`PUT: /api/cart/cart-items/{itemId}` - endpoint for updating items quantity (User authority)_
+ _`DELETE: /api/cart/cart-items/{itemId}` - endpoint for deleting items from your shopping cart (User authority)_
___
## 🚀 ___Getting Started___ 🚀
___
To clone the repository, follow these steps:

1. Open IntelliJ IDEA.
2. Navigate to File → New Project from Version Control.
3. Insert the repository link: https://github.com/HovorukhaBohdan/book-shop.
4. Alternatively, you can clone from the console using the command: git clone https://github.com/HovorukhaBohdan/book-shop.
5. After cloning, build the project and download Maven dependencies using the command: mvn clean install.

Finally, Docker Compose your project with the following commands:

1. docker compose build
2. docker compose up

## 📌 ___Challenges:___ 📌

+ _Authentication and authorization: Implementing secure user registration, login, and access control can be complex._
+ _Database management: Choosing the right database technology and optimizing queries for efficient data retrieval are crucial._
+ _Search functionality: Building a robust search engine with diverse parameters and accurate results can be challenging._
+ _Testing and debugging: Ensuring comprehensive testing and efficient debugging processes are essential for a stable and reliable application._

## 🎇 ___Overcoming these challenges:___ 🎇

+ _Utilize security best practices: Implement libraries and frameworks like Spring Security for secure user authentication and authorization._
+ _Choose appropriate database technology: Use a database like MySQL for efficient data storage and retrieval._
+ _Implement automation: Use testing frameworks like JUnit and Mockito for automated testing, and debugging tools for efficient error identification and resolution._

## 🔄 ___Possible Improvements___ 🔄 
+ Implement an advanced search functionality with more parameters.
+ Enhance user interface for a more visually appealing and user-friendly experience.
+ Integrate additional payment options for a broader user base.
+ Implement caching mechanisms to improve performance, especially for frequently accessed data.
+ Expand test coverage for comprehensive code validation.

## Have a good use 😇
