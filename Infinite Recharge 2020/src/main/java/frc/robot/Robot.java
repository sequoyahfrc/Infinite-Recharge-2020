/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Robot extends TimedRobot {
  private DifferentialDrive _robot;
  private XboxController driver1;

  @Override
  public void robotInit() {
    _robot = new DifferentialDrive(new PWMVictorSPX(0), new PWMVictorSPX(1));
    driver1 = new XboxController(0);
  }

  @Override
  public void teleopPeriodic() {
    double left = driver1.getY(GenericHID.Hand.kLeft);
    double right = driver1.getY(GenericHID.Hand.kRight);
    _robot.tankDrive(left, right);
  }
} 