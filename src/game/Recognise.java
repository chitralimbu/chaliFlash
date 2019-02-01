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

//class to calculate mostly the ai actions. 
public class Recognise extends ChaliFlash{
    
    boolean bool;
    String combo;
    int intCombo;
    double winPercentage;

    public Recognise(String name, Boolean human, List<Card> cards, int cash, int minBoot, boolean seen, double chance, int toalBoot) {
        super(name, human, cards, cash, minBoot, seen, chance, toalBoot);
    }

    public boolean trail(int[] cards){
        int temp = cards[0];
        boolean bool = true;
        for(int i : cards){
            if(i == temp){
                bool = true;
            }else{
                bool = false;
                break;
            }
        }
       return bool;
    }
    
    public boolean run(int[] cards){
        int temp = cards[1];
        boolean bool = false;
        int count = 0;
        Arrays.sort(cards);
        //System.out.println(Arrays.toString(cards));
        //System.out.println(temp);
        
        for(int i=0; i < cards.length-1; i++){
            //System.out.println(cards[i]);
            //System.out.println(cards[i]);
            if(cards[i] + 1 == temp){
                temp = cards[2];
                count+=1;
                //System.out.println(count);
            }
            if(count == 2){
                bool = true;
            }
        }
        
        //System.out.println(bool);
        return bool;
    }
    
    public boolean pair(int[] cards){
        boolean bool = false;
        //System.out.println(cards.length);
            if(cards[0] == cards[1]){
                bool = true;
            }
            if(cards[0] == cards[2]){
                bool = true;
            }
            if(cards[1] == cards[2]){
                bool = true;
            }
        
        //System.out.println(bool);
        return bool;
    }
    
    public boolean flush(){
        boolean bool = false;
        this.cards = getCards();
        Suits mySuit = cards.get(0).getSuit();
        
        if(cards.get(1).getSuit() == mySuit && cards.get(2).getSuit() == mySuit){
            bool=true;
        }
        //System.out.println("current suit: " + mySuit + " : " + cards.get(0).getNumber());
        return bool;
    }
    
    public boolean flushRun(){
        boolean bool = false;
        int[] intCards = {cards.get(0).getNumber(), cards.get(1).getNumber(), cards.get(2).getNumber() };
        Arrays.sort(intCards);
        if(run(intCards) && flush()){
            bool = true;
        }
        
        
        return bool;
    }
    
    
    public String recogCard(){
        this.cards = getCards();
        int[] intCards = {cards.get(0).getNumber(), cards.get(1).getNumber(), cards.get(2).getNumber() };
        Arrays.sort(intCards);
        boolean stop = true;
        //boolean combination = pair(cards);
        while(stop){
            if(trail(intCards)){
                this.combo = "trail";
                //System.out.println("trail");
                break;
            }
            if(flushRun()){
                this.combo = "flush run";
                //System.out.println("trail");
                break;
            }
            if(run(intCards)){
                this.combo = "run";
                //System.out.println("run");
                break;
            }
            if(flush()){
                this.combo = "flush";
                //System.out.println("trail");
                break;
            }
            if(pair(intCards)){
                this.combo = "pair";
                //System.out.println("pair");
                break;
            }else{
               combo = "high card";
               break;
            }
            
        }
    return combo;
    }
    
    public int convert(){
        this.combo = recogCard();
        
        String[] possCombo = {"high card", "pair", "flush" ,"run", "flush run" ,"trail"};
        
        for(int i = 0; i < possCombo.length; i++){
            if(combo == possCombo[i]){
                intCombo = i + 1;
            }
        }
        return intCombo;
    }
    
    public int chanceBrace(){
        this.intCombo = convert();
        int chance = 0;
        if(intCombo == 1){
            chance = 50;
        }
        if(intCombo == 2){
            chance = 70;
        }
        if(intCombo == 3){
            chance = 140;
        }
        if(intCombo == 4){
            chance = 280;
        }
        if(intCombo == 5){
            chance = 560;
        }
        if(intCombo == 6){
            chance = 1120;
        }
        return chance;
    }
    
    public double calcWinPercentage(){
        double cN = convert();
        double a = (double)(getCards().get(0).getNumber() -1) / 13;
        double b = (double)(getCards().get(1).getNumber() -1) / 13;
        double c = (double)(getCards().get(2).getNumber() - 1) / 13;
        //System.out.println("a: " + a + " b: " + b + " c: " + c);
        //System.out.println(cN);
        double multiple = a*b*c;
        double next_multiple = (double)1/6 * multiple;
        double minus = (double)1/6-next_multiple;
        double next_minus = (double)cN/6 - minus;
        this.winPercentage = (double)next_minus;
        
        //System.out.println(" multiple: " + multiple + "\n next_multiple: " + next_multiple + "\n minus: " + minus + "\n next_minus: " + next_minus);
        
        return winPercentage * 100;
    }
    
    
}
