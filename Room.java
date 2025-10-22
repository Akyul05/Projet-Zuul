import java.util.Set;
import java.util.HashMap;
/**
 * Classe Room - un lieu du jeu d'aventure Zuul.
 *
 * @author votre nom
 */
public class Room
{
    private String aDescription; 
    private HashMap<String, Room > aExits;
    
    public Room(final String pDescription)
    {
        this.aDescription = pDescription;
        aExits= new HashMap<String, Room>();
        
    }
    public String getDescription()
    {
        return this.aDescription;
        
    }//getDescription()
    public void setExit(String pDirection, Room pNeighbor)
    {
        aExits.put(pDirection, pNeighbor);
    }//setExits
    
    public Room getExit(String pDirection){
        return this.aExits.get(pDirection);
    }
    
    public String getExitsString()
    {
        String vExits = "Sorties: ";
        Set<String> vKeys = this.aExits.keySet();
        for(String vDirection : vKeys){
            vExits += vDirection;
        }
        return vExits;
        
    }
} // Room
