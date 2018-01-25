name := "jpacman-framework"
organization := "nl.tudelft.jpacman"
version := "1.0-SNAPSHOT"
description := "Pacman Game Project"

publishMavenStyle := true
crossPaths := false
logBuffered in Test := false
publishArtifact in Test := true
//mainClass in (Compile,run) := Some("nl.tudelft.jpacman.Launcher")

// Add task to copy extra files
lazy val runOriginalJarTask = taskKey[Unit]("Run Original Jar")

libraryDependencies ++= Seq(
  "javassist" % "javassist" % "3.12.1.GA",
  "org.eclipse.jdt" % "org.eclipse.jdt.core" % "3.10.0",
  "com.google.guava" % "guava" % "21.0",
  "org.junit.vintage" % "junit-vintage-engine" % "4.12.0-M4",
  "org.checkerframework" % "checker-qual" % "2.1.10",
  "org.checkerframework" % "checker" % "2.1.10",
  "org.checkerframework" % "jdk8" % "2.1.10",
  "org.junit.jupiter" % "junit-jupiter-api" % "5.0.0-M4" % "test",
  "org.junit.jupiter" % "junit-jupiter-engine" % "5.0.0-M4" % "test",
  "org.junit.jupiter" % "junit-jupiter-params" % "5.0.0-M4" % "test",
  "org.junit.platform" % "junit-platform-runner" % "1.0.0-M4" % "test",
  "org.junit.platform" % "junit-platform-launcher" % "1.0.0-M4" % "test",
  "junit" % "junit" % "4.12" % "test",
  "org.mockito" % "mockito-core" % "2.7.19",
  "org.assertj" % "assertj-core" % "3.6.2",
  "info.cukes" % "cucumber-java" % "1.2.5",
  "info.cukes" % "cucumber-junit" % "1.2.5"
)