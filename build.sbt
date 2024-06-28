val scala3Version = "3.4.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Skull King",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    coverageExcludedPackages := "de\\.htwg\\.se\\.skullking\\.SkullKing",

    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "org.scalactic" %% "scalactic" % "3.2.18",
      "org.scalatest" %% "scalatest" % "3.2.18" % "test",
      "org.scalafx" %% "scalafx" % "21.0.0-R32",
      "net.codingwell" %% "scala-guice" % "7.0.0",
      "org.scala-lang.modules" %% "scala-xml" % "2.3.0",
      //"org.playframework" %% "play-json" % "3.0.4"
      "com.typesafe.play" %% "play-json" % "2.10.5",
      "org.openjfx" % "javafx-controls" % "18.0.2" classifier "mac-aarch64"
    ) ++ {
      // Determine OS version of JavaFX binaries
      lazy val osName = sys.props("os.name").toLowerCase match {
        case n if n.contains("linux") => "linux"
        case n if n.contains("mac") => "mac"
        case n if n.contains("win") => "win"
        case _ => throw new Exception("Unknown platform!")
      }
      Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
        .map(m => "org.openjfx" % s"javafx-$m" % "16" classifier osName)
    },

    assembly / assemblyJarName := {
      lazy val osName = sys.props("os.name").toLowerCase match {
        case n if n.contains("linux") => "linux"
        case n if n.contains("mac") => "mac"
        case n if n.contains("win") => "win"
        case _ => throw new Exception("Unknown platform!")
      }
      s"SkullKing-${osName}.jar"
    },
    assembly / mainClass := Some("de.htwg.se.skullking.SkullKing"),
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", "substrate", "config", _*) => MergeStrategy.discard
      case "module-info.class" => MergeStrategy.discard
      case x => {
        val oldStrategy = (assembly / assemblyMergeStrategy).value
        oldStrategy(x)
      }
    },
  )