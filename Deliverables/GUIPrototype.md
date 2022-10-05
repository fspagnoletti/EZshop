# Graphical User Interface Prototype  

Authors: FRANCESCO SPAGNOLETTI, DORIANA MONACO, SELEN AKKAYA, SAEID ESMAEILI

Date: 20.04.2021

Version: 0.1



\<Report here the GUI that you propose. You are free to organize it as you prefer. A suggested presentation matches the Use cases and scenarios defined in the Requirement document. The GUI can be shown as a sequence of graphical files (jpg, png)  >

# Log in

### The page where users can log in with their ID and password or owner/administrator can create account for new employee.

![pic](./graphics/GUIlogin.png)

# Dashboard

### The page displayed after log in. 
### From this menu, users can open all other pages, if they're allowed.

#### Cashier logs in and can't access suppliers, management and users pages.

![pic](./graphics/GUIdbcashier.png)

#### Owner logs in and can't access management page. 

![pic](./graphics/GUIdbowner.png)

#### Administrator logs in and can access every page.

![pic](./graphics/GUIdbadmin.png)

# Customers

### The page where all the costumers with fidelity card are listed. They can be searched by customerName or ID. 

![pic](./graphics/GUIcustomers.png)

# Edit Customer

### The page where customer's information can be edited.

![pic](./graphics/GUIeditCustomer.png)

# Add new customer

### The page where new customer can be added into the system.

![pic](./graphics/GUIaddNewCustomer.png)

# Sales

### The page where Sales accounting is saving and transactions are recorded. 
### TransactionId, date, hour and amount is recorded daily
### Daily revanue is recorded by indicating the starting date and particular hour for day.

![pic](./graphics/GUISales.png)

# Purchase 

### The page used to start a transaction. 
### The cashier reads a product with bar code reader and it is displayed on the monitor with description , quantity, price. 
### A calculator is displayed to calculate amount of purchase.
### Cashier compute total amount and press payment button. Chooses payment method and press Print button to print and register receipt.
### Other functionalities: apply discount, add bar code manually, scan fidelity card, cancel transaction, open cash register. 
### In this page cashier can perform cash opening and cash closeout.

![pic](./graphics/GUIpurchase.png)

# Payment by cash

### This page is opened when customer wants to pay with cash.
### Cashier selects how customer chooses to pay and how much.
### System computes the change.

![pic](./graphics/GUIcash.png)

# Pyament by fidelity card

### This page appears when fidelity card payment method is selected. 
### It shows information about fidelity card, and new card amount after payment.
### A window shows some messages in case of error or success of transaction.

![pic](./graphics/GUIfcard.png)

# Inventory

### The page where all product in inventory are shown with their quantities.
### Products can be searched with their id, edited, removed or added.
### If product quantity is below a certain threshold it will be displayed in red.

![pic](./graphics/GUIinventory.png)

# Catalogue 

### The page where catalogue is shown.
### For each category of product, an image and a description are shown.
### Products can be searched and added.

![pic](./graphics/GUIcatalogue.png)

# Available product in catalogue

### The page shown when user press a certain product into catalogue. 
### Product can be edited or deleted.

![pic](./graphics/GUIprodav.png)

# Unavailable product in catalogue

### The page shown when user press a certain product into catalogue. 
### Product can be edited or deleted.

![pic](./graphics/GUIprodunav.png)

# Users

### The page where existing account are listed with information about owner of the account. 
### Owner can edit, delete or search users. 

![pic](./graphics/GUIusers.png)

# Suppliers

### The page where manufacturers are listed. 
### They can be searched, modified, deleted or added.

![pic](./graphics/GUIsuppliers.png)

# Management

### The page who only administrator can access. 
### Admin can see what other accounts can access and modify it.

![pic](./graphics/GUImanagement.png)

# Transaction

## 1. Cashier C logs in
## 2. C press "Purchase" button and open purchase page
## 3. C perform cash opening
## 4. C reads bar code of chosen products
## 5. C computes total amount
## 6. C selects payment method
## 7. Payment method GUI displayed

![pic](./graphics/paymentGUI.png)

# Add new customers

## 1. Cashier C press Customers button and new page appears
## 2. C press Add new Customer
## 3. C inserts information about new customer
## 4. C press save button or delete button
## 5. C goes back to previous page

![pic](./graphics/customersGUI.png)

# Show product into catalogue

## Cashier C press Catalogue and new page appears
## Cashier C selects product
## Product information are displayed
## Cashier C goes back to previous window

![pic](./graphics/productGUI.png)

# List all existing account

## 1. Owner O logs in
## 2. O press Users button on dashboard
## 3. Users window appears and shows all registered account associated to a role.


![pic](./graphics/usersGUI.png)