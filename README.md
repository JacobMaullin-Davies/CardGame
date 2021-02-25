# CardGame
Create n palyers, 8n cards split into n decks where the player with all matching cards wins
Each player has a hand of 4 cards, the reminaing cards are in the players deck

This program is a multi-threaded card playing simulation.
A user can input the number of players n each	 numbered 1	to	n (player 1 ..... player n),withn being	a positive	integer,
and	n decks	of cards each numbered 1 to	n (deck 1 ..... deck n). Each player will hold a hand of 4	cards.	
Both	 these	hands	and	 the	decks	will	be	drawn	from	 a	 pack which	 contains	 8n cards.	 
Each	 card	 has	 a	 face	 value	 (denomination)	 of	 a	 nonnegative	integer.

The	decks	and	players	will	form	a	ring	topology.	At	the	start	of	the	game,	each	player	will	be	distributed	four cards	in	a	round-robin	
fashion	from the	top	of	the	pack.

After	the	hands	have	been	distributed,	the	decks	will	then	be	filled	from	the	remaining	cards	
in	the	pack.	

When all the cards have been distributed equally the game will start the threads for the players, which will each
pickup and discard cards according to the game strategy. The players preferred denomination of card is equal to its 
own player number, trying to win with all 4 cards being the same.

If the player does not have cards that are equal to its player number then the most common card in its hand 
is the preferred card.

Players pickup cards from the Player on "left" (in the player array) the  discard cards the Player on "Right".
This is in one synchronised thread turn in one atomic action

When a player has all cards equal, the player will inform the other players which will stop the players threads.
The winner is shown on the Terminal.
Each player's turn is appended to an output.txt file. After a winner, the informants are told who won in the file
and the players end hand is shown.
After a winner is declared, the decks are appended to an output file as well.

# How code is run 
In Ide, running the CardGame.java will allow input in the ide terminal
Or create a jar file
Write the following:
   
    java -jar Cards.jar    


#Run tests
Change directory to the src file location  
Write the test line in cmd:

    javac -cp ;lib\* AllTests.java
    
    then run
    
    java -cp ;lib\* org.junit.runner.JUnitCore AllTests



