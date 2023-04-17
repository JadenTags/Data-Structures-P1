//IMPORTS TEEHEE
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class Runner {
    //Creates scanner for inputs and myShip for later use
    private static Scanner scanner = new Scanner(System.in);
    public static Spaceship myShip = new Spaceship(true, true);

    public static void main(String[] args) {
        //Creates a new spaceship
        Spaceship millenniumFalcon = new Spaceship();

        //Toggles invisbility and shields to demonstrate that it has neither
        millenniumFalcon.toggleInvisibility();
        millenniumFalcon.toggleShields();

        //Shoots multiple times to show the different notifications for when shots max is reached
        millenniumFalcon.shoot();
        millenniumFalcon.shoot();
        millenniumFalcon.shoot();
        millenniumFalcon.shoot();
        millenniumFalcon.shoot();

        //Travels to coordinates
        millenniumFalcon.travel(-7, 10000);

        //Gets status of ship
        millenniumFalcon.getShipStatus();

        //Sleeps for 15 seconds to allow the ship to finish cooling down and to lose more power
        try {
            TimeUnit.SECONDS.sleep((long) 15);
        } catch (InterruptedException e) {
        }

        //Recharges the ship
        millenniumFalcon.rechargeShip();
        
        //Creates a new spaceship with shields and invisibility
        Spaceship betterSpaceship = new Spaceship(true, true);

        //Toogles both invisibility and shields twice
        betterSpaceship.toggleInvisibility();
        betterSpaceship.toggleInvisibility();
        betterSpaceship.toggleShields();
        betterSpaceship.toggleShields();

        //Notifies the user of commands
        System.out.println("\n You may now use commands to control your own ship!");
        System.out.println("Commands: shoot, travel, rechargeShip, toggleShields, toggleInvisibility, getShipStatus");
        
        //Infinitely loops
        while (true) {
            //Seperates the lines
            System.out.println("\n");
            //Gets the next user input
            String command = scanner.nextLine();
            
            //Uses user input to decide which command to execute
            if (command.equals("shoot")) {
                myShip.shoot();
            } else if (command.matches("travel(|.*|)")) {
                //Creates an arraylist to store coordinates
                ArrayList<Integer> coords = new ArrayList<Integer>();

                //Sorts through the coordinates using regex to find all of the numbers and adds them to the coords arraylist
                for (String coord: command.split("[\\D*]")) {
                    if (!coord.equals("")) {
                        coords.add(Integer.parseInt(coord));
                    }
                }

                //Checks whether the right amount of numbers are in the command
                if (coords.size() == 2) {
                    //If so, executes the travel method with given numbers
                    myShip.travel(coords.get(0), coords.get(1));
                } else {
                    //Else, notifies the user
                    System.out.println("You did not input two numbers!");
                }
            } else if (command.equals("rechargeShip")) {
                myShip.rechargeShip();
            } else if (command.equals("toggleShields")) {
                myShip.toggleShields();
            } else if (command.equals("toggleInvisibility")) {
                myShip.toggleInvisibility();
            } else if (command.equals("getShipStatus")) {
                myShip.getShipStatus();
            } else {
                System.out.println("That is not a command!");
            }
        }
    }
}