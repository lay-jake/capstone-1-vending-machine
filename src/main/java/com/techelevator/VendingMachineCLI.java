package com.techelevator;

import com.techelevator.swingImplementation.DisplayFrame;
import com.techelevator.view.Accounting;
import com.techelevator.view.Inventory;
import com.techelevator.view.Item;
import com.techelevator.view.Menu;

import java.sql.SQLOutput;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_EXIT = "Exit";
	private static final String PURCHASE_MENU_FEED = "Feed Money";
	private static final String PURCHASE_MENU_SELECT = "Select Product";
	private static final String PURCHASE_MENU_FIN = "Finish Transaction";
	private static final String SALES_REPORT = "";
	private static final String SWING_IMPLEMENTATION = " ";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_EXIT, SALES_REPORT, SWING_IMPLEMENTATION};
	private static final String[] PURCHASE_OPTIONS = { PURCHASE_MENU_FEED, PURCHASE_MENU_SELECT, PURCHASE_MENU_FIN };
	private final Menu menu;

	//Restocks and loads into the stock variable the inventory on boot from the Inventory.INVENTORY_PATH var.
	public Inventory stock = new Inventory();

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {
		//On boot will run the specified method which checks if a sales log exists, if it doesn't
		//then it will construct a default one with no values.
		Accounting.initializeReport();
		while (true) {
			//Prompt user to pick menu option
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			//Could be a switch case, but default if/else causes loop to main menu
			//after a transaction is completed which is expected behaviour
			//so we do not use a switch because it would be more work.
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				//Calls the printInventory Method to prick the inventory.
				stock.printInventory();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				//Move into the purchase menu and its options
				purchaseMenuOptions();
			} else if (choice.equals(MAIN_MENU_EXIT)){
				//Exit program
				System.exit(0);
			} else if (choice.equals(SALES_REPORT)){
				//Hidden option to print the sales log that has been kept.
				Accounting.printReport();
			} else if (choice.equals(SWING_IMPLEMENTATION)){
				//Hidden option to enable Swing GUI we have been testing.
				DisplayFrame frame = new DisplayFrame();
			}
		}
	}
	public void purchaseMenuOptions() {
		//Loops while purchasing is true;
		//Purchasing is set to false once user chooses finishes the transaction.
		boolean isPurchasing = true;
		while (isPurchasing) {
			//Prints current available balance and prompts to pick from purchase menu
			System.out.println();
			System.out.printf("%s%.2f", "Current Money Provided: $", Accounting.getCustomerMoney());
			System.out.println();
			String choice = (String) menu.getChoiceFromOptions(PURCHASE_OPTIONS);

			if (choice.equals(PURCHASE_MENU_FEED)) {
				Scanner userFeed = new Scanner(System.in);

				try {
					//if customer chooses to feed money, prompts to feed, splits into array if the user adds more
					//than a single bill and adds to balance. I.E - "1 2 5", "6", and "8 9 10" would all be valid inputs.
					System.out.print("Please feed in money: ");
					String[] feedArray = userFeed.nextLine().split(" ");
					System.out.println();
					for (String s : feedArray) {
						//iterate through the split array generated above to add to balance.
						//if only one feed entered will just go through element 0.
						Accounting.feedMoney(Integer.parseInt(s));
					}
				} catch (NumberFormatException ex) {

					if (ex.getMessage().length() > 28) {
						System.out.println("You have entered too much money, moneybags.");
					}

					//Try catch in case user enters dollars and cents/not whole bills
					else {
						System.out.println("Please only insert whole dollar bills.");
					}
				}
			} else if (choice.equals(PURCHASE_MENU_SELECT)) {
				//If customer chooses to purchase item prints menu and prompts for item.
				stock.printInventory();
				Scanner userFeed = new Scanner(System.in);
				//Ignore cases because there are only so many buttons.
				System.out.print("\r\nPlease enter your selection: ");
				Inventory.getItem(userFeed.nextLine().trim().toUpperCase());

			} else if (choice.equals(PURCHASE_MENU_FIN)){
				isPurchasing=false;
				//sends back to main menu and prints change
				//giveChange returns array as quarters/dimes/nickles/pennies
				int[] change = Accounting.giveChange();
				int quarterCount = change[0];
				int dimeCount = change[1];
				int nickleCount = change[2];
				int pennyCount = change[3];
				//call to printFormattedChange method, which will take the information and try to print it in a
				//formatted even block with equal spacing.
				Accounting.printFormattedChange(quarterCount,dimeCount,nickleCount,pennyCount);
				//sets first purchase back to true since it will be first purchase for new customer.
				Accounting.setIsFirstPurchase(true);
				}
			}
		}


	public static void main(String[] args) {
		//Establish Menu class;
		Menu menu = new Menu(System.in, System.out);
		//Establish CLI vending machine class, using the above menu in the constructor
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}

