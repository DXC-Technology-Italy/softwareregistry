# Software Registry

Il **Software Registry** è un sistema a supporto dello sviluppo software che espone le seguenti funzionalità:

- Generazione automatica catalogo del parco applicativo
- Memorizzazione gerarchia aree / repository / progetti / dipendenze (solo progetti Java)
- Analisi dipendenze dei progetti Java e verifica relazioni fra moduli maven​
- Verifica presenza di librerie (dipendenze) aggiornate per ogni progetto
- Ricerca centralizzata nei sorgenti software (Source Code Search Engine)
- Visualizzazione lista oggetti software modificati in un branch di sviluppo

## Utilizzi

- Censimento parco applicativo ​
- Punto di accesso centralizzato alle informazioni su progetti, dipendenze e librerie
- Ricerche e analisi sul codice, anche per team di assistenza

- Analisi di impatto per sviluppi software
- Report sul parco applicativo
- Report di dettaglio su software rilasciato (Lista oggetti software)

## Componenti applicativi

- [Backend](./backend/README.md)
  - Applicazione basata su [Spring Boot](spring.io/projects/spring-boot)
  - Espone API Rest invocate da una Single Page Application Angular

- [Frontend](./frontend/README.md)
  - Single Page Application basata su [Angular](https://angular.io/)
  
- [Indexer](./indexer/README.md)
  - Progetto per indicizzare sorgenti, basato su [Apache Lucene](https://lucene.apache.org/) e [Spring Boot](spring.io/projects/spring-boot)

- [Pipeline Jenkins](./iac/README.md)
  - Pipeline Groovy per analizzare le dipendenze applicative Java e generare una lista di dipendenze e repository git

Sono inoltre utilizzati i seguenti componenti esterni:

- [MongoDB](https://www.mongodb.com/)
- [Apache Lucene](https://lucene.apache.org/)
- Un server LDAP
- [Jenkins](https://www.jenkins.io/) (avvio pipelines)

Il software **Software Registry** genera le informazioni sfruttando i seguenti sistemi:

- [Gitlab](https://about.gitlab.com/)
- [Redmine](https://www.redmine.org/)

![Componenti architettura](./assets/basic_architecture.png)

## Processo di acquisizione dati

- *Pipeline Software Registry Scanner*:
  - Genera una lista di repository di tutte le aree utilizzando le API REST di gitlab
  - Esegue il checkout del repository, ramo master
    - Ad ogni repository dovrebbe essere assegnato un "Topic" che ne indica la tecnologia (Java, Python, etc...)
  - Per repository di tipologia Java:
    - Raccoglie i **maven modules** : vengono registrati come Progetti
    - Lancia l’analisi delle dipendenze: `mvn dependency:tree`
    - Output: repositories.json, dependencies.txt
  - Reload oggetti database **MongoDB**:
    - HTTP GET ${BACKEND URL}/project/reload
    - HTTP GET ${BACKEND URL}/repository/reload

- Il progetto "Indexer" analizza il codice scaricato dalla *Pipeline Software Registry Scanner* e va dunque avviato in un secondo momento
- Il backend accede:
  - All'indice Lucene
  - Al database MongoDB

![Componenti di processo](./assets/components.png)