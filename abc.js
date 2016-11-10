function spellload(x){
$("#search_symbol").val(x.trim());
$('#dornsife').submit();


}

 $(function() {
 var URL_PREFIX = "http://localhost:8983/solr/dornsife/suggesthandler?q=";
 var URL_SUFFIX = "&wt=json";
 $("#search_symbol").autocomplete({
 source : function(request, response) {

 var URL = URL_PREFIX + $("#search_symbol").val() + URL_SUFFIX;
 $.ajax({
 url : URL,
 success : function(data) {
console.log(data);
if(data.suggest.mySuggester[$("#search_symbol").val()]){
 var docs = JSON.stringify(data.suggest.mySuggester[$("#search_symbol").val()].suggestions);
 var jsonData = JSON.parse(docs);
console.log(jsonData);
 response($.map(jsonData, function(item) {
 return {
 label : item.term,
     value: item.term
 }
 }));}
 },
 dataType : 'jsonp',
 jsonp : 'json.wrf' });},
 select: function (event, ui) {
console.log("select");
        if (ui.item.label) {
          $("#search_symbol").val(ui.item.label);
	$('#dornsife').submit();
        }


 }
 ,
 minLength : 1

 });
  });
