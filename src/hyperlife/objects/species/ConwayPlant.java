package hyperlife.objects.species;

import hyperlife.Action;
import hyperlife.LifeGrid;
import hyperlife.objects.LifeObject;
import hyperlife.objects.Plant;

import java.awt.*;

public class ConwayPlant extends Plant{
    public Color getColor(){
        return Color.GREEN;
    }
    public ConwayPlant(){
        super();
        health = 1;
    }
    public Action update(LifeGrid surroundings){
        int count = 0;
        for(int i = 0;i<surroundings.width;i++){
            for(int j = 0;j<surroundings.height;j++){
                for(LifeObject l: surroundings.get(i,j)){
                    if(l instanceof ConwayPlant){
                        count++;
                    }
                }
            }
        }
        count -= 1; //discount self
        if(count < 2 || count > 3){
            health = 0;
        }

        return Action.NOTHING;
    }
}
