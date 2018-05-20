package hyperlife.objects;

//plants can sense the 3x3 grid around them and can't move

import hyperlife.LifeGrid;

public abstract class Plant extends LifeForm{
    public Plant(){
        super();
        radius = 1;
    }

    /**
     * grows objects into an existing lifegrid. PLEASE ONLY ADD TO THE EXISTING SURROUNDINGS OR ELSE THINGS MIGHT GO WRONG
     */
    public abstract void grow(LifeGrid surroundings);
    public abstract int getGrowthRadius();
}
