import greenfoot.*;
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
//import java.util.Observer;
//import java.util.Observable;
/**
 * Write a description of class IGuessWhoState here.
 * 
 * @author SPAAK 
 * 
 */
public class GuessWhoState implements GameState
{
    PressHandler successor;
    GuessWho world;

    
    public GuessWhoState(GuessWho world)
    {
        
        this.world = world;

    }
 

    
    public void enter()
    {

    }
    
    public void stateRun()
    {

    }
    
    public void exit()
    {
        world.setState("scoreState");
    }

    
}
