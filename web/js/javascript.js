
function createElement(user)
{
    var p = $("<p>")
            .attr("class","user")
            .html(user.fullname)
    
    var img = $("<img>")
            .attr("alt", "Foto di " + user.fullname )
            .attr("class", "profile sidebar")
            .attr("src", user.imageURL);
    var ahref =$("<a>")
            .attr("href", "bacheca.html&user=" + user.ID)
            .append(p);
    
    var userDiv = $("<div>")
            .append(img)
            .append(ahref)
    var li = $("<li>")
            .append(userDiv);
    //creo l'elemento del DOM (il riquadro) contenente la rappresentazione grafica dell'utente.
    //restituirò una <li> contenente l'utente.
    return li;
}

function createErrorMessage()
{
    var p = $("<p>")
            .html("Nessun risultato trovato.");
    
    
    var userDiv = $("<div>")
            .append(p)
    var li = $("<li>")
            .append(userDiv);
    //creo l'elemento del DOM (il riquadro) contenente la rappresentazione grafica dell'utente.
    //restituirò una <li> contenente l'utente.
    return li;
}




function stateSuccess(data,state)
{
    console.log(state);
    //vado a modificare la <ul> contenente i risultati.
    var userListPage = $("#friendz"); 
    //la svuoto dei suoi contenuti.
    $(userListPage).empty();
    
    if (data.length ===0)
    {
        $(userListPage).append(createErrorMessage());
    }
    else
    {
        for (var instance in data)
        {
            $(userListPage).append(createElement(data[instance]));
            
        }
    }
}
function stateFailure(data, state){
    console.log(state);
    
}

//funzione richiamata in modo identico fra i due eventi.
function search()
{
    // leggo il contenuto nel textbox di ricerca
    var name = $("#search")[0].value;
    //restituisco un file json 
    $.ajax({
        url: "filter.json",
        data:{
            cmd:"search",
            q: name},
        dataType:"json",
        success:function(data,state){
            stateSuccess(data,state);
        },
        error:function(data,state){
            stateFailure(data,state);
        }
        
    });
    
}


$(document).ready(function()
{
    //poichè la milestone richiede anche l'evento alla pressione del tasto.
    //L'evento keyup è migliore di keypress in quanto la ricerca viene eseguita a tasto rilasciato.
    $("#search").keyup(function()
    {
       search(); 
    });
    //in alternativa ottengo lo stesso comportamento (anche se ridondante) al click del bottone.
    $("#sendSearch").click(function()
    {
        search();
    });
});