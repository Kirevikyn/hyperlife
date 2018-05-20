package hyperlife.objects.species;

import hyperlife.objects.Herbivore;
import hyperlife.objects.LifeObject;

import java.awt.*;

public class ConwayHerbivore extends Herbivore{
    public ConwayHerbivore(){
        super();
        health = 30;
    }
    public int getCurve(){
        return 3;
    }
    public Class[] getFoodTypes(){
        return new Class[]{ConwayPlant.class};
    }
    public Color getColor(){
        return Color.BLUE;
    }
    public LifeObject consume(LifeObject l){
        if(l instanceof ConwayPlant){
            health += 30;
        }
        return null;
    }
    public boolean reproduce(){
        if(health > 200){
            health -= 50;
            return true;
        }
        return false;
    }

}
