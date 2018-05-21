package hyperlife.objects.species;

import hyperlife.Action;
import hyperlife.LifeGrid;
import hyperlife.objects.DecayingObject;
import hyperlife.objects.LifeObject;

import java.awt.*;

public class Dung extends DecayingObject {
    private static final int DECAYTIME = 100;

    public int getDecayTime(){
        return DECAYTIME;
    }
    public Color getColor() {
        return Color.GRAY;
    }
}

