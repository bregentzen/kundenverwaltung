# Kundenverwaltung mit REST-API

# SCOPES TEST

Die REST-API soll anschließend über Swagger-UI, curl oder Insomnia aufgerufen werden.
Kunden sind dazu anzulegen, Adressen hinzuzufügen und zu ändern. Dazu sollen folgende
CDI-Build-in Scopes verwendet werden. Dokumentieren Sie die Zustände a) für einen und b)
für zwei oder mehr zeitgleiche Zugriffe:

Einheit    .boundary.rs        .control              .entity
Fall1    @ApplicationScoped  @ApplicationScoped    @Dependent
Fall2    @RequestScoped      @ApplicationScoped    @Dependent
Fall3    @ApplicationScoped  @RequestScoped        @Dependent
Fall4    @RequestScoped      @RequestScoped        @Dependent

Test: Der eine Zugriff erstellt einen Kunden und der andere Zugriff löscht diesen bereits.
Dafür wurde beim erstellen des Kunden ein Delay eingebaut. 
Während des Delays wird der angelegte Kunde vom anderen Zugriff gelöscht
und nach dem Delay wird der Kunde in der Array-List gesucht.

# Aufgabe 1: Ohne JPA/JTA
Fall 1: @ApplicationScoped  @ApplicationScoped
Rückgabe Kunde Anlegen: 500 - Kunde ist null
Rückgabe Kunde Löschen: 204 - Kunde wurde gelöscht

Fall 2: @RequestScoped      @ApplicationScoped
Rückgabe Kunde Anlegen: 500 - Kunde ist null
Rückgabe Kunde Löschen: 204 - Kunde wurde gelöscht

Fall 3: @ApplicationScoped  @RequestScoped
Rückgabe Kunde Anlegen: 201 - Kunde erstellt
Rückgabe Kunde Löschen: 404 - Kunde wurde nicht gefunden
Hinweis: Unsere Controlinstanz wird auch einfach weggeworfen nach der Erstellung, wodurch der Kunde nicht gefunden wird.

Fall 4: @RequestScoped      @RequestScoped
Rückgabe Kunde Anlegen: 201 - Kunde erstellt
Rückgabe Kunde Löschen: 404 - Kunde wurde nicht gefunden
Hinweis: Unsere Controlinstanz wird auch einfach weggeworfen nach der Erstellung, wodurch der Kunde nicht gefunden wird.

# Aufgabe 2: Mit JPA/JTA
Fall 1: @ApplicationScoped  @ApplicationScoped
Rückgabe Kunde Anlegen: 201 - Kunde erstellt
Rückgabe Kunde Löschen: 404 - Kunde wurde nicht gefunden

Fall 2: @RequestScoped      @ApplicationScoped
Rückgabe Kunde Anlegen: 201 - Kunde erstellt
Rückgabe Kunde Löschen: 404 - Kunde wurde nicht gefunden

Fall 3: @ApplicationScoped  @RequestScoped
Rückgabe Kunde Anlegen: 201 - Kunde erstellt
Rückgabe Kunde Löschen: 404 - Kunde wurde nicht gefunden

Fall 4: @RequestScoped      @RequestScoped
Rückgabe Kunde Anlegen: 201 - Kunde erstellt
Rückgabe Kunde Löschen: 404 - Kunde wurde nicht gefunden