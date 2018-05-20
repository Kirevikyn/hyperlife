package hyperlife.objects.species;

import hyperlife.Action;
import hyperlife.LifeGrid;
import hyperlife.objects.LifeObject;
import hyperlife.objects.Seed;

import java.awt.*;

public class ConwaySeed extends Seed {
    private final int RADIUS = 1;
    public Color getColor(){
        return Color.WHITE;
    }
    public ConwaySeed(){
        super();
        health = 1;
    }
    public Action update(LifeGrid surroundings){
        int count = 0;
        boolean canGrow = true;
        for(int i = 0;i<surroundings.width;i++){
            for(int j = 0;j<surroundings.height;j++){
                for(LifeObject l: surroundings.get(i,j)){
                    if(l instanceof ConwayPlant){
                        count++;
                        if(i == radius && j == radius){//if there is one on this seed
                            return Action.NOTHING;
                        }
                    }
                }
            }
        }

        if(count == 3){
            return Action.GROW;
        }else{
            return Action.NOTHING;
        }
    }
    public void grow(LifeGrid surroundings){

        surroundings.put(RADIUS,RADIUS,new ConwayPlant());
        surroundings.put(RADIUS,RADIUS,new ConwaySeed());
        System.out.println(surroundings.get(RADIUS,RADIUS).contains(new ConwaySeed()));
    }
    public int getGrowthRadius(){
        return RADIUS;
    }
}
