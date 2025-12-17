
/**
 * Un Objet dans l'univers de Aelyndor.
 *
 * @author Ghezli Ramy
 * @version 7.26
 */
public class Item
{
    private String aDescription;
    private double aWeight;
    private String aName;
    /**
     * Constructeur naturel de la classe Item.
     * @param pName Le nom court de l'objet.
     * @param pDescription La description complète de l'objet.
     * @param pWeight Le poids de l'objet
     */
    public Item(  final String pName, final String pDescription, final double pWeight)
    {
        this.aDescription = pDescription;
        this.aWeight = pWeight;
        this.aName = pName;
    } // Item()
    /**
     * Accesseur pour la description de l'objet.
     * @return La description de l'objet.
     */
    public String getDescription()
    {
        return this.aDescription;
    }
    /**
     * Accesseur pour le poids de l'objet.
     * @return Pooids de l'objet.
     */
    public double getWeight()
    {
        return this.aWeight;
    }
    /**
     * Retourne la description détaillée d'un l'objet.
     * @return Chaine de caracteres avec la description et le poids.
     */
    public String getLongDescription()
    {
        return this.aDescription + " (Poids: " + this.aWeight + "kg)";
    }
    /**
     * Accesseur pour le nom de l'objet.
     * @return Le nom court de l'objet.
     */
    public String getName()
    {
        return this.aName;
    }
}