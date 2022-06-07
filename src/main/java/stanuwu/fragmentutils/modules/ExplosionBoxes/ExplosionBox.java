package stanuwu.fragmentutils.modules.ExplosionBoxes;

public class ExplosionBox {
    double x;
    double y;
    double z;

    int age;

    public ExplosionBox(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;

        age = 0;
    }

    public void tick() {
        age++;
    }
}
