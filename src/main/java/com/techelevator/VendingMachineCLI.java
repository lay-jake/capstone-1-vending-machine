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
				printReport();
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
				} catch (NumberFormatException ex){
					//Try catch in case user enters dollars and cents/not whole bills
					System.out.println("Please only insert whole bills.");
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
				printFormattedChange(quarterCount,dimeCount,nickleCount,pennyCount);
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
	public static void printReport(){
		//Method to print report in hidden 4th option
		//Iterates through map of sales initialized at boot
		Map<String,Integer> report = Accounting.getLogRecord();
		for (String s : Accounting.getLogRecord().keySet()) {
			System.out.printf("%-20s%-2s%d\r\n",s,"|",report.get(s));
		}
		System.out.printf("\r\n%s%.2f\r\n","Total Money Made: $",Accounting.getTotalSales());
	}
	public static void printFormattedChange(int quarters, int dimes,int nickles, int pennies){
		//Final variable for the window size
		//center is 15 units, longest line we want is 36 units.
		final int centerValue = 15;
		final int totalStringLength = 36;

		//Getting the lengths for each number value so we can indent the line to keep it center, passing false since we want to control the indent in the method.
		int lengthQuarter = centerValue - lineLength(quarters,false,true);
		int lengthDimes = (centerValue + 1) - lineLength(dimes,false,false); // adding 1 to center value account for Dimes being a shorter word then rest
		int lengthNickles = centerValue - lineLength(nickles,false,false);
		int lengthPennies = centerValue - lineLength(pennies,false,false);

		final String dashedLine ="~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
		System.out.println(dashedLine);
		System.out.printf("*%11s%s%12s","","Your change is","*\r\n");

		//Printing to console the information; spacing for lengths above, and then calculating where the ending * needs to be placed using lineLength and passing true
		System.out.printf("*%" + lengthQuarter + "s%d Quarters %" + (totalStringLength - centerValue - ((lineLength(quarters,true,true))/2) - 10) +"s"+"\r\n","",quarters,"*");
		System.out.printf("*%" + lengthDimes + "s%d Dimes %" + (totalStringLength - centerValue - (lineLength(dimes,true,false)/2) - 9) +"s"+"\r\n","",dimes,"*"); // accounting for the 1 to center value from length
		System.out.printf("*%" + lengthNickles + "s%d Nickles %" + (totalStringLength - centerValue - (lineLength(nickles,true,false)/2) - 10) +"s"+"\r\n","",nickles,"*");
		System.out.printf("*%" + lengthPennies + "s%d Pennies %" + (totalStringLength - centerValue - (lineLength(pennies,true,false)/2) - 10) +"s"+"\r\n","",pennies,"*");
		System.out.println(dashedLine);
	}
	public static int lineLength(int currency, boolean trueLength,Boolean isQuarter){
		//if trueLength, we just return the length of the String of digits, else we space either its length, or 6/4 depending on length.
		// if it is a quarter, and 1 in length (0-9 quarters) int division causes weird spacing that doesn't happen with the other currencies
		// So we return 2 to get rid of the weird spacing if it is 0 or 1 and is a quarter only.
		int length = String.valueOf(currency).length();
		if ( length >8 && !trueLength){
			return 6;
		}
		else if (length > 4 && !trueLength){
			return  4;
		}
		else{
			if(isQuarter && trueLength && length < 2){
				return 2;
			}
			return length;
		}
	}
}

