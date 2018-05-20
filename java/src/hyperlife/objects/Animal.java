package hyperlife.objects;

import hyperlife.Action;
import hyperlife.LifeGrid;
import javafx.util.Pair;

import java.util.Random;

public abstract class Animal extends LifeForm{
    protected int deltaX;
    protected int deltaY;
    public Animal(){
        super();
        radius = 2;
        switch(new Random().nextInt(4)){
            case 0: deltaX = 1; deltaY = 0; break;
            case 1: deltaX = -1; deltaY = 0; break;
            case 2: deltaX = 0; deltaY = 1; break;
            case 3: deltaX = 0; deltaY = -1; break;
        }
    }
    /**
     * @param l the lifeobject it's consuming
     * @return a lifeobject if it produces one after it consumes something. May return null if it produces nothing after consuming.
     */
    public abstract LifeObject consume(LifeObject l);
    public boolean wantsToConsume(LifeObject l){
        for(Class cl: getFoodTypes()){
            if(l.getClass().equals(cl)){
                return true;
            }
        }
        return false;
    }
    public abstract Class[] getFoodTypes();
    protected Action getDirection(int deltaX, int deltaY){
        if(deltaX == 0 && deltaY == 0) {
            return Action.NOTHING;
        }
        double angle = (Math.atan2(-deltaY,deltaX) * 180 / Math.PI);
        if(angle < 0.0){
            angle = 360.0 + angle;
        }
        if(angle > 45 && angle < 135)
            return Action.UP;
        if(angle == 135)
            return rpick(Action.UP,Action.LEFT);
        if(angle > 135 && angle < 255)
            return Action.LEFT;
        if(angle == 225)
            return rpick(Action.LEFT,Action.DOWN);
        if(angle > 225 && angle < 315)
            return Action.DOWN;
        if(angle == 315)
            return rpick(Action.DOWN,Action.RIGHT);
        if(angle > 315 || angle < 45)
            return Action.RIGHT;
        if(angle == 45)
            return rpick(Action.RIGHT,Action.UP);



        System.out.println("defaulting to Action.Nothing");
        return Action.NOTHING;
    }
    public Action update(LifeGrid surroundings){
        health--;
        Pair<Integer,Integer> nearest = getClosest(surroundings,getFoodTypes());
        if(nearest != null){
            deltaX = nearest.getKey();
            deltaY = nearest.getValue();
            //normalizeDeltas();
        }else if(surroundings.isWall(deltaX + (surroundings.width-1)/2,deltaY + (surroundings.height-1)/2)){//TODO - MAKE IT MOVE ALONG WALL
            switch(new Random().nextInt(4)){
                case 0: deltaX = 1; deltaY = 0; break;
                case 1: deltaX = -1; deltaY = 0; break;
                case 2: deltaX = 0; deltaY = 1; break;
                case 3: deltaX = 0; deltaY = -1; break;
            }
        }else if(nearest == null && deltaX == 0 && deltaY == 0){

            switch(new Random().nextInt(4)){
                case 0: deltaX = 1; deltaY = 0; break;
                case 1: deltaX = -1; deltaY = 0; break;
                case 2: deltaX = 0; deltaY = 1; break;
                case 3: deltaX = 0; deltaY = -1; break;
            }
        }
        return getDirection(deltaX,deltaY);
    }
    //randomly picks between two Actions
    private Action rpick(Action a1, Action a2){
        if(new Random().nextDouble() > .5){
            return a1;
        }
        return a2;
    }
    /**
     * reproduces if possible, and returns true if it did. returns false otherwise.
     * Reproduction places a new animal at the same location.
     * @return
     */
    public abstract boolean reproduce();
}
