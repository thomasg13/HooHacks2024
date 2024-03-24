package program.stuff.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Objects;

public class Fireball extends Projectile {

    private static BufferedImage image;
    private final int speed = 10; // This can be adjusted based on your game's needs

    static {
        // Load the image once for all Fireball instances
        try {
            image = ImageIO.read(Fireball.class.getResourceAsStream("fireball_up_1.png"));
            if (image == null) {
                throw new IOException("Could not load fireball image");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load fireball image", e);
        }
        
    }

    public Fireball(int worldX, int worldY, int moveX, int moveY) {
        super(worldX, worldY, moveX, moveY, Fireball.image); // Pass the loaded image to the super constructor
    }

    private void loadImage() {
        try {
            // Assuming the image is stored at /images/projectiles/fireball.png
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/images/projectiles/fireball.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        super.update();
        // Additional fireball-specific logic can go here
        // For example, you might want to add code that removes the fireball if it travels beyond a certain range
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(image, getWorldX(), getWorldY(), null);
    }
}
