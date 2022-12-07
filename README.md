# NABOO 

**News Aggregator and Bot Observer**

    

## Indice

### 1. Che cos'è Naboo?

### 2. Installazione

### 3. Utilizzo

### 4. Problemi riscontrati

### 5. Il nostro team

### 6. Licenza

https://user-images.githubusercontent.com/107881742/206179369-f27a659f-2db4-4f0a-8e77-2c3f36ce69de.mp4

## 1. Che cos’è Naboo?

NABOO è un’ambiente per cercare, leggere e commentare notizie provenienti da feed RSS ed è formato da tre parti: un database, il bot Telegram [@GediNabooBot](http://t.me/GediNabooBot) e un’applicazione grafica. Nel database sono immagazzinate tutte le informazioni relative alle interazioni degli utenti con le notizie e dei loro feed RSS di provenienza. [@GediNabooBot](http://t.me/GediNabooBot), invece, permette a tutti gli utenti di cercare, leggere, commentare e condividere le notizie ai propri amici e contatti tramite WhatsApp, Facebook, Twitter o Telegram stesso. Infine, l’applicazione grafica è accessibile solo agli utenti registrati come amministratori, o da utenti Telegram che sono stati promossi a loro volta ad amministratori e possono così modificare ed eliminare i dati presenti sul database, aggiungendo o cancellando le fonti di provenienza delle notizie, o eliminando commenti, notizie ed utenti indesiderati. 

Per implementare tutte le principali funzioni di NABOO abbiamo utilizzato Java, un linguaggio di programmazione ad alto livello. Il principale vantaggio derivante dal suo utilizzo è la possibilità di eseguire il codice su qualunque piattaforma: da linux a windows a mac ed android perché l’obiettivo del suo fondatore James Gosling era _“Write once, run everywhere”_. Inoltre, il codice java viene convertito in bytecode, meccanismo che lo rende illeggibile agli esseri umani e di conseguenza offre una maggiore protezione da virus. Per realizzare l’interfaccia grafica della nostra applicazione ci siamo serviti di javafx, ovvero una libreria di pacchetti grafici e media che permettono di creare e distribuire un’applicazione client multi-platform, e consente l’utilizzo di file css, che abbiamo utilizzato per definire uno stile grafico uniforme a tutte le schermate dell’applicazione. CSS, infatti, sta per Cascading Style Sheet, ovvero un foglio di testo che a cascata attribuisce le regole di stile a tutti gli elementi, selezionati in base alla loro classe di appartenenza attraverso dei selectors. Infine, per la realizzazione del database ci siamo serviti di MySQL uno dei sistemi più popolari di gestione di un database. Anche in questo caso si tratta di un sistema multi-platform, compatibile per esempio con mac, linux e windows. 

Si tratta di un database relazionale, in quanto è possibile creare relazioni fra le tabelle che rappresentano diverse entità. 

![image](https://user-images.githubusercontent.com/107881742/205910691-33735230-deea-4f3a-954a-287099eae9e9.png)

Le entità che formano il nostro database sono utente, notizia commento e fonti. In particolare gli utenti sono formati dalle seguenti proprietà: username, ruolo e password (presente solo per gli utenti amministratori). Infatti, abbiamo distinto gli utenti tra user e amministratori: gli user sono gli utenti del bot, mentre gli amministratori sono utenti autorizzati ad accedere all'applicazione grafica per gestire il database. Gli utenti del bot possono essere a loro volta promossi ad amministratori, e in questo modo possono recuperare e/o modificare la loro password per accedere all'interfaccia grafica direttamente dal bot telegram. Al momento della creazione di un commento da parte di un utente viene creata una relazione interagisce tra l’utente e la notizia. In seguito, a quella notizia viene assegnato il commento scritto dall’utente attraverso la relazione possiede. Inoltre, ogni notizia è formata da una fonte, relazione che unisce la tabella delle notizie con quella delle fonti.

Per la struttura generale del nostro progetto ci siamo basati sul pattern di progettazione MVC Model-View-Controller, anche per cercare di dividere in maniera più efficiente il lavoro all’interno del gruppo.

![Presentazione senza titolo](https://user-images.githubusercontent.com/107881742/206129496-e00f0475-4f92-4ffe-b6c2-0f802f30549a.jpg)

Nel package model si trovano le classi che definiscono le proprietà e i metodi dei principali oggetti di Naboo: _Commento.java, Fonte.java, Notizia.java e Utente.java_. Nello stesso package si trovano anche le classi che permettono di gestire il database MySQL e il LettoreRss.java. Questo si occupa della raccolta delle notizie dai feed rss scelti dagli utenti amministratori dell’applicazione per inviarle al database, da cui poi saranno inviate agli utenti in base alle loro richieste. Questo lettore rss è stato realizzato attraverso l’utilizzo della libreria rome, che permette di leggere file .xml e selezionarne i vari elementi, consentendoci così di creare degli oggetti java Notizia. Per quanto riguarda invece la sezione dei controller li abbiamo divisi in due package: _controller_ per il bot telegram e _Scene_ per l’applicazione grafica javafx. 

Il bot telegram da noi realizzato implementa l’interfaccia TelegramLongPollingBot, ovvero un bot che una volta avviato continua a fare richieste al server telegram per avere notifiche di un’eventuale update (messaggio o azione dell’utente). Nel caso la risposta sia positiva, il server telegram invia al bot un file .json contenente tutte le informazioni relative all’update organizzate in maniera gerarchica (per esempio l’id della chat, e il testo del messaggio). Per meglio organizzare i metodi del bot, sono state creati delle sottoclassi alla superclasse che implementa l’interfaccia, cercando di raggruppare i metodi per similarità: nella classe _Buttons.java_ sono presenti tutti i principali metodi associati ai bottoni e nel file _Keyboards.java_ tutte le tastiere associate ai diversi tipi di messaggi e in MyBot i comandi principali utilizzabili nel bot. Infine, nella classe Naboo si può capire, in modo quasi immediato, come vengono gestiti i vari casi di update, distinguendo tra update contenenti una callback query, e quindi derivate dalla pressione di un pulsante da parte dell’utente, da quelle dovute all’invio di un messaggio di testo all’utente. Quest’ultime sono a loro volta suddivise in comandi del bot, come /start, e risposte dell’utente. 

Per quanto riguarda i controller dell’applicazione grafica, ne è presente uno per ognuna delle sette schermate dell’applicazione. 

[![](https://mermaid.ink/img/eyJjb2RlIjoiZ3JhcGggVERcbiAgICAxLmxvZ2luX3ZpZXcgLS0-IERCVXRpbHNcbiAgICAxLmxvZ2luX3ZpZXcgLS0-IEVuY3J5cHRvclxuICAgIDEubG9naW5fdmlldyAtLT4gTG9naW5Db250cm9sbGVyXG4gICAgMi5zaWdudXBfdmlldyAtLT4gRW5jcnlwdG9yXG4gICAgMi5zaWdudXBfdmlldyAtLT4gU2lnblVwQ29udHJvbGVyXG4gICAgMi5zaWdudXBfdmlldyAtLT4gREJVdGlsc1xuICAgIDMuaG9tZSAtLT4gSG9tZUNvbnRyb2xsZXJcbiAgICA0LmZvbnRpIC0tPiBGb250aUNvbnRyb2xsZXJcbiAgICA1Lm5ld3MgLS0-IE5ld3NDb250cm9sbGVyXG4gICAgNi51dGVudGkgLS0-IFVzZXJDb250cm9sbGVyXG4gICAgNy5jb21tZW50aSAtLT4gQ29tbWVudGlDb250cm9sbGVyXG5cbiAgIiwibWVybWFpZCI6eyJ0aGVtZSI6ImRlZmF1bHQifSwidXBkYXRlRWRpdG9yIjpmYWxzZX0)](https://mermaid-js.github.io/docs/mermaid-live-editor-beta/#/edit/eyJjb2RlIjoiZ3JhcGggVERcbiAgICAxLmxvZ2luX3ZpZXcgLS0-IERCVXRpbHNcbiAgICAxLmxvZ2luX3ZpZXcgLS0-IEVuY3J5cHRvclxuICAgIDEubG9naW5fdmlldyAtLT4gTG9naW5Db250cm9sbGVyXG4gICAgMi5zaWdudXBfdmlldyAtLT4gRW5jcnlwdG9yXG4gICAgMi5zaWdudXBfdmlldyAtLT4gU2lnblVwQ29udHJvbGVyXG4gICAgMi5zaWdudXBfdmlldyAtLT4gREJVdGlsc1xuICAgIDMuaG9tZSAtLT4gSG9tZUNvbnRyb2xsZXJcbiAgICA0LmZvbnRpIC0tPiBGb250aUNvbnRyb2xsZXJcbiAgICA1Lm5ld3MgLS0-IE5ld3NDb250cm9sbGVyXG4gICAgNi51dGVudGkgLS0-IFVzZXJDb250cm9sbGVyXG4gICAgNy5jb21tZW50aSAtLT4gQ29tbWVudGlDb250cm9sbGVyXG5cbiAgIiwibWVybWFpZCI6eyJ0aGVtZSI6ImRlZmF1bHQifSwidXBkYXRlRWRpdG9yIjpmYWxzZX0)

L’accesso all’applicazione è gestito dalle classi DButils ed Encryptor: nel primo sono presenti i metodi login e signup, mentre nel secondo sono presenti i metodi che permettono di criptare la password al momento della registrazione dell’utente e di decriptarla al momento dell’accesso dell’utente per confrontare quella digitata dall’utente con quella salvata sul database. Per criptare le password abbiamo utilizzato l’Advanced Encryption Standard, detto AES, creato nel 2001 dai belgi Joan Daemen e Vincent Rijmen. Si tratta di un algoritmo che utilizza una chiave simmetrica per criptare dei blocchi di testo. A seconda del livello di sicurezza desiderato si può scegliere tra una chiave a 128-bit, a 192-bit, o a 256-bit. Ovviamente la chiave a 256-bit è la più sicura, ma l’algoritmo di cifratura richiede anche più tempo, per questo abbiamo optato per una chiave a 128-bit.

[![](https://mermaid.ink/img/eyJjb2RlIjoiZ3JhcGggVERcbiAgICBBKE9yaWdpbmFsIE1lc3NhZ2UpIC0tPnxDb252ZXJ0IHRvIEhleHwgQihTcGxpdCBpbnRvIEJsb2NrcylcbiAgICBCIC0tPkQoNGMgNmYgNzIgNjUpXG4gICAgQiAtLT5FKDZkIDIwIDY5IDcwKVxuICAgIEIgLS0-Rig3MyA3NSA2ZCAyMClcbiAgICBEIC0tPiBHXG4gICAgRSAtLT4gR1xuICAgIEYgLS0-IEcoRWFjaCBibG9jaylcbiAgICBHIC0tPiBIKEJsZW5kZXIpXG4gICAgSShLZXkpIC0tPiBIXG4gICAgSCAtLT58QWVzIEFsZ29yaXRobXxKKENpcGhlcmVkIEJsb2NrKVxuICAgIEogLS0-fG5ldyBjb21wdXRlZCBrZXl8IEhcbiAgICBKIC0tPiBLKHdob2xlIG1lc3NhZ2UgY2lwaGVyZWQpXG4gICAgICAgICIsIm1lcm1haWQiOnsidGhlbWUiOiJkZWZhdWx0In0sInVwZGF0ZUVkaXRvciI6ZmFsc2V9)](https://mermaid-js.github.io/docs/mermaid-live-editor-beta/#/edit/eyJjb2RlIjoiZ3JhcGggVERcbiAgICBBKE9yaWdpbmFsIE1lc3NhZ2UpIC0tPnxDb252ZXJ0IHRvIEhleHwgQihTcGxpdCBpbnRvIEJsb2NrcylcbiAgICBCIC0tPkQoNGMgNmYgNzIgNjUpXG4gICAgQiAtLT5FKDZkIDIwIDY5IDcwKVxuICAgIEIgLS0-Rig3MyA3NSA2ZCAyMClcbiAgICBEIC0tPiBHXG4gICAgRSAtLT4gR1xuICAgIEYgLS0-IEcoRWFjaCBibG9jaylcbiAgICBHIC0tPiBIKEJsZW5kZXIpXG4gICAgSShLZXkpIC0tPiBIXG4gICAgSCAtLT58QWVzIEFsZ29yaXRobXxKKENpcGhlcmVkIEJsb2NrKVxuICAgIEogLS0-fG5ldyBjb21wdXRlZCBrZXl8IEhcbiAgICBKIC0tPiBLKHdob2xlIG1lc3NhZ2UgY2lwaGVyZWQpXG4gICAgICAgICIsIm1lcm1haWQiOnsidGhlbWUiOiJkZWZhdWx0In0sInVwZGF0ZUVkaXRvciI6ZmFsc2V9)

L’algoritmo inizialmente trasforma il testo in codice hex, e successivamente lo divide in blocchi. Poi, ogni blocco viene trasformato combinandolo insieme alla chiave. Solo per il primo blocco la chiave utilizzata sarà quella scelta da noi, mentre nei blocchi successivi la chiave utilizzata sarà calcolata direttamente dal computer. Ogni blocco di testo viene mescolato nove volte attraverso lo scambio, la moltiplicazione e lo spostamento dei bit del blocco. In questo modo si ottengono dei blocchi di testo cifrati. 

In DBUtils, inoltre, è presente il metodo che permette di cambiare schermata, _changeScene_, mantenendo nella nuova scena le impostazioni dell’utente relative alla modalità dark o light. Per la presentazione dei dati relativi alle scene 4, 5, 6 e 7 abbiamo scelto di rappresentare i dati estrapolati dal database in tabelle, per consentirne all’utente una lettura organizzata ed immediata. Per realizzarle ci siamo serviti della classe TableView di javafx, che necessita di un’ObservableList per presentare i dati nelle varie righe e colonne della tabella. ObservableList è una lista che implementa l'interfaccia Observable, permettendo di osservare se un oggetto della lista viene modificato o meno.

L’utente può cercare gli elementi digitando delle parole chiave nella barra di ricerca. Un listener percepisce, quindi, l’inserimento o la modifica del testo da parte dell’utente  tramite l’utilizzo di due funzioni lambda annidate: la prima verifica se il nuovo testo inserito sia differente da quello precedentemente inserito, e di conseguenza vengono filtrati gli elementi della tabella attraverso una FilteredList, mostrando gli elementi con _predicate_ = _true_, ovvero elementi corrispondenti alla ricerca dell’utente, mentre vengono tolti dalla lista quelli con  _predicate_ = _false_.

## 2. Come installare il progetto

Innanzitutto installare JDK 17 o successiva. In seguito, installare XAMPP e avviarlo, quindi schiacciare Start di fianco ai moduli Apache e MySQL. Successivamente accedere a http://localhost/phpmyadmin/, creare un nuovo database naboo e importare il file MySQL. Si procede aprendo il prompt dei comandi e selezionando la directory in cui è presente il file _.jar_ dell’applicazione per eseguire il comando:

```

java -jar nomeFile.jar

```

Per interrompere l’esecuzione del programma basta chiudere l'applicazione grafica. 

Attenzione: è possibile eseguire una sola istanza del bot alla volta, pertanto se lo stesso è già in esecuzione su un altro dispositivo, è necessario modificare il codice sorgente cambiando l’indirizzo http api del bot con uno nuovo creato ad hoc, ed eseguire il programma dall’editor di codice (consigliamo l’utilizzo intellijIdea) senza usare il jar fornito.

## 3. Avvertenze per l’utilizzo

Per accedere all'applicazione grafica di gestione del database è vivamente consigliato utilizzare il proprio username Telegram in modo da poter recuperare e/o modificare la password in futuro direttamente attraverso [@GediNabooBot](http://t.me/GediNabooBot) schiacciando su |👤 profilo| dal menù del bot. Attenzione, però: se siete già utenti del bot telegram non potrete accedere all'applicazione grafica di gestione del database per motivi di sicurezza, dovrete perciò chiedere ad un altro amministratore dell'applicazione di essere promossi ad amministratori. Altrimenti, è possibile registrarsi con uno username qualunque, ma in questo caso non sarà possibile in alcun modo recuperare la propria password o il proprio username.

## 4. Problemi riscontrati

Il principale problema riscontrato nella realizzazione di questo bot, è stato per noi l’ideazione del lettore RSS. Infatti, gli RSS dei giornali online sono molto diversi tra loro: non in tutti è presente la data di pubblicazione della notizia, o il link dell’immagine presente nell’articolo. Per questo, abbiamo creato 3 casi specifici, relativi agli rss provenienti da corriere, gazzetta e fanpage. In quanto non è possibile creare un caso specifico per ogni feed rss, oltre ad essere una tecnica poco efficiente e molto ridondante, abbiamo creato un caso default cercando di renderlo applicabile a qualunque altro feed rss. 

Un'altro problema che ha bloccato il nostro lavoro, soprattutto nelle fasi iniziali del progetto, era dovuto all'utilizzo di librerie javafx diverse a seconda che si trattasse di un dispositivo mac o windows. Questo perché all'interno del gruppo usavamo due dispositivi mac e uno windows. La soluzione al problema è abbastanza semplice in realtà: basta eseguire un reload del progetto ogni qual volta viene eseguito un pull da github.

Immagine esplicativa

## 5. Team members

Dan Cernei, Esther Giuliano e Gaetano Muscarello

## 6. Licenza del progetto:

Shield: [![CC BY-NC-SA 4.0][cc-by-nc-sa-shield]][cc-by-nc-sa]

This work is licensed under a

[Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License][cc-by-nc-sa].

[![CC BY-NC-SA 4.0][cc-by-nc-sa-image]][cc-by-nc-sa]

[cc-by-nc-sa]: http://creativecommons.org/licenses/by-nc-sa/4.0/

[cc-by-nc-sa-image]: https://licensebuttons.net/l/by-nc-sa/4.0/88x31.png

[cc-by-nc-sa-shield]: https://img.shields.io/badge/License-CC%20BY--NC--SA%204.0-lightgrey.svg
