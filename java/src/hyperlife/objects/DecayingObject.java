package hyperlife.objects;

import hyperlife.Action;
import hyperlife.LifeGrid;

public abstract class DecayingObject extends LifeObject{
    private int decay;
    public DecayingObject(){
        super();
        decay = 0;
    }
    public abstract int getDecayTime();
    public Action update(LifeGrid surroundings) {
        decay++;
        if(decay > getDecayTime()){
            return Action.REMOVE;
        }
        return Action.NOTHING;
    }

}
