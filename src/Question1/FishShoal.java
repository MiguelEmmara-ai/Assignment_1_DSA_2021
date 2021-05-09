package Question1;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>FishShoal.java</h1>
 *
 * @author Created by Miguel Emmara - 18022146
 */
public class FishShoal {
    private final List<Fish> fishList;

    public FishShoal() {
        this.fishList = new ArrayList<>();
    }

    public synchronized void add(Fish fish) {
        this.fishList.add(fish);
    }

    public synchronized void remove(Fish fish) {
        if (!this.fishList.isEmpty())
            this.fishList.remove(fish);
    }

    public void drawShoal(Graphics graphics) {
        if (!this.fishList.isEmpty()) {
            for (Fish fish : fishList) {
                fish.draw(graphics);
            }
        }
    }

    public Fish canEat(Fish fish) {
        double aveSize;
        double ratio;
        double xRange;
        double yRange;

        for (Fish target : this.fishList) {
            if (this.fishList.size() <= 1)
                return null;
            else {
                ratio = fish.getSize() / target.getSize();
                aveSize = 0.4 * (fish.getSize() + target.getSize());
                xRange = fish.getX() - target.getX();
                yRange = fish.getY() - target.getY();
            }
            if (ratio >= 1.4 && (-aveSize <= xRange && xRange <= aveSize) && (-aveSize <= yRange && yRange <= aveSize)) {
                return target;
            }
        }
        return null;
    }
}
