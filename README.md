# water-balloon-wars

<u>**_Really_  specific details**:</u>

Players start out on a circle with radius 10 and equally spaced from each other. The balloon starts at `(0, 0)` with height 30.

Every turn:

 - Loop through the list of players
	 - Call `player.move(map, balloon)`. `map` is a shallow copy of the arena. Let the result be `a`.
	 - If `a` is an instance of `Movement` then:
		 - If `a.x*a.x + a.y*a.y > speed*speed` then continue on to the next iteration of the loop.
		 -  Change the location of the player to `(currentX + a.x, currentY + a.y)`
	 - If `a` is an instance of `Hit` then:
		 -   Make sure that `balloon.height <= 10` and that `balloon.location.distance(currentLocation) <= 4`
		 - Change the velocity of the balloon
			 - Add to `velocityX`: `a.dirX * nextDouble() * luck` (where `nextDouble()` is a number between 0 and 1).
			 - Repeat for `velocityY` and `velocityZ`.
		 - Add the player to the list of those who hit the balloon this step
	 - With the balloon: 
		 - Choose a resistance factor `r` between `0.6` and `0.8`.
		 - Add a random number between `-1` and `1` to all velocities of the balloon.
		 - Subtract 3 from `velocityZ` (gravity)
		 - Multiply all the velocities by `r`
		 - Change the `(x, y)` location by `velocityX` and `velocityY`. Change height by `velocityZ`. If `height <= 0` then set `height = velocityX  = velocityY = velocityZ = 0`.
	 - For each player who hit the balloon, choose a random number between `0` and `luck`. Choose a player randomly using those numbers as weights. This player is saved into the variable that keeps track of who last hit the balloon.
	 - If the balloon's height is exactly 0:
		 - Create a map called `m`. This map represents the chance that each player has to be chosen. 
		 - Then for each player:
			 - Add the key value pair `(player, 1/(distance + nextDouble()*luck))` to map `m`.
		 - Let `total` be the sum of all the values of map `m`. 
		 - Each player has `chance / total` probability of being chosen to lose 4 points. Choose accordingly and remove 4 points.
		 - Add 3 to the score of the player who last hit the balloon.
		 - Set the balloon's height back to 30
