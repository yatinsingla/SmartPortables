function ajax()
{
   var search=document.getElementById("text1").value;
    var xml = new XMLHttpRequest();
     xml.onreadystatechange = function()
    {
        if(xml.readyState==4 && xml.status==200){
           document.getElementById("div1").innerHTML=xml.responseText;
        }
    };      
    xml.open("GET","AutoCompleteServlet?search="+search,true);
    xml.send();
}