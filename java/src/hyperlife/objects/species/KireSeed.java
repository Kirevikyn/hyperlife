package hyperlife.objects.species;

import hyperlife.Action;
import hyperlife.LifeGrid;
import hyperlife.objects.LifeObject;
import hyperlife.objects.Seed;
import hyperlife.objects.Sprout;

import java.awt.*;

public class KireSeed extends Sprout {
    private final int RADIUS = 0;
    public KireSeed(){
        super();
        health = 1;
        radius = 2;
    }
    public Color getColor(){
        return Color.WHITE;
    }
    public Action update(LifeGrid surroundings){
        int count = 0;
        boolean canGrow = true;
        for(int i = 0;i<surroundings.width;i++){
            for(int j = 0;j<surroundings.height;j++){
                for(LifeObject l: surroundings.get(i,j)){
                    if(l instanceof KirePlant){
                        count++;
                        if(i == radius && j == radius){//if there is one on this seed
                            return Action.NOTHING;
                        }
                    }
                }
            }
        }

        if(count == 4){
            return Action.GROW;
        }else{
            return Action.NOTHING;
        }
    }
    public void grow(LifeGrid surroundings){

        surroundings.put(RADIUS,RADIUS,new KirePlant());
        surroundings.put(RADIUS,RADIUS,new KireSeed());
    }
    public int getGrowthRadius(){
        return RADIUS;
    }
    public Class[] getFoodTypes(){return new Class[]{};}
    public LifeObject consume(LifeObject l){
        return null;
    }
}
