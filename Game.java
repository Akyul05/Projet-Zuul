 
/**
 * Classe Game - le moteur du jeu d'aventure Zuul.
 *
 * @author Ghezli Ramy
 */
public class Game
{
    private Room aCurrentRoom;
    private Parser aParser;
    /**
     * Constructeur pour les objets de la classe Game.
     */
    public Game()
    {
        this.createRooms();
        this.aParser = new Parser();
    }// Game()
    /**
     * La procédure principale du jeu.
     * Active jusqu'à ce que la commande 'quit' soit reçue.
     */
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
    }// play()
    /**
     * Affiche les informations sur la pièce courante.
     */
    private void printLocationInfo(){
        System.out.println(this.aCurrentRoom.getLongDescription());
    }// printLocationInfo()
    /**
     * Crée l'ensemble des pièces et leurs sorties.
     * Défini donc la carte du jeu
     */
    private void createRooms()
    {
        Room vVillage = new Room("dans les vestiges de votre village, un lieu pauvre mais abritant l'Érudit.");
        Room vForet = new Room("dans la Forêt des Murmures, où l'on peut trouver quelques herbes médicinales.");
        Room vRuines = new Room("au cœur des Ruines Anciennes, un lieu de pierre brisée hanté par un Garde Spectral.");
        Room vSanctuaire = new Room("devant le Sanctuaire Scellé, une grande porte verrouillée qui réagit aux gemmes.");
        Room vForge = new Room("dans la Forge des Âmes, l'enceinte circulaire où repose le socle de l'épée brisée.");
        
        vVillage.setExit("south", vForet);
        
        vForet.setExit("north", vVillage);
        vForet.setExit("south", vRuines);
        
        vRuines.setExit("west", vForet);
        
        vSanctuaire.setExit("north", vForet);
        vSanctuaire.setExit("south", vForge);
        
        vForge.setExit("north", vSanctuaire);
        
        this.aCurrentRoom = vVillage;
    }// createRooms()
    /**
     * Sert à aller  dans une direction.
     * Si la direction est invalide, affiche un message d'erreur.
     * @param pCommand La commande qui contient la direction par exemple : go north
     */
    private void goRoom(final Command pSecondWord)
    {
        //a)
        if (!pSecondWord.hasSecondWord())
        {System.out.println("Go where");
            return;
        }
        
        //b)
        String vDirection = pSecondWord.getSecondWord(); 
        Room vNextRoom = this.aCurrentRoom.getExit(vDirection);
        //c)
        
        if (vNextRoom == null)
        {
            System.out.println("there is no door !");
            return;
            
        }
        else 
        {
            this.aCurrentRoom = vNextRoom; 
            this.printLocationInfo();
            
        }
        
        
    }// goRoom()
    /**
     * Affiche le message de bienvenue.
     */
    private void printWelcome()
    {
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        this.printLocationInfo();
    }// printWelcome()
    /**
     * Affiche de l'aide et la liste des commandes valides.
     */
    private void printHelp()
    {
        System.out.println("Your are lost. You are alone.");
        System.out.println("You wander around at the university.");
        System.out.println();
        System.out.println("Vos commandes sont: ");
        this.aParser.showCommands();
        
    }// printHelp()
    /**
     * La commande qui sert a quitter le jeu.
     * @param pCommand La commande "quit" qui sera tapé par le joueur.
     * @return true si le jeu doit s'arrêter sinon false 
     */
    private boolean quit(final Command pCommand)
    {
        if(pCommand.hasSecondWord())
        {
        System.out.println("Quit what?");
        return false;
        }
        else
        return true;
    }// quit()
    private void look()
    {
        System.out.println(this.aCurrentRoom.getLongDescription());
    }//look()
    private void eat()
    {
        System.out.println("vous avez mangé , vous êtes désormais rassasié");
    }//eat()
    /**
     * Verifie si la commande est connu et appele la methode qui correpond.
     * @param pCommand La commande.
     */
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
        else if(pCommand.getCommandWord().equals("look")){
            this.look();
            return false;
        }
        else if(pCommand.getCommandWord().equals("eat")){
            this.eat();
            return false;
        }
        
        else
        {
        System.out.println("Erreur du programmeur : commande non reconnue !");
        return true;
        }
        
    }// processCommand()
} // Game
