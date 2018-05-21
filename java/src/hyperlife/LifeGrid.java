package hyperlife;

import hyperlife.objects.*;
import hyperlife.objects.species.Carcass;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class LifeGrid {
    private boolean CARCASSES_ENABLE = true;
    static int DEFAULT_WIDTH = 250;
    static int DEFAULT_HEIGHT = 250;
    public final int width,height;
    private Object[][] grid;
    public LifeGrid(){
        this(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }
    public LifeGrid(int width,int height){

        this.width = width;
        this.height = height;
        grid = new ArrayList[width][height];
        for(int i = 0;i<width;i++){
            for(int j = 0;j<height;j++){
                grid[i][j] = new ArrayList<LifeObject>();
            }
        }
    }
    public LifeGrid(int radius){
        this(radius*2+1, radius*2+1);
    }
    public LifeGrid(LifeGrid refGrid, int radius, int x, int y){
        this.width = radius*2+1;
        this.height = radius*2+1;
        grid = new ArrayList[width][height];
        for(int i = -radius;i<radius+1;i++){
            for(int j = -radius;j<radius+1;j++){
                grid[i+radius][j+radius] = refGrid.get(x+i,y+j);
            }
        }
    }
    public List<LifeObject> get(int x, int y){
        if(x < 0 || x >= width || y < 0 || y >= height){
            List<LifeObject> res = new ArrayList<LifeObject>();
            res.add(new Rock());
            return res;
        }else{
            return (List<LifeObject>) grid[x][y];
        }
    }
    public boolean isWall(int x, int y){
        List<LifeObject> objs = get(x,y);
        for(LifeObject l: objs){
            if(l instanceof Wall){
                return true;
            }
        }
        return false;
    }
    public void put(int x, int y, LifeObject l){
        if(l.canStack() || (!l.canStack() && !contains(x,y,l.getClass()))) {
            ((List<LifeObject>) grid[x][y]).add(l);
        }
    }
    //a variation of put that places the lifeform at a position with a movement being considered
    public void move(int x, int y, LifeObject l, Action a){
        switch(a){
            case UP:
                if(y == 0)
                    put(x,y,l);
                else
                    put(x,y-1,l);
                break;
            case DOWN:
                if(y == height-1)
                    put(x,y,l);
                else
                    put(x,y+1,l);
                break;
            case LEFT:
                if(x == 0)
                    put(x,y,l);
                else
                    put(x-1,y,l);
                break;
            case RIGHT:
                if(x == width-1)
                    put(x,y,l);
                else
                    put(x+1,y,l);
                break;
            case GROW:
                if(l instanceof Plant && (l.canStack() || (!l.canStack() && !contains(x,y,l.getClass())))){
                    ((Plant) l).grow(new LifeGrid(this, ((Plant) l).getGrowthRadius(), x, y));
                }
                break;
            case NOTHING:
                put(x,y,l);
                break;
            case REMOVE:
            default:
                //do nothing
        }
    }
    public boolean contains(int x, int y, Class cl){
        for(LifeObject lo: get(x,y)){
            if(lo.getClass().equals(cl)){
                return true;
            }
        }
        return false;
    }
    /**
     * merges as much of l into this grid as possible, placing the topleft corner of that grid at a position on this grid specified by x,y
     * @param l
     * @param x
     * @param y
     */
    public void mergeFrom(LifeGrid l, int x, int y){
        for(int i = x;i<width && i-x<l.width;i++){
            for(int j = y;j<height && j-y<l.height;j++){
                List<LifeObject> objs = get(i,j);
                for(LifeObject lo: l.get(i-x,j-y)){
                    objs.add(lo);
                }
            }
        }
    }
    private void cleanGrid(){
        for(int i = 0;i<width;i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new ArrayList<LifeObject>();
            }
        }
    }
    private synchronized void makeMovements(LifeGrid in){
        for(int i = 0;i<width;i++){
            for(int j = 0;j<height;j++){
                for(LifeObject l: in.get(i,j)){
                    LifeGrid surroundings;
                    if(l instanceof LifeForm){
                        surroundings = new LifeGrid(in,((LifeForm) l).getRadius(),i,j);
                    }else{
                        surroundings = new LifeGrid(in,1,i,j);
                    }
                    Action a = l.update(surroundings);
                    move(i,j,l,a);
                }
            }
        }
    }
    private void processGrid(){
        for(int i = 0;i<width;i++){
            for(int j = 0;j<height;j++){
                List<LifeObject> objs = get(i,j);
                int ind = 0;
                while (ind < objs.size() && objs.size() > 0) {
                    LifeObject l = objs.get(ind);
                    if (l instanceof LifeForm) {
                        if(l instanceof Consumer) {
                            for (LifeObject lo : objs) {
                                if (lo != l && ((Consumer) l).wantsToConsume(lo) && (!(lo instanceof LifeForm) || ((LifeForm) lo).isAlive())) {
                                    LifeObject poop = ((Consumer) l).consume(lo);
                                    objs.remove(lo);
                                    ind--;
                                    if (poop != null) {
                                        objs.add(poop);
                                        ind++;
                                    }
                                    break;
                                }
                            }
                        }
                        if(ind < 0){
                            ind = 0;
                        }
                        if(l instanceof Animal && ((Animal) l).reproduce()){
                            try {
                                objs.add(l.getClass().newInstance());
                            }catch(Exception e){}
                            ind++;
                        }
                        if (!((LifeForm) l).isAlive()) {
                            objs.remove(ind);
                            ind--;
                            if(l instanceof Animal && CARCASSES_ENABLE){//optionally add a carcass
                                objs.add(new Carcass());
                                ind++;
                            }
                        }
                    }
                    ind++;
                }
            }
        }
    }
    public synchronized void step(LifeGrid in){
        if(in.width != width || in.height != height){
            System.out.println("Invalid growth. Sizes don't match.");
            return;
        }
        //clean the current grid
        cleanGrid();
        //place all lifeforms where they want to be in the new grid
        makeMovements(in);
        //let lifeforms consume others or let them die if they're outta health. animals reproduce if possible
        processGrid();
    }
    synchronized void drawImage(BufferedImage img){
        int scalar = img.getWidth()/width;
        int scalarY = img.getHeight()/height;
        if(scalarY!=scalar){
            scalar = 1;
        }
        for(int i = 0;i<width;i++) {
            for (int j = 0; j < height; j++) {
                int color = Color.WHITE.getRGB();
                List<LifeObject> objs = get(i,j);
                if(objs.size() > 0){
                    for(LifeObject l: objs){
                        if(!(l instanceof Seed)){
                            color = l.getColor().getRGB();
                            break;
                        }
                    }
                }
                for(int i2 = i*scalar;i2<i*scalar+scalar;i2++){
                    for(int j2 = j*scalar;j2<j*scalar+scalar;j2++){
                        img.setRGB(i2,j2, color);

                    }
                }

            }
        }
    }
}
