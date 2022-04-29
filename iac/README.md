# Software Registry Project - Infrastructure as Code Sources

## Avvio Pipeline Jenkins

Il codice della pipeline Jenkins si trova nella cartella `Jenkins`. E' un file sorgente Groovy che può essere importato come "Pipeline" su una istanza Jenkins.
E' necessario configurare la sezione iniziale del sorgente Jenkinsfile in base all'ambiente di esecuzione.


## Come creare e lanciare i container docker dei progetti frontend e backend

1. Copiare:
   1. backend/* su ${SOFTWARE REGISTRY ROOT}/backend
   2. frontend/* su ${SOFTWARE REGISTRY ROOT}/frontend
2. Avviare ${SOFTWARE REGISTRY ROOT}/backend/deploy.sh
3. Avviare ${SOFTWARE REGISTRY ROOT}/frontend/deploy.sh
