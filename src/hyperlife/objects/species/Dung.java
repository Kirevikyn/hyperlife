package hyperlife.objects.species;

import hyperlife.Action;
import hyperlife.LifeGrid;
import hyperlife.objects.LifeObject;

import java.awt.*;

public class Dung extends LifeObject {
    public Action update(LifeGrid surroundings){
        return Action.NOTHING;
    }
    public Color getColor() {
        return Color.GRAY;
    }
}

