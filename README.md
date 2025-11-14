# RideAnything

Ride any animal in the game!

![image](cow.png)

Any mob in Minecraft that is an `AnimalEntity` can now be ridden by right clicking with an empty hand.

This includes basically every animal in the game, including some hostile ones, like hoglins. Good luck!

Here is a list of all the mobs you can ride with this mod:
<details>
  <summary>List of mobs</summary>

  *Italicized if already rideable this in vanilla*
  - Armadillo  
  - Axolotl  
  - Bee  
  - *Camel* 
  - Cat  
  - Chicken  
  - Cow  
  - *Donkey*  
  - Fox  
  - Frog  
  - Goat  
  - *Happy Ghast*    
  - Hoglin  
  - *Llama*    
  - Mooshroom   
  - *Mule*  
  - Ocelot  
  - Panda   
  - Parrot  
  - Pig  
  - Polar Bear  
  - Rabbit  
  - Sheep  
  - *Skeleton Horse*  
  - Sniffer  
  - *Strider*   
  - *Trader Llama*   
  - Turtle  
  - Wolf  
  - Zombie Horse  
</details>


## The Code
This mod's entire functionality is in 5 lines of code:
```java
if (entity instanceof AnimalEntity && 
player.getMainHandStack().isEmpty()) {
		if (player.startRiding(entity)) {
			return ActionResult.SUCCESS;
		}
}
```
That's it!

