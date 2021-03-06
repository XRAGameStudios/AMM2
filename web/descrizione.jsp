<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
    <c:set var="title" value="FAQ - Descrizione del social" scope="request"/>
    <jsp:include page="head.jsp"/>
    <body>
        <div class="page">
            <jsp:include page="header.jsp"/>
            <c:set var="page" value="descrizione" scope="request"/>
            <jsp:include page="navbar.jsp"/>
            <c:if test="${logged=='true'}"> 
                <jsp:include page="aside.jsp"/>    
            </c:if>
            <div class="content" <c:if test="${logged=='false'}"> id="login_content" </c:if> >
                <div id="summary">
                    <h2>Sommario</h2>
                    <!--Poichè con la rebase dell'url i link fragment non funzionano, ho aggiunto descrizione.html
                        in ciascuno dei link fragment. In tal modo, tutto funziona correttamente.-->
                    <ul>
                        <li><a href="descrizione.html#aboutUs">Cosa è Nerdbook?</a></li>
                        <li><a href="descrizione.html#howItWorks">Come funziona Nerdbook?</a></li>
                        <li><a href="descrizione.html#howMuchCosts">Devo pagare qualcosa per iscrivermi?</a>
                            <ul>
                                <li><a href="descrizione.html#stopRennove">È possibile recedere dal rinnovo?</a>
                            </ul>
                        </li>
                        <li><a href="descrizione.html#projectAuthor">Chi è il creatore del progetto?</a></li>
                        <li><a href="descrizione.html#projectBorn">Come è nato il progetto?</a></li>
                    </ul>
                </div>
                
                <div id="answers">
                    <h2>Risposte</h2>
                    <div class="faq">
                        <a id="aboutUs">
                            <h3>Cosa è NerdBook?</h3>
                        </a>
                        <p>Nerdbook è una nuova piattaforma social per trovare facilmente un team per lo sviluppo di software e
                            giochi indipendenti. NerdBook nasce per la necessità di trovare un insieme di persone disposte a
                            creare una startup di un'idea in comune.</p>
                        <p>Sei uno sviluppatore e stai cercando nuovi collaboratori? Hai una grande idea e vuoi condividerla con
                            altre persone per riuscire a realizzarla? Hai un sogno nel cassetto e vuoi portarlo avanti sino alla
                            fine?</p>
                        <p>Questo è il posto giusto per te.</p>
                    </div>
                    <div class="faq">
                        <a id="howItWorks">
                            <h3>Come funziona Nerdbook?</h3>
                        </a>
                        <ol>
                            <li>Accedi o registrati al sito tramite <a href="login.html">questo link.</a></li>
                            <li>Crea il tuo primo post e fai sapere a tutti il progetto che vuoi fare (o quello a cui vuoi partecipare).</li>
                            <li>Indica le tue competenze nel tuo profilo personale.</li>
                            <li>Aggiungi o invita gli amici che sono interessati alla tua idea.</li>
                            <li>Fai delle nuove conoscenze e partecipa ad un team, o crealo da zero!</li>
                            <li>Aggiorna costantemente la tua bacheca con i tutti i lavori svolti con il tuo team.</li>
                        </ol>
                    </div>
                    <div class="faq">
                        <a id="howMuchCosts">
                            <h3>Devo pagare qualcosa per iscrivermi?</h3>
                        </a>
                        <p>No. Nerdbook è una piattaforma gratuita aperta a tutti e non vi è alcun costo di iscrizione.</p>
                        <p>Vi è tuttavia un piccolo costo di creazione di un gruppo per i team composti da un insieme di
                            <strong>5</strong> o più persone. Tale costo permetterà alla piattaforma di rimanere aperta, e costa
                            meno di un euro a testa.</p>
                        <p>Il costo di creazione del gruppo (che si rinnoverà ogni anno) sarà di <strong>3 euro\anno</strong>. Il
                            rinnovo verrà applicato dopo un anno dalla creazione del gruppo.</p>
                        <div>
                            <a id="stopRennove">
                                <h4>È possibile recedere dal rinnovo?</h4>
                            </a>
                            <p>In qualsiasi momento è possibile bloccare il rinnovo. Ciò si può fare in due modi:</p>
                            <ol>
                                <li>Eliminando il gruppo dall'apposito tasto "Elimina".</li>
                                <li>Bloccando il rinnovo automatico dalle impostazioni del gruppo.</li>
                            </ol>
                            <p>In caso di annullamento del rinnovo, il giorno prima della data prevista per il rinnovo verrà bloccata
                                la possibilità di scrivere nel gruppo. Tutti i post scritti all'interno dei gruppi rimarranno
                                comunque salvati all'interno del gruppo stesso e non verranno cancellati.</p>
                        </div>
                    </div>
                    <div class="faq">
                        <a id="projectAuthor">
                            <h3>Chi è il creatore di questo progetto?</h3>
                        </a>
                        <p>Il progetto è stato ideato e creato nel 2017 da Fabio Perra.</p>
                    </div>
                    <div class="faq">
                        <a id="projectBorn">
                            <h3>Come è nato questo progetto?</h3>
                        </a>
                        <p>Il progetto è nato da una prima idea portata avanti come progetto Kickstarter, alla quale sono stati
                            finanziati oltre 10 mila dollari per portare questo sito alla luce. Dopo un primo anno di sviluppo,
                            NerdBook è stato creato ed ora è usato da oltre mezzo milione di persone.
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </body>
    
</html>