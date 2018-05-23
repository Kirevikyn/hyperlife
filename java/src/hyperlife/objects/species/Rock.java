package hyperlife.objects.species;

import hyperlife.Action;
import hyperlife.LifeGrid;
import hyperlife.objects.LifeObject;
import hyperlife.objects.Wall;

import java.awt.*;

public class Rock extends LifeObject implements Wall {
    public Color getColor(){
        return Color.GRAY;
    }
    public Action update(LifeGrid surroundings){
        return Action.NOTHING;
    }
}
