 
/**
 * Classe Game - le moteur du jeu d'aventure Zuul.
 *
 * @author votre nom
 */
public class Game
{
    private Room aCurrentRoom;
    private Parser aParser;
    
    public Game()
    {
        this.createRooms();
        this.aParser = new Parser();
    }
    
    public void play()
    {
        this.printWelcome();
        boolean vFinished = false;
        while(vFinished == false){
            Command vCommande = this.aParser.getCommand();
            vFinished = this.processCommand(vCommande);
        }//while
        if (vFinished == true){
            System.out.println("Thank you for playing.  Good bye.");
        }
    }
    
    private void createRooms()
    {
        Room vVillage = new Room("dans les vestiges de votre village, un lieu pauvre mais abritant l'Érudit.");
        Room vForet = new Room("dans la Forêt des Murmures, où l'on peut trouver quelques herbes médicinales.");
        Room vRuines = new Room("au cœur des Ruines Anciennes, un lieu de pierre brisée hanté par un Garde Spectral.");
        Room vSanctuaire = new Room("devant le Sanctuaire Scellé, une grande porte verrouillée qui réagit aux gemmes.");
        Room vForge = new Room("dans la Forge des Âmes, l'enceinte circulaire où repose le socle de l'épée brisée.");
        
        vVillage.setExits(null,vForet,null,null);
        vForet.setExits(vVillage,vRuines,null,null);
        vRuines.setExits(null,null,null,vForet);
        vSanctuaire.setExits(vForet,vForge,null,null);
        vForge.setExits(vSanctuaire,null,null,null);
        
        this.aCurrentRoom = vVillage;
    }
    private void goRoom(final Command pSecondWord)
    {
        //a)
        if (!pSecondWord.hasSecondWord())
        {System.out.println("Go where");
            return;
        }
        
        //b)
        Room vNextRoom = null;
        String vDirection = pSecondWord.getSecondWord(); 
        //vDirection : recupere le deuxieme mot qui correspond 
        //a ce qu'on a placé en parametre de goRoom
        
        if (vDirection.equals("North"))
        {
            vNextRoom = this.aCurrentRoom.aNorthExit; 
        }
        else if (vDirection.equals("South"))
        {
            vNextRoom = this.aCurrentRoom.aSouthExit;
        }
        else if(vDirection.equals("East"))
        {
            vNextRoom = this.aCurrentRoom.aEastExit;
        }
        else if(vDirection.equals("West"))
        {
            vNextRoom = this.aCurrentRoom.aWestExit;
        }
        else 
        {
            System.out.println("Unknown direction ! !");
            return;
        }
        
        //c)
        
        if (vNextRoom == null)
        {
            System.out.println("there is no door !");
            return;
            
        }
        else 
        {
            this.aCurrentRoom = vNextRoom; 
            System.out.println("current room :"+ this.aCurrentRoom.getDescription() );
            System.out.println("Exits : ");
            
            if (this.aCurrentRoom.aNorthExit != null)
            {
                System.out.println("North");
            }
            if (this.aCurrentRoom.aSouthExit != null)
            {
                System.out.println("South");
            }
            if (this.aCurrentRoom.aEastExit != null)
            {
                System.out.println("East");
            }
            if (this.aCurrentRoom.aWestExit != null)
            {
                System.out.println("West");
            }
        }
        
        
    }
    
    private void printWelcome()
    {
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println("You are " + this.aCurrentRoom.getDescription());
        System.out.println(this.aCurrentRoom.getExitsString());
    }
    
    private void printHelp()
    {
        System.out.println("Your are lost. You are alone.");
        System.out.println("You wander around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help");
        
    }
    
    private boolean quit(final Command pCommand)
    {
        if(pCommand.hasSecondWord())
        {
        System.out.println("Quit what?");
        return false;
        }
        else
        return true;
    }
    
    private boolean processCommand(final Command pCommand)
    {
        if (pCommand.isUnknown())
        {
            System.out.println("I don't know what you mean...");
            return false;
        }
    
        else if (pCommand.getCommandWord().equals("help"))
        {
            this.printHelp();
            return false;
        }
        else if (pCommand.getCommandWord().equals("go"))
        {
            this.goRoom(pCommand);
            return false;
        }
        else if (pCommand.getCommandWord().equals("quit"))
        {
            return quit(pCommand);
        }
        
        else
        {
        System.out.println("Erreur du programmeur : commande non reconnue !");
        return true;
        }
        
    }
} // Game
