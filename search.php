<?php

// make sure browsers see this page as utf-8 encoded HTML
header('Content-Type: text/html; charset=utf-8');

$limit = 10;
$query = isset($_REQUEST['q']) ? $_REQUEST['q'] : false;
$results = false;
$sortval = isset($_REQUEST['pagerank']) ? 'pageRankFile desc' : '';

if ($query)
{
  // The Apache Solr Client library should be on the include path
  // which is usually most easily accomplished by placing in the
  // same directory as this script ( . or current directory is a default
  // php include path entry in the php.ini)
  require_once('Apache/Solr/Service.php');

  // create a new solr service instance - host, port, and webapp
  // path (all defaults in this example)
  $solr = new Apache_Solr_Service('localhost', 8983,'/solr/dornsife/');

  // if magic quotes is enabled then stripslashes will be needed
  if (get_magic_quotes_gpc() == 1)
  {
    $query = stripslashes($query);
  }

  // in production code you'll always want to use a try /catch for any
  // possible exceptions emitted  by searching (i.e. connection
  // problems or a query parsing error)
  try
  {
$searchOptions = array(
'sort'=>$sortval
);

    $results = $solr->search($query, 0, $limit,$searchOptions);
  


}
  catch (Exception $e)
  {
    // in production you'd probably log or email this error to an admin
    // and then show a special message to the user but for this example
    // we're going to show the full exception
    die("<html><head><title>SEARCH EXCEPTION</title><body><pre>{$e->__toString()}</pre></body></html>");
  }
}

?>
<html>
  <head>
    <title>Search Dornsife</title>
    <link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">


	<style>
	@import "http://fonts.googleapis.com/css?family=Roboto:300,400,500,700";

.container { margin-top: 20px; }
.mb20 { margin-bottom: 20px; } 

