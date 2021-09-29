package Model;

/**
 *
 * @author Damon Heard
 */


/**
 * Class used to model in-house parts. Extends Part model.
 */
public class InHousePart extends Part {
    public InHousePart(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        setMachineID(machineID);
    }

    /**
     * Machine ID for in-house parts.
     */
    private int machineID;

    /**
     * Returns machine ID.
     * @return machine ID of part.
     */
    public int getMachineID() {
        return machineID;
    }

    /**
     * Sets machine ID of part.
     * @param machineID machine ID of specific part.
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
