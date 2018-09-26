// ------------------------------------------------------------------------- \\
// SBT Build File                                                            \\
// ------------------------------------------------------------------------- \\

organization := "nz.co.rossphillips"

name := "scala-thumbnailer"
version := s"${sys.props.getOrElse("build.majorMinor", "0.6")}.${sys.props.getOrElse("build.version", "SNAPSHOT")}"
licenses += ("GPL-2.0", url("http://www.gnu.org/licenses/gpl-2.0.txt"))
homepage := Some(url("https://github.com/dmk99/scala-thumbnailer"))
bintrayOrganization := Some("dmk99-dev")
bintrayRepository := "lib"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
	"org.apache.pdfbox" % "pdfbox" % "1.8.2",
	"org.imgscalr" % "imgscalr-lib" % "4.2",
	"commons-io" % "commons-io" % "2.4",
	"org.ostermiller" % "utils" % "1.07.00",
	"fr.opensagres.xdocreport" % "org.apache.poi.xwpf.converter.pdf" % "1.0.2",
	"com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
)

// ------------------------------------------------------------------------- \\
// Publishing                                                                \\
// ------------------------------------------------------------------------- \\

publishMavenStyle := true

crossPaths := false

pomIncludeRepository := { _ => false }

pomExtra := (
	<scm>
		<url>git@github.com:rphillips-nz/scala-thumbnailer.git</url>
		<connection>scm:git@github.com:rphillips-nz/scala-thumbnailer.git</connection>
	</scm>
	<developers>
		<developer>
			<id>rphillips</id>
			<name>Ross Phillips</name>
			<url>http://rossphillips.co.nz</url>
		</developer>
	</developers>
)

mappings in (Compile, packageBin) ~= { (ms: Seq[(File, String)]) =>
	ms filter { case (file, toPath) =>
		toPath != "nz/co/rossphillips/thumbnailer/Main.class" &&
		toPath != "nz/co/rossphillips/thumbnailer/Main$.class"
	}
}

