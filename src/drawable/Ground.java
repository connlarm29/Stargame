package drawable;

import src.WindowManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Ground extends Drawable{

    BufferedImage image;


    public Ground()
    {
        super(0);
        this.sizeX = 200 * 4;
        this.sizeY = 32 * 4;

        this.x = 0;
        this.y = WindowManager.WIN_HEIGHT-sizeY;

        try {
            image = ImageIO.read(this.getClass().getResource("ground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Drawable.addObjectToUpdateQueue(this);
    }

    @Override
    public void toRender(Graphics2D g)
    {
        g.drawImage(image,x,y,sizeX,sizeY,null);
    }

    public void onUpdate() {/*do nothing*/}
}
