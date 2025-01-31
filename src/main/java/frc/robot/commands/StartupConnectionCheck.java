// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class StartupConnectionCheck extends SequentialCommandGroup {
  private boolean connectionFailed = false;

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }

  /** Creates a new StartupConnectionCheck. */
  public StartupConnectionCheck(Command whilePinging, Command onSuccess, Command onFailed) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new RadioPing()
            .handleInterrupt(() -> connectionFailed = true)
            .withTimeout(30.0)
            .deadlineWith(whilePinging),
        Commands.none(),
        Commands.either(onSuccess, onFailed, () -> !connectionFailed));
  }
}
