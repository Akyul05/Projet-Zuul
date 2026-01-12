import java.util.HashMap;
import java.util.Set;
/**
 * Classe ItemList 
 * Cette classe permet de manipuler une collection d'items .
 * @author Ghezli Ramy
 * @version 7.31.1
 */
public class ItemList
{
    private HashMap <String , Item> aItems;
    
    public ItemList()
    {
        this.aItems= new HashMap<String, Item>();
    }
    
    /**
     * Ajoute un item à la pièce.
     * @param pItem L'item à ajouter dans cette pièce.
     */
    public void addItem(final Item pItem)
    {
        this.aItems.put(pItem.getName(), pItem);
    }
    /**
     * Retire un item de la pièce.
     * @param pItem Le nom de l'item à retirer.
     */
    public void removeItem(final String pItem)
    {
        this.aItems.remove(pItem);
    }
    /**
     * Retourne l'item qui correspond au nom passé en paramètre.
     * @param pName Le nom de l'item recherché.
     * @return L'objet Item correspondant.
     */
    public Item getItem(final String pName)
    {
        return this.aItems.get(pName);
    }
    /**
     * Vérifie si la liste est vide.
     * @return true si vide.
     */
    public boolean isEmpty()
    {
        return this.aItems.isEmpty();
    }
    /**
     * Vérifie si la liste contient un item.
     * @param pItem Le nom de l'item.
     * @return true si l'item est présent.
     */
    public boolean hasItem(final String pItem)
    {
        return this.aItems.containsKey(pItem);
    }
    public String getItemsString()
    {
        if (this.aItems.isEmpty()){
            return "vous ne portez rien.";
        }
        String vReturnString = "Objet : ";
        Set <String> vKeys = this.aItems.keySet();
        for (String vItem:vKeys){
            vReturnString += " "+vItem;
        }
        return vReturnString;
    }
    public double getTotalWeight()
    {
        double vTotal = 0;
        for(Item vItem : this.aItems.values()){
            vTotal = vTotal + vItem.getWeight();
        }
        return vTotal; 
        
    }
    
}