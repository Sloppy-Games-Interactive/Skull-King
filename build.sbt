val scala3Version = "3.4.0"

val setJavaFXVersion = settingKey[Seq[ModuleID]]("Sets the JavaFX version and classifier based on the system architecture")

setJavaFXVersion := {
  val arch = sys.props("os.arch")
  val isArm = arch == "aarch64"
  val javafxVersion = if (isArm) "22-ea+28" else "22"
  val javafxClassifier = if (isArm) "mac-aarch64" else "mac"
  Seq(
    "org.openjfx" % "javafx-base" % javafxVersion classifier javafxClassifier,
    "org.openjfx" % "javafx-controls" % javafxVersion classifier javafxClassifier,
    "org.openjfx" % "javafx-fxml" % javafxVersion classifier javafxClassifier,
    "org.openjfx" % "javafx-graphics" % javafxVersion classifier javafxClassifier,
    "org.openjfx" % "javafx-media" % javafxVersion classifier javafxClassifier,
    "org.openjfx" % "javafx-swing" % javafxVersion classifier javafxClassifier,
    "org.openjfx" % "javafx-web" % javafxVersion classifier javafxClassifier
  )
}

lazy val root = project
  .in(file("."))
  .settings(
    name := "Skull King",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    coverageExcludedPackages := "de\\.htwg\\.se\\.skullking\\.SkullKing",

    libraryDependencies ++= (Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "org.scalactic" %% "scalactic" % "3.2.18",
      "org.scalatest" %% "scalatest" % "3.2.18" % "test",
      "org.scalafx" %% "scalafx" % "22.0.0-R33" excludeAll(
        ExclusionRule(organization = "org.openjfx")
        ),
      "net.codingwell" %% "scala-guice" % "7.0.0",
      "org.scala-lang.modules" %% "scala-xml" % "2.3.0",
      "com.typesafe.play" %% "play-json" % "2.10.5"
    ) ++ setJavaFXVersion.value),
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