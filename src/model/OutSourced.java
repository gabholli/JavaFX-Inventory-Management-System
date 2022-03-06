package model;

/**
 * This is a class for outsourced parts
 */
public class OutSourced extends Part {
    /**
     * Variable for outsourced part company name
     */
    private String companyName;

    /**
     * Constructor for an outsourced part
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Getter for company name
     *
     * @return companyName returns company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Setter for company name
     *
     * @param companyName the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
