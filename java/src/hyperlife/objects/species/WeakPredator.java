package hyperlife.objects.species;

import hyperlife.objects.Herbivore;
import hyperlife.objects.LifeObject;
import hyperlife.objects.Predator;

import java.awt.*;
import java.util.Random;

public class WeakPredator extends Predator {
    private static final int HEALTH = 200;
    private static final int RADIUS = 2;
    private static final int HERBIVORE_NUTRIENTS = 120;
    private static final int CARCASS_NUTRIENTS = 60;
    private static final double POOP_CHANCE = .33;


    public WeakPredator(){
        super();
        radius = RADIUS;
        health = HEALTH;
    }
    public Color getColor(){
        return Color.RED;
    }
    public LifeObject consume(LifeObject l) {
        if(l instanceof Herbivore){
            health += HERBIVORE_NUTRIENTS;
        }else if(l instanceof Carcass){
            health += CARCASS_NUTRIENTS;
        }
        if(new Random().nextDouble() < POOP_CHANCE){
            return new ConwayOrchid();
        }
        return null;
    }
    public Class[] getFoodTypes(){
        return new Class[]{Herbivore.class,Carcass.class};
    }
    public boolean reproduce(){
        if(health > HEALTH*5){
            health -= HEALTH;
            return true;
        }
        return false;
    }
}
