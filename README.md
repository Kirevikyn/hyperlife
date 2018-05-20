# Hyperlife
## An enhanced and extensible version of Conway's GOL



### Game rules:

Every cycle, every LifeForms updates
* LifeForms die if they have 0 health

* Animals
    * can move
    * can consume other LifeObjects
        * can produce waste after consuming another LifeObject
    * can reproduce
* Plants
    * can't move
    * Seeds grow into plants based on conditions
    * Seeds remain after the plant grows
* Non-rival LifeObjects can occupy the same position

### How to mess with the code

To support a new species, extend some subclass of LifeObject in the Species package.

ConwayPlant/ConwaySeed is an example of a Seed/Plant relationship
WeakPredator is a good example of an Animal

To add a new species to the simulation, edit LifeWorld.populateWorld() 

To change the simulation size, edit the main method in LifeWorld (which is the main class).



More documentation coming soon<sup>tm</sup>


## TODO

* Make plants "growable" so they can transform into other plants at certain points in time
   * Make certain animals able to poop seeds that allow for repopulation of a conway forest
* Make a way for plants to grow into more than 1 pixel
* Add mouse option to redraw pixels of choice
* Add generation jump to skip rendering for some number of generations
* Mechanics
    * Add animal/plant realtionships to model bees/flowers
    * Add a seed that has to be composted by Dung before it can grow

### Extra Notes

* As a tradeoff for performance, some operations aren't as safe (in terms of potentially allowing a LifeForm to tamper with the grid in ways that it shouldn't.) This isn't *really* an issue since this is a game and safetey considerations don't need to be taken.


### Author

Kirevikyn, May 2018

### License

This project is licensed under the MIT License