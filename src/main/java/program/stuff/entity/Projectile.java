package program.stuff.entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends Entity {

    private int moveX, moveY;
    private int speed = 10; // You can adjust this value based on your needs
    private BufferedImage image;

    public Projectile(int worldX, int worldY, int moveX, int moveY, BufferedImage image) {
        this.setWorldX(worldX);
        this.setWorldY(worldY);
        this.moveX = moveX;
        this.moveY = moveY;
        this.image = image;
    }

    @Override
    public void update() {
        // Update the projectile's position
        this.setWorldX(this.getWorldX() + moveX * speed);
        this.setWorldY(this.getWorldY() + moveY * speed);
        
        // Here, you might want to check for collisions or if the projectile should be removed
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(image, getWorldX(), getWorldY(), null);
    }
}
