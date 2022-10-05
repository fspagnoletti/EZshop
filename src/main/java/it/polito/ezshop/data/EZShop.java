package it.polito.ezshop.data;

import it.polito.ezshop.exceptions.*;
import it.polito.ezshop.model.*;
import it.polito.ezshop.dataBase.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;

public class EZShop implements EZShopInterface {

	TreeMap<Integer, User> usersList = UserDB.getUsers();
	TreeMap<Integer, ProductType> productsList = ProductTypeDB.getProducts();
	User loggedUser;
	TreeMap<Integer, Customer> customerList = CustomerDB.getCustomers();
	TreeMap<Integer, BalanceOperation> balanceOperationsList = BalanceOperationDB.getBalanceOperations();
	TreeMap<Integer, Order> ordersList = OrderDB.getOrders();
	double shopBalance;
	TreeMap<Integer, SaleTransaction> saleTransactionsList = SaleTransactionDB.getSaleTransactions();
	TreeMap<Integer, SaleTransaction> returnTransactionsList = SaleTransactionDB.getReturnTransactions();
//	TreeMap<Integer, TicketEntryClass> returnTickets = new TreeMap<Integer, TicketEntryClass>();
	TreeMap<Integer, ProductType> tempProductsList = new TreeMap<Integer, ProductType>();
	

	@Override
	public void reset() {
		
		CustomerDB.removeAllCustomers();
		SaleTransactionDB.removeAllSaleTransactions();
		OrderDB.removeAllOrders();
		ProductTypeDB.deleteAll();
		BalanceOperationDB.removeAllBalanceOperations();
		TicketEntryDB.removeAllTickets();
		UserDB.deleteAll();
		shopBalance = 0;
		loggedUser=null;
		usersList.clear();
		productsList.clear();
		customerList.clear();
		balanceOperationsList.clear();
		ordersList.clear();
		saleTransactionsList.clear();
		returnTransactionsList.clear();
	}

	@Override
	public Integer createUser(String username, String password, String role)
			throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {

		if (username == null || username.isEmpty()) {
			throw new InvalidUsernameException();
		}

		if (password == null || password.isEmpty()) {
			throw new InvalidPasswordException();
		}

		if ( role == null || role.isEmpty()) {
			throw new InvalidRoleException();
		}

		if (role.compareTo("Administrator") != 0 && role.compareTo("Cashier") != 0
				&& role.compareTo("ShopManager") != 0)
			throw new InvalidRoleException();

		// not empty: check if exist that username
		for (User u : usersList.values()) {
			if (u.getUsername().compareTo(username) == 0) {
				return -1;
			}
		}

		// in case map is empty
		if (usersList.isEmpty()) {
			User user = new UserClass(username, password, role);
			user.setId(1);
			if (UserDB.addUser(1, user) == -1)
				return -1;
			usersList.put(1, user);
			return 1;
		} else {
			User user = new UserClass(username, password, role);
			int id = usersList.lastEntry().getKey() + 1;
			user.setId(id);
			if (UserDB.addUser(id, user) == -1)
				return -1;
			usersList.put(id, user);
			return id;
		}

	}

	@Override
	public boolean deleteUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
		if (loggedUser == null || loggedUser.getRole().compareTo("Administrator") != 0 )
			throw new UnauthorizedException();
		if (id == null || id <= 0)
			throw new InvalidUserIdException();

