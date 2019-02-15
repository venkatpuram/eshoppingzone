package com.eshoppingzone.eshoppingzone.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.eshoppingzone.eshoppingzone.pojo.Address;
import com.eshoppingzone.eshoppingzone.pojo.Cart;
import com.eshoppingzone.eshoppingzone.pojo.Ewallet;
import com.eshoppingzone.eshoppingzone.pojo.Items;
import com.eshoppingzone.eshoppingzone.pojo.Orders;
import com.eshoppingzone.eshoppingzone.pojo.Product;
import com.eshoppingzone.eshoppingzone.pojo.UserProfile;

@Controller
public class HomeController {

	@Autowired
	private RestTemplate restTemplate;

	private static UserProfile currentProfile = new UserProfile();

	private static Address staticAddress = new Address();

	private Product product;

	@RequestMapping("/")
	public String home() {
		return "index";
	}

	@RequestMapping("/signin")
	public String loginForm() {
		return "login";
	}

	@RequestMapping("/signup")
	public String signUp() {
		return "signup";
	}

	@RequestMapping("/profilecreation")
	public String creatingProfile(@RequestParam("mobilenumber") Long mobilenumber,
			@RequestParam("email") String emailId, @RequestParam("password") String password) {
		UserProfile profile = new UserProfile();
		profile.setMobileNumber(mobilenumber);
		profile.setEmailId(emailId);
		profile.setPassword(password);
		UserProfile newProfile = restTemplate.postForObject("http://localhost:8080/profiles/customers", profile,
				UserProfile.class);
		System.out.println(newProfile);
		restTemplate.postForEntity("http://localhost:9091/carts/" + newProfile.getProfileId(), null, Cart.class);
		restTemplate.postForEntity("http://localhost:8426/wallets/" + newProfile.getProfileId(), null, null);
		return "login";
	}

	@RequestMapping(value = "/loginmanually", method = RequestMethod.POST)
	public String profileLogin(@RequestParam("mobilenumber") Long mobilenumber,
			@RequestParam("password") String password) {
		UserProfile profile = restTemplate.getForObject("http://localhost:8080/profiles/mobile/" + mobilenumber,
				UserProfile.class);

		currentProfile.setProfileId(profile.getProfileId());
		System.out.println(currentProfile.getProfileId());
		System.out.println(profile.getPassword() + " password in if:" + password);
		System.out.println(profile.getMobileNumber() + " mobile:" + mobilenumber);
		if ((profile.getMobileNumber().equals(mobilenumber)) && (profile.getPassword().equals(password)))
			return "index";
		else
			return "login";

	}

	@RequestMapping("/searchproduct")
	public String searchProduct(@RequestParam("search") String search, Model model) {
		Product entity = restTemplate.getForObject("http://localhost:8989/products/productname/" + search,
				Product.class);
		return "index";
	}

	@RequestMapping("/getProductsByCategory")
	public ModelAndView electronicsCategory(@RequestParam("category") String category) {
		List productsList = restTemplate.getForObject("http://localhost:8989/products/category/" + category,
				List.class);
		return new ModelAndView("ProductsByCategory", "product", productsList);
	}

