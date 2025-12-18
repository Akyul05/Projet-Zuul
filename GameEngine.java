import java.util.Stack;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

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
    private UserInterface aGui;
    private Player aPlayer;
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
        this.aGui.println( "Bienvenue dans Aelyndor : L'Épée des Âmes !" );
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
        Room vForet = new Room("dans la Forêt des Murmures, où l'on peut trouver quelques herbes médicinales.", "foret.png");
        Room vRuines = new Room("aux Ruines Anciennes, un lieu de pierre brisée hanté par un Garde Spectral.", "ruines.png");
        Room vTour = new Room("devant une Tour Effondrée qui semble toucher les nuages.", "tour.png");
        Room vRiviere = new Room("au bord de la Rivière des Ombres, l'eau est sombre et calme.", "riviere.png");
        Room vCaverne = new Room("dans la Caverne de Cristal, les parois scintillent d'une lueur étrange.", "caverne.png");
        Room vTemple = new Room("dans le Temple Oublié, un lieu sacré envahi par la végétation.", "temple.png");
        Room vPlaine = new Room("sur la Plaine Stérile, un champ de bataille abandonné.", "pleines.png");
        Room vSanctuaire = new Room("devant le Sanctuaire Scellé, une grande porte verrouillée qui réagit aux gemmes.", "sanctuaire.png");
        Room vForge = new Room("dans la Forge des Âmes, l'enceinte circulaire où repose le socle de l'épée brisée.", "forge.png");
        // Initialisation des sorties
        vVillage.setExit("east", vForet);
        
        vForet.setExit("west", vVillage);
        vForet.setExit("east", vRuines);
        vForet.setExit("south", vSanctuaire);
        
        vRuines.setExit("west", vForet);
        vRuines.setExit("east", vTour);
        
        vTour.setExit("west", vRuines);
        vTour.setExit("down", vPlaine);
        
        vRiviere.setExit("north", vForet);
        vRiviere.setExit("south", vTemple);
        
        vTemple.setExit("north", vRiviere);
        vTemple.setExit("down", vCaverne);
        vTemple.setExit("east", vPlaine);
        vTemple.setExit("south", vSanctuaire);
        
        vCaverne.setExit("up", vTemple);
        
        vPlaine.setExit("west", vTemple);
        vPlaine.setExit("up", vTour);
        
        vSanctuaire.setExit("north", vTemple);
        vSanctuaire.setExit("down", vForge);
        
        vForge.setExit("north", vSanctuaire);
        
        // Initialisation des Items
        // Village
        vVillage.addItem(new Item("livre", "un vieux livre poussiéreux laissé par l'Érudit", 1.2));
        vVillage.addItem(new Item("epee", "une vieille épée d'entraînement brisée", 2.0));
        // Forêt
        
        vForet.addItem(new Item("herbes", "des herbes médicinales luminescentes", 0.5));

        // Ruines (Gemme 1)
        vRuines.addItem(new Item("gemme1", "la Gemme de Force (Rouge)", 1.0));

        // Rivière
        vRiviere.addItem(new Item("coffre", "un petit coffre verrouillé échoué sur la rive", 3.0));

        // Caverne (Gemme 2)
        vCaverne.addItem(new Item("gemme2", "la Gemme de l'Esprit (Bleue)", 1.0));
        
        // Temple (Gemme 3)
        vTemple.addItem(new Item("gemme3", "la Gemme du Courage (Verte)", 1.0));
        
        // Plaine
        vPlaine.addItem(new Item("bouclier", "un bouclier de soldat abandonné", 4.5));

        // Forge
        vForge.addItem(new Item("socle", "le socle sacré de l'épée des âmes", 50.0));
        
        this.aPlayer = new Player ("Hero", vVillage);
    } // createRooms()

    /**
     * Interprète la commande donnée (exécutée par l'interface graphique).
     * @param pCommandLine La chaîne de commande tapée par le joueur.
     */
    public void interpretCommand( final String pCommandLine ) 
    {
        this.aGui.println( "> " + pCommandLine ); 
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
        else if (vCommandWord.equals("back")){
            this.back(vCommand);
        }
        else if ( vCommandWord.equals( "quit" ) ) {
            if ( vCommand.hasSecondWord() )
                this.aGui.println( "Quit what?" );
            else
                this.endGame();
        }
        else if (vCommandWord.equals("test")){
            this.test(vCommand);
        }
        else if (vCommandWord.equals("take")){
            this.take(vCommand);
        }
        else if (vCommandWord.equals("drop"))
        {
            this.drop(vCommand);
        }
        
    } // interpretCommand(.)
    
    /**
     * Affiche les informations sur le lieu courant (description + image).
     */
    private void printLocationInfo()
    {
        this.aGui.println( this.aPlayer.getCurrentRoom().getLongDescription() );
        // Affiche l'image si elle existe
        if ( this.aPlayer.getCurrentRoom().getImageName() != null ) {
            this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
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
        Room vNextRoom = this.aPlayer.getCurrentRoom().getExit( vDirection );

        if ( vNextRoom == null ) {
            this.aGui.println( "There is no door!" );
        }
        else {
            this.aPlayer.move(vNextRoom);
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
    
    /**
     * commande 'back'.
     * Permet de revenir à la pièce précédente.
     * @param pCommand La commande saisie par l'utilisateur.
     */
    private void back(final Command pCommand)
    {
        if (pCommand.hasSecondWord()){
            this.aGui.println("back where?");
            return;
        }
        
        if (this.aPlayer.canGoBack()){
            this.aPlayer.back();
            this.printLocationInfo();
        }
        else{
            this.aGui.println("Retour en arriere imporssible");
        }
    }
    
    /**
     * Exécute une série de commandes lues depuis un fichier texte.
     * @param pCommand La commande contenant le nom du fichier.
     */
    private void test(final Command pCommand)
    {
        if(!pCommand.hasSecondWord()){
            this.aGui.println("test what?");
            return;
        }
        
        String vFileName = pCommand.getSecondWord();
        try {
            Scanner vScanner = new Scanner(new File(vFileName));
            this.aGui.println(" test "+ vFileName +" :");
            while (vScanner.hasNextLine()){
                String vLine = vScanner.nextLine();
                this.interpretCommand(vLine);
            }
            vScanner.close();
            this.aGui.println("  Test fini ");
        }
        catch (FileNotFoundException pExpeption){
            this.aGui.println("Fichier non trouvé : "+ vFileName);
        }
    }
    private void take(final Command pCommand)
    {
        if(!pCommand.hasSecondWord()){
            this.aGui.println("take what?");
            return;
        }
        
        String vItem = pCommand.getSecondWord();
        Item vItemInRoom = this.aPlayer .getCurrentRoom().getItem(vItem);
        if(vItem == null){
            this.aGui.println(vItem + " n'est pas present dans cette pièce");
            return;
        }
        this.aPlayer.take(vItem);
        this.aGui.println("vous aves ramassé : "+ vItem);
    }
    
    private void drop(final Command pCommand)
    {
        if (!pCommand.hasSecondWord()) {
            this.aGui.println("drop what?");
            return;
        }
        String vItemName = pCommand.getSecondWord();
        Item vItemCarried = this.aPlayer.getCurrentItem();

      
        if (vItemCarried == null) {
            this.aGui.println("Vous ne portez pas d'objet");
            return;
        }
        this.aPlayer.drop(vItemName);
        this.aGui.println("Vous avez posé : "+vItemName);
    }
} // GameEngine