		if (UserDB.removeUser(id) == -1)
			return false;
		User user = usersList.remove(id);
		if (user == null) {
			return false;
		}
		return true;
	}

	@Override
	public List<User> getAllUsers() throws UnauthorizedException {
		if ( loggedUser == null || loggedUser.getRole().compareTo("Administrator") != 0)
			throw new UnauthorizedException();
		List<User> res = new ArrayList<User>(usersList.values());
		return res;
	}

	@Override
	public User getUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
		if ( loggedUser == null || loggedUser.getRole().compareTo("Administrator") != 0)
			throw new UnauthorizedException();
		if ( id == null || id <= 0 )
			throw new InvalidUserIdException();

		return usersList.get(id);
	}

	@Override
	public boolean updateUserRights(Integer id, String role)
			throws InvalidUserIdException, InvalidRoleException, UnauthorizedException {
		if ( loggedUser == null || loggedUser.getRole().compareTo("Administrator") != 0)
			throw new UnauthorizedException();
		if ( id == null || id <= 0 )
			throw new InvalidUserIdException();
		if ( role == null || role.isEmpty())
			throw new InvalidRoleException();
		if (role.compareTo("Administrator") != 0 && role.compareTo("Cashier") != 0
				&& role.compareTo("ShopManager") != 0)
			throw new InvalidRoleException();

		if (UserDB.updateUserRights(id, role) == -1)
			return false;
		User user = usersList.get(id);
		if (user == null) {
			return false;
		}
		user.setRole(role);
		return true;
	}

	@Override
	public User login(String username, String password) throws InvalidUsernameException, InvalidPasswordException {
		if (username == null || username.isEmpty())
			throw new InvalidUsernameException();
		if ( password == null || password.isEmpty() )
			throw new InvalidPasswordException();

		for (User u : usersList.values()) {
			if (u.getUsername().compareTo(username) == 0) {
				if (u.getPassword().compareTo(password) == 0) {
					loggedUser = u;
					return u;
				}
			}
		}
		return null; // wrong credentials or not in list
	}

	@Override
	public boolean logout() {
		if (loggedUser == null)
			return false;
		loggedUser = null;
		return true;
	}
 
	@Override
	public Integer createProductType(String description, String productCode, double pricePerUnit, String note)
			throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException,
			UnauthorizedException {

		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (description == null || description.isEmpty())
			throw new InvalidProductDescriptionException();
		if (productCode == null || productCode.isEmpty())
			throw new InvalidProductCodeException();
		if (!productCode.matches("^[0-9]{12,14}$"))
			throw new InvalidProductCodeException();
		if (!validBarCode(productCode))
			throw new InvalidProductCodeException();
		if (pricePerUnit <= 0)
			throw new InvalidPricePerUnitException();
		
		productsList = ProductTypeDB.getProducts();
		
		for (ProductType p : productsList.values()) {
			if (p.getBarCode().compareTo(productCode) == 0) {
				return -1;
			}
		}
		
		if (productsList.isEmpty()) {
			ProductType p = new ProductTypeClass(description, productCode, pricePerUnit, note);
			p.setId(1);
			if (ProductTypeDB.addProductType(1, p) == -1)
				return -1;
			productsList.put(1, p);
			return 1;
		} else {
			ProductType p = new ProductTypeClass(description, productCode, pricePerUnit, note);
			int id = productsList.lastKey() + 1;
			p.setId(id);
			if (ProductTypeDB.addProductType(id, p) == -1)
				return -1;
			productsList.put(id, p);
			return id;
		}
		

	}

	@Override
	public boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote)
			throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException,
			InvalidPricePerUnitException, UnauthorizedException {

		if (id == null || id <= 0)
			throw new InvalidProductIdException();
		if ( newDescription == null || newDescription.isEmpty())
			throw new InvalidProductDescriptionException();
		if (newCode == null || newCode.isEmpty())
			throw new InvalidProductCodeException();
		if (!newCode.matches("^[0-9]{12,14}$"))
			throw new InvalidProductCodeException();
		if (!validBarCode(newCode))
			throw new InvalidProductCodeException();
		if (newPrice <= 0)
			throw new InvalidPricePerUnitException();
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();

		if (productsList.containsKey(id)) {
			for (ProductType p : productsList.values()) {
				if (p.getBarCode().compareTo(newCode) == 0 && p.getId()!=id)
					return false;
			}
			if (ProductTypeDB.updateProductType(id, newDescription, newCode, newPrice, newNote) == -1)
				return false;
			ProductType p = productsList.get(id);
			p.setProductDescription(newDescription);
			p.setBarCode(newCode);
			p.setPricePerUnit(newPrice);
			p.setNote(newNote);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean deleteProductType(Integer id) throws InvalidProductIdException, UnauthorizedException {

		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if(id==null) throw new InvalidProductIdException();
		if (id <= 0)
			throw new InvalidProductIdException();

		if (ProductTypeDB.removeProductType(id) == -1)
			return false;
		if (productsList.remove(id) == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public List<ProductType> getAllProductTypes() throws UnauthorizedException {
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		List<ProductType> res = new ArrayList<ProductType>(productsList.values());
		return res;
	}

	private boolean validBarCode(String barCode) {
		int i;
		int sum = 0;
		int num;
		if(barCode.length()==13) {
			for (i = 0; i < barCode.length() - 1; i++) {
				if (i % 2 == 0) {
					sum += Integer.parseInt(String.valueOf(barCode.charAt(i)));
				} else {
					sum += Integer.parseInt(String.valueOf(barCode.charAt(i))) * 3;
				}
			}
			num=sum;
			while(num%10!=0) num++;
			sum=num-sum;
			if (sum == Integer.parseInt(String.valueOf(barCode.charAt(i)))) {
				return true;
			} else {
				return false;
			}
		} else {
			for (i = 0; i < barCode.length() - 1; i++) {
				if (i % 2 != 0) {
					sum += Integer.parseInt(String.valueOf(barCode.charAt(i)));
				} else {
					sum += Integer.parseInt(String.valueOf(barCode.charAt(i))) * 3;
				}
			}
			num=sum;
			while(num%10!=0) num++;
			sum=num-sum;
			if (sum == Integer.parseInt(String.valueOf(barCode.charAt(i)))) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public ProductType getProductTypeByBarCode(String barCode)
			throws InvalidProductCodeException, UnauthorizedException {

		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (barCode == null || barCode.isEmpty() )
			throw new InvalidProductCodeException();
		if (!validBarCode(barCode))
			throw new InvalidProductCodeException();

		for (ProductType p : productsList.values()) {
			if (p.getBarCode().compareTo(barCode) == 0) {
				return p;
			}
		}
		return null; 
	}

	@Override
	public List<ProductType> getProductTypesByDescription(String description) throws UnauthorizedException {

		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();

		if (description == null || description.isEmpty()) {
			List<ProductType> res = new ArrayList<ProductType>(productsList.values());
			return res;
		}
		List<ProductType> list = new ArrayList<ProductType>();
		for (ProductType p : productsList.values()) {
			if (p.getProductDescription().contains(description)) {
				list.add(p);
			}
		}
		return list;
	}

	@Override
	public boolean updateQuantity(Integer productId, int toBeAdded)
			throws InvalidProductIdException, UnauthorizedException {

		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if(productId == null) throw new InvalidProductIdException();
		if (productId <= 0)
			throw new InvalidProductIdException();

		ProductType p = productsList.get(productId);
		if (p == null) return false;
		if( p.getLocation() == null) return false;
		if(p.getLocation().isEmpty()) return false;	
		
		int newQ = p.getQuantity() + toBeAdded;
		if (newQ < 0)
			return false;
		if (ProductTypeDB.updateProductQuantity(productId, toBeAdded) == -1)
			return false;
		p.setQuantity(newQ);
		return true;
	}

	@Override
	public boolean updatePosition(Integer productId, String newPos)
			throws InvalidProductIdException, InvalidLocationException, UnauthorizedException {
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if(productId==null) throw new InvalidProductIdException();
		if (productId <= 0)
			throw new InvalidProductIdException();
		String array[] = newPos.split("-");
		
		if(!array[0].matches("[0-9]+")) throw new InvalidLocationException();
		if(!array[1].matches("[A-Za-z]+")) throw new InvalidLocationException();
		if(!array[2].matches("[0-9]+")) throw new InvalidLocationException();
//		for (int i = 0; i < array.length; i++) {
//			if (!array[i].matches("[0-9]+")) 
//				throw new InvalidLocationException();
//		}

		if (!productsList.isEmpty()) {
			for (ProductType p : productsList.values()) {
				if (p.getLocation() != null) {
					if (p.getLocation().compareTo(newPos) == 0)
						return false;
				}
			}
		}

		ProductType p = productsList.get(productId);
		if (p == null) {
			return false;
		} else {
			if (ProductTypeDB.updateProductPosition(productId, newPos) == -1)
				return false;
			p.setLocation(newPos);
			return true;
		}
	}

	@Override
	public Integer issueOrder(String productCode, int quantity, double pricePerUnit) throws InvalidProductCodeException,
			InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {
		// Check Exceptions
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (quantity <= 0)
			throw new InvalidQuantityException();
		if (productCode == null || productCode.isEmpty())
			throw new InvalidProductCodeException();
		if (!productCode.matches("^[0-9]{12,14}$"))
			throw new InvalidProductCodeException();
		if (!validBarCode(productCode))
			throw new InvalidProductCodeException();
		if (pricePerUnit <= 0)
			throw new InvalidPricePerUnitException();
		
		// Check the product
		ProductType p;
		p = getProductTypeByBarCode(productCode);
		if (p == null) return -1;
		
		// Create a new balance Operation
		double money = quantity * pricePerUnit;
		int newBalanceId;

		// in case map is empty
		if (balanceOperationsList.isEmpty()) {
			BalanceOperation balanceOperation = new BalanceOperationClass(LocalDate.now(), money, "ORDER_TO_PAY");
			balanceOperation.setBalanceId(1);
			if (BalanceOperationDB.addBalanceOperation(1, balanceOperation) == -1)
				return -1;
			balanceOperationsList.put(1, balanceOperation);
			newBalanceId = 1;
		}
		// in case map is not empty
		else {
			BalanceOperation balanceOperation = new BalanceOperationClass(LocalDate.now(), money, "ORDER_TO_PAY");
			int balanceId = balanceOperationsList.lastEntry().getKey() + 1;
			balanceOperation.setBalanceId(balanceId);
			if (BalanceOperationDB.addBalanceOperation(balanceId, balanceOperation) == -1)
				return -1;
			balanceOperationsList.put(balanceId, balanceOperation);
			newBalanceId = balanceId;
		}

		// Create a new Order

		// in case map is empty
		if (ordersList.isEmpty()) {
			Order order = new OrderClass(productCode, pricePerUnit, quantity, "ISSUED");
			order.setOrderId(1);
			order.setBalanceId(newBalanceId);
			if (OrderDB.addOrder(1, newBalanceId, order) == -1)
				return -1;
			ordersList.put(1, order);
			return 1;
		}
		// in case map is not empty
		else {
			Order order = new OrderClass(productCode, pricePerUnit, quantity, "ISSUED");
			int orderId = ordersList.lastEntry().getKey() + 1;
			order.setOrderId(orderId);
			if (OrderDB.addOrder(orderId, newBalanceId, order) == -1)
				return -1;
			ordersList.put(orderId, order);
			return orderId;
		}

	}

	@Override
	public Integer payOrderFor(String productCode, int quantity, double pricePerUnit)
			throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException,
			UnauthorizedException {
		// Check Exceptions
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (quantity <= 0)
			throw new InvalidQuantityException();
		if (productCode == null || productCode.isEmpty())
			throw new InvalidProductCodeException();
		if (!productCode.matches("^[0-9]{12,14}$"))
			throw new InvalidProductCodeException();
		if (!validBarCode(productCode))
			throw new InvalidProductCodeException();
		if (pricePerUnit <= 0)
			throw new InvalidPricePerUnitException();
		
		// Check the product
		ProductType p;
		p = getProductTypeByBarCode(productCode);
		if (p == null) return -1;

		// Check the Balance of the Shop
		double money = quantity * pricePerUnit;
		shopBalance = computeBalance();
		if (shopBalance - money < 0)
			return -1;

		// Create a new balance Operation
		int newBalanceId;

		// in case map is empty
		if (balanceOperationsList.isEmpty()) {
			BalanceOperation balanceOperation = new BalanceOperationClass(LocalDate.now(), -money, "ORDER");
			balanceOperation.setBalanceId(1);
			if (BalanceOperationDB.addBalanceOperation(1, balanceOperation) == -1)
				return -1;
			balanceOperationsList.put(1, balanceOperation);
			newBalanceId = 1;
		}
		// in case map is not empty
		else {
			BalanceOperation balanceOperation = new BalanceOperationClass(LocalDate.now(), -money, "ORDER");
			int balanceId = balanceOperationsList.lastEntry().getKey() + 1;
			balanceOperation.setBalanceId(balanceId);
			if (BalanceOperationDB.addBalanceOperation(balanceId, balanceOperation) == -1)
				return -1;
			balanceOperationsList.put(balanceId, balanceOperation);
			newBalanceId = balanceId;
		}

		// Create a new Order

		// in case map is empty
		if (ordersList.isEmpty()) {
			Order order = new OrderClass(productCode, pricePerUnit, quantity, "PAYED");
			order.setOrderId(1);
			order.setBalanceId(newBalanceId);
			if (OrderDB.addOrder(1, newBalanceId, order) == -1)
				return -1;
			ordersList.put(1, order);
			shopBalance = shopBalance - money;
			return 1;
		}
		// in case map is not empty
		else {
			Order order = new OrderClass(productCode, pricePerUnit, quantity, "PAYED");
			int orderId = ordersList.lastEntry().getKey() + 1;
			order.setOrderId(orderId);
			if (OrderDB.addOrder(orderId, newBalanceId, order) == -1)
				return -1;
			ordersList.put(orderId, order);
			shopBalance = shopBalance - money;
			return orderId;
		}

	}

	@Override
	public boolean payOrder(Integer orderId) throws InvalidOrderIdException, UnauthorizedException {
		// Check Exceptions
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (orderId == null || orderId <= 0)
			throw new InvalidOrderIdException();

		Order o = ordersList.get(orderId);
		if (o.getStatus() == "PAYED")
			return true;

		// Check the Balance of the Shop
		double money = o.getPricePerUnit() * o.getQuantity();
		shopBalance = computeBalance();
		if (shopBalance - money < 0)
			return false;

		// Change the status of the order o inside the map
		o.setStatus("PAYED");
		if (OrderDB.updateOrderToPayed(orderId) == -1 && BalanceOperationDB.updateBalanceToOrder(o.getBalanceId()) == -1)
			return false;
		ordersList.remove(orderId);
		ordersList.put(orderId, o);
//		shopBalance = shopBalance - money;

		return true;

	}

	@Override
	public boolean recordOrderArrival(Integer orderId)
			throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException {
		// Check Exceptions
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		if (orderId == null || orderId <= 0)
			throw new InvalidOrderIdException();
		Order o = ordersList.get(orderId);
		if (o==null) return false;
		String productCode = o.getProductCode();
		ProductType p;
		try {
			p = getProductTypeByBarCode(productCode);
		} catch (InvalidProductCodeException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (UnauthorizedException e) {
			// TODO Auto-generated catch block
			return false;
		}
		if (p.getLocation() == null)
			throw new InvalidLocationException();

		if (o.getStatus() == "COMPLETED")
			return true;

		// Change the status of the order o inside the map
		o.setStatus("COMPLETED");
		if (OrderDB.updateOrderToCompleted(orderId) == -1 && BalanceOperationDB.updateBalanceToOrder(o.getBalanceId()) == -1)
			return false;
		ordersList.remove(orderId);
		ordersList.put(orderId, o);

		return true;

	}

	@Override
	public List<Order> getAllOrders() throws UnauthorizedException {
		if(loggedUser==null) throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		List<Order> res = new ArrayList<Order>(ordersList.values());
		return res;

	}

	@Override
	public Integer defineCustomer(String customerName) throws InvalidCustomerNameException, UnauthorizedException {
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0 && loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if (customerName == null || customerName.isEmpty() ) throw new InvalidCustomerNameException();
		
		// not empty: check if exist that username
		for (Customer c : customerList.values()) {
			if (c.getCustomerName().compareTo(customerName) == 0) {
				return -1;
			}
		}
		// in case map is empty
		if(customerList.isEmpty()){
			Customer customer=new CustomerClass(customerName, "0000000000", 0);
			customer.setId(1);
			if(CustomerDB.addCustomer(1, customer)==-1) return -1;
			customerList.put(1,customer);
			return 1;
		} else {
			Customer customer = new CustomerClass(customerName, "0000000000", 0);
			int id = customerList.lastEntry().getKey() + 1;
			customer.setId(id);
			if(CustomerDB.addCustomer(id, customer)==-1) return -1;
			customerList.put(id, customer);
			return id;
		}
	}

	@Override
	public boolean modifyCustomer(Integer id, String newCustomerName, String newCustomerCard)
			throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException,
			UnauthorizedException {
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0 && loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if (id == null || id <= 0) throw new InvalidCustomerIdException();
		if (newCustomerName == null || newCustomerName.isEmpty()) throw new InvalidCustomerNameException();
		
		
		
		
		//update Customer name only if (newCustomerCard is null)
		if (newCustomerCard == null) {
			Customer c = customerList.get(id);
			if (c == null) return false;
			if(CustomerDB.updateCustomerName(id, newCustomerName)==-1) return false;
			c.setCustomerName(newCustomerName);
			customerList.put(id, c);
			return true;
		}
		
		
		//delete Customer if (newCustomerCard is empty)
		if(newCustomerCard.isEmpty()){
			if(CustomerDB.removeCustomer(id)==-1) return false;
			customerList.remove(id);
			return true;
		} else {
			if(!newCustomerCard.matches("^[0-9]{10}$")) throw new InvalidCustomerCardException();
			if(CustomerDB.updateCustomer(id, newCustomerName, newCustomerCard)==-1) return false;
			Customer c = customerList.get(id);
			if (c == null) return false;
			c.setCustomerName(newCustomerName);
			c.setCustomerCard(newCustomerCard);
			customerList.put(id, c);
			return true;
		}
	}

	@Override
	public boolean deleteCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0 && loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if(id==null || id <=0 ) throw new InvalidCustomerIdException();

		if(CustomerDB.removeCustomer(id)==-1) return false;
		Customer customer= customerList.remove(id);

		if(customer==null) {
			return false;
		}
		return true;

	}

	@Override
	public Customer getCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0 && loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();

		if(id==null || id <=0) throw new InvalidCustomerIdException();
		customerList=CustomerDB.getCustomers();
		
		for (Customer c : customerList.values()) {
			if (c.getId().compareTo(id) == 0) {
				return c;
			}
		}
		return null;


	}

	@Override
	public List<Customer> getAllCustomers() throws UnauthorizedException {
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0 && loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		customerList=CustomerDB.getCustomers();
		List<Customer> res = new ArrayList<Customer>(customerList.values());
		return res;

	}

	@Override
	public String createCard() throws UnauthorizedException {
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0 && loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		
		//I create a valid format for a cardNumber 10 digit String
		if (CustomerDB.maxCustomerCard() == null) return null;
		int i = Integer.parseInt(CustomerDB.maxCustomerCard());
		i++;
		String res = String.format("%010d", i);
		
		return res;
	}

	@Override
	public boolean attachCardToCustomer(String customerCard, Integer customerId)
			throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {

		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0 && loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();

		if(customerId==null || customerId <= 0){
			throw new InvalidCustomerIdException();
		}
		if(customerCard==null || customerCard.isEmpty() || customerCard.length()!= 10) throw new InvalidCustomerCardException();
		customerList=CustomerDB.getCustomers();
		
		for (Customer c : customerList.values()) {
			if (c.getCustomerCard().compareTo(customerCard) == 0) {
				return false;
			}
		}
		Customer c = customerList.get(customerId);
		if (c == null) return false;
		if(CustomerDB.updateCustomerCard(customerId, customerCard) == -1) return false;
		c.setCustomerCard(customerCard);
		customerList.put(customerId, c);

		return true;
	}

	@Override
	public boolean modifyPointsOnCard(String customerCard, int pointsToBeAdded)
			throws InvalidCustomerCardException, UnauthorizedException {
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0 && loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if(customerCard==null || customerCard.isEmpty()) throw new InvalidCustomerCardException();
		if(!customerCard.matches("^[0-9]{10}$")) throw new InvalidCustomerCardException();
		if(pointsToBeAdded <= 0) return false;
		
		for (Customer c : customerList.values()) {
			if (c.getCustomerCard().compareTo(customerCard) == 0) {
				
				int p = c.getPoints() + pointsToBeAdded;
				if(CustomerDB.updateCustomerPoints(c.getId(), p) == -1) return false;
//				c.setPoints(p);
//				customerList.put(c.getId(), c);
				return true;
			}
		}
		return false;
	}

	@Override
	public Integer startSaleTransaction() throws UnauthorizedException {
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		
		//load the temp product list
		tempProductsList = ProductTypeDB.getProducts();
		
		if (saleTransactionsList.isEmpty()) {
			SaleTransaction s = new SaleTransactionClass();
			s.setTicketNumber(1);
			saleTransactionsList.put(1, s);
			System.out.println(s);
			return 1;
		} else {
			SaleTransaction s = new SaleTransactionClass();
			int ticketNumber = saleTransactionsList.lastKey() + 1;
			s.setTicketNumber(ticketNumber);
			saleTransactionsList.put(ticketNumber, s);
			System.out.println(s);
			return ticketNumber;
		}
	}

	@Override
	public boolean addProductToSale(Integer transactionId, String productCode, int amount)
			throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
			UnauthorizedException {
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if ( transactionId == null || transactionId <= 0 ) throw new InvalidTransactionIdException();
		if (productCode == null || productCode.isEmpty())
			throw new InvalidProductCodeException();
		if (!productCode.matches("^[0-9]{12,14}$"))
			throw new InvalidProductCodeException();
		if (!validBarCode(productCode))
			throw new InvalidProductCodeException();
		if (amount <= 0) throw new InvalidQuantityException();
		
		SaleTransaction s;
		s = saleTransactionsList.get(transactionId);
		if (s == null) return false;
		
//		productsList = ProductTypeDB.getProducts();
	
		ProductType p;
		ProductType p1;
		p = getProductTypeByBarCode(productCode);
		if (p == null) return false;	
		System.out.println("p1 before: " + p);
		System.out.println("tempListBefore: " + tempProductsList);
		System.out.println("saleTransactionsListBefore: " + saleTransactionsList);
		
		for(Map.Entry<Integer,ProductType> entry : tempProductsList.entrySet()) {
//			  Integer key = entry.getKey();
			  p1 = entry.getValue();
			  if (p1.getBarCode().compareTo(productCode) == 0) {
					
				int quantity = p1.getQuantity();
				if (quantity < amount) return false;
				p1.setQuantity(quantity-amount);
			  }

			}
		

//		ProductType p = p1;
//		int quantity = p.getQuantity();
//		if (quantity < amount) return false;
		System.out.println("p1 after: " + p);
		System.out.println("tempList product after: " + tempProductsList);
		System.out.println("saleTransactionsListAfter: " + saleTransactionsList);
		
		ArrayList<TicketEntry> entries = (ArrayList<TicketEntry>) s.getEntries();
		TicketEntry t = new TicketEntryClass(transactionId , productCode, p.getProductDescription(), amount, p.getPricePerUnit());
		System.out.println("new Ticket " + t);
		entries.add(t);
		s.setEntries(entries);
		s.setPrice(s.getPrice()+(amount * p.getPricePerUnit()));
//		p.setQuantity(quantity - amount);
		System.out.println("s " + s);
		System.out.println("lenght entries " + entries.size());
		System.out.println("lenght S.entries " + s.getEntries().size());
		return true;
	}

	@Override
	public boolean deleteProductFromSale(Integer transactionId, String productCode, int amount)
			throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException,
			UnauthorizedException {
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if (transactionId == null || transactionId <= 0 ) throw new InvalidTransactionIdException();
		if (productCode == null || productCode.isEmpty())
			throw new InvalidProductCodeException();
		if (!productCode.matches("^[0-9]{12,14}$"))
			throw new InvalidProductCodeException();
		if (!validBarCode(productCode))
			throw new InvalidProductCodeException();
		if (amount <= 0) throw new InvalidQuantityException();
		
		SaleTransaction s;
		s = saleTransactionsList.get(transactionId);
		if (s == null) return false;
		
		ProductType p;
		ProductType p1;
		p = getProductTypeByBarCode(productCode);
		if (p == null) return false;
		
		// WARNING: Whatever I do, if I delete a product with quantity = x, the entire product is deleted from the sale. Even if the return of this function is false. 
		

//		int quantity = p.getQuantity();
		ArrayList<TicketEntry> entries = (ArrayList<TicketEntry>) s.getEntries();
		TicketEntry t = new TicketEntryClass(transactionId, productCode, p.getProductDescription(), amount, p.getPricePerUnit());
		
		for (int i = 0; i < entries.size(); i++) {
			TicketEntry tmp  = entries.get(i);
			if (tmp.getBarCode().compareTo(productCode) == 0) {			
				t.setAmount(tmp.getAmount() - amount);
				entries.set(i, t);
				if (t.getAmount() <= 0) entries.remove(t);
			}
		}
		s.setPrice(s.getPrice()-(amount * p.getPricePerUnit()));
		
		for(Map.Entry<Integer,ProductType> entry : tempProductsList.entrySet()) {
//			  Integer key = entry.getKey();
			  p1 = entry.getValue();
			  if (p1.getBarCode().compareTo(productCode) == 0) {
					
				int quantity = p1.getQuantity();
//				if (quantity < amount) return false;
				p1.setQuantity(quantity+amount);
			  }

			}
//		p.setQuantity(quantity + amount);
		
		return true;
	}

	@Override
	public boolean applyDiscountRateToProduct(Integer transactionId, String productCode, double discountRate)
			throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException,
			UnauthorizedException {
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if (transactionId == null || transactionId <= 0) throw new InvalidTransactionIdException();
		if (productCode == null || productCode.isEmpty())
			throw new InvalidProductCodeException();
		if (!productCode.matches("^[0-9]{12,14}$"))
			throw new InvalidProductCodeException();
		if (!validBarCode(productCode))
			throw new InvalidProductCodeException();
		if (discountRate <= 0 || discountRate >= 1) throw new InvalidDiscountRateException();
		
		SaleTransaction s;
		s = saleTransactionsList.get(transactionId);
		if (s == null) return false;
		
		ProductType p;
		p = getProductTypeByBarCode(productCode);
		if (p == null) return false;
		
		ArrayList<TicketEntry> entries = (ArrayList<TicketEntry>) s.getEntries();
		System.out.println(entries);
		int count = 0; 		
	    while (entries.size() > count) {	  
	    	  TicketEntry t = entries.get(count);
	    	  if (t.getBarCode() == productCode) {	  
	    		  t.setDiscountRate(discountRate);
	    		  double priceNew = s.getPrice()-(discountRate*t.getPricePerUnit()*t.getAmount());
	    		  s.setPrice(priceNew);
	    		  entries.set(count, t);
	    	  }
	         count++;
	    }
			
		s.setEntries(entries);
		System.out.println(entries);
		return true;
	}

	@Override
	public boolean applyDiscountRateToSale(Integer transactionId, double discountRate)
			throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException {
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if ( transactionId == null || transactionId <= 0) throw new InvalidTransactionIdException();
		if (discountRate <= 0 || discountRate >= 1) throw new InvalidDiscountRateException();
		

		SaleTransaction s;
		s = saleTransactionsList.get(transactionId);
		if (s == null) return false;
		
		s.setDiscountRate(discountRate);
		s.setPrice(s.getPrice()-s.getPrice()*discountRate);
		
		return true;
	}

	@Override
	public int computePointsForSale(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if ( transactionId == null || transactionId <= 0 ) throw new InvalidTransactionIdException();
		
		SaleTransaction s;
		s = saleTransactionsList.get(transactionId);
		if (s == null) return -1;
		
		double total = s.getPrice();
		int p;
		p = (int)total/10;
		
		return p;
	}

	@Override
	public boolean endSaleTransaction(Integer transactionId)
			throws InvalidTransactionIdException, UnauthorizedException {
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if ( transactionId == null || transactionId <= 0) throw new InvalidTransactionIdException();
		
		SaleTransaction s;
		s = saleTransactionsList.get(transactionId);
		if (s == null) return false;
		
		if (SaleTransactionDB.addSaleTransaction(s.getTicketNumber(), s) == -1) return false;  
		
		ArrayList<TicketEntry> entries = (ArrayList<TicketEntry>) s.getEntries();
		System.out.println(entries);
		int count = 0; 		
	    while (entries.size() > count) {	 
	    	TicketEntryClass t = (TicketEntryClass) entries.get(count);
	    	System.out.println("Ticket : " + t );
	    	if (TicketEntryDB.addTicket(s.getTicketNumber(), t) == -1) return false;  
	        count++;
	    }
	    
		return true;
	}

	@Override
	public boolean deleteSaleTransaction(Integer transactionId)
			throws InvalidTransactionIdException, UnauthorizedException {
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if ( transactionId == null || transactionId <= 0) throw new InvalidTransactionIdException();
		
		saleTransactionsList = SaleTransactionDB.getSaleTransactions();
		
		SaleTransaction s;
		s = saleTransactionsList.get(transactionId);
		if (s == null) return false;
		
		if (SaleTransactionDB.removeSaleTransaction(s.getTicketNumber()) == -1) return false;  		
	    if (TicketEntryDB.removeTicket(s.getTicketNumber()) == -1) return false;  

		return true;
	}

	@Override
	public SaleTransaction getSaleTransaction(Integer transactionId)
			throws InvalidTransactionIdException, UnauthorizedException {
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if ( transactionId == null || transactionId <= 0) throw new InvalidTransactionIdException();
		
		SaleTransaction s = SaleTransactionDB.getSaleTransactionByTicket(transactionId);
		if (s == null) {
			s = SaleTransactionDB.getSaleTransactionNoTickets(transactionId);
			if (s == null) return null;
		}
		
		return s;
	}

	@Override
	public Integer startReturnTransaction(Integer transactionId)
			throws /* InvalidTicketNumberException, */InvalidTransactionIdException, UnauthorizedException {
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if ( transactionId == null || transactionId <= 0) throw new InvalidTransactionIdException();
		
		SaleTransaction s = getSaleTransaction(transactionId);
		if (s == null) return -1;
		List<TicketEntry> returnTickets = s.getEntries();
//		returnTickets.clear();
		s.setEntries(returnTickets);
		
		// in case map is empty
		if (returnTransactionsList.isEmpty()) {
			returnTransactionsList.put(1, s);
			return 1;
		} else {
			int id = returnTransactionsList.lastEntry().getKey() + 1;
			returnTransactionsList.put(id, s);
			return id;
		}
	}

	@Override
	public boolean returnProduct(Integer returnId, String productCode, int amount) throws InvalidTransactionIdException,
			InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if ( returnId == null || returnId <= 0) throw new InvalidTransactionIdException();
		if (productCode == null || productCode.isEmpty())
			throw new InvalidProductCodeException();
		if (!productCode.matches("^[0-9]{12,14}$"))
			throw new InvalidProductCodeException();
		if (!validBarCode(productCode))
			throw new InvalidProductCodeException();
		if (amount <= 0) throw new InvalidQuantityException();
		
		SaleTransaction s = returnTransactionsList.get(returnId);
		if (s == null) return false;
		ArrayList<TicketEntryClass> tickets = TicketEntryDB.getTickets(s.getTicketNumber());
//		System.out.println("tickets : " + tickets);
//		System.out.println("return prima: " + s.getEntries());
		TicketEntry t;
		for (int i = 0; i < tickets.size(); i++) {
			t = tickets.get(i);
			if (t.getBarCode().contains(productCode)) {
				if (t.getAmount() >= amount) {
					t.setAmount(amount);
					s.getEntries().add(t);
//					System.out.println("return dopo: " + s.getEntries());
					return true;
				}
			}	
		}
		return false;
	}

	@Override
	public boolean endReturnTransaction(Integer returnId, boolean commit)
			throws InvalidTransactionIdException, UnauthorizedException {
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if ( returnId == null || returnId <= 0) throw new InvalidTransactionIdException();
		
		SaleTransaction s = returnTransactionsList.get(returnId);
		if (s == null) return false;
		int oldTicketNumber = s.getTicketNumber();
		
		if (commit == false) {			
			returnTransactionsList.remove(returnId);
			System.out.println(returnTransactionsList);
			return false;
		}
		ArrayList<TicketEntry> tickets = (ArrayList<TicketEntry>) s.getEntries();
		System.out.println("list : " + tickets);
		for (int i = 0; i < tickets.size(); i++) {
			TicketEntryClass t = (TicketEntryClass) tickets.get(i);
			double amount = t.getAmount() * t.getPricePerUnit();
			System.out.println("amount : " + amount);
			if (SaleTransactionDB.updateSaleTransaction(t.getId(), -amount) == -1 || TicketEntryDB.updateTicketEntry(-t.getAmount(), t) == -1) return false;
			
			try {
				updateQuantity(t.getId(), t.getAmount());
			} catch (InvalidProductIdException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (UnauthorizedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
				
		}	
		if (SaleTransactionDB.updateSaleTransactionStatus(s.getTicketNumber()) == -1) return false;
		saleTransactionsList = SaleTransactionDB.getSaleTransactions();
		SaleTransaction s1 = s;
		int ticketNumber = saleTransactionsList.lastKey() + 1;
		s1.setTicketNumber(ticketNumber);
		if (SaleTransactionDB.addReturnTransaction(ticketNumber, oldTicketNumber, s1) == -1) return false;
		saleTransactionsList.put(ticketNumber, s1);
		
		for (int i = 0; i < tickets.size(); i++) {
			TicketEntryClass t = (TicketEntryClass) tickets.get(i);
			if ( TicketEntryDB.addTicket(ticketNumber, t) == -1) return false;
		}
		
		return true;
	}

	@Override 
	public boolean deleteReturnTransaction(Integer returnId)
			throws InvalidTransactionIdException, UnauthorizedException {
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if (returnId == null || returnId <= 0) throw new InvalidTransactionIdException();
		
		returnTransactionsList = SaleTransactionDB.getReturnTransactions();
		System.out.println("returnList : " + returnTransactionsList);
		SaleTransaction s = returnTransactionsList.get(returnId);
		if (s == null) return false;
		System.out.println("sale : " + s);
		
//		TicketEntryClass t = returnTickets.get(returnId);
//		if (t == null) return false;
//		double amount = t.getAmount() * t.getPricePerUnit();
//		if (SaleTransactionDB.updateSaleTransaction(t.getId(), -amount) == -1) return false;
//		try {
//			updateQuantity(t.getId(), -t.getAmount());
//		} catch (InvalidProductIdException | UnauthorizedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//		
		return true;
	}

	@Override
	public double receiveCashPayment(Integer transactionId, double cash)
			throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException {
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if ( transactionId == null || transactionId <= 0 ) throw new InvalidTransactionIdException();
		if (cash <= 0) throw new InvalidPaymentException();

		shopBalance = computeBalance();
		SaleTransaction s = SaleTransactionDB.getSaleTransactionByTicket(transactionId);
		if (s == null) return -1;
		double rest = cash - s.getPrice();
		if (rest < 0) return -1;
		if (shopBalance - rest < 0) return -1;
		
		// in case map is empty
		if (balanceOperationsList.isEmpty()) {
			BalanceOperation b = new BalanceOperationClass(LocalDate.now(), s.getPrice(), "SALE");				
			b.setBalanceId(1);
			if (BalanceOperationDB.addBalanceOperation(1, b) == -1)
				return -1;
			balanceOperationsList.put(1, b);
		}
		// in case map is not empty
		else {
			BalanceOperation b = new BalanceOperationClass(LocalDate.now(), s.getPrice(), "SALE");				
			int balanceId = balanceOperationsList.lastEntry().getKey() + 1;
			b.setBalanceId(balanceId);
			if (BalanceOperationDB.addBalanceOperation(balanceId, b) == -1)
				return -1;
			balanceOperationsList.put(balanceId, b);
		}

		return rest;
		
	}

	@Override
	public boolean receiveCreditCardPayment(Integer transactionId, String creditCard)
			throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {
		
		// Just check the creditCard, not storing it has the professor said on slack
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
			&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if ( transactionId == null || transactionId <= 0 ) throw new InvalidTransactionIdException();
		if (LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(creditCard) == false) throw new InvalidCreditCardException();
		if (creditCard.isEmpty() == true || creditCard == null) throw new InvalidCreditCardException();
		
		shopBalance = computeBalance();
		SaleTransaction s = SaleTransactionDB.getSaleTransactionByTicket(transactionId);
		if (s == null) return false;

		if (shopBalance - s.getPrice() <= 0) return false;
		
		// in case map is empty
		if (balanceOperationsList.isEmpty()) {
			BalanceOperation b = new BalanceOperationClass(LocalDate.now(), s.getPrice(), "SALE");				
			b.setBalanceId(1);
			if (BalanceOperationDB.addBalanceOperation(1, b) == -1)
				return false;
//			balanceOperationsList.put(1, b);
		}
		// in case map is not empty
		else {
			BalanceOperation b = new BalanceOperationClass(LocalDate.now(), s.getPrice(), "SALE");				
			int balanceId = balanceOperationsList.lastEntry().getKey() + 1;
			b.setBalanceId(balanceId);
			if (BalanceOperationDB.addBalanceOperation(balanceId, b) == -1)
				return false;
//			balanceOperationsList.put(balanceId, b);
		}
		
		
		return true;
	}

	@Override
	public double returnCashPayment(Integer returnId) throws InvalidTransactionIdException, UnauthorizedException {
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if (returnId == null || returnId <= 0) throw new InvalidTransactionIdException();
		
		shopBalance = computeBalance();
		SaleTransaction s = returnTransactionsList.get(returnId);
		if (s == null) return -1;
		double res = s.getPrice();
		if (shopBalance - res <= 0) return -1;
		
		// in case map is empty
				if (balanceOperationsList.isEmpty()) {
					BalanceOperation b = new BalanceOperationClass(LocalDate.now(), -(s.getPrice()), "RETURN");				
					b.setBalanceId(1);
					if (BalanceOperationDB.addBalanceOperation(1, b) == -1)
						return -1;
//					balanceOperationsList.put(1, b);
				}
				// in case map is not empty
				else {
					BalanceOperation b = new BalanceOperationClass(LocalDate.now(), -(s.getPrice()), "RETURN");				
					int balanceId = balanceOperationsList.lastEntry().getKey() + 1;
					b.setBalanceId(balanceId);
					if (BalanceOperationDB.addBalanceOperation(balanceId, b) == -1)
						return -1;
//					balanceOperationsList.put(balanceId, b);
				}
		
		
		return res;
	}

	@Override
	public double returnCreditCardPayment(Integer returnId, String creditCard)
			throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {
		// Just check the creditCard, not storing it has the professor said on slack
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if (returnId == null || returnId <= 0 ) throw new InvalidTransactionIdException();
		if (LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(creditCard) == false) throw new InvalidCreditCardException();
		if (creditCard.isEmpty() == true || creditCard == null) throw new InvalidCreditCardException();
		
		shopBalance = computeBalance();
		SaleTransaction s = returnTransactionsList.get(returnId);
		if (s == null) return -1;
		double res = s.getPrice();
		if (shopBalance - res <= 0) return -1;
		
		// in case map is empty
		if (balanceOperationsList.isEmpty()) {
			BalanceOperation b = new BalanceOperationClass(LocalDate.now(), -(s.getPrice()), "RETURN");				
			b.setBalanceId(1);
			if (BalanceOperationDB.addBalanceOperation(1, b) == -1)
				return -1;
//			balanceOperationsList.put(1, b);
		}
		// in case map is not empty
		else {
			BalanceOperation b = new BalanceOperationClass(LocalDate.now(), -(s.getPrice()), "RETURN");				
			int balanceId = balanceOperationsList.lastEntry().getKey() + 1;
			b.setBalanceId(balanceId);
			if (BalanceOperationDB.addBalanceOperation(balanceId, b) == -1)
				return -1;
//			balanceOperationsList.put(balanceId, b);
		}
		
		return res;
	}

	@Override
	public boolean recordBalanceUpdate(double toBeAdded) throws UnauthorizedException {
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		
		shopBalance = computeBalance();
		if (shopBalance + toBeAdded < 0) return false;
		if (toBeAdded >= 0) {
			// in case map is empty
			if (balanceOperationsList.isEmpty()) {
				BalanceOperation b = new BalanceOperationClass(LocalDate.now(), toBeAdded, "CREDIT");				
				b.setBalanceId(1);
				if (BalanceOperationDB.addBalanceOperation(1, b) == -1)
					return false;
				balanceOperationsList.put(1, b);
				return true;
			}
			// in case map is not empty
			else {
				BalanceOperation b = new BalanceOperationClass(LocalDate.now(), toBeAdded, "CREDIT");				
				int balanceId = balanceOperationsList.lastEntry().getKey() + 1;
				b.setBalanceId(balanceId);
				if (BalanceOperationDB.addBalanceOperation(balanceId, b) == -1)
					return false;
				balanceOperationsList.put(balanceId, b);
				return true;
			}
	
		} else {
			// in case map is empty
			if (balanceOperationsList.isEmpty()) {
				BalanceOperation b = new BalanceOperationClass(LocalDate.now(), toBeAdded, "DEBIT");				
				b.setBalanceId(1);
				if (BalanceOperationDB.addBalanceOperation(1, b) == -1)
					return false;
				balanceOperationsList.put(1, b);
				return true;
			}
			// in case map is not empty
			else {
				BalanceOperation b = new BalanceOperationClass(LocalDate.now(), toBeAdded, "DEBIT");				
				int balanceId = balanceOperationsList.lastEntry().getKey() + 1;
				b.setBalanceId(balanceId);
				if (BalanceOperationDB.addBalanceOperation(balanceId, b) == -1)
					return false;
				balanceOperationsList.put(balanceId, b);
				return true;
			}
		}
	}

	@Override
	public List<BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to) throws UnauthorizedException {
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0)
			throw new UnauthorizedException();
		
		List<BalanceOperation> res = new ArrayList<BalanceOperation>();
		if (from == null && to == null) {
			res = new ArrayList<BalanceOperation>(balanceOperationsList.values());
			return res;
		}
		if (from != null && to == null) {
			TreeMap<Integer, BalanceOperation> oBalanceOperationsList = BalanceOperationDB.getBalanceOperationsByFrom(from);
			res = new ArrayList<BalanceOperation>(oBalanceOperationsList.values());
			return res;
		}
		if (from == null && to != null) {
			TreeMap<Integer, BalanceOperation> oBalanceOperationsList = BalanceOperationDB.getBalanceOperationsByTo(to);
			res = new ArrayList<BalanceOperation>(oBalanceOperationsList.values());
			return res;
		}
		// Check if from and to are correct, switch the values if not
		int c = from.compareTo(to);
		if (c > 0) {
			LocalDate tmp = from;
			from = to;
			to = tmp;
		}
		
		TreeMap<Integer, BalanceOperation> oBalanceOperationsList = BalanceOperationDB.getBalanceOperationsByDates(from, to);
		res = new ArrayList<BalanceOperation>(oBalanceOperationsList.values());
		
		return res;

	}

	@Override
	public double computeBalance() throws UnauthorizedException {
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		
		double res = 0;
		res = BalanceOperationDB.balanceSum();
		return res;
	}

	

    @Override
    public boolean recordOrderArrivalRFID(Integer orderId, String RFIDfrom) throws InvalidOrderIdException, UnauthorizedException, 
InvalidLocationException, InvalidRFIDException {
    	if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager")!=0)
			throw new UnauthorizedException();
		if (orderId == null || orderId <= 0)
			throw new InvalidOrderIdException();
		if(RFIDfrom==null) throw new InvalidRFIDException();
		if(RFIDfrom.isEmpty()) throw new InvalidRFIDException();
		if(!RFIDfrom.matches("^[0-9]{12}$")) throw new InvalidRFIDException();
		
		Order o = ordersList.get(orderId);
		if (o==null) return false;
		String productCode = o.getProductCode();
		ProductType p;
		try {
			p = getProductTypeByBarCode(productCode);
		} catch (InvalidProductCodeException e) {
			return false;
		}
		if (p.getLocation() == null)
			throw new InvalidLocationException();
		if (o.getStatus() == "COMPLETED")
			return true;
		
		if (OrderDB.updateOrderToCompleted(orderId) == -1 && BalanceOperationDB.updateBalanceToOrder(o.getBalanceId()) == -1)
			return false;
		
		//RFID 12 digit String to int
		Long tmp = Long.parseLong(RFIDfrom);

		
		for(int i=0;i<o.getQuantity();i++) {
			
			String res = String.format("%012d", tmp);
			tmp = tmp+1;
			ProductDB.addProduct(productCode, res);
			
		}
		
		o.setStatus("COMPLETED");
		int oldQ=p.getQuantity();
		p.setQuantity(oldQ+o.getQuantity());
		ordersList.remove(orderId);
		ordersList.put(orderId, o);


        return true;
    }
    

    @Override
    public boolean addProductToSaleRFID(Integer transactionId, String RFID) throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException{
    	/**
         * This method adds a product to a sale transaction receiving  its RFID, decreasing the temporary amount of product available on the
         * shelves for other customers.
         * It can be invoked only after a user with role "Administrator", "ShopManager" or "Cashier" is logged in.
         *
         * @param transactionId the id of the Sale transaction
         * @param RFID the RFID of the product to be added
         * @return  true if the operation is successful
         *          false   if the RFID does not exist,
         *                  if the transaction id does not identify a started and open transaction.
         *
         * @throws InvalidTransactionIdException if the transaction id less than or equal to 0 or if it is null
         * @throws InvalidRFIDException if the RFID code is empty, null or invalid
         * @throws UnauthorizedException if there is no logged user or if it has not the rights to perform the operation
         */
    	
    	
		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if ( transactionId == null || transactionId <= 0 ) throw new InvalidTransactionIdException();
		if(RFID==null) throw new InvalidRFIDException();
		if(RFID.isEmpty()) throw new InvalidRFIDException();
		if(!RFID.matches("^[0-9]{12}$")) throw new InvalidRFIDException();
		
		
		SaleTransaction s;
		s = saleTransactionsList.get(transactionId);
		if (s == null) return false;
		
//		productsList = ProductTypeDB.getProducts();
	
		ProductType p;
		ProductType p1;
		String pCode = ProductDB.getBarCode(RFID);
		try {
			p = getProductTypeByBarCode(pCode);
		} catch (InvalidProductCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if (p == null) return false;	
		System.out.println("p1 before: " + p);
		System.out.println("tempListBefore: " + tempProductsList);
		System.out.println("saleTransactionsListBefore: " + saleTransactionsList);
		
		for(Map.Entry<Integer,ProductType> entry : tempProductsList.entrySet()) {
//			  Integer key = entry.getKey();
			  p1 = entry.getValue();
			  if (p1.getBarCode().compareTo(pCode) == 0) {
					
				int quantity = p1.getQuantity();
				if (quantity < 1) return false;
				p1.setQuantity(quantity-1);
			  }

			}
		

//		ProductType p = p1;
//		int quantity = p.getQuantity();
//		if (quantity < amount) return false;
		System.out.println("p1 after: " + p);
		System.out.println("tempList product after: " + tempProductsList);
		System.out.println("saleTransactionsListAfter: " + saleTransactionsList);
		
		ArrayList<TicketEntry> entries = (ArrayList<TicketEntry>) s.getEntries();
		TicketEntry t = new TicketEntryClass(transactionId , pCode , p.getProductDescription(), 1, p.getPricePerUnit());
		System.out.println("new Ticket " + t);
		entries.add(t);
		s.setEntries(entries);
		s.setPrice(s.getPrice()+(1 * p.getPricePerUnit()));
//		p.setQuantity(quantity - amount);
		System.out.println("s " + s);
		System.out.println("lenght entries " + entries.size());
		System.out.println("lenght S.entries " + s.getEntries().size());
		return true;
	}

    
    

    @Override
    public boolean deleteProductFromSaleRFID(Integer transactionId, String RFID) throws InvalidTransactionIdException, InvalidRFIDException, InvalidQuantityException, UnauthorizedException{
    	/**
         * This method deletes a product from a sale transaction , receiving its RFID, increasing the temporary amount of product available on the
         * shelves for other customers.
         * It can be invoked only after a user with role "Administrator", "ShopManager" or "Cashier" is logged in.
         *
         * @param transactionId the id of the Sale transaction
         * @param RFID the RFID of the product to be deleted
         *
         * @return  true if the operation is successful
         *          false   if the product code does not exist,
         *                  if the transaction id does not identify a started and open transaction.
         *
         * @throws InvalidTransactionIdException if the transaction id less than or equal to 0 or if it is null
         * @throws InvalidRFIDException if the RFID is empty, null or invalid
         * @throws UnauthorizedException if there is no logged user or if it has not the rights to perform the operation
         */

		if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0)
			throw new UnauthorizedException();
		if ( transactionId == null || transactionId <= 0 ) throw new InvalidTransactionIdException();
		if(RFID==null) throw new InvalidRFIDException();
		if(RFID.isEmpty()) throw new InvalidRFIDException();
		if(!RFID.matches("^[0-9]{12}$")) throw new InvalidRFIDException();
		
		SaleTransaction s;
		s = saleTransactionsList.get(transactionId);
		if (s == null) return false;
		
		ProductType p;
		ProductType p1;
		String pCode = ProductDB.getBarCode(RFID);
		try {
			p = getProductTypeByBarCode(pCode);
		} catch (InvalidProductCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (UnauthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if (p == null) return false;
		

		

//		int quantity = p.getQuantity();
		ArrayList<TicketEntry> entries = (ArrayList<TicketEntry>) s.getEntries();
		TicketEntry t = new TicketEntryClass(transactionId, pCode, p.getProductDescription(), 1, p.getPricePerUnit());
		
		for (int i = 0; i < entries.size(); i++) {
			TicketEntry tmp  = entries.get(i);
			if (tmp.getBarCode().compareTo(pCode) == 0) {			
				t.setAmount(tmp.getAmount() - 1);
				entries.set(i, t);
				if (t.getAmount() <= 0) entries.remove(t);
			}
		}
		s.setPrice(s.getPrice()-(1 * p.getPricePerUnit()));
		
		for(Map.Entry<Integer,ProductType> entry : tempProductsList.entrySet()) {
//			  Integer key = entry.getKey();
			  p1 = entry.getValue();
			  if (p1.getBarCode().compareTo(pCode) == 0) {
					
				int quantity = p1.getQuantity();
//				if (quantity < amount) return false;
				p1.setQuantity(quantity+1);
			  }

			}
//		p.setQuantity(quantity + amount);
		
		return true;
    }

   

    @Override
    public boolean returnProductRFID(Integer returnId, String RFID) throws InvalidTransactionIdException, InvalidRFIDException, UnauthorizedException 
    {
    	if (loggedUser == null)
			throw new UnauthorizedException();
		if (loggedUser.getRole().compareTo("Administrator") != 0 && loggedUser.getRole().compareTo("ShopManager") != 0
				&& loggedUser.getRole().compareTo("Cashier") != 0) throw new UnauthorizedException();
			
		if(RFID==null) throw new InvalidRFIDException();
		if(RFID.isEmpty()) throw new InvalidRFIDException();
		if(!RFID.matches("^[0-9]{12}$")) throw new InvalidRFIDException();	
		if(returnId == null) throw new InvalidTransactionIdException();
		if(returnId <= 0) throw new InvalidTransactionIdException();
		
		SaleTransaction s = returnTransactionsList.get(returnId);
		if (s ==null) return false;
		String barCode=ProductDB.getBarCode(RFID);
		if(barCode == null) return false;
		
		
		ArrayList<TicketEntryClass> tickets = TicketEntryDB.getTickets(s.getTicketNumber());
		TicketEntry t;
		for (int i = 0; i < tickets.size(); i++) {
			t = tickets.get(i);
			if (t.getBarCode().contains(barCode)) {
				s.getEntries().add(t);
				return true;
			}
		}
        return false;
    }


   
}
