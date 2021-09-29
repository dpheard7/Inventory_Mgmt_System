package Model;

/**
 *
 * @author Damon Heard
 */

/**
 * Class used to model outsourced/third-party parts.
 */
public class OutsourcedPart extends Part {
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        setCompanyName(companyName);
    }

    /**
     * Company name for outsourced parts.
     */
    private String companyName;

    /**
     * Retrieves company name of part.
     * @return company name of outsourced part.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets company name for outsourced part.
     * @param companyName for specific part.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
