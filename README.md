# VIPR
VIPR is a software which visualizes single steps of a prolog execution. 

Anleitung zum Erstellen der jar (des ausführbaren Programmes):
Um die jar zu erzeugen müsst ihr den Mavenbefehl "mvn package" in project/vipr ausführen (i.d.R. über die Kommandozeile). Dafür muss Maven auf eurem Betriebssystem installiert sein.
Installationsanleitung für Maven: https://www.baeldung.com/install-maven-on-windows-linux-mac

Es ist auch möglich die jar über eine IDE zu erzeugen (z.B. Eclispe). Dafür müsst ihr den projekt-Ordner als Mavenprojekt importieren und über die IDE "mvn package" ausführen.

In beiden Fällen befindet sich die jar in project/vipr/target/vipr-0.0.1-SNAPSHOT-jar-with-dependencies.jar.
