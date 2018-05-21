package hyperlife.objects.species;

import hyperlife.Action;
import hyperlife.LifeGrid;
import hyperlife.objects.LifeObject;


import java.awt.*;


public class BasicFruit extends LifeObject {
    @Override
    public boolean canStack() {
        return false;
    }

    public Action update(LifeGrid surroundings){
        return Action.NOTHING;
    }
    @Override
    public Color getColor() {
        return Color.PINK;
    }

}
