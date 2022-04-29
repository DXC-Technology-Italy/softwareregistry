# Software Registry Project - Indexer

Il progetto Indexer permette di indicizzare i sorgenti applicativi scaricati dalla pipeline `Jenkins Software Registry Scanner`.

## Compilazione e avvio

### Configurazione

Configurare il seguente file di properties Spring Boot:

- `src/main/resources/application.properties`

### Avvio della applicazione

E' consigliabile eseguire i seguenti passi per rigenerare gli indici Lucene:

1. Cancellare la cartella dell'indice precedentemente creata
2. Lanciare l'applicazione
   - java -jar <APP>.jar
3. Riavviare il container docker del backend

Esempio:

```bash
rm -rf /app/software-registry/index
java -jar /app/software-registry/lucene-indexer.jar
docker restart software-registry-backend
```