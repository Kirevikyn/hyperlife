package hyperlife.objects;

import java.awt.*;

public abstract class Seed extends Plant {
    public Color getColor(){
        return Color.WHITE;
    }
    @Override
    public boolean canStack() {
        return false;
    }
}
