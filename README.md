# pathfinder
This is some JavaFX application I made to play with path finding algorithms.

### What it is
This is a JavaFX application. All of the squares are added to the window dynamically and have their event filters added before being added.

The user can select what path finding algorithm they would like to use to traverse the intractable map.

### Controls
Below are the controls that can be used with a mouse:
* Left Click - Draw Walls
* Right Click - Erase
* Left Click + SHIFT - Set start square
* Left Click + CONTROL - Set end square

The user is able to select what path finding variables they would like to be enabled from the top menu.

### Future Development
##### Features
* Alter the highlight feature to be the border of the rectangle.
* Calculate each rectangles distance to the end rectangle for more serious algorithms to be added.
* Automatically clear the map.
* Add slider/textbox for animation speed.
* Additional algorithms (A*, Dijkstra, D*, etc).
* Different rectangles will have different distances that the algorithms will have to account for
* Animated rectangles that change color as the selected algorithm progressed
##### Rewrite
* Change method names in Algorithm/Bruteforce files.
* Check FXML file to see if the algorithm radio menu tags are correct.
* Decide what to do with mode label. Maybe get rid of the top menu and throw it onto the bottom bar?
* Consolidate the rectangle size, how many rectangles in x/y direction, and the map size into one file.
* Better state system. Currently setFill and setState are used and I would like to get rid of setFill. It is confusing when developing what to use. This should alter the paint colors to be private inside the MapRectangle class.
* Organize Bruteforce.java and the Algorithm interface as all algorithm classes will have to use the same format.
* Rewrite the time portion to factor in how long the animation takes.
##### Bugs
* The animation process will slow down as it progresses.
* The map will keep drawing and not stop when it finds the final tile.
* The map will overwrite the color on the final tile.
* When starting to hold down shift or control, a rectangle does not change color unless the cursor moves to a new rectangle (can be fixed by using keymoved, but it is not instant. key still needs to move on the rectangle).
