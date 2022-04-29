# Software Registry - Progetto Back End

## Compilazione e avvio

### Configurazione

Configurare il seguente file di properties Spring Boot:

- `src/main/resources/application.properties`

### Avvio della applicazione

Configurare il file "deploy.sh" e avviarlo. Comandi invocati:

```bash
docker build -t ${GROUP}/${NAME}:${TAG} .
docker run --name ${NAME} -d -p 0.0.0.0:${EXTERNAL_PORT}:${INTERNAL_PORT} -v ${DATA_HOME}:/data -v ${INDEX_HOME}:/index ${GROUP}/${NAME}:${TAG
```