	@RequestMapping("/shoppingcart")
	public ModelAndView shoppingCart() {
		System.out.println("value:" + currentProfile.getProfileId());
		Cart cart = restTemplate.getForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(),
				Cart.class);
		return new ModelAndView("cart", "cart", cart);
	}

	@RequestMapping("/addToCart")
	public ModelAndView addToCart(@RequestParam("productId") int productId) {
		boolean isItemPresent = false;
		System.out.println("productId" + productId);
		Product product = restTemplate.getForObject("http://localhost:8989/products/" + productId, Product.class);
		String productName = product.getProductName();
		double productPrice = product.getPrice();
		Items items = new Items();
		items.setProductName(productName);
		items.setPrice(productPrice);
		items.setQuantity(1);

		Cart cart = restTemplate.getForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(),
				Cart.class);
		List<Items> itemsInCart = cart.getItems();

		if (itemsInCart == null)
			itemsInCart = new ArrayList<>();

		for (Items item : itemsInCart) {
			if (item.getProductName().equalsIgnoreCase(productName)) {
				item.setQuantity(1 + item.getQuantity());
				cart.setTotalPrice(cart.getTotalPrice() + (productPrice * 1));
				isItemPresent = true;
				break;
			}
		}
		if (isItemPresent == false) {
			itemsInCart.add(items);
			cart.setTotalPrice(cart.getTotalPrice() + (productPrice * 1));
		}

		cart.setItems(itemsInCart);

		restTemplate.put("http://localhost:9091/carts", cart);

		return new ModelAndView("cartDetails", "cart", cart);
	}

	@RequestMapping("/remove")
	public ModelAndView removeProduct(@RequestParam("quantity") int quantity,
			@RequestParam("productName") String productName) {
		System.out.println("ProductName is : " + productName);
		System.out.println("Quantity is : " + quantity);
		Cart cart = restTemplate.getForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(),
				Cart.class);
		List<Items> itemsInCart = cart.getItems();
		System.out.println("Items present now" + itemsInCart);
		for (Items item : itemsInCart) {
			if (item.getProductName().equals(productName)) {
				System.out.println("Match Found !!!");
				itemsInCart.remove(item);
				System.out.println("Items present after removing" + itemsInCart);
				break;
			}
		}
		restTemplate.put("http://localhost:9091/carts", cart);
		return new ModelAndView("cart", "cart", cart);
	}

	@RequestMapping("/removeProduct")
	public String addressForm(@RequestParam("productName") String[] productName,
			@RequestParam("quantity") int[] quantity, @RequestParam("price") double[] price) {
		for (int i = 0; i < productName.length; i++) {
			System.out.println("Products" + productName[i]);
		}

		List<Items> newList = new ArrayList<Items>();
		

		for (int i = 0; i < productName.length; i++) {
			Items item = new Items();
			item.setProductName(productName[i]);
			item.setPrice(price[i]);
			item.setQuantity(quantity[i]);
			System.out.println("qty" + quantity[i] + "Price" + price[i] + "Products" + productName[i]);
			newList.add(item);
		}
		
		System.out.println("NewList" + newList);
		Cart emptyCart = new Cart();
		restTemplate.postForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(), emptyCart,
				Cart.class);
		Cart cart = restTemplate.getForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(),
				Cart.class);
		cart.setItems(newList);
		restTemplate.put("http://localhost:9091/carts", cart);
		/*
		 * Cart emptyCart= new Cart();
		 * 
		 * restTemplate.postForObject("http://localhost:9091/carts/" +
		 * currentProfile.getProfileId(), emptyCart, Cart.class);
		 */

		/*
		 * List<Items> items = cart.getItems(); for (int i = 0; i < productName.length;
		 * i++) for (Items items2 : items) { if
		 * (!(items2.getProductName().equalsIgnoreCase(productName[i]) &&
		 * items2.getQuantity() == quantity[i])) {
		 * System.out.println("Details are not same"); } else
		 * System.out.println("Details are  same"); }
		 */
		return "address";
	}

	@RequestMapping("/address")
	public ModelAndView checkout(@ModelAttribute Address address) {
		Cart cart = restTemplate.getForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(),
				Cart.class);
		int customerId = cart.getCartId();
		address.setCustomerId(customerId);
		staticAddress = address;
		restTemplate.postForEntity("http://10.246.92.126:9090/orders/address", address, null);
		Address confirmAddress = address;
		return new ModelAndView("placeorder", "address", confirmAddress);
	}

	@RequestMapping("/payment")
	public ModelAndView paymentMode(@RequestParam("customerId") int customerId) {

		return new ModelAndView("paymentmode", "customerId", customerId);
	}

	@RequestMapping("/codpayment")
	public ModelAndView cashOnDelivery() {
		Cart cart = restTemplate.getForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(),
				Cart.class);
		restTemplate.postForObject("http://10.246.92.126:9090/orders", cart, Cart.class);
		Orders order = restTemplate.getForObject("http://10.246.92.126:9090/orders/orderId", Orders.class);
		Cart emptyCart = new Cart();
		restTemplate.postForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(), emptyCart,
				Cart.class);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("orders", order);
		model.put("useraddress", staticAddress);
		return new ModelAndView("ordersummary", "order", model);
	}

	@RequestMapping("/walletpayment")
	public ModelAndView proceedToPay() {
		Double wallet = restTemplate.getForObject("http://localhost:8426/wallets/" + currentProfile.getProfileId(),
				Double.class);
		// System.out.println("wallet:"+wallet);
		// wallet.getCurrentBalance();
		return new ModelAndView("ewallet", "wallet", wallet);
	}

	@RequestMapping("/paymoney")
	public ModelAndView paymoney() {
		Cart cart = restTemplate.getForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(),
				Cart.class);
		// System.out.println(cart);
		double totalPrice = cart.getTotalPrice();
		Double wallet = restTemplate.getForObject("http://localhost:8426/wallets/" + currentProfile.getProfileId(),
				Double.class);
		double walletBalance = wallet;
		if (walletBalance > totalPrice) {
			restTemplate.postForObject("http://10.246.92.126:9090/orders", cart, Cart.class);
			Orders order = restTemplate.getForObject("http://10.246.92.126:9090/orders/orderId", Orders.class);
			System.out.println(order);
			int orderId = order.getOrderId();
			restTemplate.put("http://localhost:8426/wallets/pay/" + currentProfile.getProfileId() + "?currentBalance="
					+ totalPrice + "&orderId=" + orderId, null);
			Double updatedBalance = restTemplate
					.getForObject("http://localhost:8426/wallets/" + currentProfile.getProfileId(), Double.class);
			// System.out.println("balance:"+updatedBalance);
			Cart emptyCart = new Cart();
			// List<Address>
			// address=restTemplate.getForObject("http://10.246.92.126:9090/orders/"+currentProfile.getProfileId(),
			// List.class);
			restTemplate.postForObject("http://localhost:9091/carts/" + currentProfile.getProfileId(), emptyCart,
					Cart.class);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("orders", order);
			model.put("useraddress", staticAddress);
			return new ModelAndView("ordersummary", "order", model);
		} else
			return new ModelAndView("addmoney");
	}

	@RequestMapping("/addmoney")
	public ModelAndView addMoneyToWallet(@RequestParam("money") double money) {
		restTemplate.put("http://localhost:8426/wallets/" + currentProfile.getProfileId() + "?currentBalance=" + money,
				null);
		Double updatedBalance = restTemplate
				.getForObject("http://localhost:8426/wallets/" + currentProfile.getProfileId(), Double.class);
		System.out.println("balance:" + updatedBalance);
		return new ModelAndView("ewallet", "wallet", updatedBalance);
	}
}
