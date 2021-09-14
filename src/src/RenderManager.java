package src;

import drawable.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RenderManager extends JPanel implements Runnable
{

    static ArrayList<Drawable> renderQueue = new ArrayList<>();

    private Dimension canvasSize =
            new Dimension(WindowManager.WIN_WIDTH, WindowManager.WIN_HEIGHT);

    private Graphics2D graphics2DObject;
    private int frameRate = 0;
    private int tempFps = 0;
    private long frameTime = 0;
    private int frameDelay = 1;

    public RenderManager()
    {
        setPreferredSize(canvasSize);
        setName("src.RenderManager");
        setVisible(true);
    }


    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        graphics2DObject = (Graphics2D) g;

        graphics2DObject.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2DObject.setColor(Color.WHITE);
        graphics2DObject.fillRect(0,0, WindowManager.WIN_WIDTH, WindowManager.WIN_HEIGHT);
        graphics2DObject.setColor(Color.BLACK);

        if(RenderManager.renderQueue.size() > 0) {
            for (Drawable d : RenderManager.renderQueue)
            {
                d.toRender(graphics2DObject);
            }
        }

        //Draw FPS to the screen
        graphics2DObject.transform(new AffineTransform());
        graphics2DObject.setColor(Color.WHITE);
        graphics2DObject.drawString("FPS:"+
                frameRate, WindowManager.WIN_WIDTH-50,10);
    }

    public void startRenderThread(int fr)
    {
        if((fr>=1)) frameDelay = 1000/fr;
        new Thread(this).start();
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Game Render Thread");
        while(!WindowManager.exitStatus)
        {
            long startTime = System.currentTimeMillis();

            //Repaints the screen
            this.repaint();

            long deltaFrame = System.currentTimeMillis() - startTime;

            //FPS capped to 30fps
            if(deltaFrame < frameDelay)
            {
                try {
                    Thread.sleep(frameDelay-deltaFrame);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //Calculates FPS
            frameTime = frameTime + System.currentTimeMillis() -startTime;
            ++tempFps;
            if(frameTime >= 1000)
            {
                frameRate = tempFps;
                tempFps = 0;
                frameTime = 0;
            }
        }
    }
    static void addObjectToRenderQueue(Drawable objectToAdd)
    {
        if(!RenderManager.renderQueue.contains(objectToAdd))
        {
            RenderManager.renderQueue.add(objectToAdd);
            System.out.println("I ["+ LocalTime.now()+
                    "] Successfully added [."+objectToAdd.toString()+
                    "] to renderQueue.");
            //Sorts the renderQueue by priority of drawable.Drawable Objects
            Collections.sort(
                    RenderManager.renderQueue,
                    new OrderRenderQueueByPriority()
            );
        }
        else
        {
            System.out.println("E ["+ LocalTime.now()+
                    "] Could not add [."+objectToAdd.toString()+
                    "] to renderQueue, already exists!");
        }
    }
}


class OrderRenderQueueByPriority implements Comparator<Drawable>
{

    @Override
    public int compare(Drawable a, Drawable b) {
        return a.getRenderPriority() - b.getRenderPriority();
    }
}