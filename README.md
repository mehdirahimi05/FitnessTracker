Es soll ein Fitness-Tracker-System abgebildet werden, das Trainingseinheiten, Benutzer:innen und Aktivitätstypen (z. B. Laufen, Radfahren, Schwimmen) verwaltet. Eine Trainingseinheit besitzt ein Datum, eine Dauer (in Minuten), eine Distanz (in km) und den Kalorienverbrauch.

Die Geschäftslogik umfasst dabei folgende Punkte:

1.	Das Erfassen neuer Trainingseinheiten für Benutzer:innen.
2.	Das Bearbeiten bestehender Trainingseinheiten (z. B. Dauer, Distanz).
3.	Das Löschen von Trainingseinheiten.
4.	Das Berechnen der gesamten Trainingszeit und Gesamtdistanz pro Benutzer:in und Zeitraum.
5.	Das Ermitteln der durchschnittlichen Geschwindigkeit einer Trainingseinheit.
6.	Das Auffinden der aktivsten Benutzer:innen (z. B. nach Gesamtzeit oder -distanz).
7.	Der Filter für Trainingseinheiten soll folgende Kriterien umsetzen:
      1.	Datum (von – bis)
      2.	Aktivitätstyp
      3.	Benutzer:in
      4.	Kalorienverbrauch (von – bis)
8.	Es ist eine geeignete String-Repräsentation von Benutzer:innen, Aktivitätstypen und Trainingseinheiten umzusetzen.

Folgendes Anwendungsszenario soll sich in einem Test wiederfinden: Ermittle alle Lauftrainings der letzten Woche eines bestimmten Benutzers, sortiert nach der längsten Distanz.
