 
/**
 * Classe Room - un lieu du jeu d'aventure Zuul.
 *
 * @author votre nom
 */
public class Room
{
    private String aDescription; 
    public Room aNorthExit; 
    public Room aSouthExit;
    public Room aEastExit;
    public Room aWestExit;
    
    public Room(final String pDescription)
    {
        this.aDescription = pDescription;
        
    }
    public String getDescription()
    {
        return this.aDescription;
        
    }//getDescription()
    public void setExits(final Room pNorthExit,final Room pSouthExit,final Room pEastExit,final Room pWestExit)
    {
        this.aNorthExit=pNorthExit;
        this.aSouthExit=pSouthExit;
        this.aEastExit=pEastExit;
        this.aWestExit=pWestExit;
    }//setExits
    
    public Room getExit(String pDirection){
        if(pDirection.equals("north")){
            return aNorthExit;
        }
        if(pDirection.equals("south")){
            return aSouthExit;
        }
        if(pDirection.equals("east")){
            return aEastExit;
        }
        if(pDirection.equals("west")){
            return aWestExit;
        }
        
        return null;
        
    }
    
    public String getExitsString()
    {
        String vExits = "Exits : ";
        if( this.aNorthExit!=null){
            vExits= vExits + " North";
        }
        if( this.aSouthExit!=null){
            vExits= vExits + " South";
        }
        if( this.aEastExit!=null){
            vExits= vExits + " East";
        }
        if( this.aWestExit!=null){
            vExits= vExits + " West";
        }
        return vExits;
        
    }
} // Room
