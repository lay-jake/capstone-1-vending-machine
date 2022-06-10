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
	private static final String SALES_REPORT = "";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_EXIT, SALES_REPORT };
	private static final String[] PURCHASE_OPTIONS = { PURCHASE_MENU_FEED, PURCHASE_MENU_SELECT, PURCHASE_MENU_FIN };
	private final Menu menu;
	//Restocks inventory on boot.
	public Inventory stock = new Inventory();

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {
		Accounting.initializeReport();
		while (true) {
			//Prompt user to pick menu option
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				stock.printInventory();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				//display purchase menu
				purchaseMenuOptions();
			} else if (choice.equals(MAIN_MENU_EXIT)){
				//Exit program
				System.exit(0);
			} else if (choice.equals(SALES_REPORT)){
				//some output print for report
			}
		}
	}
	public void purchaseMenuOptions() {
		//Loops while purchasing is true;
		boolean isPurchasing = true;
		while (isPurchasing) {
			//Prints current available balance and prompts to pick from purchase menu
			System.out.println();
			System.out.printf("%s%.2f", "Current Money Provided: $", Accounting.getCustomerMoney());
			System.out.println();
			String choice = (String) menu.getChoiceFromOptions(PURCHASE_OPTIONS);

			//if customer chooses to feed money, prompts to feed, splits into array and adds to balance
			if (choice.equals(PURCHASE_MENU_FEED)) {
				Scanner userFeed = new Scanner(System.in);

				try {
					System.out.print("Please feed in money: ");
					String[] feedArray = userFeed.nextLine().split(" ");
					System.out.println();
					for (String s : feedArray) {
						//iterate through split array to add to balance
						Accounting.feedMoney(Integer.parseInt(s));
					}
				} catch (NumberFormatException ex){
					//Try catch in case user enters dollars and cents/not whole bills
					System.out.println("Please only insert whole bills.");
				}
			} else if (choice.equals(PURCHASE_MENU_SELECT)) {
				//If customer chooses to purchase item prints menu and prompts for item.
				stock.printInventory();
				Scanner userFeed = new Scanner(System.in);
				//Ignores cases because there are only so many buttons.
				Inventory.getItem(userFeed.nextLine().trim().toUpperCase());
			} else if (choice.equals(PURCHASE_MENU_FIN)){
				isPurchasing=false;
				//sends back to main menu and prints change
				int[] change = Accounting.giveChange();
					System.out.printf("Your change is %d Quarters, %d Dimes, %d Nickles, and %d pennies. \n",change[0],change[1],change[2],change[3]);
					Accounting.setIsFirstPurchase(true);
					Accounting.setIsFirstCustomer(false);
				}
			}
		}


	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
