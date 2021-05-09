package Question1;

import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.Random;

/**
 * <h1>Fish.java</h1>
 *
 * @author Created by Miguel Emmara - 18022146
 */
public class Fish implements Runnable {
    private static final Random random = new Random();
    public static int world_width, world_height;
    private final Color[] color;
    private final FishShoal shoal;
    private double x, y;
    private double dx, dy;
    private double size;
    private boolean isAlive;

    public Fish(FishShoal shoal) {
        this.color = new Color[3];

        do {
            this.dx = (double) random.nextInt(7) - 3; //-3 to 7
            this.dy = (double) random.nextInt(7) - 3; //-3 to 7
        } while (dx != 0 || dy != 0);

        for (int i = 0; i < color.length; i++) {
            // Random RGB
            color[i] = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        }

        this.shoal = shoal;
        this.x = random.nextInt(Fish.world_width) / 2.0;
        this.y = random.nextInt(Fish.world_height) / 2.0;
        this.size = 10 + (40 - 10) * random.nextDouble();
        this.isAlive = true;
    }

    @Override
    public void run() {
        this.isAlive = true;

        while (isAlive) {
            try {
                move();
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ConcurrentModificationException e) {
                System.out.println("Concurrent Modification Exception Error!");
            }
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSize() {
        return size;
    }

    public synchronized void kill() {
        this.isAlive = false;
        shoal.remove(this);
    }

    private void move() {
        if (this.dx == 0 || this.dy == 0) {
            if (this.dx == 0)
                this.dx = (double) random.nextInt(7) - 3;
            else
                this.dy = (double) random.nextInt(7) - 3;
        }

        if (this.y >= world_height || this.y <= 0) {
            this.dy *= -1;
        }

        if (this.x >= world_width || this.x <= 0) {
            this.dx *= -1;
        }

        this.x += this.dx;
        this.y += this.dy;

        Fish target = this.shoal.canEat(this);
        if (target != null) {
            eat(target);
        }
    }

    public void eat(Fish target) {
        if (target != null) {
            if (target.size < 40) {
                // Increase the size by 20%
                size += target.size * 0.2;
            } else if (target.size > 40) {
                // Increase the size by 10%
                size += target.size * 0.1;
            }
        }
        assert target != null;
        target.isAlive = false;
        target.kill();
    }

    public void draw(Graphics graphics) {
        double speed = Math.sqrt(Math.pow(dx, 2)) + Math.sqrt(Math.pow(dy, 2)); // Speed = Square root of dx2 + dy2
        double velX = (getSize() * dx) / (2 * speed);                           // VelX = (size * dx) / (2 x speed)
        double velY = (getSize() * dy) / (2 * speed);                           // VelX = (size * dy) / (2 x speed)

        // Drawing the fish with random colour
        graphics.setColor(color[0]);
        graphics.drawLine((int) x, (int) y, (int) (x - velX + velY), (int) (y - velX - velY));  // Left Fin

        graphics.setColor(color[1]);
        graphics.drawLine((int) x, (int) y, (int) (x - 2 * velX), (int) (y - 2 * velY));        // Body

        graphics.setColor(color[2]);
        graphics.drawLine((int) x, (int) y, (int) (x - velX - velY), (int) (y + velX - velY));   // Right Fin

    }
}