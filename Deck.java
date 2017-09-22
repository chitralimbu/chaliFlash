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
public class Deck {
    
    List<Card>  deckOfCards;
    int[] myNumbers = {2,3,4,5,6,7,8,9,10,11,12,13,14};
    
    public List<Card> cardDeck(){
        this.deckOfCards = new ArrayList<Card>();
        Card thisCard;
        
        for(Suits x: Suits.values()){
            for(int y: myNumbers){
                thisCard = new Card(y,x);
                deckOfCards.add(thisCard);
            }
        }
        
        return deckOfCards;
    }
    
}
