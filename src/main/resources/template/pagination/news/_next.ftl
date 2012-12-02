<#if (currentPage < totalPages)>
    <a  ${href(currentPage+1)} >
    	<span class="long">next ►</span>
    </a>
</#if>
<#if (currentPage < totalPages)>
<a ${href(totalPages)}>
	<span class="last">|►</span>
</a>
</#if>