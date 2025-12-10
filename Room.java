import java.util.Set;
import java.util.HashMap;
/**
 * Classe Room - un lieu du jeu d'aventure Zuul.
 *
 * @Ghezli Ramy
 */
public class Room
{
    private String aDescription; 
    private HashMap<String, Room > aExits;
    private String aImageName;
    /**
     * Constructeur pour les objets de la classe Room.
     * Initialise la description et le HashMap des sorties.
     * @param pDescription La description de la pièce .
     */
    public Room(final String pDescription, final String pImage)
    {
        this.aDescription = pDescription;
        aExits= new HashMap<String, Room>();
        this.aImageName=pImage;
    }//Room
    public String getImageName()
    {
        return this.aImageName;
    }
    /**
     * Accesseur pour la description d'une pièce.
     * @return Description de la pièce.
     * @param pImage Le nom de l'image
     */
    public String getDescription()
    {
        return this.aDescription;
        
    }//getDescription()
    /**
     * Retourne une despription longue de de cette salle sous la forme:
     *      Vous etes dans ...
     *      sorties : (les sorties possibles). 
     *      @return Une description des salles incluant les sorties.
     */
    public String getLongDescription()
    {
        return "vous etes " + aDescription +".\n" + getExitString();
    }
    /**
     * Définit une sortie pour cette pièce.
     * @param pDirection La direction de la sortie
     * @param pNeighbor La pièce à laquelle on accède par cette direction.
     */
    public void setExit(String pDirection, Room pNeighbor)
    {
        aExits.put(pDirection, pNeighbor);
    }//setExits
    /**
     * Retourne la pièce atteinte en suivant une direction.
     * @param pDirection La direction.
     * @return La pièce dans cette direction
     */
    public Room getExit(String pDirection){
        return this.aExits.get(pDirection);
    }//getExit()
    /**
     * Retourne les sorties possibles depuis cette pièce.
     * @return Chaîne qui liste les sorties.
     */
    public String getExitString()
    {
        String vExits = "Sorties: ";
        Set<String> vKeys = this.aExits.keySet();
        for(String vDirection : vKeys){
            vExits += " " + vDirection;
        }
        return vExits;
        
    }//getExitString()
} // Room
