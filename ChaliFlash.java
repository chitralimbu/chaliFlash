/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;
import java.util.*;
/**
 *
 * @author Niku
 */

//for the rules of the game.
public abstract class ChaliFlash implements Moves{
    public int minBoot;
    public String name;
    public Boolean human;
    public List<Card> cards;
    public int cash;
    boolean seen;
    double chance;
    int totalBoot;
    
    
    public ChaliFlash(String name, Boolean human, List<Card> cards, int cash, int minBoot, boolean seen, double chance, int toalBoot) {
        this.name = name;
        this.human = human;
        this.cards = cards;
        this.cash = cash;
        this.minBoot = minBoot;
        this.seen = seen;
        this.chance = chance;
        this.totalBoot = totalBoot;
    }
    
    public boolean getSeen(){
        return seen;
    }
    
    public double getChance(){
        return chance;
    }
    
    public void setSeen(boolean seen){
        this.seen = seen;
    }
    
    public void setChance(double chance){
        this.chance = chance;
    }
    
    public void setCards(List<Card> myCards){
        this.cards = myCards;
    }
    
    //calculate win percentage
    public void calculate(int[] MyCards){
        
    }
    
    public int getCash(){
        return cash;
    }
    
    public void setCash(int cash){
        this.cash = cash;
    }
    
    public List<Card> getCards(){
        return cards;
    }

    public Boolean getHuman(){
        return human;
    }

    public String getName(){
        return name;
    }
    
    public void setBoot(int boot){
        this.minBoot = boot;
    }
    
    public int getBoot(){
        return minBoot;
    }
    
    public void setTotalBoot(int totalBoot){
        this.totalBoot = totalBoot;
    }
    
    public int getTotalBoot(){
        return totalBoot;
    }
    
    //recognising what type of combination a player has.
    //new class
   
}
