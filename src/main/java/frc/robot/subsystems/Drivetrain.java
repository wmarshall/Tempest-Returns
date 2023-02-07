package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Drivetrain implements Subsystem {

    

    private final Encoder leftEnc;
    private final Encoder rightEnc;

    private final DifferentialDrive drive;
    public Drivetrain(Talon left, Talon right, Encoder leftEnc, Encoder rightEnc) {
        this.leftEnc = leftEnc;
        this.rightEnc = rightEnc;

        drive = new DifferentialDrive(left, right);
        // deadbanding is managed at the OI layer, not here
        drive.setDeadband(0);
    }

    public void set(double l, double r) {
        l = MathUtil.clamp(l, -1, 1);
        r = MathUtil.clamp(r, -1, 1);

        // Input scaling is managed at the OI layer, not here
        var squareInputs = false;
        drive.tankDrive(l, r, squareInputs);
    }

    public double getLeftDistanceFeet() {
        return leftEnc.getDistance();
    }

    public double getRightDistanceFeet() {
        return rightEnc.getDistance();
    }
    public double getLeftRateFeetPerSecond() {
        return leftEnc.getRate();
    }

    public double getRightRateFeetPerSecond() {
        return rightEnc.getRate();
    }

    
}