hgroup { padding-left: 15px; border-bottom: 1px solid #ccc; }
hgroup h1 { font: 500 normal 1.625em "Roboto",Arial,Verdana,sans-serif; color: #2a3644; margin-top: 0; line-height: 1.15; }
hgroup h2.lead { font: normal normal 1.125em "Roboto",Arial,Verdana,sans-serif; color: #2a3644; margin: 0; padding-bottom: 10px; }

.search-result .thumbnail { border-radius: 0 !important; }
.search-result:first-child { margin-top: 0 !important; }
.search-result { margin-top: 20px; }
.search-result .col-md-2 { border-right: 1px dotted #ccc; min-height: 140px; }
.search-result ul { padding-left: 0 !important; list-style: none;  }
.search-result ul li { font: 400 normal .85em "Roboto",Arial,Verdana,sans-serif;  line-height: 30px; }
.search-result ul li i { padding-right: 5px; }
.search-result .col-md-7 { position: relative; }
.search-result h3 { font: 500 normal 1.375em "Roboto",Arial,Verdana,sans-serif; margin-top: 0 !important; margin-bottom: 10px !important; }
.search-result h3 > a, .search-result i { color: #248dc1 !important; }
.search-result p { font: normal normal 1.125em "Roboto",Arial,Verdana,sans-serif; } 
.search-result span.plus { position: absolute; right: 0; top: 126px; }
.search-result span.plus a { background-color: #248dc1; padding: 5px 5px 3px 5px; }
.search-result span.plus a:hover { background-color: #414141; }
.search-result span.plus a i { color: #fff !important; }
.search-result span.border { display: block; width: 97%; margin: 0 15px; border-bottom: 1px dotted #ccc; }
	</style>




    <script src="http://code.jquery.com/jquery-1.11.3.js"></script>
    <script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

    <script src="abc.js"></script>



	
  </head>
  <body>
    <form id="dornsife" accept-charset="utf-8" method="get">
      <label for="q">Search:</label>
	<div class="ui-widget">
      		<input id="search_symbol" name="q" required  type="text" value="<?php echo htmlspecialchars($query, ENT_QUOTES, 'utf-8'); ?>"/>
	  </div>
	  <input type="checkbox" name="pagerank" value="sort" <?php if(isset($_REQUEST['pagerank'])) echo 'checked'; ?>  onClick="$('#dornsife').submit();" >Sort by Page Rank<br>

      <input type="submit"/>
    </form>
<?php

// display results
if ($results)
{
  $total = (int) $results->response->numFound;
  $start = min(1, $total);
  $end = min($limit, $total);
?>
    <div class="container">

    <hgroup class="mb20">
		<h1>Search Results</h1>
		<h2 class="lead">
<strong class="text-danger"><?php echo $total; ?></strong> results were found for the search for 
<strong class="text-danger"><?php echo htmlspecialchars($query, ENT_QUOTES, 'utf-8'); ?></strong>
<h2>								
	    <div>
<strong class="text-danger"><?php


$keywords   = preg_split('#\s+#',$query, null, PREG_SPLIT_NO_EMPTY);

$result=false;

for($x = 0; $x < count($keywords); $x++)
                    {

                           $url = "http://localhost/spell/spellcheck.php?q=".$keywords[$x];
			   $jsonObj = file_get_contents($url);  
			$json = json_decode($jsonObj);

if(strcasecmp($keywords[$x].trim(),$json.trim())!=0){

$result=true;
//echo $result;	
}
$res=$res.' '.$json;       
}
if($result){ 
//echo $result;	
echo 'Did you mean?';
?>
<a id="spellcorrect" href="#" onclick="spellload('<?php echo $res.trim();?>');"><?php echo $res;?></a>


<?php }






?></strong>Results <strong class="text-danger"><?php echo $start; ?></strong> - <strong class="text-danger"><?php echo $end;?> </strong>of <strong class="text-danger"><?php echo $total; ?></strong>:
</div>
	</hgroup>
 <section class="col-xs-12 col-sm-6 col-md-12">
	<?php
  // iterate result documents

 
 foreach ($results->response->docs as $doc)
  {
?>
	
<?php 
	 $link = preg_replace('/^(\\/home\\/himica\\/dornsifedata\\/)/', 'http://dornsife.usc.edu', $doc->id);
$link=urldecode($link);

$link= str_replace(".html","",$link);

if (isset($doc->title))
  {
 $title=$doc->title;
  }
else
  {
 if (isset($doc->dc_title))
  {
 $title=$doc->dc_title;
  }
  else{
  $title='Document';
  }
  }
if (isset($doc->description))
  {
 $desc=$doc->description;
  }
else
  {
 if (isset($doc->dc_subject))
  {
 $desc=$doc->dc_subject;
  }else{

$desc='No description';
}

}  if (isset($doc->keywords))
  {
 $keywords=$doc->keywords;
  }else{
  $keywords='No keywords';
  }
  
if (isset($doc->author)){
$author=$doc->author;

}
else{
$author='No author Found';
}
if (isset($doc->url)){
$url=$doc->url;

}
else{
$url='No URL Found';
}

if (isset($doc->created)){
 $timeval=strtotime($doc->created);

      $dateval= date('Y-m-d h:i (T)  A',$timeval);
}
else{
if (isset($doc->creation_date)){
 $timeval=strtotime($doc->creation_date);

      $dateval= date('Y-m-d h:i (T)  A',$timeval);
}else{

$dateval='No date found';}}
?>
<article class="search-result row">
<div class="col-xs-12 col-sm-12 col-md-7 excerpet">
				<h3>
<a href="<?php  echo $link;?>" title="result">
				
				<?php 		echo htmlspecialchars($title, ENT_NOQUOTES, 'utf-8'); ?>

</a></h3>
							<p><b>Link:</b><?php echo $link;?><br/><b> File Type:</b>	<?php 	echo htmlspecialchars($doc->stream_content_type, ENT_NOQUOTES, 'utf-8'); ?>
&nbsp;&nbsp;&nbsp;&nbsp;<b>File Size:</b>	<?php 	echo htmlspecialchars((round(($doc->stream_size/1024),2)."KB"), ENT_NOQUOTES, 'utf-8'); ?><br> <b>Description:</b> <?php 	echo htmlspecialchars($desc, ENT_NOQUOTES, 'utf-8'); ?>		

&nbsp;&nbsp;&nbsp;&nbsp;<b>Author</b>:	<?php 	echo htmlspecialchars($author, ENT_NOQUOTES, 'utf-8'); ?>&nbsp;&nbsp;&nbsp;&nbsp;<b>Keywords:</b> <?php 	echo htmlspecialchars($keywords, ENT_NOQUOTES, 'utf-8'); ?>	
<br><b> URL:</b> <?php 	echo htmlspecialchars($url, ENT_NOQUOTES, 'utf-8'); ?> 
&nbsp;&nbsp;&nbsp;&nbsp;<b>Date</b> :<?php 	echo htmlspecialchars($dateval, ENT_NOQUOTES, 'utf-8'); ?> 
</p>	
			</div>







<span class="clearfix borda"></span>
</article>
	<?php }?>
</section>

</div> 
<?php
}
?>

  </body>
</html>