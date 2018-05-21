package hyperlife.objects.species;

import hyperlife.Action;
import hyperlife.LifeGrid;
import hyperlife.objects.LifeObject;
import hyperlife.objects.Sprout;
import javafx.util.Pair;

import java.awt.*;
import java.util.Random;

public class BasicFlower extends Sprout {
    private static final int GROW_THRESHOLD = 75;
    public BasicFlower(){
        super();
        health = 50;
        radius = 1;
    }
    public Action update(LifeGrid surroundings){
        health++;
        if(health > GROW_THRESHOLD){
            return Action.GROW;
        }
        return Action.NOTHING;
    }

    @Override
    public boolean canStack() {
        return false;
    }

    @Override
    public Color getColor() {
        return Color.YELLOW;
    }
    public int getGrowthRadius(){
        return 1;
    }
    public void grow(LifeGrid surroundings){

        for(int i = 0;i<surroundings.width;i++){
            for(int j = 0;j<surroundings.height;j++){
                if(i == radius && j == radius){
                    continue;
                }
                if(new Random().nextDouble() > .95){
                    surroundings.put(i,j,new BasicFlower());
                }
            }
        }
        surroundings.put(0,1,new BasicFruit());
        surroundings.put(2,1,new BasicFruit());
        surroundings.put(1,0,new BasicFruit());
        surroundings.put(1,2,new BasicFruit());
        //surroundings.put(1,1,new BasicFlower());

    }
    public Class[] getFoodTypes(){return new Class[]{Dung.class};}
    public LifeObject consume(LifeObject l){
        health += 100;
        return null;
    }
}
