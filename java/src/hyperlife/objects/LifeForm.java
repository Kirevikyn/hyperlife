package hyperlife.objects;
import hyperlife.Action;
import hyperlife.LifeGrid;

public abstract class LifeForm extends LifeObject {
    protected int health;
    protected int radius;
    public boolean isAlive(){
        return health > 0;
    }
    public int getRadius(){
        return radius;
    }
    public abstract Action update(LifeGrid surroundings);



}
