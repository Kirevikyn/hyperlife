package hyperlife.objects;

import hyperlife.Action;
import hyperlife.LifeGrid;
import javafx.util.Pair;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class LifeObject {
    public abstract Color getColor();
    public abstract Action update(LifeGrid surroundings);


    /**
     * returns true if the list of LifeObjects contains at least one instance of a class included in cl
     * @param objs
     * @param cl
     * @return
     */
    private boolean containsClass(List<LifeObject> objs, Class[] cl){
        for(LifeObject lo: objs){
            for(Class l: cl) {
                if (lo.getClass().equals(l)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * gets closest instance of any class in the surroundings. returns null if there are none in the surroundings or if the surroundings aren't square (square is just so doing closeness is easier to optimize)
     * @param surroundings
     * @param cl
     * @return the relative coordinates (ie (-1,1)) of the nearest instance of l (randomly picked if multiple are the same distance). If none are in the surroundings, returns null
     */
    protected Pair<Integer,Integer> getClosest(LifeGrid surroundings, Class[] cl){
        int xrad = ((surroundings.width - 1) / 2);
        int yrad = ((surroundings.height - 1) / 2);
        if(xrad != yrad){
            System.out.println("not square surroundings. invalid.");
            return null;
        }

        if(containsClass(surroundings.get(xrad,yrad),cl)){ //needs to come before the next 'if'
            return new Pair<Integer,Integer>(0,0);
        }

        if(xrad == 0){ //if the rad is 0 and there wasn't something in the current position, then there arent any instances of the sought-after class
            return null;
        }

        ArrayList<Integer> xcd = new ArrayList<Integer>();
        ArrayList<Integer> ycd = new ArrayList<Integer>();
        for(int r = 1;r<=xrad;r++){
            for(int i = -r;i<=r;i++){
                for(int j = -r;j<=r;j++){
                    if(containsClass(surroundings.get(i+xrad,j+yrad),cl)){
                        xcd.add(i);
                        ycd.add(j);
                        //return new Pair<Integer,Integer>(i,j);
                    }
                }
            }

            if(xcd.size() != 0){
                int ind = new Random().nextInt(xcd.size());
                return new Pair<Integer,Integer>(xcd.get(ind),ycd.get(ind));
            }

        }
        return null;
    }
    public boolean canStack(){
        return true;
    }

}
