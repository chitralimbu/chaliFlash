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
public class Card {
    
    int number;
    Suits suit;
    
    public Card(int num, Suits suit){
        this.number = num;
        this.suit = suit;
    }
    
    public Suits getSuit(){
        return suit;
    }
    
    public int getNumber(){
        return number;
    }
    
    public void setSuit(Suits suit){
        this.suit = suit;
    }
    
    public void setNum(int num){
        this.number = num;
    }
    
    public String faceCards(){
        
        Map<Integer, String> faceCard = new HashMap<Integer, String>();
        faceCard.put(11, "J");
        faceCard.put(12, "Q");
        faceCard.put(13, "K");
        faceCard.put(14, "A");
        
        if(faceCard.containsKey(getNumber())){
            return faceCard.get(getNumber());
        }
        else{
            return Integer.toString(getNumber());
        }
        
    }
    
}
