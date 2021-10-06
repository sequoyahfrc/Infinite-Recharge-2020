package frc.robot;

import edu.wpi.first.wpilibj2.command.*;

public class Utils {
    public static CommandBase withRequirements(CommandBase c, SubsystemBase... subsystems) {
      c.addRequirements(subsystems);
      return c;
    }
}