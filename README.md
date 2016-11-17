# Exablog - der mit dem ![Vogel](doc/twitter.png)

## Einleitung
### Was ist das hier?
Als Team von vier Informatik-Studenten entwickeln wir einen Twitter-Klon, der geografisch skalierbar arbeiten und Benachrichtigungen zu neuen Tweets in beinahe-Echtzeit vermitteln soll.

### Wie kann ich helfen\*?

1\. Klone das Repository
```
    git clone https://github.com/VS2Group/VS2.git
```
2\. Erstelle einen Branch für dein Feature, z.B. login-handler. (Prüfe vorher bitte, ob der gewünschte Name nicht schon vergeben ist!)
```
    git branch login-handler
    git checkout login-handler
```
3\. Pushe ein paar Änderungen in deinen Branch.
```
    git add src/x.java
    git add src/y.java
    git commit -A -m "Kurze Beschreibung deiner Veränderung 1"
    git add src/z.java
    git commit -A -m "Kurze Beschreibung deiner Veränderung 2"
    git push
```
4\. Erstelle einen **Pull Request** über Github.

5\. Warte auf ein **Code Review** deiner Teamkollegen und passe dein Feature entsprechend an. (Wiederholend)
```
    git add src/x.java
    git commit -A -m "Code ist jetzt Checkstyle-konform!"
    git push
```
6\. **Merge** dein Feature über Github ins Projekt.

7\. Ist dein Feature damit beendet? Dann darfst du den Branch jetzt löschen.

Mehr Infos zum **Feature Branch Workflow** findest du auf: https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow

\**Gilt nur für Team-Mitglieder. Als Außenstehender kannst du leider nicht mithelfen, da dies ist eine Pflichtarbeit für unser Studium ist.*

### Projektstruktur
- *doc/*   Enthält die Dokumentation des Projekts
- *src/*   Enthält den Code

### Was brauche ich?
- Eine IDE für Spring:
    - Spring Tool Suite - [Informationen](https://spring.io/tools) - [Download Win 64-bit](http://download.springsource.com/release/STS/3.8.2.RELEASE/dist/e4.6/spring-tool-suite-3.8.2.RELEASE-e4.6.1-win32-x86_64.zip)
    - IntelliJ
    - Eclipse mit entsprechenden Plugins
- Eine IDE für UML (optional)
    - Visual Diagram Community Edition
