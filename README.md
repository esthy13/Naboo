# NABOO 

**News Aggregator and Bot Observer**

https://user-images.githubusercontent.com/107881742/206179369-f27a659f-2db4-4f0a-8e77-2c3f36ce69de.mp4

## Che cos’è Naboo?

NABOO è un’ambiente per cercare, leggere e commentare notizie provenienti da feed RSS ed è formato da tre parti: un database, il bot Telegram [@GediNabooBot](http://t.me/GediNabooBot) e un’applicazione grafica. Nel database sono immagazzinate tutte le informazioni relative alle interazioni degli utenti con le notizie e dei loro feed RSS di provenienza. [@GediNabooBot](http://t.me/GediNabooBot), invece, permette a tutti gli utenti di cercare, leggere, commentare e condividere le notizie ai propri amici e contatti tramite WhatsApp, Facebook, Twitter o Telegram stesso. Infine, l’applicazione grafica è accessibile solo agli utenti registrati come amministratori, o da utenti Telegram che sono stati promossi a loro volta ad amministratori e possono così modificare ed eliminare i dati presenti sul database, aggiungendo o cancellando le fonti di provenienza delle notizie, o eliminando commenti, notizie ed utenti indesiderati. 

Per implementare tutte le principali funzioni di NABOO abbiamo utilizzato Java, un linguaggio di programmazione ad alto livello. Il principale vantaggio derivante dal suo utilizzo è la possibilità di eseguire il codice su qualunque piattaforma: da linux a windows a mac ed android perché l’obiettivo del suo fondatore James Gosling era _“Write once, run everywhere”_. Inoltre, il codice java viene convertito in bytecode, meccanismo che lo rende illeggibile agli esseri umani e di conseguenza offre una maggiore protezione da virus. Per realizzare l’interfaccia grafica della nostra applicazione ci siamo serviti di javafx, ovvero una libreria di pacchetti grafici e media che permettono di creare e distribuire un’applicazione client multi-platform, e consente l’utilizzo di file css, che abbiamo utilizzato per definire uno stile grafico uniforme a tutte le schermate dell’applicazione. CSS, infatti, sta per Cascading Style Sheet, ovvero un foglio di testo che a cascata attribuisce le regole di stile a tutti gli elementi, selezionati in base alla loro classe di appartenenza attraverso dei selectors. Infine, per la realizzazione del database ci siamo serviti di MySQL uno dei sistemi più popolari di gestione di un database. Anche in questo caso si tratta di un sistema multi-platform, compatibile per esempio con mac, linux e windows. 

Si tratta di un database relazionale, in quanto è possibile creare relazioni fra le tabelle che rappresentano diverse entità. 

![e-r](https://user-images.githubusercontent.com/107881742/206228696-315dd9b8-7317-4777-be3a-b4abb0de8d89.svg)

Le entità che formano il nostro database sono utente, notizia commento e fonti. In particolare gli utenti sono formati dalle seguenti proprietà: username, ruolo e password (presente solo per gli utenti amministratori). Infatti, abbiamo distinto gli utenti tra user e amministratori: gli user sono gli utenti del bot, mentre gli amministratori sono utenti autorizzati ad accedere all'applicazione grafica per gestire il database. Gli utenti del bot possono essere a loro volta promossi ad amministratori, e in questo modo possono recuperare e/o modificare la loro password per accedere all'interfaccia grafica direttamente dal bot telegram. Al momento della creazione di un commento da parte di un utente viene creata una relazione interagisce tra l’utente e la notizia. In seguito, a quella notizia viene assegnato il commento scritto dall’utente attraverso la relazione possiede. Inoltre, ogni notizia è formata da una fonte, relazione che unisce la tabella delle notizie con quella delle fonti.

Per la struttura generale del nostro progetto ci siamo basati sul pattern di progettazione MVC Model-View-Controller, anche per cercare di dividere in maniera più efficiente il lavoro all’interno del gruppo.

![Presentazione senza titolo](https://user-images.githubusercontent.com/107881742/206216265-f32e0a0b-24c0-46fd-9d54-337513838068.svg)

Nel package model si trovano le classi che definiscono le proprietà e i metodi dei principali oggetti di Naboo: _Commento.java, Fonte.java, Notizia.java e Utente.java_. Nello stesso package si trovano anche le classi che permettono di gestire il database MySQL e il LettoreRss.java. Questo si occupa della raccolta delle notizie dai feed RSS scelti dagli utenti amministratori dell’applicazione per inviarle al database, da cui poi saranno inviate agli utenti in base alle loro richieste. Questo lettore RSS è stato realizzato attraverso l’utilizzo della libreria Rome, che permette di leggere file .xml e selezionarne i vari elementi, consentendoci così di creare degli oggetti java Notizia. Per quanto riguarda invece la sezione dei controller li abbiamo divisi in due package: _controller_ per il bot telegram e _Scene_ per l’applicazione grafica javafx. 

Il bot telegram da noi realizzato implementa l’interfaccia TelegramLongPollingBot, ovvero un bot che una volta avviato continua a fare richieste al server telegram per avere notifiche di un’eventuale update (messaggio o azione dell’utente). Nel caso la risposta sia positiva, il server telegram invia al bot un file .json contenente tutte le informazioni relative all’update organizzate in maniera gerarchica (per esempio l’id della chat, e il testo del messaggio). Per meglio organizzare i metodi del bot, sono state creati delle sottoclassi alla superclasse che implementa l’interfaccia, cercando di raggruppare i metodi per similarità: nella classe _Buttons.java_ sono presenti tutti i principali metodi associati ai bottoni e nel file _Keyboards.java_ tutte le tastiere associate ai diversi tipi di messaggi e in MyBot i comandi principali utilizzabili nel bot. Infine, nella classe Naboo si può capire, in modo quasi immediato, come vengono gestiti i vari casi di update, distinguendo tra update contenenti una callback query, e quindi derivate dalla pressione di un pulsante da parte dell’utente, da quelle dovute all’invio di un messaggio di testo all’utente. Quest’ultime sono a loro volta suddivise in comandi del bot, come /start, e risposte dell’utente. 

Per quanto riguarda i controller dell’applicazione grafica, ne è presente uno per ognuna delle sette schermate dell’applicazione. 

[![](https://mermaid.ink/img/eyJjb2RlIjoiZ3JhcGggVERcbiAgICBBKDEuIGxvZ2luX3ZpZXcpIC0tPiBCKERCVXRpbHMpXG4gICAgQSAtLT4gQyhFbmNyeXB0b3IpXG4gICAgQSAtLT4gRChMb2dpbkNvbnRyb2xsZXIpXG4gICAgRSgyLiBzaWdudXBfdmlldykgLS0-IENcbiAgICBFIC0tPiBGKFNpZ25VcENvbnRyb2xlcilcbiAgICBFIC0tPiBCXG4gICAgRygzLiBob21lKSAtLT4gSChIb21lQ29udHJvbGxlcilcbiAgICBJKDQuIGZvbnRpKSAtLT4gSihGb250aUNvbnRyb2xsZXIpXG4gICAgSyg1LiBuZXdzKSAtLT4gTChOZXdzQ29udHJvbGxlcilcbiAgICBNKDYuIHV0ZW50aSkgLS0-IE4oVXNlckNvbnRyb2xsZXIpXG4gICAgbyg3LiBjb21tZW50aSkgLS0-IFAoQ29tbWVudGlDb250cm9sbGVyKVxuICAgICIsIm1lcm1haWQiOnsidGhlbWUiOiJkZWZhdWx0In0sInVwZGF0ZUVkaXRvciI6ZmFsc2V9)](https://mermaid-js.github.io/docs/mermaid-live-editor-beta/#/edit/eyJjb2RlIjoiZ3JhcGggVERcbiAgICBBKDEuIGxvZ2luX3ZpZXcpIC0tPiBCKERCVXRpbHMpXG4gICAgQSAtLT4gQyhFbmNyeXB0b3IpXG4gICAgQSAtLT4gRChMb2dpbkNvbnRyb2xsZXIpXG4gICAgRSgyLiBzaWdudXBfdmlldykgLS0-IENcbiAgICBFIC0tPiBGKFNpZ25VcENvbnRyb2xlcilcbiAgICBFIC0tPiBCXG4gICAgRygzLiBob21lKSAtLT4gSChIb21lQ29udHJvbGxlcilcbiAgICBJKDQuIGZvbnRpKSAtLT4gSihGb250aUNvbnRyb2xsZXIpXG4gICAgSyg1LiBuZXdzKSAtLT4gTChOZXdzQ29udHJvbGxlcilcbiAgICBNKDYuIHV0ZW50aSkgLS0-IE4oVXNlckNvbnRyb2xsZXIpXG4gICAgbyg3LiBjb21tZW50aSkgLS0-IFAoQ29tbWVudGlDb250cm9sbGVyKVxuICAgICIsIm1lcm1haWQiOnsidGhlbWUiOiJkZWZhdWx0In0sInVwZGF0ZUVkaXRvciI6ZmFsc2V9)

L’accesso all’applicazione è gestito dalle classi DButils ed Encryptor: nel primo sono presenti i metodi login e signup, mentre nel secondo sono presenti i metodi che permettono di criptare la password al momento della registrazione dell’utente e di decriptarla al momento dell’accesso dell’utente per confrontare quella digitata dall’utente con quella salvata sul database. Per criptare le password abbiamo utilizzato l’Advanced Encryption Standard, detto AES, creato nel 2001 dai belgi Joan Daemen e Vincent Rijmen. Si tratta di un algoritmo che utilizza una chiave simmetrica per criptare dei blocchi di testo. A seconda del livello di sicurezza desiderato si può scegliere tra una chiave a 128-bit, a 192-bit, o a 256-bit. Ovviamente la chiave a 256-bit è la più sicura, ma l’algoritmo di cifratura richiede anche più tempo, per questo abbiamo optato per una chiave a 128-bit.

[![](https://mermaid.ink/img/eyJjb2RlIjoiZ3JhcGggVERcbiAgICBBKE9yaWdpbmFsIG1lc3NhZ2UpIC0tPnxjb252ZXJ0IHRvIEhFWHwgQihTcGxpdCBpbnRvIGJsb2NrcylcbiAgICBCIC0tPiBDKDRjIDZmIDcyIDY1KVxuICAgIEIgLS0-IEQoNmQgMjAgNjkgNzApXG4gICAgQiAtLT4gRSg3MyA3NSA2ZCAyMClcbiAgICBDIC0tPiBGKEVhY2ggYmxvY2spXG4gICAgRCAtLT4gRlxuICAgIEUgLS0-IEZcbiAgICBGIC0tPiBHKEFFUyBhbGdvcml0aG0pXG4gICAgSChLZXkpIC0tPiBHXG4gICAgRyAtLT4gSShDaXBoZXJlZCBibG9jaylcbiAgICBJIC0tPnxuZXcgY29tcHV0ZWQga2V5fCBHXG4gICAgSSAtLT4gSih3aG9sZSBtZXNzYWdlIGNpcGhlcmVkKVxuXG4gICAgIiwibWVybWFpZCI6eyJ0aGVtZSI6ImRlZmF1bHQifSwidXBkYXRlRWRpdG9yIjpmYWxzZX0)](https://mermaid-js.github.io/docs/mermaid-live-editor-beta/#/edit/eyJjb2RlIjoiZ3JhcGggVERcbiAgICBBKE9yaWdpbmFsIG1lc3NhZ2UpIC0tPnxjb252ZXJ0IHRvIEhFWHwgQihTcGxpdCBpbnRvIGJsb2NrcylcbiAgICBCIC0tPiBDKDRjIDZmIDcyIDY1KVxuICAgIEIgLS0-IEQoNmQgMjAgNjkgNzApXG4gICAgQiAtLT4gRSg3MyA3NSA2ZCAyMClcbiAgICBDIC0tPiBGKEVhY2ggYmxvY2spXG4gICAgRCAtLT4gRlxuICAgIEUgLS0-IEZcbiAgICBGIC0tPiBHKEFFUyBhbGdvcml0aG0pXG4gICAgSChLZXkpIC0tPiBHXG4gICAgRyAtLT4gSShDaXBoZXJlZCBibG9jaylcbiAgICBJIC0tPnxuZXcgY29tcHV0ZWQga2V5fCBHXG4gICAgSSAtLT4gSih3aG9sZSBtZXNzYWdlIGNpcGhlcmVkKVxuXG4gICAgIiwibWVybWFpZCI6eyJ0aGVtZSI6ImRlZmF1bHQifSwidXBkYXRlRWRpdG9yIjpmYWxzZX0)

L’algoritmo inizialmente trasforma il testo in codice hex, e successivamente lo divide in blocchi. Poi, ogni blocco viene trasformato combinandolo insieme alla chiave. Solo per il primo blocco la chiave utilizzata sarà quella scelta da noi, mentre nei blocchi successivi la chiave utilizzata sarà calcolata direttamente dal computer. Ogni blocco di testo viene mescolato nove volte attraverso lo scambio, la moltiplicazione e lo spostamento dei bit del blocco. In questo modo si ottengono dei blocchi di testo cifrati. 

In DBUtils, inoltre, è presente il metodo che permette di cambiare schermata, _changeScene_, mantenendo nella nuova scena le impostazioni dell’utente relative alla modalità dark o light. Per la presentazione dei dati relativi alle scene 4, 5, 6 e 7 abbiamo scelto di rappresentare i dati estrapolati dal database in tabelle, per consentirne all’utente una lettura organizzata ed immediata. Per realizzarle ci siamo serviti della classe TableView di javafx, che necessita di un’ObservableList per presentare i dati nelle varie righe e colonne della tabella. ObservableList è una lista che implementa l'interfaccia Observable, permettendo di osservare se un oggetto della lista viene modificato o meno.

L’utente può cercare gli elementi digitando delle parole chiave nella barra di ricerca. Un listener percepisce, quindi, l’inserimento o la modifica del testo da parte dell’utente  tramite l’utilizzo di due funzioni lambda annidate: la prima verifica se il nuovo testo inserito sia differente da quello precedentemente inserito, e di conseguenza vengono filtrati gli elementi della tabella attraverso una FilteredList, mostrando gli elementi con _predicate_ = _true_, ovvero elementi corrispondenti alla ricerca dell’utente, mentre vengono tolti dalla lista quelli con  _predicate_ = _false_.

## Come installare il progetto

Innanzitutto installare [Temurin JDK 17](https://adoptium.net/installation/). In seguito, installare [XAMPP](https://www.apachefriends.org/download.html) e avviarlo, quindi schiacciare Start di fianco ai moduli Apache e MySQL. Successivamente accedere a http://localhost/phpmyadmin/, creare un nuovo database naboo e importare il file MySQL. Si procede aprendo il prompt dei comandi e selezionando la directory in cui è presente il file _.jar_ dell’applicazione per eseguire il comando:
- per windows:

```
java -jar nomeFile.jar
```
- per mac:
```
/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home/bin/java -Dfile.encoding=UTF-8 -jar nomeFile.jar
```
Sia il file MySQL che i file jar eseguibili si trovano all'interno della seguente [Directory](https://github.com/esthy13/Naboo/tree/main/src/main/resources/Directory).

Per interrompere l’esecuzione del programma basta chiudere l'applicazione grafica. 

Attenzione: è possibile eseguire una sola istanza del bot alla volta, pertanto se lo stesso è già in esecuzione su un altro dispositivo, è necessario modificare il codice sorgente cambiando l’indirizzo http api del bot con uno nuovo creato ad hoc, ed eseguire il programma dall’editor di codice (consigliamo l’utilizzo intellijIdea) senza usare il jar fornito.

## Avvertenze per l’utilizzo

Per accedere all'applicazione grafica di gestione del database è vivamente consigliato utilizzare il proprio username Telegram in modo da poter recuperare e/o modificare la password in futuro direttamente attraverso [@GediNabooBot](http://t.me/GediNabooBot) schiacciando su ``` 👤 profilo ``` dal menù del bot. Attenzione, però: se siete già utenti del bot telegram non potrete accedere all'applicazione grafica di gestione del database per motivi di sicurezza, dovrete perciò chiedere ad un altro amministratore dell'applicazione di essere promossi ad amministratori. Altrimenti, è possibile registrarsi con uno username qualunque, ma in questo caso non sarà possibile in alcun modo recuperare la propria password o il proprio username.

## Problemi riscontrati

Il principale problema riscontrato nella realizzazione di questo bot, è stato per noi l’ideazione del lettore RSS. Infatti, gli RSS dei giornali online sono molto diversi tra loro: non in tutti è presente la data di pubblicazione della notizia, o il link dell’immagine presente nell’articolo. Per questo, abbiamo creato 3 casi specifici, relativi agli RSS provenienti da corriere, gazzetta e fanpage. Visto che non è possibile creare un caso specifico per ogni feed RSS, oltre ad essere una tecnica poco efficiente e molto ridondante, abbiamo creato un caso default cercando di renderlo applicabile a qualunque altro feed RSS. 

Un'altro problema che ha bloccato il nostro lavoro, soprattutto nelle fasi iniziali del progetto, era dovuto all'utilizzo di librerie javafx diverse a seconda che si trattasse di un dispositivo mac o windows. Questo perché all'interno del gruppo usavamo due dispositivi mac e uno windows. La soluzione al problema è abbastanza semplice in realtà: basta eseguire un reload del progetto ogni qual volta viene eseguito un pull da github.

![Presentazione senza titolo (2)](https://user-images.githubusercontent.com/107881742/206218881-bfd1a1d9-9611-4f9f-bf6e-b06b66ee3731.svg)

## Il nostro team

Dan Cernei, Esther Giuliano e Gaetano Muscarello

## Licenza del progetto

Shield: [![CC BY-NC-SA 4.0][cc-by-nc-sa-shield]][cc-by-nc-sa]

This work is licensed under a

[Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License][cc-by-nc-sa].

[![CC BY-NC-SA 4.0][cc-by-nc-sa-image]][cc-by-nc-sa]

[cc-by-nc-sa]: http://creativecommons.org/licenses/by-nc-sa/4.0/

[cc-by-nc-sa-image]: https://licensebuttons.net/l/by-nc-sa/4.0/88x31.png

[cc-by-nc-sa-shield]: https://img.shields.io/badge/License-CC%20BY--NC--SA%204.0-lightgrey.svg
