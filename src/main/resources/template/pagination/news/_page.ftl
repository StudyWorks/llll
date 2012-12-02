<#macro page page>
	<#if (page == currentPage)>    <#-->如果是当前页 <-->
	    <a><span class="current">${currentPage}</span></a>
	<#else>
	    <a  ${href(page)}><span>${page}</span></a>
	</#if>
</#macro>
    
<#function link page>
	<#assign sortAscString=sortAsc?string("true", "false")>
	<#return "${baseLink}&sortColumn=${sortColumn}&sortAsc=${sortAscString}&currentPage=${page}"/>
</#function>

<#function href page>
	<#return "href=\"${link(page)}\"" />
</#function>