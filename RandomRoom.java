import java.util.Random;
import java.util.List;
import java.util.ArrayList;
/**
 * Classe RandomRoom
 * Choix aléatoire de pièces.
 * * @author Ghezli Ramy
 * @version 7.46
 */
public class RandomRoom
{
   private List<Room> aRooms;
   private Random aRandom;
   
   /**
     * Constructeur.
     * @param pRooms Liste de toutes les pièces
     */
   public RandomRoom(final List<Room> pRooms)
    {
        this.aRooms = pRooms;
        this.aRandom = new Random();
    }
    public Room findRandomRoom()
    {
        if (this.aRooms.isEmpty()){
            return null;
        }
        
        int vIndex = this.aRandom.nextInt(this.aRooms.size());
        return this.aRooms.get(vIndex);
    }
}