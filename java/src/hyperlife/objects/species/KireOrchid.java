package hyperlife.objects.species;

import hyperlife.Action;
import hyperlife.LifeGrid;
import hyperlife.objects.LifeObject;
import hyperlife.objects.Plant;

import java.awt.*;
import java.util.Random;

public class KireOrchid extends Plant {
    public KireOrchid(){
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
        return 4;
    }
    public void grow(LifeGrid surroundings){
        for(int i = 0;i<surroundings.width;i++){
            for(int j = 0;j<surroundings.height;j++){
                if(new Random().nextDouble() > .66){
                    surroundings.put(i,j,new KirePlant());
                }
            }
        }
    }
    public Class[] getFoodTypes(){return new Class[]{};}
    public LifeObject consume(LifeObject l){
        return null;
    }
}
