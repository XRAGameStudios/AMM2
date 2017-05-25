--Inserirò in questo file l'insieme di righe necessarie per popolare le varie tabelle.

--Creo i vari utenti del sito web.
INSERT INTO users (userID,name,surname,email,password,url,birthday,status)
VALUES (default,'Fabio','Perra','fperra@gnerd.com','123','images/user/me.jpg','06/10/1994','Keep calm and follow XRA.'),
       (default,'Orazio','Grinzosi','oraziogrinzosi@gnerd.com','123','images/user/user3.jpg','11/09/1990','Le lasagne sono buone a colazione.'),
       (default,'Flavio','Facoceri','flaviofac@gnerd.com','123','images/user/user1.jpg','13/11/1992','Perchè lavorare se posso giocare a DOTA?.'),
       (default,'Tonio','Sgrinzi','tonietto@gnerd.com','123','images/user/user4.jpg','03/01/1980','Sono un dragonborn!'),
       (default,'Lella','Cespuglia','lella@gnerd.com','123','images/user/user6.jpg','21/03/1994','Le donne ke si rasano nn sn bll'),
       (default,'Beppe','Sgrulli','beppone@gnerd.com','123','images/user/user5.jpg','24/12/1979','Android vs iOS? Meglio Symbian.'),
       (default,'Giuseppe','Simone','uomofocoso@gnerd.com','123','','11/09/1970','Siete monelle!');

--Aggiungo amicizie
INSERT INTO friends (follower, followed)
VALUES (1,2),
       (2,1),
       (5,7), 
       (7,5), 
       (5,3),   
       (3,5), 
       (6,4),
       (4,6);

--Creo ora i vari gruppi del sito.
INSERT INTO groups (groupID,name,icon,founder)
VALUES (default,'XRA Game Studios','group1',1),
       (default,'I monnezzari','group2',5),
       (default,'DOTA 2','group3',6);

--Creo i tipi di post.
INSERT INTO postType(postTypeID,name)
VALUES (default,'ONLY_TEXT'), 
       (default,'IMAGE'),
       (default,'URL');

--Creo i post fra utente ed utente
INSERT INTO userPosts (postID,content,type,author,toUser,attachment)
VALUES (default,'Il codice da vinci è stato scritto in C#.',1,1,1,''),
       (default,'Nuovo progetto XRA Game Studios! Contattatemi per maggiori informazioni.',1,1,1,''),
       (default,'Ciao Fabio, poi mandami il link del gioco, voglio acquistarlo.',2,3,1,'images/attached/buong.jpg'),     
       (default,'Voglio comprare una skin per league of legends.',1,2,2,''),
       (default,'Ciao, vendo skin di league of legends. Se ti interessa, clicca qui:',3,4,2,'http://www.mobafire.com/league-of-legends/skins'),
       (default,'Ki nn ama nn è felice!!1',1,5,5,''),
       (default,'Ciao :-) se ti levi la barba possiamo fare cose belle assieme ;-)',1,7,5,''),
       (default,'Xk mi dite tt di togliere la brba? è fikissima, skordatelo!!!!1!!1!uno',1,5,5,''),
       (default,'Chi vuole sviluppare con me per la Xbox 720?.',1,3,3,''),
       (default,'Cerco donna focosa.',1,7,7,''),
       (default,'Siete tutte monelle! MONELLE!.',1,7,7,''),
       (default,'Voglio imparare a programmare per android.',1,4,4,''),
       (default,'mA WINDOWS è SCRIVIBILE CON XCODE????????',1,6,6,'');
       
--Creo alcuni post all'interno dei gruppi      
INSERT INTO groupPosts (postID,content,type,author,toGroup,attachment)
VALUES (default,'Come siete messi col progetto?',1,1,1,''),
       (default,'Ho fatto una parte importante del gioco. Dopo te la mando.',1,6,1,''),
       (default,'Ragazzi avete scaricato overwatch?',1,5,2,''),
       (default,'No io gioco solo a lol.LOL',1,4,2,''),
       (default,'Ragazzi nuovo match su DOTA?',1,2,3,''),
       (default,'Io ci sono, aggiungimi :-D',3,3,3,'http://dota2.gamepedia.com/Link');

--creo i team
INSERT INTO teams(joiner, team)
VALUES (1,1), (6,1), (3,1), (5,2), (4,2), (2,3), (3,3), (6,3);

--creo gli amministratori (in questo caso solo uno)
INSERT INTO administrators(adminID, adminUser)
VALUES (default,1);
