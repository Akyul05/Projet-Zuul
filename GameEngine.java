import java.util.Stack;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

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
    private int aChrono;
    private int aTime = 50;
    private HashMap<String , Room> aRooms;
    private boolean aTestMode;
    
    /**
     * Constructeur pour les objets de la classe GameEngine.
     * Crée le parseur et la carte du jeu.
     */
    public GameEngine()
    {
        this.aParser = new Parser();
        this.aRooms = new HashMap<String, Room>();
        this.createRooms();
        this.aChrono = 0;
        this.aTestMode = false;
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
     * Crée une piece etl'ajoute à la HashMap.
     * @param pName Le nom de la piece
     * @param pDescription La description de la piece
     * @param pImage l'image
     * @return La Room créé.
     */
    private Room createRoom(final String pName, final String pDescription, final String pImage)
    {
        Room vRoom = new Room(pDescription, pImage);
        this.aRooms.put(pName, vRoom);
        return vRoom;
    }
    /**
     * Crée toutes les pièces et relie leurs sorties.
     */
    private void createRooms()
    {
        // Création des lieux avec des images (noms de fichiers temporaires)
       Room vVillage = this.createRoom("Village","dans les vestiges de votre village, un lieu pauvre mais abritant l'Érudit.", "village.png");
        Room vForet = this.createRoom("Foret","dans la Forêt des Murmures, où l'on peut trouver quelques herbes médicinales.", "foret.png");
        Room vRuines = this.createRoom("Ruines","aux Ruines Anciennes, un lieu de pierre brisée hanté par un Garde Spectral.", "ruines.png");
        Room vTour = this.createRoom("Tour","devant une Tour Effondrée qui semble toucher les nuages.", "tour.png");
        Room vRiviere = this.createRoom("Riviere","au bord de la Rivière des Ombres, l'eau est sombre et calme.", "riviere.png");
        Room vCaverne = this.createRoom("Caverne","dans la Caverne de Cristal, les parois scintillent d'une lueur étrange.", "caverne.png");
        Room vTemple = this.createRoom("Temple","dans le Temple Oublié, un lieu sacré envahi par la végétation.", "temple.png");
        Room vPlaine = this.createRoom("Plaine","sur la Plaine Stérile, un champ de bataille abandonné.", "pleines.png");
        Room vSanctuaire = this.createRoom("Sanctuaire","devant le Sanctuaire Scellé, une grande porte verrouillée qui réagit aux gemmes.", "sanctuaire.png");
        Room vForge = this.createRoom("Forge","dans la Forge des Âmes, l'enceinte circulaire où repose le socle de l'épée brisée.", "forge.png");
        
        List<Room> vRooms = new ArrayList<Room>(this.aRooms.values());
        RandomRoom vRandomizer = new RandomRoom(vRooms);
        
        TransporterRoom vCercleRunique = new TransporterRoom("au centre d'un cercle de pierres ancestrales gravées de runes pulsantes. L'air crépite d'une magie instable et le paysage semble changer à chaque clignement d'œil.","runes.png",vRandomizer);
        this.aRooms.put("CercleRunique",vCercleRunique );
        vVillage.setExit("north",vCercleRunique);
        
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
        
        //vCaverne.setExit("up", vTemple);
        
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
        
        vForet.addItem(new Item("herbes", "des herbes médicinales luminescentes qui senbles donner des forces", 0.5));

        // Ruines (Gemme 1)
        vRuines.addItem(new Item("gemme1", "la Gemme de Force (Rouge)", 1.0));

        // Caverne (Gemme 2)
        vCaverne.addItem(new Item("gemme2", "la Gemme de l'Esprit (Bleue)", 1.0));
        
        // Temple (Gemme 3)
        vTemple.addItem(new Item("gemme3", "la Gemme du Courage (Verte)", 1.0));
        
        // Plaine
        vPlaine.addItem(new Item("bouclier", "un bouclier de soldat abandonné", 4.5));

        // Forge
        vForge.addItem(new Item("socle", "le socle sacré de l'épée des âmes", 50.0));
        
        Item vBeamer = new Beamer("beamer","entregistre la piece actuelle et vous permet d'y revenir une fois declanché",2.5);
        vTemple.addItem(vBeamer);
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
            this.eat(vCommand);
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
        else if (vCommandWord.equals("items"))
        {
            this.items(vCommand);
        }
        else if (vCommandWord.equals("charge")) {
            this.charge(vCommand);
        }
        else if (vCommandWord.equals("fire")) {
            this.fire(vCommand);
        }
        else if (vCommandWord.equals("alea")){
            this.alea(vCommand);
        }
        
        if (!vCommandWord.equals("quit")){
            this.aChrono++;
        }
        if(this.aChrono >= aTime){
            this.aGui.println("TEMPS ECOULE ");
            this.aGui.println("Aelyndor sombre dans les tenebres");
            this.endGame();
        }
    } // interpretCommand(.)
    
    /**
     * Affiche les informations sur le lieu courant (description + image).
     */
    private void printLocationInfo()
    {
        this.aGui.println( this.aPlayer.getCurrentRoom().getLongDescription() );
        int vTempRestant = aTime - this.aChrono;
        this.aGui.println("Temps restant :"+ vTempRestant);
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
    private void eat(final Command pCommand)
    {
        if (!pCommand.hasSecondWord()){
            this.aGui.println("Manger quoi ?");
        }
        String vManger = this.aPlayer.eat(pCommand.getSecondWord());
        this.aGui.println(vManger);
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
            this.checkVictory();
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
        this.aTestMode = true;
        
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
        this.aTestMode=false;
        this.aGui.println("test fini");
        
    }
    private void take(final Command pCommand)
    {
        if(!pCommand.hasSecondWord()){
            this.aGui.println("prendre quoi?");
            return;
        }
        
        String vItem = pCommand.getSecondWord();
        Item vItemInRoom = this.aPlayer .getCurrentRoom().getItem(vItem);
        if(vItemInRoom == null){
            this.aGui.println(vItem + " n'est pas present dans cette pièce");
            return;
        }
        boolean vTake = this.aPlayer.take(vItem);
        if (vTake == true){
            this.aGui.println("Vous avez ramassé : " + vItem);
        }
        else {
            this.aGui.println("Trop lourd !! (capacité :)"+ this.aPlayer.getMaxWeight()+ "kg)"+"\n consommez des herbes medicinales pour augmenter votre capacité");
        }
        
    }
    
    private void drop(final Command pCommand)
    {
        if (!pCommand.hasSecondWord()) {
            this.aGui.println("drop what?");
            return;
        }
        String vItem = pCommand.getSecondWord();
        Item vItemCarried = this.aPlayer.getItem(vItem);

      
        if (vItemCarried == null) {
            this.aGui.println("Vous ne portez pas d'objet");
            return;
        }
        this.aPlayer.drop(vItem);
        this.aGui.println("Vous avez posé : "+vItem);
    }
    private void items (final Command pCommand)
    {
        this.aGui.println(this.aPlayer.getInventoryString());
    }
    
    private void charge(final Command pCommand)
    {
        if (!pCommand.hasSecondWord()) {
            this.aGui.println("charger quoi");
            return;
        }
        
        String vCharge = this.aPlayer.charge(pCommand.getSecondWord());
        this.aGui.println(vCharge);
    }
    private void fire(final Command pCommand)
    {
        if (!pCommand.hasSecondWord()) {
            this.aGui.println("declancher quoi?");
            return;
        }
        String vFire = this.aPlayer.fire(pCommand.getSecondWord());
        this.aGui.println(vFire);
        printLocationInfo();
} 
    /**
     * Commande Alea utilisable uniquement lors d'un test
     * Force la sortie de la TransporterRoom a une destination precisé.
     */
    private void alea(final Command pCommand)
    {
        if (!this.aTestMode) {
            this.aGui.println("Attention !! Commande réservée aux tests");
            return;
        }
        Room vRoom = this.aRooms.get("CercleRunique");
        if(vRoom instanceof TransporterRoom){
            TransporterRoom vTransporter = (TransporterRoom) vRoom;
            if(pCommand.hasSecondWord()){
                String vRoomName = pCommand.getSecondWord();
                Room vTargetRoom = this.aRooms.get(vRoomName);
                
                if (vTargetRoom != null){
                    vTransporter.setAlea(vTargetRoom);
                }
                }
            }
    
    }
    /**
     * Vérifie si les conditions de victoire sont réunies.
     */
    private void checkVictory()
    {
        if (this.aPlayer.getCurrentRoom().getImageName().equals("forge.png")) {
            
            if (this.aPlayer.victoire()) {
                this.aGui.println("\n***************************************");
                this.aGui.println("               VICTOIRE !                ");
                this.aGui.println("   Vous placez les gemmes sur le socle...");
                this.aGui.println("   L'épée des Âmes est reforgée !        ");
                this.aGui.println("   Aelyndor est sauvé grâce à vous.      ");
                this.aGui.println("*****************************************");
                
                this.endGame();
            }
            else {
                this.aGui.println("Vous êtes à la Forge, mais il vous manque des éléments pour réparer l'épée...");
            }
        }
    }
}// GameEngine
