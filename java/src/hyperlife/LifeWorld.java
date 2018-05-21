package hyperlife;

import hyperlife.objects.LifeObject;
import hyperlife.objects.Seed;
import hyperlife.objects.species.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
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
    private final Class[] species;
    private Class cursor;
    private final int width,height;

    private final int CONWAY_CLEAN = 0;
    private final int CONWAY_ANIMAL = 1;
    private final int KIRE = 2;
    private final String[] simNames = {"Conway","Conway + Animals","Kire"};
    private int simulationSetup;


    public LifeWorld(){
        this(LifeGrid.DEFAULT_WIDTH,LifeGrid.DEFAULT_WIDTH);
    }
    public LifeWorld(int width, int height){
        String packageName = "hyperlife/objects/species";
        File[] speciesFiles = new File("src/" + packageName).listFiles();
        Class[] spec = new Class[speciesFiles.length];
        try {
            for (int i = 0; i < speciesFiles.length; i++) {
                spec[i] = Class.forName(packageName.replace("/", ".") + "." + speciesFiles[i].getName().replace(".java",""));
            }

        }catch(Exception e){}
        species = spec;

        this.width = width;
        this.height = height;
        paused = false;
        simulationSetup = CONWAY_CLEAN;
        resetGrid();

        fr = new JFrame();
        fr.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setPreferredSize(new Dimension(width*IMAGE_SCALAR,height*IMAGE_SCALAR));
        GridBagConstraints c2 = new GridBagConstraints();
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new GridBagLayout());

        JButton clear = new JButton("CLEAR");
        clear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                boolean holdp = paused;
                paused = true;
                resetGrid();
                plantSeeds();
                update();
                repaint();
                paused = holdp;
            }
        });
        JButton reset = new JButton("RESET");
        reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                boolean holdp = paused;
                paused = true;
                resetGrid();
                plantSeeds();
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

        cursor = species[0];
        String[] names = new String[species.length];
        for(int i = 0;i<species.length;i++){
            names[i] = species[i].getSimpleName();
        }
        JComboBox<String> cursorSelector = new JComboBox<String>(names);
        cursorSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cursor = species[cursorSelector.getSelectedIndex()];
            }
        });

        JComboBox<String> simSelector = new JComboBox<String>(simNames);
        simSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulationSetup = simSelector.getSelectedIndex();
                boolean holdp = paused;
                paused = true;
                resetGrid();
                plantSeeds();
                populateWorld();
                update();
                repaint();
                paused = holdp;
            }
        });



        MouseAdapter mouse = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
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

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

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


        c.gridx = 0; c.gridy = 1; toolbar.add(clear,c);
        c.gridx = 1; c.gridy = 1; toolbar.add(reset,c);
        c.gridx = 2; c.gridy = 1; toolbar.add(pause,c);
        c.gridx = 3; c.gridy = 0; toolbar.add(new JLabel("Cursor"),c);
        c.gridx = 4; c.gridy = 0; toolbar.add(new JLabel("World Mode"),c);
        c.gridx = 3; c.gridy = 1; toolbar.add(cursorSelector,c);
        c.gridx = 4; c.gridy = 1; toolbar.add(simSelector,c);
        toolbar.setPreferredSize(toolbar.getPreferredSize());

        c2.gridx = 0; c2.gridy = 0; fr.add(toolbar,c2);
        c2.gridx = 0; c2.gridy = 1; fr.add(this,c2);
        fr.pack();
        fr.setVisible(true);


        plantSeeds();
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
                    System.out.println(System.currentTimeMillis() - init);
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

            }
        }
    }
    public Class getSeedType(int simMode){
        switch(simMode){
            case CONWAY_ANIMAL:
            case CONWAY_CLEAN:
                return ConwaySeed.class;
            case KIRE:
            default:
                return KireSeed.class;
        }
    }
    public synchronized void plantSeeds(){
        plantSeeds(getSeedType(simulationSetup));
    }
    public synchronized void plantSeeds(Class cl){
        try {
            for (int i = 0; i < even.width; i++) {
                for (int j = 0; j < even.height; j++) {
                    even.put(i,j,(LifeObject)cl.newInstance());
                }
            }
        }catch(Exception e){}
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
