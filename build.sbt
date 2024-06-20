val scala3Version = "3.4.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Skull King",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    coverageExcludedPackages := "de\\.htwg\\.se\\.skullking\\.SkullKing",

    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.18",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % "test",
    libraryDependencies += "org.scalafx" %% "scalafx" % "21.0.0-R32",
    libraryDependencies += "net.codingwell" %% "scala-guice" % "7.0.0",

    assembly / assemblyJarName := "SkullKing.jar",
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