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
   private ItemList aInventory;
   private double aMaxWeight;
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
       this.aInventory = new ItemList();
       this.aMaxWeight = 4.0;
    }
    public boolean victoire()
    {
        return (this.getItem("gemme1")!=null&&this.getItem("gemme2")!=null&&this.getItem("gemme3")!=null&&this.getItem("epee")!=null);
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
        if(pNextRoom.isExit(this.aCurrentRoom)){
            this.aPrevRooms.push(this.aCurrentRoom);
        }
        else {
            this.aPrevRooms.clear();
        }
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
    
    /**
     * prend un item présent dans la pièce courante.
     * @param pItem Le nom de l'item à prendre.
     */
    public boolean take(final String pItem)
    {
        Item vItem = this.aCurrentRoom.getItem(pItem);
        if (vItem == null){
        return false;}
        
        double vTotalWeight = this.aInventory.getTotalWeight();
        double vItemWeight = vItem.getWeight();
        
        if (vTotalWeight + vItemWeight > this.aMaxWeight){
            return false;
        }
        
        this.aCurrentRoom.removeItem(pItem);
        this.aInventory.addItem(vItem);
        
        return true;
    }
    
    /**
     * dépose l'item dans la pièce courante.
     * @param pItem Le nom de l'item à déposer.
     */
    public void drop(final String pItem)
    {
        Item vItem = this.aInventory.getItem(pItem);
        if(vItem != null){
            this.aInventory.removeItem(pItem);
            this.aCurrentRoom.addItem(vItem);
        }
        
    }
    
    public Item getItem(final String pItem)
    {
        return this.aInventory.getItem(pItem);
    }
    
    public String getInventoryString()
    {
        return "Inventaire : "+ this.aInventory.getItemsString()+" \n Poid total : "+ this.aInventory.getTotalWeight();
    }
    public double getMaxWeight()
    {
        return this.aMaxWeight;
    }
    /**
     * Consomme un item .
     * @param pItem Le nom de l'item à manger.
     * @return Une String contenant le message.
     */
    public String eat(final String pItem)
    {
        Item vItem = this.aInventory.getItem(pItem);
        if(vItem== null){
            return("cet objet est introuvable dans l'inventaire.");
            
        }
        if (pItem.equals("herbes")){
            this.aInventory.removeItem(pItem);
            this.aMaxWeight = this.aMaxWeight * 4;
            return("vous avez consommé les herbes medicinales , vous vous sentez plus fort");
        }
        else{
            return pItem+ "n'est pas consommable.";
        }
    }
    /**
     * Charge le beamer avec la pièce actuelle.
     * @param pItem Nom de l'item à charger (Beamer).
     * @return message de confirmation.
     */
    public String charge(final String pItem)
    {
        Item vItem = this.aInventory.getItem(pItem);
        
        if (vItem instanceof Beamer){
            Beamer vBeamer = (Beamer) vItem;
            vBeamer.charge(this.aCurrentRoom);
            return("Vous avez mémorisé cette piece dans le beamer, declanchez le pour y revenir");
            
        }
        
        else{
            return ("impossible de charger cet objet ");
        }
    }
    /**
     * Utilise le beamer pour se téléporter à la pièce mémorisée.
     * @param pItem Nom de l'item à utiliser (Beamer).
     * @return Un message de succès erreur.
     */public String fire(final String pItem)
    {
        Item vItem = this.aInventory.getItem(pItem);
        if (vItem instanceof Beamer){
            Beamer vBeamer = (Beamer) vItem;
            if(!vBeamer.isCharged()){
                return ("le beamer n'est pas chargé");
            }
            
            Room vRoomTP = vBeamer.fire();
            this.aCurrentRoom = vRoomTP;
            return ("Vous vous etes teleporté ");
            
    }
    else {
                return ("vous ne pouvez pas utiliser cet objet");
            }
}

}