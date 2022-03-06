package model;

/**
 * This is a class for in-house parts
 */
public class InHouse extends Part {
    /**
     * Variable for in-house part machine ID
     */
    private int machineId;

    /**
     * Constructor for in-house parts
     *
     * @param id    the ID for the part
     * @param name  the name of the part
     * @param price the price of the part
     * @param stock the stock amount for the part
     * @param min   the minimum number of parts
     * @param max   the maximum number of parts
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Getter for machine ID
     *
     * @return returns machineID the machineID of the part
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Setter for machine ID
     *
     * @param machineId the number of the machineID
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }


}
