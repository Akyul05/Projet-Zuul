
/**
 * Décrivez votre classe Item ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Item
{
    private String aDescription;
    private double aWeight;
    private String aName;
    public Item(  final String pName, final String pDescription, final double pWeight)
    {
        this.aDescription = pDescription;
        this.aWeight = pWeight;
        this.aName = pName;
    } // Item()
    
    public String getDescription()
    {
        return this.aDescription;
    }
    
    public double getWeight()
    {
        return this.aWeight;
    }
    
    public String getLongDescription()
    {
        return this.aDescription + " (Poids: " + this.aWeight + "kg)";
    }
    public String getName()
    {
        return this.aName;
    }
}