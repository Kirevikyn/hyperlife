package hyperlife.objects;

import hyperlife.Action;
import hyperlife.objects.species.ConwayPredator;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @org.junit.jupiter.api.Test
    void getDirection() {
        assertTrue(new ConwayPredator().getDirection(1,0)== Action.RIGHT);
        assertTrue(new ConwayPredator().getDirection(0,-1)== Action.UP);
        assertTrue(new ConwayPredator().getDirection(-1,0)== Action.LEFT);
        assertTrue(new ConwayPredator().getDirection(0,1)== Action.DOWN);

    }


}