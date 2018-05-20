package hyperlife.objects;

import hyperlife.Action;
import hyperlife.LifeGrid;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class LifeObject {
    public abstract Color getColor();
    public abstract Action update(LifeGrid surroundings);

    /**
     *
     * @param surroundings
     * @param cl
     * @return the relative coordinates (ie (-1,1)) of the nearest instance of l (randomly picked if multiple are the same distance). If none are in the surroundings, returns null
     */
    protected Pair<Integer,Integer> getClosest(LifeGrid surroundings, Class[] cl){
        ArrayList<Integer> xcd = new ArrayList<Integer>();
        ArrayList<Integer> ycd = new ArrayList<Integer>();
        for(int i = 0;i<surroundings.width;i++) {
            for (int j = 0; j < surroundings.height; j++) {
                for (LifeObject lo : surroundings.get(i, j)) {
                    for(Class l: cl) {
                        if (lo.getClass().equals(l)) {
                            xcd.add(i - ((surroundings.width - 1) / 2));
                            ycd.add(j - ((surroundings.height - 1) / 2));
                            break;
                        }
                    }
                }
            }
        }
        if(xcd.size() == 0){
            return null;
        }
        int ind = new Random().nextInt(xcd.size());
        //ind = 0;
        return new Pair<Integer,Integer>(xcd.get(ind),ycd.get(ind));
    }
}
