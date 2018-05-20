package hyperlife.objects;

import hyperlife.Action;
import hyperlife.objects.species.WeakPredator;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @org.junit.jupiter.api.Test
    void getDirection() {
        assertTrue(new WeakPredator().getDirection(1,0)== Action.RIGHT);
        assertTrue(new WeakPredator().getDirection(0,1)== Action.UP);
        assertTrue(new WeakPredator().getDirection(-1,0)== Action.LEFT);
        assertTrue(new WeakPredator().getDirection(0,-1)== Action.DOWN);

    }


}