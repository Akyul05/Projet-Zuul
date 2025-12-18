import java.util.Stack;
/**
 * Décrivez votre classe Player ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Player
{
   private String aName;
   private Room aCurrentRoom;
   private Stack <Room> aPrevRooms;
   /**
    * Constructeur naturel de Player.
    * @param pName Le nom du joueur.
    * @param pCurrentRoom La pièce de départ.
    */
   public Player(final String pName, final Room pCurrentRoom)
   {
       this.aName= pName;
       this.aCurrentRoom= pCurrentRoom;
       this.aPrevRooms = new Stack<Room>();
    }
    /**
     * Accesseur de la pièce courante.
     */
    public Room getCurrentRoom()
    {
        return this.aCurrentRoom;
    }
    
    /**
     * Accesseur du nom du joueur.
     */
    public String getName()
    {
        return this.aName;
    }
    
    /**
     * Déplace le joueur vers une nouvelle pièce.
     * Gere aussi l'historique pour le retour en arrière.
     * @param pNextRoom La pièce dans laquelle on va.
     */
    public void move(final Room pNextRoom)
    {
        this.aPrevRooms.push(this.aCurrentRoom);
        this.aCurrentRoom= pNextRoom;
    }
    /**
     * Commande 'back'
     * Revient à la pièce précédente.
     */
    public void back()
    {
        this.aCurrentRoom = this.aPrevRooms.pop();
    }
    
    /**
     * Vérifie si le joueur peut revenir en arrière ,donc que la pile n'est pas vide.
     * @return true si le retour est possible, false sinon.
     */
    public boolean canGoBack()
    {
        return !this.aPrevRooms.isEmpty();
    }
}