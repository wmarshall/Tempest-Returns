// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final class Compressor {
        public static final class Control {
            public static final double MIN_RUN_VOLTAGE = 10;
        }
    }

    public static final class Drivetrain {
        public static final class Ports {

            public static final int TALON_LEFT = 1;
            public static final int TALON_RIGHT = 2;

            public static final int ENCODER_LEFT_A = 1;
            public static final int ENCODER_LEFT_B = 2;
            public static final int ENCODER_RIGHT_A = 3;
            public static final int ENCODER_RIGHT_B = 4;
        }

        public static final class Inverted {
            public static final boolean TALON_LEFT = false;
            public static final boolean TALON_RIGHT = true;
            public static final boolean ENCODER_LEFT = false;
            public static final boolean ENCODER_RIGHT = true;
        }

        public static final class Kinematics {
            public static final double WHEEL_DIAMETER_INCHES = 4;
            public static final double WHEEL_CIRCUMFERENCE_FEET = (WHEEL_DIAMETER_INCHES * Math.PI) / 12;
            public static final double LOW_MAX_FPS = 7;
            public static final double HIGH_MAX_FPS = 16;
            public static final double ACCEPTABLE_TURN_ERROR_DEGREES = 1;
            public static final int ENCODER_SAMPLES = 8;
            public static final double ENCODER_CPR = 128;
            public static final double ENC_TO_WHEEL_RATIO = 1.0 / 5.0;
            public static final double DIST_PER_PULSE = (1.0 / ENCODER_CPR)
                    * WHEEL_CIRCUMFERENCE_FEET
                    * ENC_TO_WHEEL_RATIO;
        }

    }

    public static final class Shifter {
        public static final class Ports {
            public static final int HIGH = 1;
            public static final int LOW = 2;
        }
    }

    public static class Catapult {
        public static class Ports {
            public static final int TALON = 3;
            public static final int POTENTIOMETER = 1;
        }

        public static class Inverted {
            public static final boolean TALON = true;
            public static final boolean POTENTIOMETER = false;
        }

        public static class Kinematics {
            public static final double POTENTIOMETER_MAX_ANGLE_DEGREES = 360;
            public static final double POTENTIOMETER_OFFSET_ANGLE_DEGREES = -80; 
        }
    }

    public static class ShotSelector {
        public static final class Ports {
            public static final int FULL_SOLENOID = 6;
            public static final int LOB_SOLENOID = 5;
        }
    }

    public static class Intake {
        public static class Ports {
            public static final int TALON1 = 4;
            public static final int TALON2 = 5;
            public static final int OUT_SOLENOID = 4;
            public static final int IN_SOLENOID = 3;
        }

        public static class Inverted {
            public static final boolean TALON1 = false;
            public static final boolean TALON2 = true;
        }
    }

    public static class OI {
        public static class Ports {
            public static final int DT_LEFT_JOY = 1;
            public static final int DT_RIGHT_JOY = 2;
            public static final int EE_JOY = 3;
        }

        public static class Inverted {
            public static final boolean DT_LEFT_Y = true;
            public static final boolean DT_RIGHT_Y = true;
            public static final boolean EE_Y = true;
        }

        public static class Buttons {
            public static final int SHIFT = 1;

            public static final int SHOOT = 1;
            public static final int SELECT_FULL_SHOT = 10;
            public static final int SELECT_LOB_SHOT = 12;
            public static final int LOAD = 5;
            public static final int UNLOAD = 3;
            public static final int EXTEND_TOGGLE = 7;

        }

        public static class Deadbands {
            public static double JOYSTICK_Y = 0.05;
        }
    }

}
