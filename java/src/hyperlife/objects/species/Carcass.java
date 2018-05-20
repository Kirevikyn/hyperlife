package hyperlife.objects.species;

import hyperlife.Action;
import hyperlife.LifeGrid;
import hyperlife.objects.LifeObject;

import java.awt.*;

public class Carcass extends LifeObject {
    private static final int DECAYTIME = 70;
    private int decay;
    public Carcass(){
        super();
        decay = 0;
    }
    public Action update(LifeGrid surroundings) {
        decay++;
        if(decay > DECAYTIME){
            return Action.REMOVE;
        }
        return Action.NOTHING;
    }
    public Color getColor() {
        return Color.MAGENTA;
    }
}
