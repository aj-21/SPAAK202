import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import java.util.Map;


import java.awt.Color;
/**
 * Write a description of class GuessWho here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GuessWho extends StatefulWorld
{
    
    GameSession gameSession;

    GameState currentState; 
    GameState guessWhoState;
    GameState scoreState;
    
    

    public GuessWho(GameSession gameSession)
    {    
        super(1536, 864, 1); 
        this.gameSession = gameSession;
        
        guessWhoState = new GuessWhoState(this,gameSession);
        scoreState = new ScoreState(this,gameSession);
        
        
        setup();
    }
    
    private void setup()
    {
        StringImageFactory a = new StringImageFactory();
        a.setTextColor(new Color(128,0,128));
        DummyImage text = new DummyImage(a.createImage("Guess Who",100));
        addObject(text,getWidth()/2,50);
        
        //charCanvas setting
        ZoomContainer playCon = new ZoomContainer(gameSession.getPlaySet());
        playCon.resizeOnScale(0.8);   
        
        //use this to for chain of responsility
        DisplayCanvas charCanvas = new IDisplayCanvas(gameSession.getPlaySet());
        
        addObject(charCanvas,780,581);
        charCanvas.setBackground("characterCanvas.png").setMargin(3.5,3.5,2.5,2.5).display();     
        
        //myCharCanvas setting
        Character myChar = gameSession.getMe().getChosenChar();
        myChar.resizeOnScale(1.3);
        DisplayCanvas myCharCanvas = new DisplayCanvas(myChar);
        addObject(myCharCanvas,1370,578);
        myCharCanvas.setBackground("yourCharacterCanvas.png").setColRow(1,1).display();  
        
        
        
        
        //suboption canvas setup without any set for display. the display will be handled by UpdateSubOpt class
        DisplayCanvas subOptButCanvas = new IDisplayCanvas();
        addObject(subOptButCanvas,175,580);
        subOptButCanvas.setBackground("subOptionsCanvas.png").setMargin(0,0,2,2);
        //enable unique selection for suboption Button Canvas
        
        ((IDisplayCanvas)subOptButCanvas).addObserver(new UniqueSelection());
        

        
        //updateSubOptionReceiver is responsbile for update subOptionButtonCanvas
        UpdSubOptRcv updSubRcv = new UpdSubOptRcv(subOptButCanvas); 

        //create new option buttons (one time)
        Set<LButton> optButSet = gameSession.getPropertyInfo().getOptButtons();
        for(LButton button : optButSet )
        {            
            //new command for every button
            DisplayCommand cmd = new UpdSubOptCmd(gameSession.getPropertyInfo());
            //this command work with update sub option receiver
            cmd.setReceiver(updSubRcv);
            //the button invokes command when is pressed (select/deselect)
            button.setCommand(cmd);
        }
        
        //optionButtonCanvas setting
        DisplayCanvas optButCanvas = new IDisplayCanvas(optButSet);
        addObject(optButCanvas,630,255);
        optButCanvas.setBackground("optionCanvas.png").setMargin(2.5,2.5,0,0).setColRow(optButSet.size(),1).display();
        
        
        

        //enable press handler with chain of responsibility
        PressHandlerState pressState = new PressHandlerState(guessWhoState);
        pressState.setSuccessor((PressHandler)charCanvas);
        pressState.setSuccessor((PressHandler)subOptButCanvas);
        pressState.setSuccessor((PressHandler)optButCanvas);
        
        guessWhoState = pressState;
        
        //keep either filter or guess
        UniqueSelection guessOrFilter = new UniqueSelection();
        ((IDisplayCanvas)charCanvas).addObserver(guessOrFilter);
        ((IDisplayCanvas)optButCanvas).addObserver(guessOrFilter);
        
        //showProperty setup
        ShowProperty showProperty = new ShowProperty(this,gameSession);
        ((IDisplayCanvas)optButCanvas).addObserver(showProperty);
        ((IDisplayCanvas)subOptButCanvas).addObserver(showProperty);
        ((IDisplayCanvas)charCanvas).addObserver(showProperty);
        
        
        scoreState= new TimeState( scoreState);
        ((TimeState)scoreState).setTimer(5);
        ((TimeState)scoreState).setTimeBoxLoc(this,getWidth()/2,getHeight()/2);
        ((TimeState)scoreState).setTimeBoxText("Please wait\n%d");
        ((TimeState)scoreState).setTimeBoxSize(100);
        
        guessWhoState = new TimeState( guessWhoState);
        ((TimeState)guessWhoState).setTimeBoxLoc(this,1370,260);
        ((TimeState)guessWhoState).setTimer(25);
        setState("guessWhoState");
    }
    
    public GameState getState(String stateName)
    {
        switch (stateName)
        {
            case "guessWhoState": return guessWhoState;
            case "scoreState": return scoreState;
            
            default: return guessWhoState;
        }
        
    }
    
    public void setState(String stateName)
    {
        
        currentState = getState(stateName);
        currentState.enter();
    }
    
    public GameState getCurrentState()
    {
        return currentState;
    }
    
    public boolean isCurrentState(GameState gameState)
    {
        return currentState == gameState;
    }
    
    public void act()
    {
        currentState.stateRun();
    }
}
