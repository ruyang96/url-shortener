# **URL SHORTENER**

## **This is an URL shortener for shortening long url 🚀**

### **Description**

This service provides users with the ability to generate short URLs for their original links,  like Bitly or Rebrandly. It converts lengthy links into more concise, readable forms. When users click on the shortened link, they are automatically redirected to the destination URL.

This project implements four interconnected REST APIs using Spring Boot and Java to achieve this functionality. These APIs are outlined below, with detailed specifications available in the project directory under **src/main/resources/specifications/UrlShortenerAPI.yaml**:

- **POST /user/new:** Allows users to create a new account by providing an email and password.
- **POST /user/auth:** Enables users to authenticate themselves using their login credentials. Upon successful authentication, an authentication token is returned.
- **POST /url/shorten:** Permits authenticated users to generate shortened URLs.
- **GET /url/{urlId}:** Allows any user to access shortened URLs, which automatically redirect them to the original destination.

These APIs facilitate account creation, authentication, URL shortening, and seamless redirection to the original links.

### **How to RUN the application**

To run the application, you'll need Maven installed on your computer to manage dependencies and build the project. If you haven't installed Maven yet, you can follow [this tutorial](https://www.baeldung.com/install-maven-on-windows-linux-mac) or any other guide to install it.

Once Maven is installed, you can use the following commands to install dependencies and build the project:

```bash
mvn clean install
```

After the project is built successfully, you can start the application by running the following command:

```bash
mvn spring-boot:run
```

The application will be accessible at *localhost:8080*. To get started, you can register a new user by accessing *localhost:8080/api/user/new*.

### **Technical Choices and Architecture**

#### **Frameworks and Technologies:**

- **Java**
- **Maven**: Utilized for dependency management and project build automation
- **Spring Boot**: Chosen for its ease of development, convention-over-configuration approach, and robust ecosystem for building RESTful APIs.

#### **Database:**

- **Database** : Chose **H2 Database** to eliminate dependencies on external databases during development and testing phases, simplifying setup and configuration
- **Hibernate**: Utilized **Hibernate** for database interaction because of its powerful features such as automatic table creation, querying simplifying database operations and reducing boilerplate code.

#### **Security:**

- **Token-Based Authorization**: Implemented JWT token authorization to restrict access to authenticated users only for the API endpoint **POST /url/shorten**.
- **Hashing the password**

#### **Performance:**

- **Caching**: Spring caching mechanisms used for the API endpoint **GET /url/{urlId}** to cache frequently accessed URL mappings. By reducing database queries for repeated requests, caching improves response time and overall performance.

#### **Error Handling and Logging:**

- **Exception Handling**: Implemented a custom exception handler to provide informative error messages and custom error codes
- **Logging**: Implemented logging to capture log each request and response for future debugging and analysis. By logging detailed information, the system facilitates troubleshooting and monitoring of application behavior.

### **Possible extension**

- **User-Defined Short URLs**: Implemented functionality to allow users to define custom short URLs (e.g., /BirthdayPics) for their links.
- **SQL Injection Prevention**: Implemented input validation to mitigate SQL injection vulnerabilities, and other security considerations as well, like Doss attack.
- **Metrics Collection**: To monitor key performance indicators, resource usage, and user activity, etc.
- **Alert Mechanisms**: Configured alert mechanisms to notify stakeholders of critical issues