
/**
 * Classe TransporterRoom - Une pièce qui téléporte le joueur aléatoirement.
 * @author Ghezli Ramy
 * @version 7.46
 */
public class TransporterRoom extends Room
{
   private RandomRoom aRandomiser;
   private Room aAleaRoom;
   public TransporterRoom(final String pDescription, final String pImage, final RandomRoom pRandomiser)
    {
        super(pDescription, pImage);
        this.aRandomiser = pRandomiser;
        this.aAleaRoom = null;
    }
    
   @Override
    public Room getExit(final String pDirection)
    {
        if(this.aAleaRoom !=null){
            return this.aAleaRoom;
        }
        return this.aRandomiser.findRandomRoom();
    }
    public void setAlea(final Room pRoom)
    {
        this.aAleaRoom = pRoom;
    }
    
}