# Integration and API Test Documentation

Authors:

Date:

Version:

# Contents

- [Dependency graph](#dependency graph)

- [Integration and API Test Documentation](#integration-and-api-test-documentation)
- [Contents](#contents)
- [Dependency graph](#dependency-graph)
- [Integration approach](#integration-approach)
  - [Bottom up](#bottom-up)
- [Tests](#tests)
  - [Step 15](#step-15)
  - [Step 16](#step-16)
  - [Step 17](#step-17)
  - [Step 18](#step-18)
  - [Step 19](#step-19)
  - [Step 21](#step-21)
- [Coverage of Scenarios and FR](#coverage-of-scenarios-and-fr)
- [Coverage of Non Functional Requirements](#coverage-of-non-functional-requirements)
    - [](#)

- [Tests](#tests)

- [Scenarios](#scenarios)

- [Coverage of scenarios and FR](#scenario-coverage)
- [Coverage of non-functional requirements](#nfr-coverage)



# Dependency graph 

     <report the here the dependency graph of the classes in EzShop, using plantuml>
```plantuml
component EzShop
component BalanceOperation
component	Customer
component	Order
component	ProductType
component	SaleTransaction
component	TicketEntry
component User
component BalanceOperationDB
component	CustomerDB
component	OrderDB
component	ProductTypeDB
component	SaleTransactionDB
component	TicketEntryDB
component UserDB
EzShop -->  BalanceOperation
EzShop -->  Customer
EzShop -->  Order
EzShop -->  ProductType
EzShop -->  SaleTransaction
EzShop -->  TicketEntry
EzShop -->  User
EzShop -->  BalanceOperationDB
EzShop -->  CustomerDB
EzShop -->  OrderDB
EzShop -->  ProductTypeDB
EzShop -->  SaleTransactionDB
EzShop -->  TicketEntryDB
EzShop -->  UserDB

```
# Integration approach

    <Write here the integration sequence you adopted, in general terms (top down, bottom up, mixed) and as sequence
    (ex: step1: class A, step 2: class A+B, step 3: class A+B+C, etc)> 
    <Some steps may  correspond to unit testing (ex step1 in ex above), presented in other document UnitTestReport.md>
    <One step will  correspond to API testing>
    
## Bottom up
Step1: UserClass

Step2: ProductTypeClass

Step3: CustomerClass 

Step4: OrderClass  

Step5: SaleTransactionClass

Step6: TickeEntryClass

Step7: BalanceOperationClass

Step8: UserDB

Step9: ProductTypeDB

Step10: CustomerDB

Step11: OrderDB

Step12: SaleTransactionDB

Step13: TicketEntryDB

Step14: BalanceOperationDB

Step15: UserClass + UserDB

Step16: ProductTypeClass + ProductTypeDB + UserClass 

Step17: CustomerClass + CustomerDB + UserClass 

Step18: OrderClass + OrderDB + ProductTypeClass + UserClass

Step19: SaleTransactionClass + SaleTransactionDB + ProductTypeClass + UserClass + Ticketentry

Step20: BalanceOperationClass + BalanceOperationDB + UserClass

#  Tests

   <define below a table for each integration step. For each integration step report the group of classes under test, and the names of
     JUnit test cases applied to them> JUnit test classes should be here src/test/java/it/polito/ezshop

## Step 15
| Classes          | JUnit test cases |
| ---------------- | ---------------- |
| UserClass+UserDB | testEZShopUser   |


## Step 16
| Classes                                | JUnit test cases  |
| -------------------------------------- | ----------------- |
| ProductClass+ProductTypeDB + UserClass | testEZShopProduct |


## Step 17 

| Classes                                | JUnit test cases   |
| -------------------------------------- | ------------------ |
| CustomerClass + CustomerDB+  UserClass | testEZShopCustomer |


## Step 18 

| Classes                                             | JUnit test cases |
| --------------------------------------------------- | ---------------- |
| OrderClass + OrderDB + ProductTypeClass + UserClass | testEZShopOrders |

## Step 19 

| Classes                                                                 | JUnit test cases |
| ----------------------------------------------------------------------- | ---------------- |
| SaleTransactionClass + SaleTransactionDB + ProductTypeClass + UserClass | testEZShopSales  |

## Step 20 

| Classes                                                | JUnit test cases  |
| ------------------------------------------------------ | ----------------- |
| BalanceOperationClass + BalanceOperationDB + UserClass | testEZShopBalance |


# Coverage of Scenarios and FR


<Report in the following table the coverage of  scenarios (from official requirements and from above) vs FR. 
Report also for each of the scenarios the (one or more) API JUnit tests that cover it. >




| Scenario ID | Functional Requirements covered | JUnit  Test(s)                                                                         |
| ----------- | ------------------------------- | -------------------------------------------------------------------------------------- |
| 2.1         | FR1.1 FR1.2 FR1.5               | TestEZShopUser -> TestDelete4                                                          |
| 2.2         | FR1.1 FR1.2 FR1.5               | TestEZShopUser ->TestDelete4                                                           |
| 2.3         | FR1.1 FR1.5                     | TestEZShopUser ->TestUpdate1                                                           |
| 5.1         | FR1                             | TestEZShopUser ->TestLogin5                                                            |
| 5.2         | FR1                             | TestEZShopUser ->TestLogout1                                                           |
| 1.1         | FR3.1 FR3.2                     | TestEZShopProduct ->TestCreate1                                                        |
| 1.2         | FR3.1 FR3.4 FR4.2               | TestEZShopProduct ->TestUpdateLocation                                                 |
| 4.1         | FR5.1                           | TestEZShopCustomer -> TestDefineCustomer                                               |
| 4.2         | FR5.5 FR5.6                     | TestEZShopCustomer ->TestAttachCardToCustomer                                          |
| 3.1         | FR4.3                           | TestEZShopOrders->TestShouldIssueOrder1                                                |
| 3.2         | FR4.4 FR4.5                     | TestEZShopOrders->TestShouldPayOrder                                                   |
| 3.3         | FR4.6                           | TestEZShopOrders->TestShouldRecordOrderArrival                                         |
| 6.1         | FR6.1 FR6.2                     | TestEZShopSales -> ShouldAddProductToSale                                              |
| 6.2         | FR6.4                           | TestEZShopSales -> ShouldApplyDiscountRateToProduct                                    |
| 6.5         | FR6.10                          | TestEZShopSales -> ShouldDeleteSaleTransaction                                         |
| 7.4         | FR7.1                           | TestEZShopPayment->TestReceiveCashPayment                                              |
| 7.1         | FR7.2                           | TestEZShopPayment->testReceiveCreditCardPayment                                        |
| 8.2         | FR7.3                           | TestEZShopSales ->  shouldEndReturnTransaction                                         |
| 9.1         | FR8.1 FR8.2 FR8.3               | TestEZShopBalance -> testGetCreditsDebits3,testGetCreditsDebits4,testGetCreditsDebits5 |



# Coverage of Non Functional Requirements


<Report in the following table the coverage of the Non Functional Requirements of the application - only those that can be tested with automated testing frameworks.>


### 

| Non Functional Requirement | Test name                                       |
| -------------------------- | ----------------------------------------------- |
| NFR2                       | All previous JUnit tests                        |
| NFR4                       | TestEZShopProduct->testCreate4                  |
| NFR5                       | TestEZShopPayment->testReceiveCreditCardPayment |


