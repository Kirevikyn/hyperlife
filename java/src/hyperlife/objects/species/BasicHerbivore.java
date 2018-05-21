package hyperlife.objects.species;

import hyperlife.objects.Herbivore;
import hyperlife.objects.LifeObject;

import java.awt.*;
import java.util.Random;

public class BasicHerbivore extends Herbivore {
    private static final int HEALTH = 10;
    private static final int RADIUS = 2;
    private static final int BASIC_FRUIT_NUTRIENTS = 5;


    @Override
    public boolean canStack() {
        return false;
    }

    public BasicHerbivore(){
        super();
        health = HEALTH;
        radius = RADIUS;
    }
    public Class[] getFoodTypes(){
        return new Class[]{BasicFruit.class};
    }
    public Color getColor(){
        return Color.BLUE;
    }
    public LifeObject consume(LifeObject l){
        if(l instanceof BasicFruit){
            health += BASIC_FRUIT_NUTRIENTS;
        }
        if(new Random().nextDouble() > .8) {
            return new BasicSeed();
        }
        if(new Random().nextDouble() > .9) {
            return new Dung();
        }
        return null;
    }
    public boolean reproduce(){
        if(health > HEALTH*5){
            health -= HEALTH*3;
            return true;
        }
        return false;
    }

}

