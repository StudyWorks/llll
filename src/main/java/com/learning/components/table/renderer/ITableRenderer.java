package com.learning.components.table.renderer;

import com.learning.components.table.HtmlBuilder;
import com.learning.components.table.tags.TableTag;

public interface ITableRenderer {
	void render(HtmlBuilder html, TableTag table);
}
