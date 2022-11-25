package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Shifter implements Subsystem {

    public static enum Gear {
        HIGH(Value.kForward),
        LOW(Value.kReverse);

        // We don't need a getter since this value is only used by Shifter
        private Value solenoidValue;

        private Gear(Value v) {
            this.solenoidValue = v;
        }

    }

    private final DoubleSolenoid shifter;
    private Gear currentGear;

    public Shifter(DoubleSolenoid shifter) {
        this.shifter = shifter;
        this.set(Gear.LOW);
    }

    public void set(Gear g) {
        this.currentGear = g;
        shifter.set(this.currentGear.solenoidValue);
    }

    public Gear getGear() {
        return this.currentGear;
    }

}
