package hyperlife.objects.species;

import hyperlife.objects.Herbivore;
import hyperlife.objects.LifeObject;

import java.awt.*;

public class ConwayHerbivore extends Herbivore{
    private static final int HEALTH = 100;
    private static final int RADIUS = 2;
    private static final int CONWAY_PLANT_NUTRIENTS = 30;



    public ConwayHerbivore(){
        super();
        health = HEALTH;
        radius = RADIUS;
    }
    public Class[] getFoodTypes(){
        return new Class[]{ConwayPlant.class};
    }
    public Color getColor(){
        return Color.BLUE;
    }
    public LifeObject consume(LifeObject l){
        if(l instanceof ConwayPlant){
            health += CONWAY_PLANT_NUTRIENTS;
        }
        return null;
    }
    public boolean reproduce(){
        if(health > HEALTH*5){
            health -= HEALTH;
            return true;
        }
        return false;
    }

}
