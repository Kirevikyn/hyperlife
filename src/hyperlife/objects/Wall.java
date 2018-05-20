package hyperlife.objects;

import hyperlife.Action;
import hyperlife.LifeGrid;

import java.awt.*;

public class Wall extends LifeObject {
    public Color getColor(){
        return Color.GRAY;
    }
    public Action update(LifeGrid surroundings){
        return Action.NOTHING;
    }
}
