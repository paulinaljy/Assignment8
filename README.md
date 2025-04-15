FEATURES THAT WORK SUCCESSFULLY:
- Integrating provider view to be used as Player 2 of our PawnsBoard game
  - Full game play - selecting a cell, card, placing a card, and passing (human and AI)
  - Modal notifying it's your turn 
  - Error modal for invalid moves
  - Game over modal 
- Retain features of our own view from previous assignments
  - Player 1 uses our view implementation 
  - Command-line arguments continue to work

FEATURES THAT DON'T WORK/BUGS:
- The game doesn't prevent Player 1 from selecting when it's not their turn (i.e. Player 1 can still select cells when it's Player 2's turn)
- When clicking on the view of Player 2 while it's Player 1's turn, it automatically switches to Player 2's turn (despite Player 1 not playing yet)
REASON:
In our view implementation, we only allow selecting cells/cards if the controller indicates it's their turn. 
However, in our provider's view implementation, they don't seem to have a feature that checks if view selection should be enabled.
So we can't write an adapter because our provider's view seems to be missing this game logic itself 
so we have no mechanism to enforce turn-based restrictions through just adaptation.

RUNNING THE GAME:
To run the main() method in PawnsBoardGame, 6 string arguments must be entered:
(1) Path to the file for Player 1 Red’s deck
(2) Path to the file for Player 2 Blue’s deck
(3) Player 1 type, either "human", "strategy1", "strategy 2", or "strategy 3" to represent a human player or machine player with a strategy
(4) Player 2 type, either "human", "strategy1", "strategy 2", or "strategy 3"
(5) Width of the game board
(6) Height of the game board

EX: for a human vs. machine strategy 1 game, run:
java -jar Assignment8.jar docs/gameDeck.config docs/gameDeck.config human strategy1 5 7

CHANGES FROM ASSIGNMENT 7:
- Added adapter for provider model: ModelActionAdapter
  - Represents an adapter for the model used in the provider view
  - Implements the provider's generic ModelActionInterface interface, while composing our pawns board model implementation
  - Key behaviors include getting the board, current player, winner of the game, and calculating the score
  - CAN BE FOUND IN: src/cs3500/pawnsboard/provider/model/ModelActionAdapter.java
  
- Added adapter for provider Board object: BoardAdapter 
  - Represents an adapter for a board used in the provider view
  - Implements the provider's generic Board interface, while composing our model implementation of the board, which is a list of rows of our board Cells
  - Key behaviors include getting width and height of the board and cells at specific locations of the board
  - CAN BE FOUND IN: src/cs3500/pawnsboard/provider/model/BoardAdapter.java
  
- Added adapter for provider Card object: CardAdapter
  - Represents an adapter for a card used in the provider view
  - Implements the provider's generic Card interface, while composing our model implementation of a card, which is a GameCard 
  - Key behaviors include getting name, cost, value, owner, and influence grid of the card
  - CAN BE FOUND IN: src/cs3500/pawnsboard/provider/model/CardAdapter.java
  
- Added adapter for provider Cell object: CellAdapter
  - Represents an adapter for a cell used in the provider view
  - Implements the provider's generic Cell interface, while composing our model implementation of the cell
  - Key behaviors include getting the pawn count, card, and owner of the cell
  - CAN BE FOUND IN: src/cs3500/pawnsboard/provider/model/CellAdapter.java
  
- Added adapter for provider Hand object: HandAdapter
  - Represents an adapter for a hand used in the provider view
  - Implements the provider's generic Hand interface, while composing our model implementation of a player
  - Key behaviors include checking if this hand contains a card and getting the cards in this hand 
  - CAN BE FOUND IN: src/cs3500/pawnsboard/provider/model/HandAdapter.java
  
- Added adapter for our view: ViewAdapter 
  - Represents an adapter for the pawns board view used in our view
  - Implements our PawnsBoardView interface, while composing our provider's view implementation, PawnsView
  - Key behaviors include adding a player action listener to our provider's view when subscribing to our observer, displaying error messages, and displaying game over messages
  - CAN BE FOUND IN: src/cs3500/pawnsboard/provider/view/ViewAdapter.java
  
- Added adapter for provider's listener: PlayerActionListenerAdapter
  - Represents an adapter for the player action listener used in the provider's code
  - Implements the provider's generic PlayerActionListener interface, while composing our listener implementation, ViewActions 
  - Key behaviors include performing the player action event based on the action type (select card, select cell, confirm, pass)
  - CAN BE FOUND IN: src/cs3500/pawnsboard/provider/view/PlayerActionListenerAdapter.java