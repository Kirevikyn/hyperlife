package hyperlife;

import hyperlife.objects.LifeObject;
import hyperlife.objects.species.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.TimerTask;

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
    private final int IMAGE_SCALAR = 3;
    private final int FPS = 60;
    private boolean paused;
    private final Class[] species = {Carcass.class, ConwayOrchid.class, ConwayHerbivore.class, ConwayPlant.class, Dung.class, WeakPredator.class};
    private Class cursor = ConwayPlant.class;
    private final int width,height;


    public LifeWorld(){
        this(LifeGrid.DEFAULT_WIDTH,LifeGrid.DEFAULT_WIDTH);
    }
    public LifeWorld(int width, int height){
        this.width = width;
        this.height = height;
        paused = false;
        resetGrid();

        fr = new JFrame();
        fr.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        setPreferredSize(new Dimension(width*IMAGE_SCALAR,height*IMAGE_SCALAR));

        GridBagConstraints c2 = new GridBagConstraints();

        JPanel toolbar = new JPanel();


        JButton clear = new JButton("CLEAR");
        clear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                resetGrid();
                repaint();
            }
        });
        JButton reset = new JButton("RESET");
        reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                boolean holdp = paused;
                paused = true;
                resetGrid();
                populateWorld();
                update();
                repaint();
                paused = holdp;
            }
        });
        JButton pause = new JButton("PAUSE");
        pause.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(!paused){
                    //pause.setText("UNPAUSE");
                    pause.setBackground(Color.RED);
                }else{
                    pause.setText("PAUSE");
                    pause.setBackground(Color.GREEN);
                }

                paused = !paused;
                repaint();
            }
        });

        String[] names = new String[species.length];
        for(int i = 0;i<species.length;i++){
            names[i] = species[i].getSimpleName();
        }
        JComboBox<Class> cursorSelector = new JComboBox<Class>();
        cursorSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cursor = species[cursorSelector.getSelectedIndex()];
            }
        });



        MouseAdapter mouse = new MouseAdapter() {
            private boolean drawEnabled = false;
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                drawEnabled = true;
                System.out.println("clicked");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                drawEnabled = false;
            }

            @Override
            public synchronized void mouseDragged(MouseEvent e) {
                super.mouseMoved(e);
                try {
                    LifeObject lo = (LifeObject)cursor.newInstance();
                    Point pt = fr.getLocation();
                    int xcd = (int)(e.getX()-pt.getX())/IMAGE_SCALAR;
                    int ycd = (int)(e.getY()-pt.getY())/IMAGE_SCALAR;

                    even.put(xcd,ycd,lo);
                    odd.put(xcd,ycd,lo);

                    repaint();

                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        };
        addMouseListener(mouse);
        addMouseMotionListener(mouse);





        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; c.gridy = 0; toolbar.add(clear,c);
        c.gridx = 1; c.gridy = 0; toolbar.add(reset,c);
        c.gridx = 2; c.gridy = 0; toolbar.add(pause,c);
        c.gridx = 3; c.gridy = 0; toolbar.add(cursorSelector,c);
        toolbar.setPreferredSize(toolbar.getPreferredSize());

        c2.gridx = 0; c2.gridy = 0;
        fr.add(toolbar,c2);

        c2.gridx = 0; c2.gridy = 1;
        fr.add(this,c2);
        fr.pack();
        fr.setVisible(true);
        populateWorld();

    }
    private synchronized void resetGrid(){
        odd = new LifeGrid(width,height);
        even = new LifeGrid(width,height);
        step = 0;
        img = new BufferedImage(width*IMAGE_SCALAR,height*IMAGE_SCALAR,BufferedImage.TYPE_INT_ARGB);
    }
    public void run(){
        long init = System.currentTimeMillis();
        long last = System.currentTimeMillis();
        long current;
        while(fr.isVisible()){
            if(fr.isActive() && !paused){
                current = System.currentTimeMillis();
                update();
                if(current - last >= (1000/FPS)){
                    repaint();
                    last = current;
                }
                if(step% 100 == 0){
                    //System.out.println(System.currentTimeMillis() - init);
                }
            }

        }
    }
    public synchronized void populateWorld(){
        Random r = new Random();
        for(int i = 0;i<even.width;i++){
            for(int j = 0;j<even.height;j++){
                if(r.nextDouble() > .5){
                    even.put(i,j,new ConwayPlant());

                }
                if(r.nextDouble() > .9995){
                    even.put(i,j,new ConwayHerbivore());
                }
                if(r.nextDouble() > .9990){
                    even.put(i,j,new WeakPredator());
                }
                even.put(i,j,new ConwaySeed());
            }
        }
    }
    private void update(){
        //System.out.println("updating " + step);
        if(step % 2 == 0){
            odd.step(even);
        }
        else{
            even.step(odd);
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
        new LifeWorld(200,200).run();
    }

}
