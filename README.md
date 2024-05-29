# Command-Line-Battleship

Welcome to the Battleship Game Played On The Command Line!

This game is made 100% with Java and is based almost entirely off of the Battleship board game
hence the name of the program running on the command line as of this moment.

**REQUIRMENTS**
- Your computer will need to have a JDK so that the computer can run Java applications on the command line.
- Link to the JDK download is here: https://www.oracle.com/ca-en/java/technologies/downloads/

**SETUP**
1. Click on the green code button and then click "Download ZIP"
2. Once you downloaded the ZIP file, extract the compressed folder.
3. Open the folder and you should see the files "BATTLESHIP.txt", "BattleshipUI.java" and "BattleshipManager.java"
4. On the folder, right click and then press "Open In Terminal". Disregard the class files in the image for now

![image](https://github.com/smm2005/Command-Line-Battleship/assets/70491113/225fd1f4-a4cd-4911-b6ec-d65a537f2e99)

6. Once you are on the terminal, type out "javac BattleshipUI.java" and then press enter. This will compile the code for the
   JVM and will allow you to run the code on the terminal on your end
7. Next, type out "java BattleshipUI" (**IMPORTANT**: DO NOT type out "BattleshipUI.java" as it won't run the "BattleshipManager" class file)

![Screenshot 2024-05-29 142908](https://github.com/smm2005/Command-Line-Battleship/assets/70491113/d414b868-93e0-4448-a9ce-8e465eaf152e)


**HOW DOES THIS GAME WORK?** (**READ CAREFULLY**):

- You play against the COMPUTER. The computer selects 5 random places to position 5 different ships.
The ships for both the player and the computer will be randomly assigned.

- The 5 ships and the spaces they take up on the board are as follows:
	- Destroyer: 2 spaces
  - Submarine: 3 spaces
  - 
	Cruiser: 3 spaces
  - Battleship: 4 spaces
  - Carrier: 5 spaces.
    
Now sure, this is technically the older layout of ships not updated until 2002 however the general idea for the ships is the same.
In total, 17 spaces of the board will be taken up by the ships.

- SHIPS CANNOT OVERLAP EACH OTHER. So 17 spaces WILL be filled up in the board
	
- The board will look like the following for whenever the 5 ships are placed in 5 different places

![image](https://github.com/smm2005/Command-Line-Battleship/assets/70491113/269f878c-a982-4701-b167-d0cadf525f59)

As you can see, the ships denoted by "S" have a blue colour. "O" denotes an empty space which is indicated in white.

- One player can NOT see the other player's board. If players were to see boards they see their own board.

- Players will also have a secondary board which will track any hits or misses they made to the computer's ships.

![image](https://github.com/smm2005/Command-Line-Battleship/assets/70491113/fd6f8e4c-f6c6-4b57-9c33-12748bdd0c6f)

- Now it's getting fun; players will also be prompted to LAUNCH a "missile" to another spot in the computer's board 
This prompt will consist of a row number and a column number indicated as "{row} {column}" where the numbers will be
ZERO indexed.

![image](https://github.com/smm2005/Command-Line-Battleship/assets/70491113/22fb99d5-4a73-434d-b52e-58e59c881bd3)

- What if you enter a negative number or a number that exceeds the matrix length and matrix width limits? There's always
Math.abs(...) and the modulo operator.

- Invalid inputs besides the above mentioned will return an error message where the user will be prompted to enter new 
launch coordinates.

![image](https://github.com/smm2005/Command-Line-Battleship/assets/70491113/cc1eb8d0-6ecb-4be9-9c58-4e7d82bd2642)

- Whenever a missile is launched, it will either hit one part of the ship (denoted in yellow by "*")

![image](https://github.com/smm2005/Command-Line-Battleship/assets/70491113/a6fd9e26-f97b-46af-bf05-06e6292d7971)

 or miss (denoted in green by "M")

![image](https://github.com/smm2005/Command-Line-Battleship/assets/70491113/3a179006-ddb8-4c5f-bb09-a9f91042775c)

- If all parts of the specific ship are hit, then the command line will print out "YOU SUNK MY" followed by the ships name. This indicates that the ship positioned by the computer has sunk since all missiles hit each and every single part of the ship

![image](https://github.com/smm2005/Command-Line-Battleship/assets/70491113/e40e773a-adb9-4038-ba1e-1a2c86a477dd)

- However if all parts of the specific ship for YOUR board are hit, the command line will print out "COMPUTER SUNK YOUR" followed by the ships name. This indicates that the ship positioned by the player has sunk since all missiles hit each and every single part of the ship

- There are no limits as to how many "missiles" are present for both the player and the computer.

- The game ends when the player sinks all of the computers ships or vice versa.

- What if you want to quit while you are playing? At the prompt, just type "quit" or "q" and the program will automatically stop running.

**MY TAKEAWAY FROM DEVELOPING THIS GAME**

Development of this game is actually not too hard primarily because I took a university course on Java a few months ago. It was time-consuming though but that was mainly because I stretched out development of this game for the entirety of May. The thing that is interesting about this terminal line game is the use of ANSI codes to colorize text within the terminal. The usage of ANSI codes for this program made me ponder about the labour intensity, if you will, on the development of games a long time ago (80s, 90s, 70s?) before there existed game engines, modules, etc and before the existence of contemporary GPUs and graphics as we know it.
Overall, this project was simply a warm-up project to occupy my time appropriately during the currently long break as of typing this that I currently have and to not forget Java seeing as the backbone of Java entails fundamentally important topics of computer science as a whole (primarily Object Oriented Programming).
