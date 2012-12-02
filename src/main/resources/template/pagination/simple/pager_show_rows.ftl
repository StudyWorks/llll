<div class="show_rows"> 
	<strong>显示行数：</strong>
    <form action="" method="get">
        <select>
            <option value="10" <#if (10 == perPage)> selected </#if>>10</option>
            <option value="20" <#if (20 == perPage)> selected </#if>>20</option>
            <option value="50" <#if (50 == perPage)> selected </#if>>50</option>
            <option value="100" <#if (100 == perPage)> selected </#if>>100</option>
        </select>
    </form>
</div>

