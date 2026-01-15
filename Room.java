import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
/**
 * Classe Room - un lieu du jeu d'aventure Zuul.
 *
 * @author Ghezli Ramy
 */
public class Room
{
    private String aDescription; 
    private HashMap<String, Room > aExits;
    private String aImageName;
    private ItemList aItems;
    /**
     * Constructeur pour les objets de la classe Room.
     * Initialise la description et le HashMap des sorties.
     * @param pDescription La description de la pièce .
     * @param pImage Le nom du fichier image.
     */
    public Room(final String pDescription, final String pImage)
    {
        this.aDescription = pDescription;
        aExits= new HashMap<String, Room>();
        this.aImageName=pImage;
        this.aItems = new ItemList();
    }//Room
    
    /**
     * Accesseur pour le nom de l'image de la pièce.
     * @return Le nom du fichier image.
     */
    public String getImageName()
    {
        return this.aImageName;
    }
    /**
     * Accesseur pour la description d'une pièce.
     * @return Description de la pièce.
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
        return "vous etes " + this.aDescription +".\n" 
        +this.getItemString()+"\n"+
        this.getExitString()
        ;
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
    /**
     * Ajoute un item à la pièce.
     * @param pItem L'item à ajouter dans cette pièce.
     */
    public void addItem(final Item pItem)
    {
        this.aItems.addItem(pItem);
    }
    
    /**
     * Retire un item de la pièce.
     * @param pItem Le nom de l'item à retirer.
     */
    public void removeItem(final String pItem)
    {
        this.aItems.removeItem(pItem);
    }
    
    /**
     * Retourne l'item qui correspond au nom passé en paramètre.
     * @param pName Le nom de l'item recherché.
     * @return L'objet Item correspondant.
     */
    public Item getItem(final String pName)
    {
        return this.aItems.getItem(pName);
    }
    
    /**
     * Retourne une chaîne décrivant tous les items présents dans la pièce.
     * @return Une chaîne de caractères listant les items (ex: "Objets : une épée un bouclier").
     */
    public String getItemString()
    {
        return this.aItems.getItemsString();
    }
    /**
     * Vérifie si la pièce passée en paramètre est une des sorties.
     * @param pRoom Le nom de la pièce.
     * @return true si pRoom est une sortie, false sinon.
     */
    public boolean isExit(final Room pRoom)
    {
        return this.aExits.containsValue(pRoom);
    }
} // Room
