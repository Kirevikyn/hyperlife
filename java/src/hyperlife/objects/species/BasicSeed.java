package hyperlife.objects.species;

import hyperlife.Action;
import hyperlife.LifeGrid;
import hyperlife.objects.LifeObject;
import hyperlife.objects.Seed;

import java.awt.*;
import java.util.Random;

public class BasicSeed extends Seed {
    public BasicSeed(){
        super();
        health = 1;
    }
    public Action update(LifeGrid surroundings){
        health++;
        if(health > 80){
            return Action.GROW;
        }
        return Action.NOTHING;
    }

    public int getGrowthRadius(){
        return 0;
    }
    public void grow(LifeGrid surroundings){
        surroundings.put(0,0,new BasicFlower());

    }

}
