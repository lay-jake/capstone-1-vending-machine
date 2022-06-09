package com.techelevator;

import com.techelevator.view.Accounting;
import com.techelevator.view.Inventory;
import com.techelevator.view.Menu;

import java.util.Scanner;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_EXIT = "Exit";
	private static final String PURCHASE_MENU_FEED = "Feed Money";
	private static final String PURCHASE_MENU_SELECT = "Select Product";
	private static final String PURCHASE_MENU_FIN = "Finish Transaction";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_EXIT };
	private static final String[] PURCHASE_OPTIONS = { PURCHASE_MENU_FEED, PURCHASE_MENU_SELECT, PURCHASE_MENU_FIN };
	private Menu menu;
	public Inventory stock = new Inventory();

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				stock.printInventory();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				purchaseMenuOptions();
			} else if (choice.equals(MAIN_MENU_EXIT)){
				System.exit(0);
			}
		}
	}
	public void purchaseMenuOptions(){
		System.out.println();
		System.out.printf("%s%.2f","Current Money Provided: $",Accounting.getCustomerMoney());
		System.out.println();
		String choice = (String) menu.getChoiceFromOptions(PURCHASE_OPTIONS);

		if (choice.equals(PURCHASE_MENU_FEED)){
			Scanner userFeed = new Scanner(System.in);
			System.out.print("Please feed in money: ");
			String [] feedArray = userFeed.nextLine().split(" ");
			System.out.println();
			for (String s : feedArray) {
				Accounting.feedMoney(Integer.parseInt(s));
			}
		} else if (choice.equals(PURCHASE_MENU_SELECT)){
			stock.printInventory();
			Scanner userFeed = new Scanner(System.in);
			Inventory.getItem(userFeed.nextLine().trim().toUpperCase());
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
