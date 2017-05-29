

function createElement(user)
{
    var img = $("<img>")
            .attr("alt", "Foto Profilo")
            .attr("src", user.imageURL);
    
    var profilePic = $("<div>")
            .attr("class", "profilePic")
            .append(img)
    
    var userData = $("<div>")
    
    
    //creo l'elemento del DOM (il riquadro) contenente la rappresentazione grafica dell'utente.
    //dobbiamo restituire un div di classe user che sia incapsulato come la sidebar.
    return $("<div>")
            .attr("class","user")
            .append(profilePic)
            .append(userData)
    
}

function stateSuccess(data,state)
{
    console.log(state);
    var userListPage = $("#friendz"); //div contenente la lista utenti.
    $(userListPage).empty();
    for (var instance in data)
    {
        $(userListPage).append(createElement(data[instance]));
        
    }
}
function stateFailure(data, state){
    console.log(state);
}

function search()
{
    
}

$(document).ready(function()
{
    $("#sendSearch").click(function()
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
        
    });
});