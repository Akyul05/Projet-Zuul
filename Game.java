 
/**
 * Classe Game - le moteur du jeu d'aventure Zuul.
 *
 * @author Ghezli Ramy
 */
public class Game
{
    private UserInterface aGui;
    private GameEngine aEngine;

    /**
     * Crée le jeu et initialise la carte interne.
     */
    public Game() 
    {
        this.aEngine = new GameEngine();
        this.aGui = new UserInterface( this.aEngine );
        this.aEngine.setGUI( this.aGui );
    } // Game()
} // Game
