# cs5004-blackboard-game
Team: Daniel Lisko, Jiayi Ning
![Image of Cards](https://www.888casino.com/blog/sites/newblog.888casino.com/files/inline-images/blackjack-card-shoe.png)

### Problem Statement: 
1. Create a Card class which stores the suit (e.g. SPADE) and name values (e.g. TEN)
2. Create a Deck class which stores an Arraylist of Card objects (include functions such as print, shuffle, add, remove and sort cards)
3. Create a Hand class which inherits from Deck class
4. Extend the program to play Blackjack game

### Our approach to solving the problem:
I. Card Class
* Created two enum classes -- Suit and CardName -- to store suit and card values
![FaceValue of Cards](https://www.888casino.com/blog/sites/newblog.888casino.com/files/inline-images/blackjack-card-values.png)
* To compare cards based on card name and suit, the Card class implements Comparable interface and overrides the compareTo method

II. Deck Class
* Added the for loop to the constructor for generation of Arraylist containing a full deck of 52 cards
* Utilized built-in methods within the Collections framework for shuffling and sorting Card objects
* Added appropriate exception handling as needed

III. Hand Class
* Constructor to initialize an empty arraylist
* Added the following methods to faciliate with the Blackjack Game:
>* dealCards(): Deals cards from the Deck to the given Hand object
>* returnCard(): Returns dealt cards from the hand back to the deck
>* calculateHandValue(): calculates total hand value
>* getTotalHandValue(): getter method to be used in Blackjack class
>* isBust(): returns boolean for whether the hand value is over 21
* Added appropriate exception handling as needed

IV. Game Class
* Included added factors such as bankroll, bet, and timeUnit delay to simulate a real Blackjack game
* Added appropriate exception handling as needed, and sought to maximize playability of the game even when errors occur

### Potential Ways to Improve Our Code
* Follow the Clean Code concepts and refactor our Game class. We could break the Bankroll and bet methods into its own class. By doing so, we can condense our instance variables currently in our Game class, and the change could make our classes more extendable. Other games could also leverage the new class to implement betting capabilities.
