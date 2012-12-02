<#if (currentPage < totalPages)>
    <a  ${href(currentPage+1)} class="page_next">
    	<span>下一页</span>
    </a>
<#else>
    <span class="page_next">
    	<span>下一页</span>
    </span>
</#if>