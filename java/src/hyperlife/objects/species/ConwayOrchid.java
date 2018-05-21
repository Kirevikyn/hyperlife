package hyperlife.objects.species;

import hyperlife.Action;
import hyperlife.LifeGrid;
import hyperlife.objects.LifeObject;
import hyperlife.objects.Plant;
import hyperlife.objects.Sprout;

import java.awt.*;
import java.util.Random;

public class ConwayOrchid extends Sprout{
    public ConwayOrchid(){
        super();
        health = 1;
    }
    public Action update(LifeGrid surroundings){
        health++;
        if(health > 20){
            return Action.GROW;
        }
        return Action.NOTHING;
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }
    public int getGrowthRadius(){
        return 3;
    }
    public void grow(LifeGrid surroundings){
        for(int i = 0;i<surroundings.width;i++){
            for(int j = 0;j<surroundings.height;j++){
                if(new Random().nextDouble() > .66){
                    surroundings.put(i,j,new ConwayPlant());
                }
            }
        }
    }
}
