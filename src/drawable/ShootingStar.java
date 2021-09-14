package drawable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ShootingStar extends Drawable{

    BufferedImage currentImage, spriteSheet;
    private int offset = 0; // ether 0 or 32, depending on which image is selected
    private int spriteLifetime = 0;

    private final int IMG_HEIGHT = 32,
    IMG_WIDTH = 32;

    private boolean reverse = false;


    public ShootingStar(int x, int y, int layer) {
        super(layer);

        this.sizeX = IMG_WIDTH*2;
        this.sizeY = IMG_HEIGHT*2;
        this.x = x-sizeX/2;
        this.y = y-sizeY/2;

        try {
            spriteSheet = ImageIO.read(this.getClass().getResource("star.png"));
            currentImage = getCurrentSubImage();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Drawable.addObjectToUpdateQueue(this);

    }


    private BufferedImage getCurrentSubImage()
    {
        return spriteSheet.getSubimage(offset,0,IMG_WIDTH,IMG_HEIGHT);
    }

    @Override
    public void toRender(Graphics2D g)
    {
        g.drawImage(currentImage,x,y,sizeX,sizeY,null);

    }
    public void onUpdate()
    {
        ++spriteLifetime;
        if(spriteLifetime == 8)
        {
            if(reverse)
            {
                offset -= 32;
            }else{
                offset += 32;
            }

            if(offset == 0 || offset == 64)
            {
                reverse = !reverse;
            }
            currentImage = getCurrentSubImage();
            spriteLifetime = 0;
        }
    }
}
