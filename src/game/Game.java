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
public class Game{

    private static Scanner scanner;
	private static Scanner scan;
	private static Scanner scan2;

	/**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int myCash = 10000;
        int boot = 10;
        boolean seen = false;
        double chance = 0;
        int totalBoot = 0;
        
        List<Card> newCard = new ArrayList<Card>();
        Player chitra = new Player("chitra", true, newCard , myCash , boot, seen, chance, totalBoot);
        Player niku = new Player("niku", false, newCard, myCash , boot , seen, chance, totalBoot );
        Player bob = new Player("bob", false, newCard , myCash , boot , seen, chance, totalBoot );
        Player sam = new Player("sam", false, newCard , myCash , boot, seen, chance, totalBoot);
        Player tom = new Player("tom", false, newCard , myCash , boot, seen, chance, totalBoot);
        int currentMove = 0;
        
        while(true){
            
            List<Player> myallPlayers = new ArrayList<Player>();
            myallPlayers.add(chitra);
            myallPlayers.add(niku);
            myallPlayers.add(bob);
            myallPlayers.add(sam);
            myallPlayers.add(tom);
            System.out.println("--------CurrentMove++++++++++++ " + currentMove);
            //check to ensure that the same player doesnt get dealt the first card everytime
            
            removeLoser(myallPlayers);
            
            Collections.rotate(myallPlayers, currentMove);
            
            System.out.println("Starting a new game ..... Press \"ENTER\" to continue...");
            scanner = new Scanner(System.in);
            scanner.nextLine();
            
            
            
            System.out.println("mayllPlayers Size: " + myallPlayers.size());
            List<Player> finalPlayers = setCardsPlayers(initialiseShuffleDeal(myallPlayers), myallPlayers);
            //System.out.println("\nCURRENT FIRST DEALT PLAYER: " + finalPlayers.get(0).getName());
            System.out.println("\noldFinalPlayers Size: " + finalPlayers.size());
            
            
           
            boolean turnBool = true;
            //setting initial chances of seeing based on players money.
            setChanceBootSeen(finalPlayers, myCash, boot, false);
            //counting turns
            int myCount = 0;
            //collecting boot at the beginning of a game, the rest will be collected when the player decides his move
            //int bootAll = collectBoot(finalPlayers);
            
            updateTotalBoot(finalPlayers, collectBoot(finalPlayers));
            System.out.println("TOTAL BOOT COLLECTED: " + finalPlayers.get(0).getTotalBoot());
            printPlayers(finalPlayers);        
            while(turnBool){
                //current turn
                System.out.println("\nCURRENT TURN IS: " + myCount);
                System.out.println("*******************************************");
                //check to see if all players have seen and notify 
                if(allSeen(finalPlayers)){
                    System.out.println("ALL PLAYERS HAVE SEEN");
                    System.out.println("-----------------------------------------\n");
                }
                
                //printPlayers(finalPlayers);
                
                //start of each players turn, for loop
                for(Player p: finalPlayers){
                    System.out.println("\nTOTAL CURRENT BOOT: " + p.getTotalBoot());
                    //check to see if they are only player left, if so then break, 
                    if(checkOnlyPlayer(finalPlayers)){

                        break;
                    }
                    //check if player has enough money
                    if(p.getBoot() >= p.getCash()){
                        System.out.println(p.getName() + " doesnt have enough cash he has to fold\n");
                        //System.out.println("Breaking Loop 2");
                        p.fold();
                        continue;
                    }
                    
                    //human player method starts here -------
                    if(p.getHuman()){
                        printPlayers(finalPlayers);
                        if(human(p, finalPlayers)){
                            turnBool = false;
                            break;
                        }
                        printPlayers(finalPlayers);
                        continue;
                    }
                    //check to see if al players have seen 
                    if(allSeen(finalPlayers)){
                        if(p.see_Play(p.getChance())){
                            //now that all playrers have seen, player either, show, play, raise
                            int selected = p.seePlayRaise();

                            //run all seen moves
                            p.allSeenMoves(selected, finalPlayers);

                            //if selected is 0 then current game ends break for loop and end game.
                            if(selected == 0){
                                //select winner from current finalplayer
                                //printPlayers(finalPlayers);
                                finalWinners(finalPlayers, p.getBoot());
                                turnBool = false;

                                break;
                            }
                            //if player wants to play on without raising
                            
                        }else{

                            System.out.println(p.getName() + " Has decided to fold\n");
                            printCards(p.getCards(), p);
                            p.fold();
                        }
                    }else{
                        //if all players have not seen then, if current player has not seen
                        if(!p.getSeen()){
                            //check to see if player wants to see or continue
                            if(p.see_Play(p.getChance())){
                                //player has decided to see, now change his chance depending on combo
                                p.setChance(p.chanceBrace());
                                System.out.println(p.getName() + " has seen, changing chance\n");
                                //System.out.println("P.getname: " + p.getName() + " p.getchance: " + p.getChance());
                                
                                //set seen to true
                                p.setSeen(true);
                                //player has seen chance has been changed, see if he wants to play now
                                if(p.see_Play(p.getChance())){                                  
                                    p.setBoot(p.getBoot() * 2);
                                    p.playerSeenMoves(finalPlayers);
                                    
                                    
                                }else{
                                    System.out.println(p.getName() + " Has decided to fold\n");
                                    printCards(p.getCards(), p);
                                    p.fold();
                                }
                            }else{
                                System.out.println(p.getName() + " has decided not to see\n");
                                //System.out.println(p.getName() + " chance: " + p.getChance());
                                p.updateTotalBoot(finalPlayers, p.getTotalBoot() + p.getBoot());
                                //p.setCash(p.getCash() - p.getBoot());
                                p.updateCash();
                                

                            }
                        //if player has seen
                        }else{
                            //if player wants to play
                            if(p.see_Play(p.getChance())){
                                p.playerSeenMoves(finalPlayers);
                            }else{
                                System.out.println(p.getName() + " Has decided to fold\n");
                                printCards(p.getCards(), p);
                                p.fold();
                            }
                        }
                       
                    }
                    System.out.println(p.getName() + " : MONEY: " + p.getCash() /* + " CHANCE: " + p.getChance()*/ + " BOOT: " + p.getBoot());
                    System.out.println("=========================================================");
                    //Scanner scan = new Scanner(System.in);
                    //scan.nextLine();
                }//end of for loop
                myCount+=1;
                //remove all players who have folded.
                finalPlayers = checkBoot(finalPlayers);
                
                if(finalPlayers.size() == 1){
                    finalWinners(finalPlayers, finalPlayers.get(0).getTotalBoot());
                    turnBool = false;
                }
                //printPlayers(finalPlayers);
                
            }//end of while loop for current game
            printPlayers(finalPlayers);
            currentMove-=1;
            finalPlayers.clear();
            
        }
    }
    
    public static boolean human(Player p, List<Player> finalPlayers){
        System.out.println("You are human player what will you do");
        if(allSeen(finalPlayers)){
            System.out.println("YOUR CARDS: ");
            printCards(p.getCards(), p);
            System.out.println("\nAll Players Have Seen, press, 0 to show, 1 to play, 2 to raise, 4 to fold ");
            scan = new Scanner(System.in);
            int num = scan.nextInt();
            if(num == 4){
                p.fold();
                return true;
            }else{
                p.allSeenMoves(num, finalPlayers);
                if(num == 0){
                    finalWinners(finalPlayers, finalPlayers.get(0).getTotalBoot());
                    return true;
                }

            }
            
        }else{
            if(p.getSeen()){
                System.out.println("YOUR CARDS: ");
                printCards(p.getCards(),p);
                p.humanPlay(finalPlayers);
            }else{
                System.out.println("You have not seen, press, 0 to play, 1 to see");
                scan2 = new Scanner(System.in);
                int num = scan2.nextInt();
                if(num == 0){
                    System.out.println("YOU HAVE DECIDED TO PLAY, NOT TO SEE");
                    p.updateTotalBoot(finalPlayers, p.getTotalBoot() + p.getBoot());
                    //p.setCash(p.getCash() - p.getBoot());
                    p.updateCash();
                }
                if(num == 1){
                    System.out.println("YOUR CARDS: ");
                    printCards(p.getCards(), p);
                    p.humanPlay(finalPlayers);
                }
            }
        }
        return false;

    }
    
    public static List<Player> removeLoser(List<Player> finalPlayers){
        System.out.println("REMOVING A PLAYER FROM GAME NOT ENOUGH MONEY");
        finalPlayers.removeIf((Player p) -> p.getCash() <= p.getBoot());
        
        return finalPlayers;
    }
    
    public static void updateTotalBoot(List<Player> finalPlayers, int totalBoot){
        for(Player p: finalPlayers){
            p.setTotalBoot(totalBoot);
        }
    }
    
    public static void finalWinners(List<Player> finalPlayers, int bootAll){
        //initialise new aray of winners
                    List<Player> winners = Winner(finalPlayers);
                    //give the winners their winnings
                    setWinnerCash(winners, bootAll);
                    printWinner(winners);
    }
    

    public static boolean checkOnlyPlayer(List<Player> finalPlayers){
        int count = 0;
        for(Player p: finalPlayers){
            if(p.getBoot() != 0){
                count+=1;
            }
        }
        
        if(count == 1){
            return true;
        }else{
            return false;
        }
        
    }

    public static boolean allSeen(List<Player> allPlayers){
        boolean check = true;
        for(Player x: allPlayers){
            if(!x.getSeen()){
                check = false;
            }
        }
        return check;
    }
    
    public static void setChanceBootSeen(List<Player> p, int myCash, int boot, boolean bool){
        double playerCashRatio = 0;
        double percentage = 0;
        double actualSetChance = 0;
        for(Player x: p){
            playerCashRatio = (double) x.getCash()/myCash;
            percentage = (double) playerCashRatio * 30;
            actualSetChance = 30 + (30-percentage);
            x.setChance(actualSetChance);
            x.setBoot(boot);
            x.setSeen(bool);
            //System.out.println("playercashratio: " + playerCashRatio + " percentage: " + percentage + " actualSetChance: " + actualSetChance);
            
        }
        
        
    }

    //delete player if not needed
    public static List<Player> checkBoot(List<Player> allPlayers){
        List<Player> newAllPlayers = allPlayers;
        newAllPlayers.removeIf((Player p) -> p.getBoot() == 0);
        return newAllPlayers;
    }
    
    public static void printHmap(HashMap<Player, Integer> mp){
        mp.entrySet().stream().forEach((m) -> {
            System.out.println(m.getKey().getName() + " times won: " + m.getValue() + " : has total Cash: " + m.getKey().getCash());
        });
    }
    
    public static HashMap<Player, Integer> calculateWins(List<Player> winner, List<Player> allPlayers, HashMap<Player, Integer> hmap){
        
        for(int i =0; i < winner.size(); i++){
            for(Player x: allPlayers){
                if(x.getName() == winner.get(i).getName()){
                    hmap.put(x, hmap.get(x) + 1);
                }
            }
        }
        return hmap;
    }

    public static int collectBoot(List<Player> players){
        int totalBoot = 0;
        for(Player x: players){
            x.setCash(x.getCash() - x.getBoot());
            totalBoot = totalBoot + x.getBoot();
        }
        return totalBoot;
    }
    
    public static void setBoot(List<Player> allPlayers, int boot){
        for(Player x: allPlayers){
            x.setBoot(boot);
        }
    }
    
    public static void setWinnerCash(List<Player> winners, int cash){
        for(Player x: winners){
            //System.out.println("winner: " + (x.getCash() + cash));
            x.setCash(x.getCash() + (cash / winners.size()));
        }
    }
    
    //take dealtCards as arguement from initialiseShuffleDeal static method and assign dealt cards to players. 
    //may need to delete ====================== //
    public static List<Player> setCardsPlayers(List<List<Card>> dealtCards, List<Player> allPlayers){
        int i = 0;
        for(Player x: allPlayers){
            x.setCards(dealtCards.get(i));
            i+=1;
        }
        
        return allPlayers;
        
    }
    //shuffle, initialise, deal cards
    public static List<List<Card>> initialiseShuffleDeal(List<Player> allPlayers){
        List<List<Card>> emptyCards = new ArrayList<List<Card>>();
        Deck d = new Deck();
        
        List <Card> newDeck = d.cardDeck() ;

        Collections.shuffle(newDeck);
        emptyCards = cardDeal(allPlayers, newDeck);
        
        return emptyCards;
    }
    
    //choose winners from the list of players, the winners are chhosen by who has the highest combo returned as an int through the convert method;
    //it is then passed on to the final winner static method to calculate winning pairs between players, and to calculate the highest card if two or more layers have the same combo
    public static List<Player> Winner(List<Player> players){
        List<Player> allPlayers = players;
        List<Player> finalAllPlayer = new ArrayList<Player>();
        SortedSet<Integer> PlayerComboInt = new TreeSet<Integer>();
        
        for(Player x: allPlayers){
            PlayerComboInt.add(x.convert());
            //System.out.println("Players: " + x.getName());
        }

        int largest = PlayerComboInt.last();

        for(Player x: allPlayers){
            if(x.convert() == largest){
                //System.out.println("player name: " + x.getName());
                finalAllPlayer.add(x);   
            }
        }
        return finalWinnerChoice(finalAllPlayer);
    }
    
    //shortcut to print the list of winners
    public static void printWinner(List<Player> winners){
        for(Player x: Winner(winners)){
            System.out.println("\nWinner is/are: " + x.getName()+ " [" + Math.round(x.calcWinPercentage()) + "%] " + " with combo: " + x.recogCard());
            printCards(x.getCards(), x);
        }
        
        System.out.println("\n*****************************************");
    }
    
    //if i ever want to print the pplayers details
    public static void printPlayers(List<Player> players){
        for(Player x: players){
            System.out.print(x.getName() + " £: "+ x.getCash() + /*" [" + x.getChance() + "%] " +  " : (" + (x.recogCard()) + ") " + */" TotalBoot: (" + x.getTotalBoot() + ") " + " minBoot[" + x.minBoot + "]" + " Seen: (" + x.getSeen() + ")");
            //printCards(x.getCards(), x);
            
            System.out.println("\n***************");
        }
    }

    //print just the cards and its details
    public static void printCards(List<Card> cards, Player p){
        Map<Integer, Suits> cardMap = new HashMap<Integer, Suits>();
        List<Integer> cardNumbers = new ArrayList<Integer>();
        for(Card c: cards){
            cardNumbers.add(c.getNumber());
            cardMap.put(c.getNumber(), c.getSuit());
        }
        
        Collections.sort(cardNumbers);
        cards.clear();
        
        for(int c: cardNumbers){
            Card d = new Card(c,cardMap.get(c));
            cards.add(d);
        }
        
        
        System.out.print("\nWith Cards: ");
        for(Card x: cards){
            System.out.print(x.faceCards() + " " + x.getSuit() + " ,");
        }
        System.out.print("(" + p.recogCard() + ")");
    }
    

    
    //compare the winning combos and see which one is highest, total exp works for all but pair, for pair call winnerPiar mehod. otherwise call the calculate final method.
    public static List<Player> finalWinnerChoice(List<Player> players){
        
        List<Player> finalWinner = new ArrayList<Player>();
        SortedSet<Double> sortValues = new TreeSet<Double>();
        //calculate the sum of cards and sort it by adding to sortValues. this will return the highest card between the players
        for(Player x: players){
            //for pairs
            if(x.convert() == 2){
                sortValues.add(winnerPair(x.getCards()));
            }
            else{sortValues.add(calculateFinal(x.getCards()));}
        }
        
        //check all players cards sum and see which ones match the highest sorted value. those are the winners.
        for(Player y: players){
            if(calculateFinal(y.getCards()) == sortValues.last() && y.convert() != 2){
                finalWinner.add(y);
            }
            else if(winnerPair(y.getCards()) == sortValues.last() && y.convert() == 2){
                finalWinner.add(y);
            }
        }
        //System.out.println("player card: " + Arrays.toString(players.get(1).getCards()) + "total " + sumIntArray(players.get(1).getCards()));  
        return finalWinner;
    }
    
    //calculating the total of a pair so it can be accurately compared if two or more players have pairs. compare and extract the pair, multiply
    //the pair, divide odd number by 100 and add them together. 
    public static double winnerPair(List<Card> pair){
        double storePair = 1;
        double oddNumber = 1;
        
        if(pair.get(0).getNumber() == pair.get(1).getNumber()){
            storePair = pair.get(0).getNumber();  
            oddNumber = pair.get(2).getNumber();  
        }
        if(pair.get(0).getNumber() == pair.get(2).getNumber()){
            storePair = pair.get(0).getNumber(); 
            oddNumber = pair.get(1).getNumber();  
        }
        if(pair.get(1).getNumber() == pair.get(2).getNumber()){
            storePair = pair.get(1).getNumber();  
            oddNumber = pair.get(0).getNumber();  
        }
        
        double sum = (double)(storePair * 2) + (oddNumber/100);
        
        return sum;
    }
    
    //method to calculate the total of each combination so it can later be compared to see whos the winner betwen two or more people. 
    public static double calculateFinal(List<Card> array){
        double sum = 0;
        for(Card x: array){
            sum = sum + Math.exp(x.getNumber());
        }
        //System.out.println(sum);
        return sum; 
    }

    //dealing cards to the created players
    public static List<List<Card>> cardDeal( /*List<List<Card>> listDeal */ List<Player> allPlayers, List<Card> Deck){   
        //using hashmaps
        List<List<Card>> listDeal = new ArrayList<List<Card>>();
        Map<Player, List<Card>> dealtCards = new HashMap<Player, List<Card>>();
        
        for(Player x: allPlayers){
            dealtCards.put(x, new ArrayList<Card>());
        }
        
        for(int i = 0; i < 3; i++){
            for(Map.Entry<Player, List<Card>> myEntry: dealtCards.entrySet()){
                myEntry.getValue().add(Deck.get(0));
                Deck.remove(0);
            }
        }
        
        for(Map.Entry<Player, List<Card>> newEntry: dealtCards.entrySet()){
                listDeal.add(newEntry.getValue());
            }

        
        return listDeal;
    }
    
}
