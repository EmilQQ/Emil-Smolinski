A classic PacMan game written in Java using Swing and the MVC pattern, where the game board is rendered inside a JTable.

🔧 Features

MVC (Model-View-Controller) – clear separation of game logic, rendering, and control.

Maze generation inside a JTable, filled with walls, food, and interactive objects.

PacMan controlled with arrow keys, automatically rotating to the movement direction.

Ghosts moving randomly across the maze, colliding with PacMan.

Food and scoring system – PacMan collects food to earn points.

Power-ups & upgrades – PacMan can collect special items (speed boost, freezing ghosts, invincibility, double points, extra life).

Cherries randomly appear on the map, giving bonus points.

Lives system – PacMan has a limited number of lives before Game Over.

High Scores – results saved with Serializable and displayed in a dedicated view (JList).

Main Menu – start screen with New Game, High Scores, and Exit buttons.

Game timer – tracks elapsed time starting from the first move.

Animations – PacMan sprite changes depending on direction, ghosts blink when under power-up effects.

🛠 Technologies

Java 17+

Swing (JFrame, JTable, JLabel, JPanel) – graphical user interface

MVC – application architecture

Serializable – saving and loading scores

Threads – handling character movement, power-up spawning, and game timer
