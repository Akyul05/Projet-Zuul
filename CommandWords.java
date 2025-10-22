 

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration table of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kolling and David J. Barnes + D.Bureau
 * @version 2008.03.30 + 2019.09.25
 */
public class CommandWords
{
    // a constant array that will hold all valid command words
    private final String[] aValidCommands;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        this.aValidCommands = new String[5];
        this.aValidCommands[0] = "go";
        this.aValidCommands[1] = "help";
        this.aValidCommands[2] = "quit";
        this.aValidCommands[3] = "look";
        this.aValidCommands[4] = "eat";
    } // CommandWords()
    public void showAll()
    {
        for(String vCommand : aValidCommands){
            System.out.println(vCommand + " ");
        }
        System.out.println();
    }//showAll()
    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand( final String pString )
    // pour vérifier si pString est une commande valide
    {
        for ( int vI=0; vI<this.aValidCommands.length; vI++ )
        // aValidCommands.length : vaut le nombre de cases du tableau, ici 3
        // vI++ est strictement équivalent à vI = vI+1 (exécuté à la fin de chaque tour de boucle)
        // donc, POUR vI prenant les valeurs 0, 1, 2, FAIRE :
        {
            if ( this.aValidCommands[vI].equals( pString ) )
            // [vI] pour indiquer qu'on veut accéder à la case n°vI du tableau
                return true;
                // termine la méthode et retourne vrai puisqu'on a trouvé le mot parmi les commandes valides
        } // for
        // if we get here, the string was not found in the commands :
        return false;
    } // isCommand()
} // CommandWords
