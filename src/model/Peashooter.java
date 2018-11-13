package model;
/* Peashooter is a type of damage dealing plant
 * Class sets the attributes and type of the plant */
public class Peashooter extends OffensivePlant {
	public Peashooter() {
		super(10, 15, 2, 1, 1);
		this.setType(this.peaShooter);
	}

	@Override
	public String toString() {
		return Character.toString(this.getType());
	}
}
