/**
 *  This class is part of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.
 * 
 *  This class creates all rooms, creates the parser and starts
 *  the game.  It also evaluates and executes the commands that 
 *  the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (Jan 2003) DB edited (2019)
 */
public class GameEngine
{
    private Parser        aParser;
    private Room          aCurrentRoom;
    private UserInterface aGui;

    /**
     * Constructeur pour les objets de la classe GameEngine.
     * Crée le parseur et la carte du jeu.
     */
    public GameEngine()
    {
        this.aParser = new Parser();
        this.createRooms();
    } // GameEngine()

    /**
     * Associe l'interface graphique au moteur de jeu.
     * Cette méthode est appelée par la classe Game au démarrage.
     * @param pUserInterface L'interface graphique à utiliser.
     */
    public void setGUI( final UserInterface pUserInterface )
    {
        this.aGui = pUserInterface;
        this.printWelcome();
    } // setGUI(.)

    /**
     * Affiche le message de bienvenue pour le joueur.
     */
    private void printWelcome()
    {
        this.aGui.print( "\n" );
        this.aGui.println( "Bienvenue dans 'Aelyndor : L'Épée des Âmes' !" );
        this.aGui.println( "Le royaume est en ruines. Retrouvez les gemmes, reforgez l'épée, sauvez le monde." );
        this.aGui.println( "Tapez 'help' si vous avez besoin d'aide." );
        this.aGui.print( "\n" );
        
        this.printLocationInfo();
    } // printWelcome()

    /**
     * Crée toutes les pièces et relie leurs sorties.
     */
    private void createRooms()
    {
        // Création des lieux avec des images (noms de fichiers temporaires)
        Room vVillage = new Room("dans les vestiges de votre village, un lieu pauvre mais abritant l'Érudit.", "village.png");
        Room vForet = new Room("dans la Forêt des Murmures, où l'on peut trouver quelques herbes médicinales.", "foret.jpg");
        Room vRuines = new Room("au cœur des Ruines Anciennes, un lieu de pierre brisée hanté par un Garde Spectral.", "ruines.jpg");
        Room vSanctuaire = new Room("devant le Sanctuaire Scellé, une grande porte verrouillée qui réagit aux gemmes.", "sanctuaire.jpg");
        Room vForge = new Room("dans la Forge des Âmes, l'enceinte circulaire où repose le socle de l'épée brisée.", "forge.jpg");

        // Initialisation des sorties
        vVillage.setExit("south", vForet);
        
        vForet.setExit("north", vVillage);
        vForet.setExit("south", vRuines);
        
        vRuines.setExit("west", vForet);
        
        vSanctuaire.setExit("north", vForet);
        vSanctuaire.setExit("south", vForge);
        
        vForge.setExit("north", vSanctuaire);

        this.aCurrentRoom = vVillage; // Le jeu commence au village
    } // createRooms()

    /**
     * Interprète la commande donnée (exécutée par l'interface graphique).
     * @param pCommandLine La chaîne de commande tapée par le joueur.
     */
    public void interpretCommand( final String pCommandLine ) 
    {
        this.aGui.println( "> " + pCommandLine ); // Affiche la commande tapée (écho)
        Command vCommand = this.aParser.getCommand( pCommandLine );

        if ( vCommand.isUnknown() ) {
            this.aGui.println( "I don't know what you mean..." );
            return;
        }

        String vCommandWord = vCommand.getCommandWord();
        
        if ( vCommandWord.equals( "help" ) ) {
            this.printHelp();
        }
        else if ( vCommandWord.equals( "go" ) ) {
            this.goRoom( vCommand );
        }
        else if ( vCommandWord.equals( "look" ) ) {
            this.look();
        }
        else if ( vCommandWord.equals( "eat" ) ) {
            this.eat();
        }
        else if ( vCommandWord.equals( "quit" ) ) {
            if ( vCommand.hasSecondWord() )
                this.aGui.println( "Quit what?" );
            else
                this.endGame();
        }
    } // interpretCommand(.)

    // --- Implémentation des commandes utilisateur ---

    /**
     * Affiche les informations sur le lieu courant (description + image).
     */
    private void printLocationInfo()
    {
        this.aGui.println( this.aCurrentRoom.getLongDescription() );
        
        // Affiche l'image si elle existe
        if ( this.aCurrentRoom.getImageName() != null ) {
            this.aGui.showImage( this.aCurrentRoom.getImageName() );
        }
    } // printLocationInfo()

    /**
     * Commande 'look'.
     */
    private void look()
    {
        this.printLocationInfo();
    } // look()

    /**
     * Commande 'eat'.
     */
    private void eat()
    {
        this.aGui.println("Vous avez mangé, vous êtes désormais rassasié.");
    } // eat()

    /**
     * Commande 'help'.
     */
    private void printHelp() 
    {
        this.aGui.println( "Vous êtes perdu. Vous êtes seul." );
        this.aGui.println( "Vous errez dans le royaume d'Aelyndor." + "\n" );
        this.aGui.println( "Vos commandes sont : " + this.aParser.getCommandList() );
    } // printHelp()

    /**
     * Commande 'go'.
     */
    private void goRoom( final Command pCommand ) 
    {
        if ( ! pCommand.hasSecondWord() ) {
            this.aGui.println( "Go where?" );
            return;
        }

        String vDirection = pCommand.getSecondWord();
        Room vNextRoom = this.aCurrentRoom.getExit( vDirection );

        if ( vNextRoom == null ) {
            this.aGui.println( "There is no door!" );
        }
        else {
            this.aCurrentRoom = vNextRoom;
            this.printLocationInfo();
        }
    } // goRoom(.)

    /**
     * Termine le jeu (appelé par 'quit').
     */
    private void endGame()
    {
        this.aGui.println( "Thank you for playing.  Good bye." );
        this.aGui.enable( false ); // Désactive la zone de saisie
    } // endGame()
    
} // GameEngine
