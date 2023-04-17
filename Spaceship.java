//Imports TimeUnit for sleep and Random for RNG
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Spaceship {
    //Creates a new Random object for the spaceship to use
    private Random random = new Random();

    //Declares and initializes the spaceship's status attributes
    private int powerPercent = 100;
    private int[] coordinates = new int[] {0, 0};
    private boolean isCooling = false;
    private boolean isShielded = false;
    private boolean isInvisible = false;
    private int shots = 0;

    //Declaring the attribute variables of the ship that decide its capabilities
    private boolean hasShields;
    private boolean hasInvisibility;

    //Constructor that takes no arguments, sets shields and invisibility to off and then begins decrementing power and cycle refreshing shots
    public Spaceship() {
        //Sets attributes to false
        hasShields = false;
        hasInvisibility = false;

        //Uses thread for asynchronous code and decrements power
        new Thread(() -> {
            decrementPower();
        }).start();

        //Uses thread for asynchronous code and cycle refreshes shots
        new Thread(() -> {
            cycleRefreshShots();
        }).start();
    }

    //Constructor that takes shields and invisibility and sets the variables to the argument and then begins decrementing power and cycle refreshing shots
    public Spaceship(boolean hasShields, boolean hasInvisibility) {
        //Sets attributes to arguments
        this.hasShields = hasShields;
        this.hasInvisibility = hasInvisibility;

        //Uses thread for asynchronous code and decrements power
        new Thread(() -> {
            decrementPower();
        }).start();

        //Uses thread for asynchronous code and cycle refreshes shots
        new Thread(() -> {
            cycleRefreshShots();
        }).start();
    }

    //Decrements power to mimic a spaceship using its power up
    //Ran asynchronously so that the while loop can run alongside other functions
    private void decrementPower() {
        //Loops until all power is lost
        while (powerPercent > 0) {
            //Uses RNG to sleep for a random number of seconds from 0 to 2
            try {
                TimeUnit.SECONDS.sleep((long) random.ints(0, 2).findFirst().getAsInt());
            } catch (InterruptedException e) {
            }

            //POWER PERCENT TRACKER | UNCOMMENT IF WANT TO CHECK POWER PERCENT DECREASE
            // System.out.println("\nPower Percent: " + powerPercent);
            //Decreases powerPercent by 1
            powerPercent--;
        }
    }

    //Cycle refreshes shots and works alongside shoot function to ensure that a maximum amount of shots isn't shot in a certain time frame
    //Sets shots to 0 every 5 seconds
    //Ran asynchronously so that the while loop can run alongside other functions
    private void cycleRefreshShots() {
        //Loops infinitely
        while (true) {
            //Sleeps for 5 seconds
            try {
                TimeUnit.SECONDS.sleep((long) 5);
            } catch (InterruptedException e) {
            }

            //Sets shots to 0
            shots = 0;
        }
    }

    //Shoots
    public void shoot() {
        //Checks whether spaceship has power or not
        if (powerPercent > 0) {
            //Checks whether spaceship is cooling or not
            if (isCooling) {
                //Prints notification to remind user that ship is cooling down
                System.out.println("\nCooling Down!");
            //Checks whether the max amount of shots have been shot before shots are refreshed
            } else if (shots >= 3) {
                //Prints notification to let user know that they have shot too quickly and the ship is now cooling
                System.out.println("\nYou Have Shot Too Quickly! Cooling Down!");

                //Sets isCooling to true
                isCooling = true;

                //Runs an asynchronous method that waits 5 seconds to prevent user from shooting during those 5 seconds then stops cooling
                new Thread(() -> {
                    //Sleeps for 5 seconds
                    try {
                        TimeUnit.SECONDS.sleep((long) 5);
                    } catch (InterruptedException e) {
                    }

                    //Sets isCooling to false
                    isCooling = false;
                    //Prints notification that spaceship is done cooling
                    System.out.println("\nDone Cooling!");
                }).start();
            //Shoots
            } else {
                //Incriments shots by 1
                shots++;
    
                //Prints notification that the user has shot
                System.out.println("\nYou Have Shot!");
            }
        } else {
            //Prints notification to recharge ship
            System.out.println("\nYou Need to Recharge Your Ship!");
        }
    }

    //Changes the spaceship's coordinates to mimic traveling, takes an x value and a y value
    public void travel(int x, int y) {
        //Checks whether spaceship has power or not
        if (powerPercent > 0) {
            //Sets coordinates to new coordinates
            coordinates[0] = x;
            coordinates[1] = y;

            //Prints notification that the spaceship has traveled to the inputted coordinates
            System.out.println("\nYou Have Traveled to {" + x + ", " + y + "}!");
        } else {
            //Prints notification to recharge ship
            System.out.println("\nYou Need to Recharge Your Ship!");
        }
    }

    //Recharges the ship from any power to 100 over a certain timespan
    public void rechargeShip() {
        //Creates a variable for whether the initial power was at 0 or not
        boolean atZero = powerPercent == 0;

        //Prints notification that the spaceship has started recharging
        System.out.println("\nRecharging Ship!");

        //Loops until powerPercent is full
        while (powerPercent < 100) {
            //Sleeps for a random time from 1 to 2
            try {
                TimeUnit.SECONDS.sleep((long) random.ints(1, 2).findFirst().getAsInt());
            } catch (InterruptedException e) {
            }

            //Incriments by a random number from 5 to 10, or sets powerPercent to 100 if incriment would've added powerPercent to over 100
            int incirment = random.ints(5, 10).findFirst().getAsInt();
            if (powerPercent + incirment <= 100) {
                powerPercent += incirment;
            } else {
                powerPercent = 100;
            }

            //Prints notification letting user know progress on recharging
            System.out.println("Power Percent: " + powerPercent + "%");
        }

        //Uses variable created earlier to start decrementing power if method was called when powerPercent was 0
        if (atZero) {
            new Thread(() -> {
                decrementPower();
            }).start();
        }
    }

    //Toggles the spaceship's shields
    public void toggleShields() {
        //Checks whether spaceship has power or not
        if (powerPercent > 0) {
            //Checks whether spaceship has shields or not
            if (hasShields) {
                //Changes isShielded attribute to its opposite
                isShielded = !isShielded;
    
                //Notifies the user on the current state of the ship's shields
                if (isShielded) {
                    System.out.println("\nShields Are Engaged!");
                } else {
                    System.out.println("\nShields Disabled!");
                }
            } else {
                System.out.println("\nThis Ship Has No Shields!");
            }
        } else {
            //Prints notification to recharge ship
            System.out.println("\nYou Need to Recharge Your Ship!");
        }
    }

    //Toggles the spaceship's invisibility
    public void toggleInvisibility() {
        //Checks whether spaceship has power or not
        if (powerPercent > 0) {
            //Checks whether spaceship has invisibility or not
            if (hasInvisibility) {
                //Changes isInvisible attribute to its opposite
                isInvisible = !isInvisible;
    
                //Notifies the user on the current state of the ship's invisibility
                if (isInvisible) {
                    System.out.println("\nThis Ship Is Now Invisible!");
                } else {
                    System.out.println("\nThis Ship Is Now Visible!");
                }
            } else {
                System.out.println("\nThis Ship Cannot Turn Invisible!");
            }
        } else {
            //Prints notification to recharge ship
            System.out.println("\nYou Need to Recharge Your Ship!");
        }
    }

    //Prints the ship's status attributes
    public void getShipStatus() {
        System.out.println("\nHas " + powerPercent + "% Power");
        System.out.println("At {" + coordinates[0] + ", " + coordinates[1] + "}!");
        System.out.println("Shielded: " + isShielded);
        System.out.println("Invisible: " + isInvisible);
        System.out.println("Cooling: " + isCooling);
    }
}
