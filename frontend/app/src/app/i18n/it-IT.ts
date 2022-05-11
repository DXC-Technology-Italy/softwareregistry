export const it = { 
  labels: {
    home: 'Home',
    gitlab: 'Gitlab',
    nexus: 'Nexus',
    sonarqube: 'SonarQube',
    jenkins: 'Jenkins',
    settings: 'Impostazioni',
    role: 'Ruolo',
    theme: 'Tema',
    search: 'Cerca',
    search_codebase: 'Ricerca',
    tags: 'Tags',
    type: 'Tipo',
    area: 'Area',
    repository: 'Repository',
    project: 'Progetto',
    extension: 'Estensione',
    reset: 'Azzera',
    sha1sum: 'Sha1sum',
    dependencies: 'Dipendenze',
    library: 'Libreria',
    version: 'Versione',
    groupid: 'GroupId',
    parent: 'Parent',
    group: 'Gruppo',
    file: 'File',
    filename: "Nome File",
    username: 'Username',
    password: 'Password',
    path: 'Path',
    object: 'Oggetto',
    action: 'Azione',
    details: 'Dettaglio',
    description: 'Descrizione',
    close: 'Chiudi',
    save: 'Salva',
    true: 'Vero',
    false: 'Falso',
    name: 'Nome',
    user: 'User',
    admin: 'Admin',
    login: 'Login',
    batch: 'Batch',
    online: 'Online',
    logout: 'Esci',
    results: 'Risultati',
    current_version: 'Versione attuale',
    prerequisites: "Prerequisiti",
    object_type: 'Tipo Oggetto',
    git_url: "Url git",
    web_service: "Servizio Web",
    client_lib: "Libreria Client",
    maven_modules: "Moduli Maven",
    maven_modules_list: "Elenco Moduli Maven",
    view_modules: "View Modules",
    users_list: "Lista Utenti",
    user_details: "Dettaglio Utente",
    title: "Software Registry",
    dev_env: "Ambiente di Sviluppo",
    java_projects: "Progetti Java",
    projects_list: "Lista Progetti",
    dep_analysis: "Analisi dipendenze",
    code_search: "Cerca in tutti i sorgenti",
    file_search: "Cerca nome file in tutti i progetti",
    search_software: "Ricerca Software",
    generate_los: "Genera LOS per obiettivo di sviluppo",
    generate_los_report: "Genera report rilasci per obiettivo di sviluppo",
    dev_releases: "Obiettivi di sviluppo",
    edit_users_role: "Modifica ruolo Utenti",
    manage_users: "Gestione utenti",
    env_info: "Informazioni relative all'ambiente di sviluppo",
    view_java_projects: "Visualizzazione di tutti i progetti di tipologia Java e analisi delle dipendenze maven.",
    search_in_all_projects: "Ricerche in tutti i sorgenti software.",
    view_los: "Generazione delle liste oggetti software per obiettivi di sviluppo in corso.",
    delivery_check: "Delivery Check",
    results_number: "Numero risultati",
    no_results: "Nessun risultato ottenuto",
    releases: "Elenco rilasci obiettivo",
    merge_request_type: "Merge Request di @type@",
    merge_request_details: "La richiesta di Merge � stata presentata da @author@ e ha generato i seguenti url nexus:",
    nexus_url: "Url Nexus",
    filesystem_path: "Path su filesystem del server",  
    temp_libs: "Librerie temporanee",
    nexus_link: 'Link Nexus',
    vpn_credentials: " Utilizzare le credenziali di <em>accesso alla VPN</em>",
    maven_certified: "Certificato Maven",
    help_table: "Come compilare la tabella",
    all_modules: "Visualizza tutti i moduli",
    repo_list: "Elenco Repository",
    software_items_list: "Lista oggetti Software",    
  },
  lists: {
    technologies: ['Java', 'Cobol', 'Database', 'SQL', 'Batch', 'Python', 'Altro'],
  },
  search: {
    all: "Cerca in tutti i sorgenti",
    all_prj: "Cerca nome file in tutti i progetti",
    text: "Testo da cercare",
    filename: "Filename in cui cercare",
    limit: "Limite risultati esposti",
    whole_word: "Ricerca parola intera",
    filename_to_search: "Nome file da cercare",
    bigCode: "Inserire il codice BIG di un obiettivo",
    max_version: "Ricerca massima versione librerie progetto",
    project_helper: "Inserire il nome di un progetto per controllare l'esistenza di librerie pi� aggiornate",
    show_temp_libs:"Mostra librerie temporanee",
    projects_library: "Ricerca progetti che usano una libreria",
    library_helper: "Inserire il nome di una libreria.",
    big_code: "Inserire il codice BIG di un obiettivo di sviluppo attualmente aperto",
  },
  buttons: {
      reset_filters: "Azzera Filtri",
      reset_updates: "Azzera Modifiche",
      download_report: "Scarica il report",
      download_text: "Scarica il report in formato testuale",
      download_xls: "Scarica il report in formato CSV",
  }, 
  text: {
    los_info: "Per generare la Lista degli <strong>Oggetti Software per un Obiettivo</strong> &egrave; necessario:" +
        "<ul>" +
          "<li>Che l'obiettivo sia attualmente aperto</li>" +
          "<li>Che i ticket di branch dell'obiettivo abbiano il campo repository nel formato <strong>&lt;CODICE AREA&gt;/&lt;REPOSITORY_NAME&gt;</strong>" +
          "  e che il <u>repository corrisponda a quello presente su git</u>." +
          "</li>" +
        "</ul>",
    projects_warning: "Attenzione! Sono stati modificati alcuni dati sulla pagina principale. <br>" + 
                        "Procedendo con il salvataggio tutte le modifiche verranno perse"
  }
};