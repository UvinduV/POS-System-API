# POS System Backend 


- API Documentation : https://documenter.getpostman.com/view/36185079/2sAXjJ6YWk

# Introduction

The POS System is designed to manage customer orders, inventory, and sales efficiently. The frontend has already been developed, This repositry is include backend was developed using Jakarta EE, with a focus on maintaining a proper layered architecture, applying best practices in coding, AJAX for handling asynchronous communication and ensuring secure MySQL as the database interactions.

# Tech Stack 

- Jakarta EE: Enterprise framework for building robust and scalable applications. 
- MySQL: Relational database for storing persistent data.
- AJAX/Fetch: Used for asynchronous communication between the frontend and backend. 
- JNDI: Java Naming and Directory Interface for managing database configuration.

# Database Configuration

- Database: MySQL
- JNDI Name: java:comp/env/jdbc/POSSystemApi
- Schema: The database include in Customers, Orders, Items, and Order Details tables.

# Logging Configuration

-INFO: General application flow.
- DEBUG: Detailed debugging information.
- ERROR: Error events of considerable importance that might still allow the application to continue running.
- WARN: Potentially harmful situations.

# Setup and Started
- Copy code git clone https://github.com/UvinduV/POS-System-API.git cd pos-system-backend Set up the database:
- Ensure MySQL is installed and running. Import the database schema provided in the sql directory. Configure JNDI:
- Set up the JNDI resource in your Jakarta EE server configuration (e.g.TomCat). Build and deploy the application:
- Use your preferred Jakarta EE compatible server to deploy and Run the application:
- Access the application with frontend application via your web browser.




