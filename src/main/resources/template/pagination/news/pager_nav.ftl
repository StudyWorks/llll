<#include "_page.ftl" />


 <#include "_prev.ftl" />
 <#if (totalPages < 1)>

 <#elseif (totalPages == 1)>	
 	<@page page=1/>
 <#elseif (totalPages < 3)>       <#-->总页数小于9的情况每页都显示 <-->
     <#list 1..(totalPages) as pages>
		  <@page page=pages/>
     </#list>
 <#else>
     <#if (currentPage <= 3)>
		     <#list 1..(currentPage+1) as pages>
	             <@page page=pages/>
	         </#list>
	         <#include "_points.ftl" />
		     <@page page=totalPages/>
		 <#elseif  (currentPage > 3 && currentPage <(totalPages-2))>
		 	 <@page page=1/>
		 	 <#include "_points.ftl" />
		 	 <#list (currentPage-1)..(currentPage+1) as pages>
	             <@page page=pages/>
	         </#list>
		 	 <#include "_points.ftl" />
		 	 <@page page=totalPages/>
		 <#elseif  (currentPage >= totalPages-2)>
		 	 <@page page=1/>
		 	 <#include "_points.ftl" />
		 	 <#list (currentPage-1)..totalPages as pages>
	             <@page page=pages/>
	         </#list>
	     </#if>
 </#if>   
<#include "_next.ftl" />
