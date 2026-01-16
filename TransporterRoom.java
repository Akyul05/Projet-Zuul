
/**
 * Classe TransporterRoom - Une pièce qui téléporte le joueur aléatoirement.
 * @author Ghezli Ramy
 * @version 7.46
 */
public class TransporterRoom extends Room
{
   private RandomRoom aRandomiser;
   public TransporterRoom(final String pDescription, final String pImage, final RandomRoom pRandomiser)
    {
        super(pDescription, pImage);
        this.aRandomiser = pRandomiser;
    }
    
   @Override
    public Room getExit(final String pDirection)
    {
        return this.aRandomiser.findRandomRoom();
    }
}