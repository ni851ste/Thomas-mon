package de.knxamk.controller;

import de.knxamk.model.GameFrame;
import de.knxamk.model.Player;
import de.knxamk.model.stage.stageComponents.Free;
import de.knxamk.model.stage.stageComponents.StageObject;
import de.knxamk.model.stage.stageComponents.Tree;
import de.knxamk.util.TwoTouple;
import de.knxamk.util.observerPattern.Observable;
import de.knxamk.util.observerPattern.Observer;

import java.util.ArrayList;
import java.util.List;


public class Controller extends Observable
{
    //public enum objects {bounds, free, player, highgrass, tree}
    private ControllerState controllerState;
    private GameFrame gF;
    private Player currPlayer;
    private List<Observer> observers = new ArrayList<>();

    public Controller(GameFrame gF, Player p)
    {
        this.gF = gF;
        this.currPlayer = p;
        this.controllerState = ControllerState.MOVE;
    }

    public boolean move(char direction)
    {
        if (!controllerState.equals(ControllerState.MOVE))
        {
            System.out.println("state failure");
            return false;
        }
        int newWidth, newHeight;

        newWidth = currPlayer.currentMap.currentPos.get(0);
        newHeight = currPlayer.currentMap.currentPos.get(1);

        switch (direction)
        {
            case 'N':
                ++newHeight;
                break;
            case 'W':
                --newWidth;
                break;
            case 'S':
                --newHeight;
                break;
            case 'E':
                ++newWidth;
                break;
        }

        if (currPlayer.currentMap.isMapBlockFree(newWidth, newHeight))
        {
            currPlayer.currentMap.changePosition(newWidth, newHeight);
            notifyObservers();
            return true;

        }

        return false;
    }

    public TwoTouple<Integer> getPosition()
    {
        return currPlayer.currentMap.currentPos;
    }

    public String getObjectCurrMap(int w, int h)
    {
        StageObject mO = currPlayer.currentMap.specMapObject(w, h);
        if (mO == null)
            return "bounds";
        if (mO instanceof Player)
            return "player";
        if (mO instanceof Free)
            return "free";
        if (mO instanceof Player)
            return "player";
        if (mO instanceof Tree)
            return "tree";
        System.exit(2);
        return null;
    }
}
