package drawable;

import src.WindowManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpinningMeme extends Drawable
{
    BufferedImage image;

    private int velocityX = 1,
            velocityY = 1;

    public SpinningMeme()
    {
        super(1);

        try {
            image = ImageIO.read(this.getClass().getResource("test1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.x = 0;
        this.y = 0;
        this.sizeX = 300;
        this.sizeY = 300;
        this.rotation = 0.0;

        Drawable.addObjectToUpdateQueue(this);

    }

    @Override
    public void toRender(Graphics2D g)
    {
        AffineTransform orig = g.getTransform();
        g.rotate(
                Math.toRadians(this.rotation),
                this.x+(this.sizeX/2.0),
                this.y+(this.sizeY/2.0));
        g.drawImage(image,x,y,sizeX,sizeY,null);
        g.setTransform(orig);
    }

    @Override
    public void onUpdate()
    {
        rotation += velocityX;
        x += velocityX;
        y += velocityY;

        if( (x+sizeX) >= WindowManager.WIN_WIDTH)
            velocityX = -1;
        if( (x) <= 0)
            velocityX = 1;

        if( (y+sizeY) >= WindowManager.WIN_HEIGHT)
            velocityY = -1;
        if( (y) <= 0)
            velocityY = 1;



    }
}
