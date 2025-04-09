Overview:
This codebase implements a two-player card-and-board game called Pawns Board. Two players (Red and
Blue or AI in the future) each use a deck of cards to play on a rectangular grid. The game rules
specify how cards are placed, how pawns are added or converted, and how final scores are calculated.
The goal is to provide a working model, textual view, and configuration-file loading so users can
instantiate and simulate the game (or build further UIs and controllers on top). Key assumptions:

Each deck file lists valid cards with specific costs, values, and “influence” grids.
No more than two copies of any single card appear in a deck.
The board size is constrained so rows > 0 and columns > 1 (and columns must be odd).
The code is designed for extensibility: you can easily plug in new user-players (e.g. AI or GUI)
without changing the underlying model.

To Run the Game:
Run the jar file:
java -jar CS3500HWs5-8.jar <red_deck_path> <blue_deck_path> <red_player> <blue_player>
Player types: human, strategy1, strategy2, strategy3

example commend line:
java -jar CS3500HWs5-8.jar docs/red_deck.config docs/blue_deck.config human strategy2

Key Components:
Model
Board: Maintains the grid of Cells, places cards, and initializes pawn positions.
Cell: Represents a single board cell, which can hold up to 3 pawns or a single card.
Card: Has a name, cost, value, owner, and a 5×5 influence grid describing how pawns change.
Deck: A list of Cards belonging to a player; can be drawn from in order.
Hand: The in-hand cards a player currently holds.
DeckReader: Reads deck config files and converts them into Card objects.
PawnsBoardModel: The central game logic. It enforces the turn structure, applies rules for placing
cards, passing, scoring, etc.

View
PawnsBoardView: Produces a console-friendly string representation of the board state, showing cards,
pawns, and gameEnd string.

Key Subcomponents:
Board and Cell:
The Board ensures valid placements.
Cell enforces that it can’t hold pawns and a card simultaneously and supports up to 3 pawns.

Card:
Tracks cost (1–3), value (> 0), and a 5×5 influence pattern.
When placed on the board, it calls applyInfluence to update cells around it.

PawnsBoardModel:
Maintains references to both players’ decks, hands, and the shared board.
Controls turn order, draws a card at the start of each turn if available, and checks for consecutive
passes to end the game.
Exposes calculateScore(Player p) to compute final or in-progress scores.

DeckReader:
Parses configuration files line by line, validating the 5×5 grid format (C in the center, I for
influence, X for no influence).
Ensures no more than two copies of any identical card exist.

PawnsBoard (main class):
Demonstrates an example usage of the model, reading deck files, creating a 3×5 game, and
auto-playing until game end.

Changes for part 2:
broke down methods that exceeds 50 lines.

Changes for part 3:
1. Added GUI Implementation:
   - Created PawnsBoardFrame for game visualization
   - Added BoardPanel and HandPanel for interactive gameplay
   - Implemented mouse listeners for card/cell selection

2. Added AI Players:
   - Strategy1Player: Aggressive strategy focusing on high-value cards
   - Strategy2Player: Defensive strategy blocking opponent's strong positions
   - Strategy3Player: Balanced strategy managing resources efficiently

3. Enhanced Controller:
   - Added support for both human and AI players
   - Implemented turn management and move validation
   - Added error handling for invalid moves

4. Command Line Arguments:
   - Added support for configuring player types via command line
   - Allows mixing human and AI players in any combination

5. Event System:
   - PlayerActionListener for handling player moves
   - ModelStatusListener for game state updates
   - Improved error messaging and game state feedback

6. Code Organization:
   - Moved player-related classes to Players package
   - Separated view components for better modularity
   - Added comprehensive documentation for new features