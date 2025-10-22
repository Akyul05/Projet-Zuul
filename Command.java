 
/**
 * Classe Command - une commande du jeu d'aventure Zuul.
 *
 * @author Ghezli Ramy
 */
public class Command
{
    private String aCommandWord; 
    private String aSecondWord;
    /**
     * Constrcteur pour les objets de la classe Command.
     * @param pCommandWord Le premier mot de la commande.
     * @param pSecondWord Le second mot de la commande.
     */
    public Command(final String pCommandWord,final String pSecondWord)
    {
        this.aCommandWord = pCommandWord;
        this.aSecondWord = pSecondWord;
        
    }//Command()
    /**
     * @return Le premier mot de la commande.
     */
    public String getCommandWord()
    {
        return this.aCommandWord;
    }//getCommandWord()
    /**
     * @return Le second mot.
     */
    public String getSecondWord()
    {
        return this.aSecondWord;
    }//getSecondWord()
    /**
     * Vérifie si la commande a un second mot.
     * @return true si un second mot existe ou false sinon.
     */
    public boolean hasSecondWord()
    {
        return this.aSecondWord != null ;
    }//hasSecondWord()
    /**
     * Vérifie si la commande est inconnue.
     * @return true si le premier mot est null ou false sinon.
     */
    public boolean isUnknown()
    {
        return this.aCommandWord == null ; 
        
    }// isUnknown()
   
} // Command
