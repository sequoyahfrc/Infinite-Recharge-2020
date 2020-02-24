package frc.robot;

import edu.wpi.first.wpilibj.SpeedController;

class MotorButtonBinding {

    //should ONLY be set in the constructor, and not modified in ANY WAY
    private final SpeedController[] motors;
    private double speed;

    //"..." means you can pass as many values as you want for one argument, it is stored as an array
    public MotorButtonBinding(double speed, SpeedController... motors) {
        this.motors = motors;
        this.speed = speed;
    }

    //if expression is true, start the motor at a certain speed specified in the constructor
    //you can pass driver1.getXButton() to bind all of the motors in motors array (see above) to the X button
    public void SetMotors(boolean expression) {
        for (SpeedController motor : motors) {
            if (expression) {
                motor.set(speed);
            } else {
                motor.stopMotor();
            }
        }
    }

    //use this to change the speed in case you want to change the speed
    //APPLIES TO ALL MOTORS IN THE MOTORS ARRAY!!!
    public void SetSpeed(double speed) {
        this.speed = speed;
    }
}