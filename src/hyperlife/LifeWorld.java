package hyperlife;

import hyperlife.objects.species.ConwayPlant;
import hyperlife.objects.species.ConwaySeed;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * every cycle, every lifeform updates
 *     all lifeforms either live or die
 *     some lifeforms move
 *         some lifeforms need to consume other lifeforms which means moving onto that lifeform's position
 *         if two lifeforms move to the same position,
 *             if both want to consume the other, the one with less health dies
 *             if
 */



public class LifeWorld extends JPanel implements Runnable{
    private BufferedImage img;
    private JFrame fr;
    private LifeGrid odd;
    private LifeGrid even;
    private int step;
    public LifeWorld(){
        this(LifeGrid.DEFAULT_WIDTH,LifeGrid.DEFAULT_WIDTH);
    }
    public LifeWorld(int width, int height){
        step = 0;
        odd = new LifeGrid(width,height);
        even = new LifeGrid(width,height);
        img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        fr = new JFrame();
        fr.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        fr.setMaximumSize(new Dimension(width+30,height+50));
        fr.add(this);
        fr.pack();
        fr.setVisible(true);
        populateWorld();

    }
    public void run(){
        long last = System.currentTimeMillis();
        while(fr.isVisible()){
            if(fr.isActive()){
                update();
                repaint();
            }

        }
    }
    public void populateWorld(){
        Random r = new Random();
        for(int i = 0;i<even.width;i++){
            for(int j = 0;j<even.height;j++){
                if(r.nextDouble() > .5){
                    even.put(i,j,new ConwayPlant());

                }
                if(r.nextDouble() > .9995){
                    //even.put(i,j,new ConwayHerbivore());
                }
                if(r.nextDouble() > .993){
                    //even.put(i,j,new WeakPredator());
                }
                even.put(i,j,new ConwaySeed());
            }
        }
    }

    private void update(){
        //System.out.println("updating " + step);
        if(step % 2 == 0){
            odd.grow(even);
        }
        else{
            even.grow(odd);
        }
        step++;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(step % 2 == 0){
            odd.drawImage(img);
        }
        else{
            even.drawImage(img);
        }
        g.drawImage(img,0,0,this);
    }


    public static void main(String[] args){
        new LifeWorld(300,300).run();
    }

}
