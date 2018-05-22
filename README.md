# Hyperlife
## An enhanced and extensible version of Conway's GOL



### Game Mechancis:

* The world is composed of a grid which contains LifeObjects

* LifeObjects
	* occupy a position in the world
	* are able to occupy the same grid position as any number of other LifeObjects of other types
	* by default are able to occupy the same position as any number of other LifeObject of the same type (but some LifeObjects may not be able to)
* DecayingObjects	
	* decay over a period of time and are eventually removed from the grid
* Walls (interface)
	* cannot be traversed by other LifeObjects
* LifeForms
	* are LifeObjects
	* die if they have 0 health
	* react to or make changes to the environment
* Consumers (interface)
	* can consume other LifeObjects, up to one per turn
	* can produce a LifeObject as output after consuming something which is placed at the consumer's current position
	* cannot consume dead LifeForms
	* cannot consume themselves
* Animals
	* are LifeForms
	* are Consumers
    * can move
    * can reproduce
	* leave Carcasses when they die
* Herbivores
	* are Animals
	* eat Plants
* Carnivores
	* are Animals
	* eat Animals and Carcasses
* Plants
	* are LifeForms
	* can be Consumers
    * can't move
	* grow based on conditions
* Sprouts
	* are Plants
	* can grow into other Plants
* Seeds 
	* are Plants
	* grow into Sprouts
	* are invisible (since they're underground)
	* aren't intended to be eaten
	
	
* Every turn, every LifeObject updates
	* Consumers may consume
	* DecayingObjects decay
	* Animals may move
	* Animals may reproduce
	* Plants may grow

### How to mess with the code

To support a new species

1. Extend some subclass of LifeObject and put this new class in the Species package.

	ConwayPlant/ConwaySeed is an example of a Seed/Plant relationship
	ConwayPredator is a good example of an Animal

2. To add the new species to the simulation,
	* make a new final int to represent the simulationType in LifeWorld
	* add the simulation name to LifeWorld.simNames
	* edit LifeWorld.plantSeeds() to populate the world with seeds if necessary
	* edit LifeWorld.populateWorld() to populate the world with non-Seed LifeObjects

To change the simulation size, edit the main method in LifeWorld (which is the main class). Smaller simulations are faster.



More documentation coming soon<sup>tm</sup>


## TODO

* Make Animal move along wall when the next pixel in its path is a wall
* Add generation jump to skip rendering for some number of generations (may not really be possible - depends on how long rendering really takes)
* Add static initialization of LifeForm variables + external Properties for balancing
* Mechanics
    * Add animal/plant realtionships to model bees/flowers

### Extra Notes

* As a tradeoff for performance, some operations aren't as safe (in terms of potentially allowing a LifeForm to tamper with the grid in ways that it shouldn't.) This isn't *really* an issue since this is a game and safetey considerations don't need to be taken.


### Author

Kirevikyn, May 2018

### License

This project is licensed under the MIT License