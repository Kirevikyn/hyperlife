package hyperlife.objects.species;

import hyperlife.Action;
import hyperlife.LifeGrid;
import hyperlife.objects.LifeObject;
import hyperlife.objects.Plant;
import hyperlife.objects.Sprout;

import java.awt.*;

public class KirePlant extends Sprout {
    public Color getColor(){
        return Color.CYAN;
    }
    public KirePlant(){
        super();
        health = 1;
        radius = 2;
    }
    public Action update(LifeGrid surroundings){
        int count = 0;
        for(int i = 0;i<surroundings.width;i++){
            for(int j = 0;j<surroundings.height;j++){
                for(LifeObject l: surroundings.get(i,j)){
                    if(l instanceof KirePlant){
                        count++;
                    }
                }
            }
        }
        count -= 1; //discount self
        if(count < 3 || count > 6){
            health = 0;
        }

        return Action.NOTHING;
    }
    public void grow(LifeGrid surroundings){}
    public int getGrowthRadius(){
        return 0;
    }
    public Class[] getFoodTypes(){return new Class[]{};}
    public LifeObject consume(LifeObject l){
        return null;
    }

}
