# README

### Submitted by: 
**David Zaydenberg - Davidzay@edu.hac.ac.il**<br>
**Ron Elian - Ronel@edu.hac.ac.il**

## General Functionality of the Website

This cigar shop website provides a complete e-commerce experience, allowing users to:

- **Register and Login**: Users can create an account and log in to access the full range of features.
- **View Products**: Browse and view detailed information about available cigar products.
- **Buy Products**: Add products to a shopping cart, adjust quantities, and proceed to checkout.
- **Add/Remove Reviews**: Leave reviews and ratings for purchased products, and manage existing reviews.
- **Password Management**: Change and reset passwords easily.
- **Shopping Cart Management**: Add items to the cart, change quantities, or remove items.
- **Account Information**: Access order history and review details on the account page.
- **Admin Dashboard**: Admins can view all users and their orders, manage products (add/remove/edit), and change the status of ongoing orders. Admins can also see shop analytics.
- **Chat Room**: Real-time chat functionality between users and admins.

## Known Bugs

- **Review Rating**: When selecting 5 stars, the interface incorrectly displays 7 stars.
- **Add to Cart Toast**: When adding a product to the cart, the toast message does not disappear automatically.

## How to Run

### Prerequisites

- **XAMPP**: Ensure XAMPP is installed and the MySQL and Apache services are turned on.
- **Java**: Make sure the Java Development Kit (JDK) is installed.
- **IntelliJ IDEA**: Recommended IDE for running the project.

### Steps to Run the Website

1. **Start XAMPP Services**:
    - Open XAMPP and start the MySQL and Apache services.

2. **Create Database**:
    - Open the MySQL admin dashboard and create a database named `ex5`.

3. **Build and Run the Project**:
    - Open the project in IntelliJ IDEA.
    - Press the 'Play' button to run the application.

### Data Initialization

The `DataLoader` class preloads all necessary data, including products and users, into the database.

### Default Credentials

- **Default User**:
    - Username: `user`
    - Password: `P@ssW0rd123!`
- **Default Admin**:
    - Username: `admin`
    - Password: `@dm1nP@ssw0rd!`

## Additional Information

- **Frontend**: Uses Thymeleaf templates and Bootstrap for styling.
- **Backend**: Built with Spring Boot, handling business logic and database interactions.
- **APIs**: RESTful APIs are provided for showing and filtering products in the shop using htmx.
- **Forgot Password**: To make forgot password work(SMTP), you should provide credentials for your own email configuration in application.properties.
