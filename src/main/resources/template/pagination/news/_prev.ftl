<#if (currentPage > 1)>
<a ${href(1)}>
	<span class="first">◄|</span>
</a>
</#if>
<#if (currentPage > 1)>
    <a ${href(currentPage-1)} >
    	<span class="long">◄ prev</span>
    </a>
</#if>  