# ConnectGame
CAP5600 Project 1

# Compiling
After extracting the uploaded zip file, navigate to the directory with \bin\ and \src\.
\bin\ should be empty, while \src\ contains all the java source files.

To compile connectM run the following command with JDK 8 or later (assuming javac is on your environment path):
`.\javac -d bin -sourcepath src src\gameElements\Game.java`

# Running
After compiling, you can run connectM with the following command 
`java gameElements.Game`

To specify the size of the board run
`java gameElements.Game 7`
(this creates a board with width and height 7)

To specify the size of the board and win condition run
`java gameElements.Game 6 3`
(this creates a board with width and height 6 and a win condition of 3)

# Playing
To play a column enter a number 0-width and hit enter. The human player is O and the AI is X.
