import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Spaceship {
    private Random random = new Random();

    private int powerPercent = 19;
    private int[] coordinates = new int[] {0, 0};
    private boolean isCooling = false;
    private boolean isShielded = false;
    private boolean isInvisible = false;
    private int shots = 0;

    private boolean hasShields;
    private boolean hasInvisibility;

    public Spaceship() {
        hasShields = false;
        hasInvisibility = false;

        new Thread(() -> {
            decrementPower();
        }).start();

        new Thread(() -> {
            cycleRefreshShots();
        }).start();
    }

    public Spaceship(boolean hasShields, boolean hasInvisibility) {
        this.hasShields = hasShields;
        this.hasInvisibility = hasInvisibility;

        new Thread(() -> {
            decrementPower();
        }).start();

        new Thread(() -> {
            cycleRefreshShots();
        }).start();
    }

    private void decrementPower() {
        while (powerPercent > 0) {
            try {
                TimeUnit.SECONDS.sleep((long) random.ints(1, 4).findFirst().getAsInt());
            } catch (InterruptedException e) {
            }

            powerPercent--;
        }
    }

    private void cycleRefreshShots() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep((long) 5);
            } catch (InterruptedException e) {
            }

            shots = 0;
        }
    }

    public void shoot() {
        if (powerPercent > 0) {
            if (isCooling) {
                System.out.println("\nCooling Down!");
            } else if (shots >= 3) {
                System.out.println("\nYou Have Shot Too Quickly! Cooling Down!");

                isCooling = true;

                new Thread(() -> {
                    try {
                        TimeUnit.SECONDS.sleep((long) 5);
                    } catch (InterruptedException e) {
                    }

                    isCooling = false;
                    System.out.println("\nDone Cooling!");
                }).start();
            } else {
                shots++;
    
                System.out.println("\nYou Have Shot!");
            }
        } else {
            System.out.println("\nYou Need to Recharge Your Ship!");
        }
    }

    public void travel(int x, int y) {
        if (powerPercent > 0) {
            coordinates[0] = x;
            coordinates[1] = y;

            System.out.println("\nYou Have Traveled to {" + x + ", " + y + "}!");
        } else {
            System.out.println("\nYou Need to Recharge Your Ship!");
        }
    }

    public void rechargeShip() {
        boolean atZero = powerPercent == 0;

        System.out.println("\nRecharging Ship!");

        while (powerPercent < 100) {
            try {
                TimeUnit.SECONDS.sleep((long) random.ints(1, 2).findFirst().getAsInt());
            } catch (InterruptedException e) {
            }

            if (powerPercent <= 90) {
                powerPercent += 10;
            } else {
                powerPercent = 100;
            }

            System.out.println("Power Percent: " + powerPercent + "%");
        }

        if (atZero) {
            new Thread(() -> {
                decrementPower();
            }).start();
        }
    }

    public void toggleShields() {
        if (powerPercent > 0) {
            if (hasShields) {
                isShielded = !isShielded;
    
                if (isShielded) {
                    System.out.println("\nShields Are Engaged!");
                } else {
                    System.out.println("\nShields Disabled!");
                }
            } else {
                System.out.println("\nThis Ship Has No Shields!");
            }
        } else {
            System.out.println("\nYou Need to Recharge Your Ship!");
        }
    }

    public void toggleInvisibility() {
        if (powerPercent > 0) {
            if (hasInvisibility) {
                isInvisible = !isInvisible;
    
                if (isInvisible) {
                    System.out.println("\nThis Ship Is Now Invisible!");
                } else {
                    System.out.println("\nThis Ship Is Now Visible!");
                }
            } else {
                System.out.println("\nThis Ship Cannot Turn Invisible!");
            }
        } else {
            System.out.println("\nYou Need to Recharge Your Ship!");
        }
    }

    public void getShipStatus() {
        System.out.println("\nHas " + powerPercent + "% Power");
        System.out.println("At {" + coordinates[0] + ", " + coordinates[1] + "}!");
        System.out.println("Shielded: " + isShielded);
        System.out.println("Invisible: " + isInvisible);
        System.out.println("Cooling: " + isCooling);
    }
}
