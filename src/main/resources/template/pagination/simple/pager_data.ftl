<p class="page_count">
	第<span>
<#if ( count==0)>
	0
<#else>
	${firstResult+1}
</#if>
	-
<#if (firstResult+perPage >  count)>
	${count}
<#else>
	${firstResult+perPage}
</#if>
	</span>条/共<span class="page_total">${count}</span>条
</p>
