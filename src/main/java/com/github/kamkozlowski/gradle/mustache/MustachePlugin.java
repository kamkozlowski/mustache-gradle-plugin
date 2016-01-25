package com.github.kamkozlowski.gradle.mustache;

import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

public class MustachePlugin implements org.gradle.api.Plugin<Project> {

    private static Logger LOG = Logging.getLogger(MustachePlugin.class);

    @Override
    public void apply(Project target) {
        LOG.debug("Mustache plugin configuration");
        target.getExtensions().create("mustache", MustacheExtension.class);
        target.getTasks().create("compileTemplate", CompileTemplateTask.class);
    }
}