package hyperlife.objects.species;

import hyperlife.objects.Herbivore;
import hyperlife.objects.LifeObject;
import hyperlife.objects.Predator;

import java.awt.*;

public class WeakPredator extends Predator {
    public WeakPredator(){
        super();
        radius = 2;
        health = 100;
    }
    public Color getColor(){
        return Color.ORANGE;
    }
    public int getCurve(){
        return 2;
    }
    public LifeObject consume(LifeObject l) {
        if(l instanceof Herbivore){
            health += 120;
        }else if(l instanceof Carcass){
            health += 60;
        }
        return null;
    }
    public Class[] getFoodTypes(){
        return new Class[]{Herbivore.class,Carcass.class};
    }
    public boolean reproduce(){
        if(health > 500){
            health -= 100;
            return true;
        }
        return false;
    }
}