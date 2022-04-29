# Software Registry - Front End Project

## Compilazione e avvio

### Configurazione

Configurare i seguenti files Angular prima di avviare l'applicazione:

- `app/src/environments/environment.dev.ts`
- `app/src/environments/environment.prod.ts`
- `app/src/environments/environment.ts`

### Avvio della applicazione

Configurare il file "deploy.sh" e avviarlo. Comandi invocati:

```bash
docker build -t ${GROUP}/${NAME}:${TAG} .
docker run --name ${NAME} -p ${EXTERNAL_PORT}:${INTERNAL_PORT} -d -v ${DATA_HOME}:/usr/share/nginx/html/data ${GROUP}/${NAME}:${TAG}
```

## Per avviare il progetto in modalità di sviluppo

`ng serve --configuration=dev`
