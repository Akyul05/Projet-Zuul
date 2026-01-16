
/**
 * Classe Beamer - Un objet permettant de se teleporter .
 *
 * @author Ghezli Ramy
 * @version 7.44
 */
public class Beamer extends Item
{
    private Room aSavedRoom;
    /**
     * Constructeur de Beamer.
     * @param pName Le nom
     * @param pDescription La description
     * @param pWeight Le poid
     */
    public Beamer(final String pName, final String pDescription, final double pWeight)
    {
        super(pName, pDescription, pWeight);
        this.aSavedRoom = null;
        
    }
    /**
     * Memorise la piece actuelle
     * @return La pièce à mémoriser.
     */
    public void charge(final Room pRoom)
    {
        this.aSavedRoom = pRoom;
    }
    /**
     * Lance la teleportation vers la piece memorisée et se decharge ensuite .
     * @return La pièce mémorisée .
     */
    public Room fire()
    {
        Room vRoom = this.aSavedRoom;
        this.aSavedRoom = null;
        return vRoom;
    }
    
    public boolean isCharged()
    {
        return this.aSavedRoom != null;
    }
}