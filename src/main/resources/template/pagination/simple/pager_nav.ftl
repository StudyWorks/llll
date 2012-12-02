<#include "_page.ftl" />


<p class="pager_num">	 
	 <#include "_prev.ftl" />
	 <#if (totalPages<=0)>
		
	 <#elseif (totalPages < 3)>       <#-->总页数小于9的情况每页都显示 <-->
	     <#list 1..(totalPages) as pages>
			  <@page page=pages/>
	     </#list>
	 <#else>
	     <#if (currentPage == 1)>
		     <@page page=1/>
		     <#include "_points.ftl" />
		     <@page page=totalPages/>
		 <#elseif  (currentPage == 2)>
		 	 <#list 1..2 as pages>
	             <@page page=pages/>
	         </#list>
	         <#if (totalPages != 3)>
		 	 	<#include "_points.ftl" />
		 	 </#if>
		 	 <@page page=totalPages/>
		 <#elseif  (currentPage > 2 && currentPage <(totalPages-1))>
		 	 <@page page=1/>
		 	 <#include "_points.ftl" />
		 	 <@page page=currentPage/>
		 	 <#include "_points.ftl" />
		 	 <@page page=totalPages/>
		 <#elseif  (currentPage == totalPages-1)>
		 	 <@page page=1/>
		 	 <#include "_points.ftl" />
		 	 <@page page=totalPages-1/>
		 	 <@page page=totalPages/>
		  <#elseif  (currentPage == totalPages)>
		 	 <@page page=1/>
		 	 <#include "_points.ftl" />
		 	 <@page page=totalPages/>
	     </#if>
	 <#-->
	     <#if (currentPage <= 2)>
	         <#list 1..2 as pages>
	             <@page page=pages/>
	         </#list>
	         <#if (currentPage==2)>
	             <@page page=3/>
	         </#if>
	         <#include "_points.ftl" />
			<@page page=totalPages/>
	     <#elseif (currentPage > 2 && currentPage < (totalPages-2))>
	      	 <@page page=1/>
	         <#include "_points.ftl" />
	         <#list (currentPage)..(currentPage+1) as pages>
	             <@page page=pages/>
	         </#list>
	         <#include "_points.ftl" />
	         <@page page=totalPages/>
	     <#elseif currentPage == totalPages-2>
	      	 <@page page=1/>
			 <#include "_points.ftl" />
	         <#list (totalPages-2)..(totalPages) as pages>
	             <@page page=pages/>
	         </#list>
	     <#elseif currentPage == totalPages-1>
	      	 <@page page=1/>
			 <#include "_points.ftl" />
	         <#list (totalPages-1)..(totalPages) as pages>
	             <@page page=pages/>
	         </#list>
	     <#else>
	         <@page page=1/>
			 <#include "_points.ftl" />
			 <#if (currentPage==totalPages)>
	             <@page page=totalPages/>
	         </#if>
	     </#if>
	 <-->    
	    
	 </#if>   
	<#include "_next.ftl" />
</p>