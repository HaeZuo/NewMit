package com.haezuo.newmit.config.layout;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class SiteMeshFilter extends ConfigurableSiteMeshFilter {

    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder
                // Map decorators to path patterns.
                .addDecoratorPath("/login", 	"defaultLayout.jsp")
                .addDecoratorPath("/*", 		"defaultLayout.jsp")
                // Exclude path from decoration.
                .addExcludedPath("/html/*")
                .addExcludedPath(".json")
                .setMimeTypes("text/html");
    }
}

