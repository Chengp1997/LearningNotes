package business.order;

import api.ApiException;
import business.BookstoreDbException;
import business.JdbcUtils;
import business.book.Book;
import business.book.BookDao;
import business.book.BookForm;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import business.customer.Customer;
import business.customer.CustomerDao;
import business.customer.CustomerForm;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {

	private BookDao bookDao;

	private OrderDao orderDao;

	private LineItemDao lineItemDao;

	private CustomerDao customerDao;

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void setLineItemDao(LineItemDao lineItemDao) {
		this.lineItemDao = lineItemDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public OrderDetails getOrderDetails(long orderId) {
		Order order = orderDao.findByOrderId(orderId);
		Customer customer = customerDao.findByCustomerId(order.getCustomerId());
		List<LineItem> lineItems = lineItemDao.findByOrderId(orderId);
		List<Book> books = lineItems
				.stream()
				.map(lineItem -> bookDao.findByBookId(lineItem.getBookId()))
				.collect(Collectors.toList());
		return new OrderDetails(order, customer, lineItems, books);
	}

	@Override
    public long placeOrder(CustomerForm customerForm, ShoppingCart cart) {

		validateCustomer(customerForm);
		validateCart(cart);
		System.out.println("DefaultOrderService: start placing order");
		try (Connection connection = JdbcUtils.getConnection()) {
			Date date = getDate(
					customerForm.getCcExpiryMonth(),
					customerForm.getCcExpiryYear());
			return performPlaceOrderTransaction(
					customerForm.getName(),
					customerForm.getAddress(),
					customerForm.getPhone(),
					customerForm.getEmail(),
					customerForm.getCcNumber(),
					date, cart, connection);
		} catch (SQLException e) {
			throw new BookstoreDbException("Error during close connection for customer order", e);
		}

	}

	private Date getDate(String monthString, String yearString) {
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM");
		String s= yearString+"-"+monthString;
		try {
			Date date =  formatter.parse(s);
			return date;
		} catch (ParseException e) {
			throw new RuntimeException("error parsing date");
		}
	}

	private long performPlaceOrderTransaction(
			String name, String address, String phone,
			String email, String ccNumber, Date date,
			ShoppingCart cart, Connection connection) {
		try {
			connection.setAutoCommit(false);
			long customerId = customerDao.create(
					connection, name, address, phone, email,
					ccNumber, date);
			long customerOrderId = orderDao.create(
					connection,
					cart.getComputedSubtotal() + cart.getSurcharge(),
					generateConfirmationNumber(), customerId);
			for (ShoppingCartItem item : cart.getItems()) {
				lineItemDao.create(connection, item.getBookId(), customerOrderId,  item.getQuantity());
			}
			connection.commit();
			return customerOrderId;
		} catch (Exception e) {
			try {
				System.out.println("rolling back!");
				connection.rollback();
			} catch (SQLException e1) {
				throw new BookstoreDbException("Failed to roll back transaction", e1);
			}
			return 0;
		}
	}

	private int generateConfirmationNumber() {
		Random random = new Random();
		int confirmationNumber = random.nextInt(999999999);
		return confirmationNumber;
	}

	private void validateCustomer(CustomerForm customerForm) {

    	String name = customerForm.getName();
		if (name == null || name.equals("") || !checkNameLength(name)) {
			throw new ApiException.InvalidParameter("Invalid name field");
		}

		//address
		String address = customerForm.getAddress();
		if(address == null || address.equals("") || !checkAddressLength(address)){
			throw new ApiException.InvalidParameter("Invalid address field");
		}

		//phone
		String phone = customerForm.getPhone();
		if (phone == null || phone.equals("")||phone.length()==0|| !checkPhone(phone)){
			throw new ApiException.InvalidParameter("Invalid phone field");
		}

		//email
		String email = customerForm.getEmail();
		if (email == null || email.equals("")||!checkEmail(email)){
			throw new ApiException.InvalidParameter("Invalid email field");
		}

		//credit card number
		String creditCardNumber = customerForm.getCcNumber();
		if(creditCardNumber==null || creditCardNumber.equals("")
				||creditCardNumber.length()==0|| !checkCreditCard(creditCardNumber)){
			throw new ApiException.InvalidParameter("Invalid credit card number");
		}

		String month = customerForm.getCcExpiryMonth();
		String year = customerForm.getCcExpiryYear();
		if (month==null||month.equals("")
				||year==null||year.equals("")
				||expiryDateIsInvalid(customerForm.getCcExpiryMonth(), customerForm.getCcExpiryYear())) {
			throw new ApiException.InvalidParameter("Invalid expiry date");
		}
	}

	private boolean checkNameLength(String name){
		int length = name.length();
		return length>=4 && length<=45;
	}

	private boolean checkAddressLength(String address){
		int length = address.length();
		return length>=4 && length<=45;
	}

	private boolean checkPhone(String phone){
		StringBuilder p = new StringBuilder();
		for (char c: phone.toCharArray()){
			if(Character.isDigit(c)){
				p.append(c);
			}else if (c==' '||c=='-'||c=='('||c==')'){
				continue;
			}else {
				return false;
			}
		}
		return p.length()==10;
	}

	private boolean checkEmail(String email){
		String trimString = email.trim();
		if(trimString.length()!=email.length())return false;

		int atCount = 0;
		for (char c: email.toCharArray()){
			if (c==' ')return false;
			if( c=='@') atCount++;
		}
		return atCount==1 && email.charAt(email.length()-1)!='.';
	}

	private boolean checkCreditCard(String creditCardNumber){
		StringBuilder ccn  = new StringBuilder();
		for (char c: creditCardNumber.toCharArray()){
			if (Character.isDigit(c)){
				ccn.append(c);
			}else if (c==' '||c=='-'){
				continue;
			}else {
				return false;
			}
		}
		return ccn.length()<=16 && ccn.length()>=14;
	}

	private boolean expiryDateIsInvalid(String ccExpiryMonth, String ccExpiryYear) {
		LocalDate today = LocalDate.now();
		int month = today.getMonthValue();
		int year = today.getYear();
		int expMonth = Integer.parseInt(ccExpiryMonth);
		int expYear = Integer.parseInt(ccExpiryYear);
		//valid
		return (expYear != year || expMonth < month|| expMonth>12||expMonth<0 ||expYear<0) && expYear <= year;

	}

	private void validateCart(ShoppingCart cart) {

		if (cart.getItems().size() <= 0) {
			throw new ApiException.InvalidParameter("Cart is empty.");
		}

		cart.getItems().forEach(item-> {
			if (item.getQuantity() < 0 || item.getQuantity() > 99) {
				throw new ApiException.InvalidParameter("Invalid quantity");
			}
			Book databaseBook = bookDao.findByBookId(item.getBookId());
			BookForm cartBook = item.getBookForm();
			if (databaseBook == null ||
					databaseBook.getPrice()!=cartBook.getPrice() ||
					databaseBook.getCategoryId()!=cartBook.getCategoryId()) {
				throw new ApiException.InvalidParameter("Invalid price");
			}
		});
	}

}
