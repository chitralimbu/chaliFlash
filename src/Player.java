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
public class Player extends Recognise {

    public Player(String name, Boolean human, List<Card> cards, int cash, int minBoot, boolean seen, double chance, int toalBoot) {
        super(name, human, cards, cash, minBoot, seen, chance, toalBoot);
    }
    
    //player folds method
    public void fold(){
        this.minBoot = 0;
        this.chance = 0;
    }
    
    //calculates the chance if all players have seen to either show play or raise, 
    public int seePlayRaise(){
        double pChance = (double) getChance() / 100;
        double d = (double)Math.random();
        double show = (double) 1/3 - ((double)1/3 * pChance);
        double play = (double)((1 - show) / 2) + show;
        double raise = (double) 1;
        int selected = 0;
        if(d < show){
            selected = 0;
        }
        else if(d < play){
            selected = 1;
        }
        else if(d < raise){
            selected = 2;
        }
        //System.out.println("Show: " + show + "\nplay: " + play + "\nraise: " + raise);
        return selected;
    }
    
    //method to change the chane of all the players depending on if they have seen.
    public void incDecChance(List<Player> allPlayers){
        for(Player x: allPlayers){
            //System.out.println(x.getName() + " LOOP NAME " + getName() + "CURRENT PLAYER NAME");
            if(x.getName() != getName()){
                
                if(x.getSeen()){
                    double chance = x.getChance();
                    double decreaseBy = (double) chance * 0.25;
                    double newChance = (double) chance - decreaseBy;
                    x.setChance(newChance);
                }
                if(!x.getSeen()){
                    double chance = x.getChance();
                    double increaseBy = (double) chance * 0.25;
                    double newChance = (double) chance + increaseBy;
                    x.setChance(newChance);
                }
                
            }
        }
    }
    
    //calculates the chance whether player will see or not see
    public boolean see_Play(double chance){
        double d = Math.random() * 100;
        //System.out.println("initial chance: " + chance);
        //System.out.println("\nchance d = " + d);
        if(d < chance){
            return true;
        }else{
            return false;
        }
    }
    
    //method for human to choose what they want to do
    public void humanPlay(List<Player> finalPlayers){
        System.out.println("\nYou have seen, press, 0 to show, 1 to play, 2 to raise, 3 to fold \n");
        Scanner sc = new Scanner(System.in);
        
        int num = sc.nextInt();
        setSeen(true);
        if(num == 3){
            System.out.println("YOU HAVE DECIDED TO FOLD");
            fold();
        }else{
            allSeenMoves(num, finalPlayers);
            }
    }
    
    //method to update cash
    public void updateCash(){
        this.cash = getCash() - getBoot();
    }
    
    ///probablydontneed
    public void seenraise(){
        setCash(getCash() - (getBoot() * 2));
        //updateCash();
    }
    
    //update the total boot
    public void updateTotalBoot(List<Player> finalPlayers, int totalBoot){
        for(Player p: finalPlayers){
            p.setTotalBoot(totalBoot);
        }
    
    }
    
    //method to update minium boot for all players
    public void updateMinBoot(List<Player> finalPlayers, int newBoot){
        for(Player p: finalPlayers){
            if(p.getSeen()){
                p.setBoot(newBoot * 2);
            } 
            else{
                //System.out.println("THIS SHOULD NOT BE DOUBLED" + newBoot);
                p.setBoot(newBoot);
        }
        }
    }
    
    //once all AI/Players have seen, AI chooses to either show, raise or play
    public void allSeenMoves(int selected, List<Player> finalPlayers){
        
        if(getBoot() * 2 >= getCash()){
            System.out.println("YOU DO NOT HAVE ENOUGH CASH TO RAISE, CAN ONLY PLAY, PLAYING...");
            System.out.println("Cash: " + getCash() + " To raise: " + getBoot()*2);
            selected = 1;
        }
        
        if(selected == 0){
            System.out.println(getName() + "Has decided to show game now ends\n");
            updateMinBoot(finalPlayers, getBoot());
            //update the total boot for everyone as player has decided to show
            updateTotalBoot(finalPlayers, getTotalBoot() + getBoot());
            //updatecash double
            updateCash();
        }
        if(selected == 1){
            System.out.println(getName() + "Has decided to play without Raising\n");
                                
            updateTotalBoot(finalPlayers, getTotalBoot() + getBoot());
            updateCash();
        }
        if(selected == 2){
            System.out.println(getName() + "Has decided to raise\n");
                                
            updateMinBoot(finalPlayers, getBoot());
            updateTotalBoot(finalPlayers, getTotalBoot() + getBoot());
            updateCash();
        }
    }
    
    //if a player has seen, method to see whether player wants to raise or play on
    public void playerSeenMoves(List<Player> finalPlayers){
        double d = Math.random() *100;
        
        if(getBoot() * 2 >= getCash()){
            System.out.println("YOU DO NOT HAVE ENOUGH CASH TO RAISE, CAN ONLY PLAY");
            System.out.println("Cash: " + getCash() + " To raise: " + getBoot()*2);
            d = 40;
        }
        
        if(d < 50){
            System.out.println(getName() + "Has decided to play without Raising\n");
            incDecChance(finalPlayers);                    
            updateTotalBoot(finalPlayers, getTotalBoot() + getBoot());
            updateCash();
        }else{
            System.out.println(getName() + "Has decided to raise\n");
            incDecChance(finalPlayers);                    
            updateMinBoot(finalPlayers, getBoot());
            updateTotalBoot(finalPlayers, getTotalBoot() + getBoot());
            updateCash();
        }
        
    }
    

    
    
    

    
//what moves can player have
//play 
//see 
//fold
//deal

    
}
