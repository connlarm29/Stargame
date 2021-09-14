package drawable;

import src.WindowManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Background extends Drawable
{

    BufferedImage backgroundImage;

    public Background()
    {
        super(-1);

        this.sizeX = WindowManager.WIN_WIDTH;
        this.sizeY = WindowManager.WIN_HEIGHT;
        this.x = 0;
        this.y = 0;
        this.rotation = 0.0;

        try {
            backgroundImage = ImageIO.read(this.getClass().getResource("sky.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Drawable.addObjectToUpdateQueue(this);
    }

    @Override
    public void toRender(Graphics2D g)
    {
        g.drawImage(backgroundImage,x,y,sizeX,sizeY,null);
    }

    @Override
    public void onUpdate(){/*do nothing*/}
}
